module Merge where

-- test lists 
l1 = [1..]
l2 = [5..100]

l3 = [50..1000]

-- merges two sorted lists into one sorted list
merge (x:xs) [] = x:xs
merge [] (x:xs) = x:xs
merge (x:xs) (y:ys)
 | (x <= y) = x:merge (xs) (y:ys)
 | otherwise = y:merge (x:xs) (ys)


odds = 1:map (+2) odds

oddPrimes (p:ps) = p : (oddPrimes [p' | p' <- ps, p' `mod` p /= 0])
primes = 2 : oddPrimes (tail odds)

powers :: Integer->Integer->Integer-> [Integer]
powers n x  i = if (i <= n) then ((x^i):(powers n x (i+1))) else []

-- for each int i in primes return a list of i^(1to n) and then merge these lists 
-- Idee ist rekursiv zu mergen und immer mit head und rest merge aufzurufen

--primepowers :: Integer -> [Integer]
--primepowers n = Infinitymerge (head map (\x -> powers n x 1) primes) (map (\x -> powers n x 1) primes)

