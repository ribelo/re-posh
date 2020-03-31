(ns re-posh.effects
  (:require [re-frame.core :as r]
            [re-posh.db :as db]
            [posh.reagent :as p]))

(r/reg-fx
 :transact
 (fn [datoms]
   (p/transact! (db/conn) datoms)))
