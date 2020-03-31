(ns re-posh.coeffects
  (:require [re-frame.core :as r]
            [re-posh.db :as db]
            [posh.reagent :as p]))

(r/reg-cofx
 :ds
 (fn [coeffects _]
   (assoc coeffects :ds @(db/conn))))
