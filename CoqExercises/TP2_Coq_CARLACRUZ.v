Require Import List.
Require Import ZArith.
Require Import Nat.


Set Implicit Arguments.

Print list.
Print app.

Locate "::".
Locate "++".

Section Ex3.

Variable t : Type.

Fixpoint sum (l:list nat) : nat :=
    match l with
      | nil => 0
      | cons x y => x + (sum y)
    end.

Theorem ex3_1 : forall l1 l2 : list nat, sum (l1 ++ l2) = sum l1 + sum l2.
Proof.
  intros.
  induction l1.
  simpl.
  reflexivity.
  simpl.
  rewrite IHl1.
  SearchRewrite (_ + (_ + _)). 
  rewrite Plus.plus_assoc_reverse.
  reflexivity.
Qed.

Theorem ex3_2 : forall (A: Type) (l: list A), length (rev l) = length l.
Proof.
  Proof.
  intros.
  induction l.
  simpl. reflexivity.
  simpl. rewrite app_length.
  rewrite IHl.
  apply Nat.add_comm.
Qed.


Theorem ex3_3 : forall (A B:Type) (f:A->B) (l1 l2:list A), (map f l1)++(map f l2) = map f (l1++l2).
Proof.
  intros.
  induction l1.
  simpl.
  reflexivity.
  simpl.
  rewrite IHl1.
  reflexivity.
Qed.

Theorem ex3_4 : forall (A B: Type) (f: A -> B) (l: list A), rev (map f l) = map f (rev l).
Proof.
  intros.
  induction l.
  simpl.
  reflexivity.
  simpl.
  rewrite IHl.
  Search map.
  rewrite map_app.
  reflexivity.
Qed.

End Ex3.

Section Ex4.


Inductive In (A: Type) (y: A) : list A -> Prop :=
  | InHead : forall xs: list A, In y (cons y xs)
  | InTail : forall (x: A) (xs: list A), In y xs -> In y (cons x xs).

(*LEMMAS AUXILIARES PARA O TPC E A SUA PROVA*)

Theorem aux1 : forall (A:Type) (l1 l2: list A) (x:A), In x l1 \/ In x l2 -> In x (l1 ++ l2).
Proof.
  intros A l1 l2 x H.
  destruct H.
  induction H.
  simpl. apply InHead.
  simpl. apply InTail. assumption.
  induction l1.
  simpl. assumption.
  apply InTail. 
  assumption.
Qed.

Theorem aux2: forall (A: Type) (x: A) (l1 l2: list A), x :: l1 = x :: l2 <-> l1 = l2.
Proof.
  red.
  split.
  intros.
  inversion H.
  reflexivity.
  intros.
  inversion H.
  reflexivity.
Qed.

Theorem aux3: forall (A: Type) (x a: A) (l: list A), In x (a :: l) <-> x = a \/ In x l.
Proof.
  red. split.
  intros.
  inversion H.
  left. reflexivity.
  right. apply H1.
  intros.
  destruct H.
  rewrite H. apply InHead.
  apply InTail. apply H.
Qed.

Theorem ex4_1: forall (A: Type) (x: A) (l: list A), In x l -> In x (rev l).
Proof.
  intros.
  induction H.
  simpl. apply aux1. right. apply InHead.
  simpl. apply aux1. left. assumption.
Qed.

Theorem ex4_2: forall (A B: Type) (y: B) (f: A -> B) (l: list A), In y (map f l) -> exists x, In x l /\ y = f x.
Proof.
  intros.
  induction l.
  simpl in H. inversion H.
  simpl in H. apply aux3 in H. destruct H.
  exists a. split.
  constructor.
  apply H.
  apply IHl in H.
  destruct H. destruct H.
  exists x. split.
  apply aux3. right. apply H.
  apply H0.
Qed.


