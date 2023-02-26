{-# LANGUAGE BlockArguments             #-}
{-# LANGUAGE DerivingStrategies         #-}
{-# LANGUAGE GeneralizedNewtypeDeriving #-}

module HW2.T6 where

import Control.Applicative
import Control.Monad
import Data.Char
import Data.Foldable
import Data.Scientific
import GHC.Natural
import HW2.T1 (Annotated (..), Except (..))
import HW2.T4 (Expr (..), Prim (..))
import HW2.T5 (ExceptState (..), runES)

data ParseError = ErrorAtPos Natural

newtype Parser a = P (ExceptState ParseError (Natural, String) a) deriving newtype (Functor, Applicative, Monad)

runP :: Parser a -> String -> Except ParseError a
runP (P parser) str =
    case runES parser (0, str) of
        Error e          -> Error e
        Success (a :# _) -> Success a

pChar :: Parser Char
pChar = P $ ES $ \(pos, s) ->
    case s of
        []     -> Error (ErrorAtPos pos)
        (c:cs) -> Success (c :# (pos + 1, cs))

parseError :: Parser a
parseError = P (ES \(pos, _) -> Error(ErrorAtPos pos))

instance Alternative Parser where
    empty = parseError
    (<|>) (P a) (P b) = P (ES (
        \(pos, str) -> case runES a (pos, str) of
            Success x -> Success x
            Error _   -> runES b (pos, str)))

instance MonadPlus Parser   -- No methods.

pEof :: Parser ()
pEof = P $ ES \(pos, s) -> case s of
    [] -> Success (() :# (pos, s))
    _  -> Error (ErrorAtPos pos)

pAbbr :: Parser String
pAbbr = do
    abbr <- some (mfilter Data.Char.isUpper pChar)
    _    <- pEof
    pure abbr

parseExpr :: String -> Except ParseError Expr
parseExpr = runP parse

parse :: Parser Expr
parse = do
    result <- parseAddSub
    _      <- pEof
    return result

parseAddSub :: Parser Expr
parseAddSub = do
    _  <- parseSpaces
    l  <- parseMulDiv
    parseAddSubInf l

parseAddSubInf :: Expr -> Parser Expr
parseAddSubInf l = do
    op <- optional $ do
        _    <- parseSpaces
        sign <- validateChar '+' <|> validateChar '-'
        r    <- parseMulDiv
        case sign of
            '+' -> return (Op (Add l r))
            '-' -> return (Op (Sub l r))
    case op of
        Nothing -> return l
        Just a  -> parseAddSubInf a

parseMulDiv :: Parser Expr
parseMulDiv = do
    _  <- parseSpaces
    l  <- parseUnary
    parseMulDivInf l

parseMulDivInf :: Expr -> Parser Expr
parseMulDivInf l = do
    op <- optional $ do
        _    <- parseSpaces
        sign <- validateChar '*' <|> validateChar '/'
        r    <- parseUnary
        case sign of
            '*' -> return (Op (Mul l r))
            '/' -> return (Op (Div l r))
    case op of
        Nothing -> return l
        Just a  -> parseMulDivInf a

parseUnary :: Parser Expr
parseUnary = do
    _      <- parseSpaces
    result <- parseDouble <|> parseExprInBraces
    return result

parseDouble :: Parser Expr
parseDouble = do
    _          <- parseSpaces
    intPart    <- parseStrInt
    _          <- validateChar '.'
    doublePart <- parseStrInt
    let fullInt = foldl (\result c -> 10 * result + toInteger (digitToInt c)) 0 (intPart ++ doublePart)
    return (Val (toRealFloat (scientific (toInteger fullInt) (-(length doublePart)))))

parseStrInt :: Parser String
parseStrInt = some (parseWhile isDigit)

parseWhile :: (Char -> Bool) -> Parser Char
parseWhile f = mfilter f pChar

parseExprInBraces :: Parser Expr
parseExprInBraces = do
    _ <- parseSpaces
    _ <- validateChar '('
    result <- parseAddSub
    _ <- parseSpaces
    _ <- validateChar ')'
    return result

parseSpaces :: Parser String
parseSpaces = do
    many (mfilter isSpace pChar)

validateChar :: Char -> Parser Char
validateChar char = do
    result <- mfilter (== char) pChar
    return result
