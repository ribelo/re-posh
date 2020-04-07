(ns re-posh.db
  (:require [posh.reagent :as p]
            [re-frame.core :as re-frame]
            [re-frame.context :as context]
            [re-frame.frame :as frame]
            [lambdaisland.glogi :as log]
            [datascript.core :as ds]))

(defn connect!
  "Connect DataScript store to the re-frame event system. Takes a freerange frame
  returns an updated frame."
  ([conn]
   (set! re-frame/default-frame (connect! re-frame/default-frame conn)))
  ([frame ds-conn]
   (p/posh! ds-conn)
   (frame/reg-fx frame :transact (fn [datoms {conn ::conn
                                              :as frame}]
                                   (when-not conn
                                     (log/error :no-conn-in-frame {:frame-id (:frame-id frame)
                                                                   :datoms datoms}))
                                   (p/transact! conn datoms)))
   (frame/reg-cofx frame :ds (fn [coeffects {conn ::conn}] (assoc coeffects :ds conn)))
   frame))
