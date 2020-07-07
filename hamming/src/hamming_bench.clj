(ns hamming-bench
  (:require hamming
            [criterium.core :as cc]))

(def strand1-cases
  [""
   "GGACTGA"
   "ACT"
   "GGACG"
   "ACCAGGG"
   "AAAC"
   "GACTACGGACAGGGTAACATAG"])

(def strand2-cases
  [""
   "GGACTGA"
   "GGA"
   "GGTCG"
   "ACTATGG"
   "TAGGGGAGGCTAGCGGTAGGAC"
   "GACA"])

;; Execution time mean : 6.244440 microseconds
(defn bench [] (cc/quick-bench (dorun (map hamming/distance strand1-cases strand2-cases))))
;; Execution time mean : 9.963830 microseconds
(defn bench* [] (cc/quick-bench (dorun (map hamming/distance* strand1-cases strand2-cases))))
