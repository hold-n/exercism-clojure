(ns secret-handshake)

(defn commands
  "Parses `num` into a sequence of commands."
  [num]
  (letfn [(parse [n]
            (filter #(pos? (bit-and % n))
                    (take 5 (iterate (partial * 2) 1))))
          (interpret-1 [state ^long command]
            (case command
              2r1     (conj state "wink")
              2r10    (conj state "double blink")
              2r100   (conj state "close your eyes")
              2r1000  (conj state "jump")
              2r10000 (reverse state)))
          (interpret [commands]
            (reduce interpret-1 [] commands))]
    (interpret (parse num))))
