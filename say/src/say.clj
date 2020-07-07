(ns say
  (:require [clojure.string :as str]))

(defn- say-teens [n]
  (case n
    10 "ten"
    11 "eleven"
    12 "twelve"
    13 "thirteen"
    14 "fourteen"
    15 "fifteen"
    16 "sixteen"
    17 "seventeen"
    18 "eighteen"
    19 "nineteen"))

(defn- say-tens [n]
  (case (-> n (quot 10) (rem 10))
    2 "twenty"
    3 "thirty"
    4 "fourty"
    5 "fifty"
    6 "sixty"
    7 "seventy"
    8 "eighty"
    9 "ninety"
    nil))

(defn- say-digit [n]
  (case (rem n 10)
    1 "one"
    2 "two"
    3 "three"
    4 "four"
    5 "five"
    6 "six"
    7 "seven"
    8 "eight"
    9 "nine"
    nil))

(defn- say-chunk [num]
  (cond
    (zero? num) nil
    (<= 10 num 19) (say-teens num)
    (<= 1 num 99) (let [t (say-tens num) d (say-digit num)]
                    (if (and t d)
                      (str t "-" d)
                      (or t d)))
    (<= num 999) (let [h (say-digit (quot num 100))
                       c (say-chunk (rem num 100))]
                    (if c
                      (str h " hundred " c)
                      (str h " hundred")))))

(def ^:private exponents
  [nil "thousand" "million" "billion"])

(defn- format-chunk [exp c]
  (when (pos? c)
    (if exp
      (str (say-chunk c) " " exp)
      (say-chunk c))))

(defn- get-chunks [num]
  (loop [num num ret []]
    (if (zero? num)
      ret
      (recur (quot num 1000) (conj ret (rem num 1000))))))

(defn number [num]
  (cond
    (zero? num) "zero"
    (<= 1 num 999999999999) (->> (get-chunks num)
                                 (map format-chunk exponents)
                                 (filter some?)
                                 reverse
                                 (str/join " "))
    :else (throw (IllegalArgumentException. "Number out of range"))))
