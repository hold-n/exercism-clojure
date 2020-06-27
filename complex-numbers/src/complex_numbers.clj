(ns complex-numbers)

(defn real [[a b]]
  a)

(defn imaginary [[a b]]
  b)

(defn abs [[a b]]
  (let [squared (+ (* a a) (* b b))]
    (Math/pow squared 0.5)))

(defn conjugate [[a b]]
  [a (- b)])

(defn add [[a b] [c d]]
  [(+ a c) (+ b d)])

(defn sub [[a b] [c d]]
  [(- a c) (- b d)])

(defn mul [[a b] [c d]]
  (let [r (- (* a c) (* b d))
        i (+ (* a d) (* b c))]
    [r i]))

(defn div [[a b] [c d]]
  (let [denom (float (+ (* c c) (* d d)))]
    (let [r (/ (+ (* a c) (* b d)) denom)
          i (/ (- (* b c) (* a d)) denom)]
      [r i])))
