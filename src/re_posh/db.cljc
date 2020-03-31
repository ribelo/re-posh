(ns re-posh.db
  (:require [posh.reagent :as p]
            [re-frame.core :as re-frame]
            [re-frame.context :as context]
            [re-frame.frame :as frame]
            [lambdaisland.glogi :as log]
            [datascript.core :as ds]))

;; (store) - datascript connection
;; @(store) - datascript database

(defn conn []
  (let [conn (::conn (context/current-frame))]
    (cond
      (nil? conn)
      (log/error :nil-conn {:msg "Context frame is missing re-posh connection."
                            :frame-keys (keys (context/current-frame))})
      (not (ds/conn? conn))
      (log/error :invalid-conn {:msg "re-posh connection is not a valid Datascript connection"
                                :conn conn
                                :frame-keys (keys (context/current-frame))}))
    conn))

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
