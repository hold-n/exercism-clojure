(ns armstrong-numbers)

(defn- get-digits [num]
  (loop [ret []
         ^long n num]
    (if (= n 0) ret
        (recur (conj ret (rem n 10)) (quot n 10)))))

(defn- int-power [base exp]
  (reduce * (repeat exp base)))

(defn armstrong? [num]
  (let [digits (get-digits num)
        ndigits (count digits)]
    (->> (map #(int-power % ndigits) digits)
         (apply +)
         (= num))))
