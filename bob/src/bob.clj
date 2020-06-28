(ns bob
  (:require [clojure.string :as str]))

(defn- question? [^String s]
  (-> s
      str/trim
      (str/ends-with? "?")))

(defn- yelling? [^String s]
  (and
   (every? #(or (Character/isUpperCase %) (not (Character/isLetter %))) s)
   (some #(Character/isLetter %) s)))

(defn response-for [^String s]
  (let [is-question (question? s)
        is-yelling (yelling? s)]
    (cond
      (str/blank? s) "Fine. Be that way!"
      (and is-question is-yelling) "Calm down, I know what I'm doing!"
      is-question "Sure."
      is-yelling "Whoa, chill out!"
      :else "Whatever.")))
