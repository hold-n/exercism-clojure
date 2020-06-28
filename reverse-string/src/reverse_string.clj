(ns reverse-string)

(defn ^String reverse-string [^String s]
  (->> s
       StringBuilder.
       .reverse
       .toString))
