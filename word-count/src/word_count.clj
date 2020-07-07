(ns word-count
  (:require [clojure.string :as str]))

(defn word-count [s]
  (-> s
      (str/replace #"\W+" " ")
      str/lower-case
      (str/split #" ")
      frequencies))
