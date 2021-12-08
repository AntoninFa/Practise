module Ast where
import Text.Read (Lexeme(String))
import Language.Haskell.TH (Lit(IntegerL))

data Exp t = Var t | Konst Integer | SumE (Exp t) (Exp t)

-- Wie testet man das ohne Env zu haben?

type Env t = Integer 


eval:: Env t -> Exp t -> Integer
eval env (Var v) = env v 
eval env (Konst c) = c 
eval env (SumE e1 e2) = (eval env e1) + (eval env e2)



--eval SumExp e1 e2 LÄUFT NED
--  | (Konst)  (Konst) = e1 + e2
--  | (SumExp ) (SumExp) = eval e1 + eval e2
--  | (Konst k1) (Exp ex) = k1 + eval ex 
--  | (Exp ex) (Konst k1) = eval ex + k1
--  | (Var v) (Exp ex) = (read v ::Integer) + eval ex 
--  | (Exp ex) (Konst v) = eval ex + (read v ::Integer)


--insertV (Val a) vn al = var c









