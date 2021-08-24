{-# LANGUAGE FlexibleInstances #-}
module Adventurers where

import DurationMonad

-- The list of adventurers
data Adventurer = P1 | P2 | P5 | P10 deriving (Show,Eq)
-- Adventurers + the lantern
type Objects = Either Adventurer ()

-- The time that each adventurer needs to cross the bridge
getTimeAdv :: Adventurer -> Int
getTimeAdv P1 = 1
getTimeAdv P2 = 2
getTimeAdv P5 = 5
getTimeAdv P10 = 10

getAdv :: Objects -> Adventurer
getAdv (Left a) = a

-- The time that a group of adventurers needs to cross the bridge
time2cross :: [Objects] -> Int
time2cross l = maximum $ map (getTimeAdv . getAdv) l

{-- The state of the game, i.e. the current position of each adventurer
+ the lantern. The function (const False) represents the initial state
of the game, with all adventurers and the lantern on the left side of
the bridge. Similarly, the function (const True) represents the end
state of the game, with all adventurers and the lantern on the right
side of the bridge.  --}
type State = Objects -> Bool

instance Show State where
  show s = (show . (fmap show)) [s (Left P1),
                                 s (Left P2),
                                 s (Left P5),
                                 s (Left P10),
                                 s (Right ())]

instance Eq State where
  (==) s1 s2 = and [s1 (Left P1) == s2 (Left P1),
                    s1 (Left P2) == s2 (Left P2),
                    s1 (Left P5) == s2 (Left P5),
                    s1 (Left P10) == s2 (Left P10),
                    s1 (Right ()) == s2 (Right ())]

-- The initial state of the game
gInit :: State
gInit = const False

-- The desired state of the game
gEnd :: State
gEnd = const True

-- Changes the state of the game for a given object
changeState :: Objects -> State -> State
changeState a s = let v = s a in (\x -> if x == a then not v else s x)

-- Changes the state of the game of a list of objects 
mChangeState :: [Objects] -> State -> State
mChangeState os s = foldr changeState s os

-- Returns the list of adventurers who can cross to the opposite side of the lantern 
whoCanCross :: State -> [Objects]
whoCanCross s = filter (\x -> (s x) == (s (Right ()))) [Left P1, Left P2, Left P5, Left P10]

-- Returns all combinations with one or two elements of the list
allComb :: [a] -> [[a]]
allComb [] = []
allComb (x:xs) = [[x]] ++ map (\y -> [x,y]) xs ++ allComb xs

{-- For a given state of the game, the function presents all changes
that can be applied to the state together with the time that adventurers 
take to cross the bridge in that change. --}
allChangeState :: State -> ListDur (State -> State)
allChangeState s = LD $ map (\l -> Duration(time2cross l, mChangeState (l ++ [Right()]))) (allComb . whoCanCross $ s)

{-- For a given state of the game, the function presents all the
possible moves that the adventurers can make.  --}
allValidPlays :: State -> ListDur State
allValidPlays s = (allChangeState s) <*> (return s)

{-- For a given number n and initial state, the function calculates
all possible n-sequences of moves that the adventures can make --}
-- To implement 
exec :: Int -> State -> ListDur State
exec 0 s = return s
exec n s = let l = exec (n-1) s in
   l >>= allValidPlays

check :: Int -> Int -> ListDur State -> Bool
check 0 t l = False
check n t (LD []) = False
check n t l = let l' = remLD $ l >>= allValidPlays in 
   if any (\x -> (getDuration x <= t) && (getValue x == gEnd)) l'
      then True
      else check (n-1) t $ LD $ filter (\x -> (getDuration x <= t)) l'

{-- Is it possible for all adventurers to be on the other side
in <=17 min and not exceeding 5 moves ? --}
leq17 :: Bool
leq17 = check 5 17 $ return gInit

check2 :: Int -> ListDur State -> Bool
check2 t (LD []) = False
check2 t l = let l' = remLD $ l >>= allValidPlays in 
   if any (\x -> (getDuration x < t) && (getValue x == gEnd)) l'
      then True
      else check2 t $ LD $ filter (\x -> (getDuration x < t)) l'

{-- Is it possible for all adventurers to be on the other side
in < 17 min ? --}
-- To implement
l17 :: Bool
l17 = check2 17 $ return gInit

--------------------------------------------------------------------------
{-- Implementation of the monad used for the problem of the adventurers.
Recall the Knight's quest --}

data ListDur a = LD [Duration a] deriving Show

remLD :: ListDur a -> [Duration a]
remLD (LD x) = x

instance Functor ListDur where
   fmap f = let f' = fmap f in
      LD . (map f') . remLD

instance Applicative ListDur where
   pure x = LD [pure x]
   l1 <*> l2 = LD $ do x <- remLD l1
                       y <- remLD l2
                       g(x,y) where
                         g(x,y) = return $ x <*> y

instance Monad ListDur where
   return = pure
   l >>= k = LD $ do x <- remLD l
                     g x where
                        g(Duration(a,b)) = let u = (remLD (k b)) in map (wait a) u

manyChoice :: [ListDur a] -> ListDur a
manyChoice = LD . concat . (map remLD)

--------------------------------------------------------------------------
{-- Implementation of the monad used for the problem of the adventurers, 
but with a log of moves. --}

data LogListDur a = LLD [(String, Duration a)] deriving Show

remLLD :: LogListDur a -> [(String, Duration a)]
remLLD (LLD x) = x

instance Functor LogListDur where
   fmap f = let f' = \(s,x) -> (s, fmap f x) in
      LLD . (map f') . remLLD

instance Applicative LogListDur where
   pure x = LLD [([], pure x)]
   l1 <*> l2 = LLD $ do x <- remLLD l1
                        y <- remLLD l2
                        g(x,y) where
                           g((s1, d1),(s2, d2)) = return (s1 ++ s2, d1 <*> d2)

instance Monad LogListDur where
   return = pure
   l >>= k = LLD $ do x <- remLLD l
                      g x where
                        g((s,Duration(a,b))) = let u = (remLLD (k b)) in map (\(s',x) -> (s ++ s', wait a x)) u

getMovesLog :: (String, Duration a) -> String
getMovesLog (s,d) = s

getMoveStr :: State -> [Objects] -> String
getMoveStr s l
   | s $ Right () = "<-" ++ (show $ map (\o -> getAdv o) l) ++ "\r\n"
   | otherwise    = "->" ++ (show $ map (\o -> getAdv o) l) ++ "\r\n"

{-- For a given state of the game, the function presents all changes
that can be applied to the state together with the time that adventurers 
take to cross the bridge in that change and the move log. --}
lallChangeState :: State -> LogListDur (State -> State)
lallChangeState s = LLD $ map (\l -> (getMoveStr s l, Duration(time2cross l, mChangeState (l ++ [Right()])))) (allComb . whoCanCross $ s)

{-- For a given state of the game, the function presents all the
possible moves that the adventurers can make.  --}
lallValidPlays :: State -> LogListDur State
lallValidPlays s = (lallChangeState s) <*> (return s)

{-- For a given number n and initial state, the function calculates
all possible n-sequences of moves that the adventures can make --}
lexec :: Int -> State -> LogListDur State
lexec 0 s = return s
lexec n s = let l = lexec (n-1) s in 
   l >>= lallValidPlays

{-- For a given initial state, the function finds the first solution
for all adventurers to be on the other side in <= 17 and returns a
string with all the moves made by the adventurers --}
find :: LogListDur State -> String
find l = let l' = remLLD $ l >>= lallValidPlays in 
   if any (\(s,x) -> (getDuration x <= 17) && (getValue x == gEnd)) l'
      then getMovesLog $ head $ filter (\(s,x) -> (getDuration x <= 17) && (getValue x == gEnd)) l'
      else find $ LLD $ filter (\(s,x) -> (getDuration x < 17)) l'

test :: IO ()
test = putStr $ find $ return gInit