module Arithmetik where

-- naive implementation of pow where b is the base and e the Exponent
pow1 b 0 = 1
pow1 b e = b * pow1 b (e-1)

--test endrekursive impl von pow1
testpow b e = powAcc b e 1
powAcc b 0 acc = acc
powAcc b e acc = powAcc b (e-1) (acc*b)


pow2 b 0 = 1
pow2 0 e = 0
pow2 b 1 = b
pow2 b e = if ((e `mod` 2) == 0) then (pow2 b (div e 2))* (pow2 b (div e 2)) else b* (pow2 b (e-1))

powA b 0 acc  = acc
powA 0 e acc = 0
powA b 1 acc  = b
powA b e acc = if(e < 0 ) then error "negative exponent not allowed"
    else  if ((e `mod` 2) == 0) then (pow2 b (div e 2))* (pow2 b (div e 2)) else powA b (e-1) (acc*b)

pow3 b e = powA b e 1


-- DISCLAIMER: Not sure if this works properly
isPrime n
  | n < 2 = error "prime numbers have to be > 1"
  | otherwise = tryToDiv n 2 (sqrt n)
   where
   tryToDiv n m l
       | n `mod` m == 0 = "n is not a prime number"
       | m == l = "n is prime"
       | otherwise = tryToDiv n (m+1) l





