module HW0.T5 where

import GHC.Natural

type Nat a = (a -> a) -> a -> a

nz :: Nat a
nz _ a = a

-- ((a -> a) -> a -> a) -> (a -> a) -> a -> a
ns :: Nat a -> Nat a
ns a b = b . a b

nplus :: Nat a -> Nat a -> Nat a
nplus a b c = a c . b c

nmult :: Nat a -> Nat a -> Nat a
nmult a b = a . b

nFromNatural :: Natural -> Nat a
nFromNatural 0 = nz
nFromNatural n = ns (nFromNatural (n - 1))

nToNum :: Num a => Nat a -> a
nToNum n = n (+1) 0
