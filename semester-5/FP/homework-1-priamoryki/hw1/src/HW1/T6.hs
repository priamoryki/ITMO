module HW1.T6 where

import Data.Either
import Data.Foldable

mcat :: Monoid a => [Maybe a] -> a
mcat = fold . fold

epart :: (Monoid a, Monoid b) => [Either a b] -> (a, b)
epart x = (fold (lefts x), fold (rights x))
