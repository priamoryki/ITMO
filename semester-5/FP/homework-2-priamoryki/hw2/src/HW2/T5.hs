module HW2.T5 where

import Control.Monad (ap)
import HW2.T1
import HW2.T4 (Expr (..), Prim (..))

data ExceptState e s a = ES { runES :: s -> Except e (Annotated s a) }

mapExceptState :: (a -> b) -> ExceptState e s a -> ExceptState e s b
mapExceptState f a = ES (\x -> mapExcept (mapAnnotated f) (runES a x))

wrapExceptState :: a -> ExceptState e s a
wrapExceptState a = ES (\s -> Success (a :# s))

joinExceptState :: ExceptState e s (ExceptState e s a) -> ExceptState e s a
joinExceptState state = ES (
    \s -> case runES state s of
        Error e          -> Error e
        Success (a :# e) -> runES a e)

modifyExceptState :: (s -> s) -> ExceptState e s ()
modifyExceptState f = ES (\s -> Success(() :# f s))

throwExceptState :: e -> ExceptState e s a
throwExceptState e = ES (const (Error e))

instance Functor (ExceptState e s) where
    fmap = mapExceptState

instance Applicative (ExceptState e s) where
    pure = wrapExceptState
    p <*> q = Control.Monad.ap p q

instance Monad (ExceptState e s) where
    m >>= f = joinExceptState (fmap f m)

data EvaluationError = DivideByZero
eval :: Expr -> ExceptState EvaluationError [Prim Double] Double
eval (Val x)        = return x
eval (Op (Add x y)) = binaryOp x y Add (+)
eval (Op (Sub x y)) = binaryOp x y Sub (-)
eval (Op (Mul x y)) = binaryOp x y Mul (*)
eval (Op (Div x y)) = do
    arg1 <- eval x
    arg2 <- eval y
    modifyExceptState (Div arg1 arg2 :)
    if arg2 == 0
        then throwExceptState DivideByZero
        else return (arg1 / arg2)
eval (Op (Abs x))   = unaryOp x Abs abs
eval (Op (Sgn x))   = unaryOp x Sgn signum

binaryOp :: Expr -> Expr -> (Double -> Double -> Prim Double) -> (Double -> Double -> Double) -> ExceptState EvaluationError [Prim Double] Double
binaryOp x y op opF = do
    arg1 <- eval x
    arg2 <- eval y
    modifyExceptState (op arg1 arg2 :)
    return (opF arg1 arg2)

unaryOp :: Expr -> (Double -> Prim Double) -> (Double -> Double) -> ExceptState EvaluationError [Prim Double] Double
unaryOp x op opF = do
    arg1 <- eval x
    modifyExceptState (op arg1 :)
    return (opF arg1)