Theorem ex4_3:forall (A:Type) (x:A) (l : list A), In x l -> exists l1, exists l2, l = l1 ++ (x::l2).
Proof.
  intros.
  induction l.
  inversion H.
  apply aux3 in H.
  destruct H.
  rewrite H. 
  exists nil. exists l. rewrite app_nil_l.
  reflexivity.
  apply IHl in H. destruct H as [l1 H]. destruct H as [l2 H].
  exists (a::l1). exists l2. simpl.
  apply aux2. 
  apply H.
Qed.

End Ex4.

Section Ex5.

Inductive Prefix (A: Type) : list A -> list A -> Prop :=
  | PreNil : forall l: list A, Prefix nil l
  | PreCons : forall (x: A) (l1 l2: list A),
                  Prefix l1 l2 -> Prefix (x::l1) (x::l2).


Theorem ex5_1: forall (A: Type) (l1 l2: list A), Prefix l1 l2 -> length l1 <= length l2.
Proof.
  intros.
  induction H.
  simpl.
  Search le.
  apply Peano.le_0_n.
  simpl.
  apply le_n_S.
  assumption.
Qed.

Theorem ex5_2: forall l1 l2, Prefix l1 l2 -> sum l1 <= sum l2.
Proof.
  intros.
  induction H.
  simpl.
  apply Peano.le_0_n.
  simpl.
  Search (_+_ <= _+_).
  apply PeanoNat.Nat.add_le_mono_l.
  assumption.
Qed.


Theorem ex5_3: forall (A:Type) (l1 l2:list A) (x:A), (In x l1) /\ (Prefix l1 l2) -> In x l2.
Proof.
  intros.
  destruct H.
  induction H0.
  inversion H.
  apply aux3 in H.
  destruct H.
  apply aux3. left. assumption.
  apply aux3. right. apply IHPrefix. 
  assumption. 
Qed.

End Ex5.

Section Ex6.


Inductive SubList (A:Type) : list A -> list A -> Prop :=
| SLnil : forall l:list A, SubList nil l
| SLcons1 : forall (x:A) (l1 l2:list A), SubList l1 l2 -> SubList (x::l1) (x::l2)
| SLcons2 : forall (x:A) (l1 l2:list A), SubList l1 l2 -> SubList l1 (x::l2).

Theorem ex6_1: forall (A:Type)(l1 l2 l3 l4:list A), SubList l1 l2 -> SubList l3 l4 -> SubList (l1++l3) (l2++l4).
Proof.
  intros.
  induction H.
  simpl. induction l.
  simpl. apply H0.
  simpl. apply SLcons2. assumption.
  simpl. apply SLcons1. assumption.
  simpl. apply SLcons2. assumption.
Qed.


Theorem ex6_2: forall (A:Type) (l1 l2:list A), SubList l1 l2 -> SubList (rev l1) (rev l2).
Proof.
  intros.
  induction H.
  simpl. 
  apply SLnil.
  simpl. 
  apply ex6_1.
  apply IHSubList.
  apply SLcons1. 
  apply SLnil. 
  simpl.
  replace (rev l1) with (rev l1 ++ nil).
  apply ex6_1.
  apply IHSubList.
  apply SLnil.
  Search (_ ++ nil).
  apply app_nil_r.
Qed.

Theorem ex6_3: forall (A:Type) (x:A) (l1 l2:list A), Prefix l1 l2 -> SubList l1 l2.
Proof.
  intros.
  induction H.
  apply SLnil.
  apply SLcons1. 
  apply IHPrefix.
Qed.


Theorem ex6_4: forall (A:Type) (x:A) (l1 l2:list A), SubList l1 l2 -> In x l1 -> In x l2.
Proof.
  intros.
  generalize H0. clear H0.
  induction H.
  intros. inversion H0.
  intros. 
  apply aux3. apply aux3 in H0.
  destruct H0 as [H1|H2].
  left. 
  apply H1.
  right. apply IHSubList. apply H2.
  intros. 
  apply aux3. right.
  apply IHSubList. apply H0.
Qed.

End Ex6.









