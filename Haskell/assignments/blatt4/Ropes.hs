module Ropes where

data Rope a = Leaf [a] | Inner (Rope a) Int (Rope a)

listlength:: [a]-> Int
listlength = foldr (\ x -> (+) 1) 0

ropeLength:: Rope a -> Int
ropeLength (Leaf a)= listlength a
ropeLength (Inner _ i (Leaf a)) = i + listlength a
ropeLength (Inner _ i (Inner (Rope a1) iL (Rope a2))) = i +  (Inner (Rope a1) iL (Rope a2))

