(ns re-posh.db
  (:require [posh.reagent :as p]
            [re-frame.core :as re-frame]
            [re-frame.context :as context]))

;; Basic store. This atom stores another atom
;; @store - datascript connection
;; @@store - datascript database
;; (def store (atom nil))

(defn connect!
  "Connect DataScript store to the re-frame event system. Takes a freerange frame
  returns an updated frame."
  ([conn]
   (set! re-frame/default-frame (connect! re-frame/default-frame conn)))
  ([frame conn]
   (p/posh! conn)
   (assoc frame ::conn conn)))

(defn conn []
  (::conn (context/current-frame)))
