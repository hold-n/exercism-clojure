(ns rna-transcription
  (:require [net.cgrand.xforms :as x]))

(defn- get-complement [c]
  (case c
    \G \C
    \C \G
    \T \A
    \A \U
    (throw (AssertionError. "Invalid nucleotide"))))

(defn to-rna [dna]
  (x/str (map get-complement) dna))
