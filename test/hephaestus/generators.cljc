(ns hephaestus.generators
  "Generators for use with clojure.test.check forms."
  (:require [clojure.test.check.generators :as gen]
            [hephaestus.core :as c]))

(def angle
  "Returns a double from 0 to 2π."
  (gen/double* {:NaN? false :min 0 :max (* 2 Math/PI)}))

(def orientation
  "Generator that returns an orientation of a unit vector, described in spherical
  coordinates. Phi is the angle around the Z axis, off of the
  X-axis (longitude), and Theta is the colatitude (the angle down off of the
  north pole.)"
  (gen/hash-map :phi angle :theta angle))

(def state
  "Generator that returns a point on the unit sphere."
  (gen/fmap c/spherical->cart orientation))

(def result
  "Generator that returns a positive or negative spin result."
  (gen/elements [1 -1]))
