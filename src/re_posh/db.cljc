(ns re-posh.db
  (:require [posh.reagent :as p]
            [re-frame.core :as re-frame]
            [re-frame.context :as context]
            [re-frame.frame :as frame]))

;; Basic store. This atom stores another atom
;; @store - datascript connection
;; @@store - datascript database
;; (def store (atom nil))

(defn conn []
  (::conn (context/current-frame)))

(defn connect!
  "Connect DataScript store to the re-frame event system. Takes a freerange frame
  returns an updated frame."
  ([conn]
   (set! re-frame/default-frame (connect! re-frame/default-frame conn)))
  ([frame ds-conn]
   (p/posh! ds-conn)
   (let [frame (assoc frame ::conn ds-conn)]
     (frame/reg-fx frame :transact (fn [datoms] (p/transact! (conn) datoms)))
     (frame/reg-cofx frame :ds (fn [coeffects _] (assoc coeffects :ds @(conn))))
     frame)))
