(ns perfect-numbers)

(defn- factors [n]
  (filter #(zero? (rem n %)) (range 1 n)))

(defn classify [n]
  (when-not (pos? n) (throw (IllegalArgumentException.)))
  (let [aliquot (reduce + (factors n))]
    (condp apply [aliquot n]
      = :perfect
      > :abundant
      :deficient)))
