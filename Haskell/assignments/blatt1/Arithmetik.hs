module Arithmetik where 

-- naive implementation of pow where b is the base and e the Exponent
pow1 b 0 = 1
pow1 b e = b * pow1 b (e-1)   

pow2 b 0 = 1
pow2 0 e = 0
pow2 b 1 = b
pow2 b e = if ((e `mod` 2) == 0) then (pow2 b (div e 2))* (pow2 b (div e 2)) else b* (pow2 b (e-1))