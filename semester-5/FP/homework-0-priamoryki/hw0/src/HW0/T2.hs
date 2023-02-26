{-# LANGUAGE TypeOperators #-}
module HW0.T2 where

import Data.Void

type Not a = a -> Void

doubleNeg :: a -> Not (Not a)
doubleNeg arg func = func arg

reduceTripleNeg :: Not (Not (Not a)) -> Not a
reduceTripleNeg func = func . doubleNeg
