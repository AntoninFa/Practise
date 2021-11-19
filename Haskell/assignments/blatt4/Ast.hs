module Ast where
import Text.Read (Lexeme(String))
import Language.Haskell.TH (Lit(IntegerL))

data Exp = Var String | Konst Integer | SumExp Exp Exp

type Env = Integer

--eval:: Env t -> Exp t -> Integer
eval SumExp e1 e2 
  | (Konst k1)  (Konst k2) = k1 + k2
  | (SumExp s1) (SumExp s2) = eval s1 + eval s2
  | (Konst k1) (Exp ex) = k1 + eval ex 
  | (Exp ex) (Konst k1) = eval ex + k1
  | (Var v) (Exp ex) = (read v ::Integer) + eval ex 
  | (Exp ex) (Konst v) = eval ex + (read v ::Integer)


--insertV (Val a) vn al = var c









