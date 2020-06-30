(ns isbn-verifier)

(defn- parse-digits [isbn]
  (if (re-matches #"\d{9}[\dX]" isbn)
    isbn
    nil))

(defn- parse-hyphens [isbn]
  (if (re-matches #"\d-\d{3}-\d{5}-[\dX]" isbn)
    (remove #{\-} isbn)
    nil))

(defn- parse-digit [^Character d]
  (if (= d \X) 10 (Character/digit d 10)))

(defn- valid? [digits]
  (->> digits
       (map vector [10 9 8 7 6 5 4 3 2 1])
       (map #(reduce * %))
       (reduce +)
       (#(rem % 11))
       zero?))

(defn isbn? [isbn]
  (let [chars (or
                (parse-digits isbn)
                (parse-hyphens isbn))
        digits (map parse-digit chars)]
    (true? (and
            (seq digits)
            (valid? digits)))))
