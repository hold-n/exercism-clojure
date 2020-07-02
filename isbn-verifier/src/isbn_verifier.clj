(ns isbn-verifier)

(defn- isbn10->nums
  "Parses a valid `isbn` and retrieves the constituent ISBN10 numbers."
  [isbn]
  (map
   #(if (= % \X) 10 (Character/digit % 10))
   (remove #{\-} isbn)))

(defn- checksum10?
  "Checks whether the `nums` sequence generates a correct ISBN10 checksum."
  [nums]
  (let [divisible-by? #(zero? (rem %2 %1))]
    (->> nums
         (map * (range 10 0 -1))
         (reduce +)
         (divisible-by? 11))))

(defn- isbn10?
  "Checks if `isbn` represents a correct ISBN10."
  [isbn]
  (and
   (some? (re-matches #"\d{9}[\dX]|\d-\d{3}-\d{5}-[\dX]" isbn))
   (let [nums (isbn10->nums isbn)]
     (checksum10? nums))))

(def isbn?
  "Checks if `isbn` represents a correct ISBN10."
  isbn10?)
