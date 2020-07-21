(ns say)

(def ^:private ^:const numbers
  {1 "one"
   2 "two"
   3 "three"
   4 "four"
   5 "five"
   6 "six"
   7 "seven"
   8 "eight"
   9 "nine"
   10 "ten"
   11 "eleven"
   12 "twelve"
   13 "thirteen"
   14 "fourteen"
   15 "fifteen"
   16 "sixteen"
   17 "seventeen"
   18 "eighteen"
   19 "nineteen"
   20 "twenty"
   30 "thirty"
   40 "forty"
   50 "fifty"
   60 "sixty"
   70 "seventy"
   80 "eighty"
   90 "ninety"})

(def ^:private ^:const exponents
  [[1000000000 "billion"]
   [1000000    "million"]
   [1000       "thousand"]
   [100        "hundred"]])

(defn- number* [num]
  (cond
    (contains? numbers num) (numbers num)
    (< num 100) (let [hi (number* (* 10 (quot num 10)))
                      lo (number* (rem num 10))]
                  (str hi "-" lo))
    :else (let [[[exp exp-name]] (drop-while #(> (first %) num) exponents)
                hi (number* (quot num exp))]
            (if (pos? (rem num exp))
              (str hi " " exp-name " " (number* (rem num exp)))
              (str hi " " exp-name)))))

(defn number [num]
  (cond
    (not (<= 0 num 999999999999)) (throw (IllegalArgumentException.))
    (zero? num) "zero"
    :else (number* num)))
