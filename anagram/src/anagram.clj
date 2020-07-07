(ns anagram
  (:require [clojure.string :as str]))

(defn- anagram?
  "Determines whether `a` and `b` are anagrams."
  [a b]
  (let [a (str/lower-case a)
        b (str/lower-case b)]
    (and
     (not= a b)
     (= (frequencies a) (frequencies b)))))

(defn anagrams-for
  "Returns a sublist of `prospect-list` with anagrams to `word`."
  [word prospect-list]
  (filter #(anagram? % word) prospect-list))
