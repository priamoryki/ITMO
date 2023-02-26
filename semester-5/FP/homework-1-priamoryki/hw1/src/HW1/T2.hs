module HW1.T2 where

import GHC.Natural

data N = Z | S N

nplus :: N -> N -> N        -- addition
nplus Z a     = a
nplus (S a) b = nplus a (S b)

nmult :: N -> N -> N        -- multiplication
nmult Z _     = Z
nmult (S a) b = nplus (nmult a b) b

nsub :: N -> N -> Maybe N   -- subtraction     (Nothing if result is negative)
nsub Z Z         = Just Z
nsub Z _         = Nothing
nsub a Z         = Just a
nsub (S a) (S b) = nsub a b

ncmp :: N -> N -> Ordering  -- comparison      (Do not derive Ord)
ncmp Z Z         = EQ
ncmp Z _         = LT
ncmp _ Z         = GT
ncmp (S a) (S b) = ncmp a b

nFromNatural :: Natural -> N
nFromNatural 0 = Z
nFromNatural n = S (nFromNatural (n - 1))

nToNum :: Num a => N -> a
nToNum Z     = 0
nToNum (S n) = nToNum n + 1

nEven :: N -> Bool
nEven Z     = True
nEven (S n) = nOdd n

nOdd :: N -> Bool
nOdd Z     = False
nOdd (S n) = nEven n

ndiv :: N -> N -> N         -- integer division
ndiv _ Z = Z
ndiv Z _ = Z
ndiv a b = case (nsub a b) of
              Nothing -> Z
              Just Z  -> S Z
              Just n  -> S (ndiv n b)

nmod :: N -> N -> N         -- modulo operation
nmod _ Z = Z
nmod Z _ = Z
nmod a b = case (nsub a b) of
              Nothing -> a
              Just Z  -> Z
              Just n  -> nmod n b
