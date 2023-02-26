module HW2.T2 where

import HW2.T1

distOption :: (Option a, Option b) -> Option (a, b)
distOption (_, None)        = None
distOption (None, _)        = None
distOption (Some l, Some r) = Some (l, r)

distPair :: (Pair a, Pair b) -> Pair (a, b)
distPair (P l1 l2, P r1 r2) = P (l1, r1) (l2, r2)

distQuad :: (Quad a, Quad b) -> Quad (a, b)
distQuad (Q l1 l2 l3 l4, Q r1 r2 r3 r4) = Q (l1, r1) (l2, r2) (l3, r3) (l4, r4)

distAnnotated :: Semigroup e => (Annotated e a, Annotated e b) -> Annotated e (a, b)
distAnnotated (l_a :# l_e, r_a :# r_e) = (l_a, r_a) :# (l_e <> r_e)

distExcept :: (Except e a, Except e b) -> Except e (a, b)
distExcept (Error l_e, _)         = Error l_e
distExcept (_, Error r_e)         = Error r_e
distExcept (Success l, Success r) = Success (l, r)

distPrioritised :: (Prioritised a, Prioritised b) -> Prioritised (a, b)
distPrioritised (Low l, Low r)       = Low (l, r)
distPrioritised (Low l, Medium r)    = Medium (l, r)
distPrioritised (Low l, High r)      = High (l, r)
distPrioritised (Medium l, Low r)    = Medium (l, r)
distPrioritised (Medium l, Medium r) = Medium (l, r)
distPrioritised (Medium l, High r)   = High (l, r)
distPrioritised (High l, Low r)      = High (l, r)
distPrioritised (High l, Medium r)   = High (l, r)
distPrioritised (High l, High r)     = High (l, r)

distStream :: (Stream a, Stream b) -> Stream (a, b)
distStream (l_head :> l_tail, r_head :> r_tail) = (l_head, r_head) :> distStream (l_tail, r_tail)

distList :: (List a, List b) -> List (a, b)
distList (_, Nil)              = Nil
distList (Nil, _)              = Nil
distList (l_head :. l_tail, r) = merge (makePairs l_head r) (distList (l_tail, r)) where
    merge :: List a -> List a -> List a
    merge Nil r                = r
    merge l Nil                = l
    merge (l_head :. l_tail) r = l_head :. merge l_tail r
    makePairs :: l -> List r -> List (l, r)
    makePairs _ Nil                = Nil
    makePairs l (r_head :. r_tail) = (l, r_head) :. makePairs l r_tail

distFun :: (Fun i a, Fun i b) -> Fun i (a, b)
distFun (F l, F r) = F (\arg -> (l arg, r arg))

wrapOption :: a -> Option a
wrapOption = Some

wrapPair :: a -> Pair a
wrapPair a = P a a

wrapQuad :: a -> Quad a
wrapQuad a = Q a a a a

wrapAnnotated :: Monoid e => a -> Annotated e a
wrapAnnotated a = a :# mempty

wrapExcept :: a -> Except e a
wrapExcept = Success

wrapPrioritised :: a -> Prioritised a
wrapPrioritised = Low

wrapStream :: a -> Stream a
wrapStream a = a :> wrapStream a

wrapList :: a -> List a
wrapList a = a :. Nil

wrapFun :: a -> Fun i a
wrapFun a = F (const a)
