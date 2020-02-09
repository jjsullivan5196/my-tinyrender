(ns user
  (:require [tinyrender.line :as line]
            [tinyrender.image :as im]
            [clojure.math.numeric-tower :as math])
  (:import [java.awt Color]))

(def my-line (line/plot-line [99 99] [150 0]))
(def my-pic (im/make-image 200 200))

(-> my-pic
    (im/fill-color Color/black)
    (im/draw-pixels Color/red my-line)
    (im/save-image "./test.png"))

