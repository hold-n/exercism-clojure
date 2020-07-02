(ns isbn-verifier)

(defn- parse-num [^Character c]
  (if (= c \X) 10 (Character/digit c 10)))

(defn- isbn10->nums [isbn]
  (when (re-matches #"\d{9}[\dX]|\d-\d{3}-\d{5}-[\dX]" isbn)
    (map parse-num (remove #{\-} isbn))))

(defn- checksum10? [nums]
  (let [divisible-by? #(zero? (rem %2 %1))]
    (->> nums
         (map * (range 10 0 -1))
         (reduce +)
         (divisible-by? 11))))

(defn- isbn10? [isbn]
  (let [nums (isbn10->nums isbn)]
    (true? (and
            (seq nums)
            (checksum10? nums)))))

(def isbn? isbn10?)
