(ns sublist)

(defn- sublist?
  ([list1 list2] (sublist? list1 list2 (count list1)))
  ([list1 list2 n]
  (cond
    (= list1 (take n list2)) true
    (empty? list2) false
    :else (recur list1 (rest list2) n))))

(defn classify [list1 list2]
  (let [lt (sublist? list1 list2)
        gt (sublist? list2 list1)]
    (cond
      (and lt gt) :equal
      lt :sublist
      gt :superlist
      :else :unequal)))
