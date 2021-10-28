import GHC.Num.Backend (bignat_powmod_word)
-- defining simple mathematical functions like f(x) = sin(x) / x
f x = sin x / x
g x = x * f(x*x)
-- ghci> mult 4 3 
-- 12
mult a b = a*b 
