(ns run-length-encoding)

(defn- format-run [run]
  (let [cnt (count run)]
    (format "%s%s" (if (= 1 cnt) "" cnt) (first run))))

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (->> plain-text
       (partition-by identity)
       (map format-run)
       (apply str)))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [cipher-text]
  (->> cipher-text
       (re-seq #"(\d+)?(\D)")
       (mapcat (fn [[_ cnt-str chr]]
                 (repeat (Integer/parseInt (or cnt-str "1")) chr)))
       (apply str)))
