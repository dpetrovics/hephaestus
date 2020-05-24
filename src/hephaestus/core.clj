(ns hephaestus.core
  (:require [clojure.spec.alpha :as s]
            [clojure.core.matrix :as m]
            [complex.core :as c]))

;; ## Concepts:

;; Apparatus: A measurement device.

;; Orientation: The apparatus has a given orientation in space. We
;; think of the orientation as being a 3D unit vector, whose direction
;; we can specify with a theta and phi. In other words, we specify the
;; orientation in spherical coordinates (radius = 1).

;; State: The quantum state of the particle that we'll want to measure
;; with our apparatus. For now, we're representing the state as a 3D
;; unit vector with x, y, and z coordinates.

;; Measurement: You can feed the apparatus a particle in some known or
;; unknown state, and 'measure' it. We think of the measurement as
;; doing two things - first, returning a value of +1 or -1 (these are
;; always the only two possible results), and second, producing a new,
;; post-measurement state for the particle. The new state will always
;; be oriented either in the *same* direction as the apparatus's
;; orientation, or in the opposite direction.

;; Prepare: A first measurement is said to 'prepare' the state of the
;; particle. This really just means 'setting to a known state.' For
;; example, if we have an orientation of our apparatus along the
;; positive z-axis and it measured +1, the state of the particle has
;; been 'prepared' to be a unit vector along the positive z-axis. Any
;; further measurement of this particle with the apparatus orientated
;; in the same manner (the positive z-axis) will continue to yield a
;; result of +1. (Of course, if that first measurement was -1 along
;; the positive z axis, we could just define the negative z axis as
;; corresponding to +1).

;; Changing Apparatus Orientation: If the apparatus is moved from
;; orientation A to orientation B, you can think of the change as
;; having rotated the apparatus an angle gamma from vector A to vector
;; B. In other words, the previous and new orientation 3D vectors (A
;; and B) are co-planar, and gamma is the angle between them in that
;; plane. Quantum mechanics dicatates that the cosine of the gamma is
;; the expected value of the measurement result - ie the average value
;; if you were to keep re-doing the experiment along the same
;; orientation (with new particles prepared the same way).
;; Lets go over some examples: if the gamma is 0, you haven't changed
;; the orientation from the prepared state, so the measurement result
;; will be the same as before with 100% probability; if the gamma is
;; 180 (apparatus orientation flipped), the measurement will be the
;; negative of the prepared result with 100% probability; if the
;; gamma is 90, we'll alternate +1 and -1 for the result with equal
;; probability (cosine 90 = 0).
;; Lets call alpha the probability of getting a measurement result of
;; +1. We can say that (cos gamma) = expected value = 1*alpha + (-1 *
;; (1 - alpha)).  In other words, the probability of measuring a +1
;; after rotating the apparatus an angle gamma from a previously
;; prepared state of +1 is ((cos gamma)+1) / 2.

;; Later: look at the half angle identites, think about meaning of
;; alpha there (Versus gamma as we defined it).


;; ## Schemas:

;; The particle state is represented as a 3-Vector with x, y, z
;; coordinates. We do it this way because its easier to do the dot
;; product.
(s/def ::state any?)

;; Orientation of the apparatus represented as a theta and phi, to
;; represent the unit vector in 3D space.
;; Theta is the angle from center of sphere sweeping from NP to SP
;; (like latitude lines) on the Earth, but we specify 0 degrees as
;; pointing towards the North Pole.
;; Phi is the angle, from center of sphere sweeping around the
;; equator, like lines of longitude on the Earth.
(s/def ::orientation any?)

;; Measurement value is +1 or -1.
(s/def ::measurement-value any?)


;; ## Helpers

(defn flip
  "Return an orientation that is 180deg to the given orientation. (Parallel, but opp direction)"
  [{:keys [x y z]}]
  {:x (- x)
   :y (- y)
   :z (- z)})


(defn spherical->cart
  "Converts given spherical coords to 3D-Cartesian."
  [{:keys [theta phi]}]
  {:x (* (Math/sin theta)
         (Math/cos phi))
   :y (* (Math/sin theta)
         (Math/sin phi))
   :z (Math/cos theta)})

(defn dot-prod
  "Takes the dot product of two 3-Vectors."
  [vec-a vec-b]
  (+ (* (:x vec-a) (:x vec-b))
     (* (:y vec-a) (:y vec-b))
     (* (:z vec-a) (:z vec-b))))

(defn ev->prob
  "Takes an expected value (between -1 and 1), and converts it to a
  probability of getting a +1."
  [ev]
  (/ (+ ev 1) 2))


;; ## Quantum API:

(defn prepare
  "Specify an orientation for the apparatus and the value you measured,
  and we'll return the prepared state."
  [{:keys [theta phi] :as orientation}
   measured-value]
  (if (= measured-value -1)
    (prepare (flip orientation) 1)
    (spherical->cart orientation)))

(defn measure
  "Perform a measurement of the state via the apparatus in the given
  orientation, and we'll return a new state and the measurement
  value."
  [state
   orientation]
  ;; Let the angle gamma represent the angle between the state and the
  ;; apparatus orientation.

  ;; The dot product of the state and orientation vectors gets us the
  ;; cosine of the angle between them, ie (cos gamma).

  ;; Remember that cosine gamma is also the expected value of the
  ;; measurement result! We can move between this expected value and
  ;; the probability of measuring a +1 with our helper.

  (let [cart-orientation (spherical->cart orientation)
        cos-gamma (dot-prod state cart-orientation)
        prob (ev->prob cos-gamma)]
    (if (< (rand) prob)
      [cart-orientation 1]
      [(flip cart-orientation) -1])))


;; ## Library Docs:

;; Spec Docs: https://clojure.org/guides/spec

;; Core.Matrix: https://github.com/mikera/core.matrix/wiki/Getting-Started-Guide

;; Complex: https://github.com/alanforr/complex

;; Just for fun, I added a dependency to Neanderthal, which is supposedly an
;; excellent matrix library for Clojure.
;;
;; To actually use it, we'll need to get the Intel Math Kernel library
;; installed, as described here:
;; https://neanderthal.uncomplicate.org/articles/getting_started.html#installation

(def idnty
  [[(c/complex 1) (c/complex 0)]
   [(c/complex 0) (c/complex 1)]])

(defn conj-transpose
  [mtx]
  (m/emap c/conjugate (m/transpose mtx)))
