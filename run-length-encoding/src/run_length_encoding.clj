(ns run-length-encoding
  (:require [clojure.string :as str]))

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (let [sb (StringBuilder.)]
    (doseq [run (partition-by identity plain-text)]
      (when (not= 1 (count run))
        (.append sb (count run)))
      (.append sb (first run)))
    (.toString sb)))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [cipher-text]
  (let [sb (StringBuilder.)]
    (doseq [[_ cnt-str chr] (re-seq #"(\d+)?(\D)" cipher-text)]
      (dotimes [_ (if (str/blank? cnt-str)
                    1
                    (Integer/parseInt cnt-str))]
        (.append sb chr)))
    (.toString sb)))
