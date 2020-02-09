(ns tinyrender.line
  (:require [clojure.math.numeric-tower :as math]))

(defn next-coord
  [pre da db ai]
  (fn [{:keys [state val]} b]
    (let [{:keys [delta a]} state
          delta? (> delta 0)]
      
      {:state {:delta (+ delta (* 2 da) (if delta? (- (* 2 db)) 0))
               :a (if delta? (+ ai a) a)}
       :val (into [] (pre [a b]))})))

(defn plot-coords
  [pre da db a0 b0 b1]
  (let [ai (if (< da 0) -1 1)
        da (math/abs da)
        proc (next-coord pre da db ai)
        init {:state {:delta (- (* 2 da) db) :a a0}
              :val []}]
    
    (->> (reductions proc init (range b0 ((if (< b1 0) - +) b1 1)))
         (drop 1)
         (map :val))))

(defn plot-line
  [[x0 y0] [x1 y1]]
  (let [dx (- x1 x0) rdx (- dx)
        dy (- y1 y0) rdy (- dy)]
    
    (if (< (math/abs dy) (math/abs dx))
      (if (> x0 x1)
        (plot-coords rseq rdy rdx y1 x1 x0)
        (plot-coords rseq dy dx y0 x0 x1))
      (if (> y0 y1)
        (plot-coords identity rdx rdy x1 y1 y0)
        (plot-coords identity dx dy x0 y0 y1)))))
