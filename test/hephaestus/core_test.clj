(ns hephaestus.core-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.properties :as prop :refer (for-all)]
            [hephaestus.core :as c]
            [hephaestus.generators :as hg]))

(defn close-enough
  "Returns true if the two inputs are within `tolerance` of each other, false
  otherwise."
  ([l r] (close-enough l r 1e-5))
  ([l r tolerance]
   (<= (Math/abs (- l r)) tolerance)))

;; Once you prepare a state to have a certain measured value in a
;; given orientation, a measurement along the same orientation should
;; always give that value back out.
(defspec measure-after-prepare-same-result
  100
  (for-all [orientation hg/orientation
            result hg/result]
           (let [prepped (c/prepare orientation result)
                 [new-state spin] (c/measure prepped orientation)]
             (and (= spin result)))))

;; After measuring, the new state is parallel to the orientation of the measurement
;; apparatus (either same, or 180 degrees apart).
(defspec measurement-collapses-state-to-apparatus-orientation
  100
  (for-all [orientation hg/orientation
            result hg/result]
           (let [prepped (c/prepare orientation result)
                 [new-state spin] (c/measure prepped orientation)
                 dp (c/dot-prod new-state (c/spherical->cart orientation))]
             (if (= 1 result)
               ;; if the expected result was 1, the two should be ~the same
               ;; after the measurement.
               (close-enough 1 dp)

               ;; if the expected measurement was -1, we should expect that the
               ;; cos of the angle between them is -1, ie, they're 180 degrees
               ;; apart.
               (close-enough -1 dp)))))

;; Flip should produce a vector that is 180 off from the input vector.
(defspec flip-makes-an-opposite-facing-vector
  100
  (for-all [state hg/state]
           (close-enough -1 (c/dot-prod state (c/flip state)))))
