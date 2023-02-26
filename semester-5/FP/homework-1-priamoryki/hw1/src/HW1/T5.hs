module HW1.T5 where

import Data.List.NonEmpty (NonEmpty (..), fromList)

split :: Eq a => a -> [a] -> [[a]]
split _ [] = [[]]
split sep string = head : if null tail then [] else split sep (case tail of
                                                                  []   -> []
                                                                  _:rs -> rs
                                                              )
                   where head = takeWhile (/= sep) string
                         tail = dropWhile (/= sep) string

splitOn :: Eq a => a -> [a] -> NonEmpty [a]
splitOn sep string = fromList (split sep string)

joinWith :: a -> NonEmpty [a] -> [a]
joinWith _ (head:|[])     = head
joinWith sep (head:|list) = head ++ foldr (\a -> ((sep:a) ++)) [] list
