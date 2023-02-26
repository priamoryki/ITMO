module HW0.T4 where

import Data.Function (fix)
import GHC.Natural

repeat' :: a -> [a]             -- behaves like Data.List.repeat
repeat' a = fix (a:)

map' :: (a -> b) -> [a] -> [b]  -- behaves like Data.List.map
map' = fix (\f g result -> case result of
                               []        -> []
                               head:rest -> g head : f g rest
           )

fib :: Natural -> Natural       -- computes the n-th Fibonacci number
fib = fix (\f cur prev n -> if n == 0
                            then prev
                            else f (cur + prev) cur (n - 1)
          ) 1 0

fac :: Natural -> Natural       -- computes the factorial
fac = fix (\f result n -> if n == 0
                          then result
                          else f (result * n) (n - 1)
          ) 1
