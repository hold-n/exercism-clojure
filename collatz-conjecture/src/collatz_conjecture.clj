(ns collatz-conjecture)

(defn get-sequence [num]
  (defn next-collatz [num]
    (if (even? num)
      (/ num 2)
      (+ 1 (* num 3)))
  )
  (if (= 1 num)
    '()
    (do
      (def n (next-collatz num))
      (conj (get-sequence n) n)
    )
  )
)

(defn collatz [num]
  (count (get-sequence num))
)
