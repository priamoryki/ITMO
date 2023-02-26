module HW2.T4 where

import Control.Monad (ap)
import HW2.T1

data State s a = S { runS :: s -> Annotated s a }

mapState :: (a -> b) -> State s a -> State s b
mapState f (S runS) = S (\x -> mapAnnotated f (runS x))

wrapState :: a -> State s a
wrapState a = S (\s -> a :# s)

joinState :: State s (State s a) -> State s a
joinState state = S (\x -> let (new_m :# new_s) = runS state x in runS new_m new_s)

modifyState :: (s -> s) -> State s ()
modifyState f = S (\s -> () :# f s)

instance Functor (State s) where
    fmap = mapState

instance Applicative (State s) where
    pure = wrapState
    p <*> q = Control.Monad.ap p q

instance Monad (State s) where
    m >>= f = joinState (fmap f m)

data Prim a =
      Add a a      -- (+)
    | Sub a a      -- (-)
    | Mul a a      -- (*)
    | Div a a      -- (/)
    | Abs a        -- abs
    | Sgn a        -- signum

data Expr = Val Double | Op (Prim Expr)

instance Num Expr where
    x + y = Op (Add x y)
    x - y = Op (Sub x y)
    x * y = Op (Mul x y)
    abs x = Op (Abs x)
    signum x = Op (Sgn x)
    fromInteger x = Val (fromInteger x)

instance Fractional Expr where
    x / y = Op (Div x y)
    fromRational x = Val (fromRational x)

eval :: Expr -> State [Prim Double] Double
eval (Val x)        = return x
eval (Op (Add x y)) = binaryOp x y Add (+)
eval (Op (Sub x y)) = binaryOp x y Sub (-)
eval (Op (Mul x y)) = binaryOp x y Mul (*)
eval (Op (Div x y)) = binaryOp x y Div (/)
eval (Op (Abs x))   = unaryOp x Abs abs
eval (Op (Sgn x))   = unaryOp x Sgn signum

binaryOp :: Expr -> Expr -> (Double -> Double -> Prim Double) -> (Double -> Double -> Double) -> State [Prim Double] Double
binaryOp x y op opF = do
    arg1 <- eval x
    arg2 <- eval y
    modifyState (op arg1 arg2 :)
    return (opF arg1 arg2)

unaryOp :: Expr -> (Double -> Prim Double) -> (Double -> Double) -> State [Prim Double] Double
unaryOp x op opF = do
    arg1 <- eval x
    modifyState (op arg1 :)
    return (opF arg1)
