(ns run-length-encoding)

(defn- flush-encoded [^StringBuilder sb cnt chr]
  (when (pos? cnt)
    (when (not= 1 cnt)
      (.append sb cnt))
    (.append sb chr)))

(defn run-length-encode
  "encodes a string with run-length-encoding"
  [plain-text]
  (let [sb (StringBuilder.)]
    (loop [chars (seq plain-text)
           chr (first chars)
           cnt 0]
      (cond
        (empty? chars) (flush-encoded sb cnt chr)
        (= chr (first chars)) (recur (rest chars) chr (inc cnt))
        :else (do
                (flush-encoded sb cnt chr)
                (recur (rest chars) (first chars) 1))))
    (.toString sb)))

(defn- flush-decoded [^StringBuilder sb cnt chr]
  (dotimes [_ (if (zero? cnt) 1 cnt)]
    (.append sb chr)))

(defn- add-digit ^long [cnt ^Character chr]
  (+ (* 10 cnt) (Character/digit chr 10)))

(defn run-length-decode
  "decodes a run-length-encoded string"
  [cipher-text]
  (let [sb (StringBuilder.)]
    (loop [chars (seq cipher-text)
           cnt 0]
      (let [^Character chr (first chars)]
        (cond
          (nil? chr) :default
          (Character/isDigit chr) (recur (rest chars) (add-digit cnt chr))
          :else (do
                  (flush-decoded sb cnt chr)
                  (recur (rest chars) 0)))))
    (.toString sb)))
