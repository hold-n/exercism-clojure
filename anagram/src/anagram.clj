(ns anagram
  (:require [clojure.string :as str]))

(defn- anagram-of
  "Creates a predicate that checks whether a string is an anagram of `word`."
  [word]
  (let [word (str/lower-case word)
        s (sort word)]
    #(let [word' (str/lower-case %)
           s' (sort word')]
       (and
        (not= word word')
        (= s s')))))

(defn anagrams-for
  "Returns a sublist of `prospects` with anagrams to `word`."
  [word prospects]
  (filter (anagram-of word) prospects))
