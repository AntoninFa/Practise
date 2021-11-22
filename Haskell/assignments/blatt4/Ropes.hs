module Ropes where

data Rope a = Leaf [a] | Inner (Rope a) Int (Rope a)

listlength:: [a]-> Int
listlength = foldr (\ x -> (+) 1) 0

ropeLength:: Rope a -> Int
ropeLength (Leaf a)= listlength a
ropeLength (Inner _ i r2) = i + ropeLength r2

ropeConcat :: Rope a -> Rope a -> Rope a
ropeConcat (Leaf a) (Inner l i r) = (Inner (Leaf a) i (Inner l i r))
ropeConcat (Inner l i r) r2 = Inner l i (ropeConcat r r2)

MusterropeConcat :: Rope a -> Rope a -> Rope a
MusterropeConcat l r = Inner l (ropeLength l) r 

