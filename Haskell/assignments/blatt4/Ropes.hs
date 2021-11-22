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

musterropeConcat :: Rope a -> Rope a -> Rope a
musterropeConcat l r = Inner l (ropeLength l) r

ropeSplitAt :: Int-> Rope a -> (Rope a, Rope a)
ropeSplitAt i (Leaf a) = (Leaf (take i a), Leaf(drop i a))
ropeSplitAt i (Inner l w r) 
     | (i < w) = let (ll, lr) = ropeSplitAt i l     -- let sagt, dass das Tuper(ll,lr) den Inhalt aus ropeSplitAt i l und das wird dann im in genutzt-> in ist der Ergb. Tupel
                   in (ll, ropeConcat lr r)
     | (i > w) = let (rl, rr) = ropeSplitAt (i-w) r
                   in (ropeConcat l rl, rr)
     | otherwise = (l,r)

