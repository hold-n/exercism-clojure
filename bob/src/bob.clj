(ns bob
  (:require [clojure.string :as str]))

(defn- question? [s]
  (str/ends-with? (str/trimr s) "?"))

(defn yelling? [s]
  (and (not= s (str/lower-case s))
       (= s (str/upper-case s))))

(defn response-for [s]
  (let [q (question? s)
        y (yelling? s)]
    (cond
      (str/blank? s) "Fine. Be that way!"
      (and q y) "Calm down, I know what I'm doing!"
      q "Sure."
      y "Whoa, chill out!"
      :else "Whatever.")))
