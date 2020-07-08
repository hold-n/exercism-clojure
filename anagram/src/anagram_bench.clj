(ns anagram-bench
  (:require [clojure.string :as str]))

;; Prime sieve shamelessly lifted from Cristophe Grand's blogpost:
;; http://clj-me.cgrand.net/2009/07/30/everybody-loves-the-sieve-of-eratosthenes/
(defn primes []
  (letfn [(enqueue [sieve n step]
            (let [m (+ n step)]
              (if (sieve m)
                (recur sieve m step)
                (assoc sieve m step))))
          (next-sieve [sieve candidate]
            (if-let [step (sieve candidate)]
              (-> sieve
                (dissoc candidate)
                (enqueue candidate step))
              (enqueue sieve candidate (+ candidate candidate))))
          (next-primes [sieve candidate]
            (if (sieve candidate)
              (recur (next-sieve sieve candidate) (+ candidate 2))
              (cons candidate
                (lazy-seq (next-primes (next-sieve sieve candidate)
                            (+ candidate 2))))))]
    (cons 2 (lazy-seq (next-primes {} 3)))))

(def ascii-primes
  (int-array
   (concat
    (repeat 97 0)
    (take 26 (primes)))))

(defn ascii->num [c]
  (aget ^ints ascii-primes (int c)))

(defn freq [^String s]
  (let [n (.length s)]
    (if (zero? n) 1
      (loop [fs 1N, i 0]
        (if (= i n) fs
          (recur (*' fs (ascii->num (.charAt s i))) (inc i)))))))

(defn impl
  "Returns an implementation of `anagrams-for` with the given frequency fn"
  [f]
  (fn [word prospects]
    (let [word     (str/lower-case word)
          word'    (f word)
          anagram? #(let [s (str/lower-case %)]
                      (and (not= s word)
                           (= (f s) word')))]
      ;; filterv is used so that laziness doesn't mess up measurements
      (filterv anagram? prospects))))

(def cases
  [["diaper" ["hello" "world" "zombies" "pants"]]
   ["ant" ["tan" "stand" "at"]]
   ["galea" ["eagle"]]
   ["good" ["dog" "goody"]]
   ["banana" ["banana"]]
   ["BANANA" ["banana"]]
   ["allergy" ["gallery" "ballerina" "regally" "clergy" "largely" "leading"]]
   ["listen" ["enlists" "google" "inlets" "banana"]]
   ["Orchestra" ["cashregister" "Carthorse" "radishes"]]])

(require '[criterium.core :as cc])

(defn bench [f]
  (let [x (impl f)]
    (cc/quick-bench (run! (fn [[w ps]] (x w ps)) cases))))

(bench frequencies)
(bench sort)
(bench freq)
