module HW2.T3 where

import HW2.T1

joinOption :: Option (Option a) -> Option a
joinOption None     = None
joinOption (Some a) = a

joinExcept :: Except e (Except e a) -> Except e a
joinExcept (Error e)   = Error e
joinExcept (Success a) = a

joinAnnotated :: Semigroup e => Annotated e (Annotated e a) -> Annotated e a
joinAnnotated ((l_head :# l_tail) :# r) = l_head :# (r <> l_tail)

joinList :: List (List a) -> List a
joinList Nil = Nil
joinList (a_head :. a_tail) = merge a_head (joinList a_tail) where
    merge :: List a -> List a -> List a
    merge Nil r                = r
    merge l Nil                = l
    merge (l_head :. l_tail) r = l_head :. merge l_tail r

joinFun :: Fun i (Fun i a) -> Fun i a
joinFun (F f) = F (\a -> let (F g) = f a in g a)
