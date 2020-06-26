(ns collatz-conjecture)

(defn next-collatz [num]
  (if (even? num)
    (/ num 2)
    (+ 1 (* num 3))))

(defn get-sequence [num]
  (if (= 1 num)
    '()
    (let [n (next-collatz num)]
      (conj (get-sequence n) n))))

(defn collatz [num]
  (->> num
       get-sequence
       count))
