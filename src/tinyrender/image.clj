(ns tinyrender.image
  (:require [clojure.math.numeric-tower :as math])
  (:import [java.io File]
           [java.awt Color]
           [java.awt.image BufferedImage]
           [javax.imageio ImageIO]))

(defn make-image
  [width height]
  (BufferedImage. width height BufferedImage/TYPE_INT_ARGB))

(defn save-image
  [img path]
  (ImageIO/write img "png" (File. path)))

(defn as-color
  [color]
  (condp #(%1 %2) color
    int? (Color. color true)
    vector? (eval `(Color. ~@color))
    (partial instance? Color) color))

(defn as-argb
  [color]
  (condp #(%1 %2) color
    int? color
    vector? (-> (eval `(Color. ~@color)) (.getRGB))
    (partial instance? Color) (.getRGB color)))

(defn fill-color
  [img color]
  (let [color (as-color color)
        width (.getWidth img)
        height (.getHeight img)
        gfx (.getGraphics img)]
    (doto gfx
      (.setPaint color)
      (.fillRect 0 0 width height)))
  img)

(defn draw-pixels
  [img color points]
  (let [argb (as-argb color)]
    (doseq [[x y] points]
      (.setRGB img x y argb)))
  img)
