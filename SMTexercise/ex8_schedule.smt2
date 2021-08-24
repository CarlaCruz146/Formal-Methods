(set-logic QF_UFLIA)

(declare-fun d11 () Int)
(declare-fun d12 () Int)
(declare-fun d21 () Int)
(declare-fun d22 () Int)
(declare-fun d31 () Int)
(declare-fun d32 () Int)

(declare-fun t11 () Int)
(declare-fun t12 () Int)
(declare-fun t21 () Int)
(declare-fun t22 () Int)
(declare-fun t31 () Int)
(declare-fun t32 () Int)
(declare-fun max () Int)

;; Precedence
(assert (>= t12 (+ t11 d11)))
(assert (>= t22 (+ t21 d21)))
(assert (>= t32 (+ t31 d31)))

;; Resource
(assert (or (>= t11 (+ t21 d21)) (>= t21 (+ t11 d11))))
(assert (or (>= t12 (+ t22 d22)) (>= t22 (+ t12 d12))))
(assert (or (>= t11 (+ t31 d31)) (>= t31 (+ t11 d11))))
(assert (or (>= t12 (+ t32 d32)) (>= t32 (+ t12 d12))))
(assert (or (>= t21 (+ t31 d31)) (>= t31 (+ t21 d21))))
(assert (or (>= t22 (+ t32 d32)) (>= t32 (+ t22 d22))))


;; The start time of the first task of every job i must
;; be greater then or equal to zero
(assert (>= t11 0))
(assert (>= t21 0))
(assert (>= t31 0))


;; The end time of the last task must be less than or
;; equal to maximum
(assert (<= (+ t12 d12) max))
(assert (<= (+ t22 d22) max))
(assert (<= (+ t32 d32) max))


;; Inputs
(assert (= d11 2))
(assert (= d12 1))
(assert (= d21 3))
(assert (= d22 1))
(assert (= d31 2))
(assert (= d32 3))


;; Podemos fazer todos os trabalhos com max = 10? - sat
(push)
(assert (= max 10))
(check-sat) 
(get-value (max t11 t12 t21 t22 t31 t32))
(pop)

;; Ainda é possível fazer todos os trabalhos em menos de 10 unidades de tempo? - sat
(push)
(assert (< max 10))
(check-sat) 
(get-value (max t11 t12 t21 t22 t31 t32))
(pop)

;; Ainda é possível em 8 unidades de tempo? - sat
(push)
(assert (= max 8))
(check-sat) 
(get-value (max t11 t12 t21 t22 t31 t32))
(pop)

;; Ainda é possível em menos de 8 unidades de tempo? - unsat
(push)
(assert (< max 8))
(check-sat)
(pop)
