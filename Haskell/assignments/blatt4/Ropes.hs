module Ropes where

data Rope a = Leaf [a] | Inner (Rope a) Int (Rope a)

listlength:: [a]-> Int
listlength = foldr (\ x -> (+) 1) 0

ropeLength:: Rope a -> Int
ropeLength (Leaf a)= listlength a
ropeLength (Inner _ i r2) = i + ropeLength r2

ropeConcat :: Rope a -> Rope a -> Rope a
ropeConcat Leaf a (Inner r1 i r2) =
