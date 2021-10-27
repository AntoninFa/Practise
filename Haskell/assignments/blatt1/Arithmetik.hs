module Arithmetik where 

-- naive implementation of pow where b is the base and e the Exponent
pow1 b 0 = 1
pow1 b e = b * pow1 b (e-1)   

pow2 b 0 = 1
pow2 b 2 = b*b
pow2 b e = if ((e `mod` 2) == 0) then (b * (pow2 b (e `div` 2))) else (b* (pow2 b (e-1)))

-- 4 4 -> 4* (pow 4 2)-> 4*4* (pow 4 1)-> 4*4*4*1