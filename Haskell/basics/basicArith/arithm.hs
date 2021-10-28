
-- naive implementation of pow where b is the base and e the Exponent
pow1 b 0 = 1
pow1 b e = b * pow1 b (e-1)   

--naive als endrekursion:
--xpow b e = powAcc b e 1

--powAcc b e acc = b (e-1) (acc*b)
xpow a b = powAcc a b 1
powAcc a b acc = a (b-1) (acc*a)



-- pow Rekursion mit halbieren des Exponenten
pow2 b 0 = 1
pow2 0 e = 0
pow2 b 1 = b
pow2 b e = if ((e `mod` 2) == 0) then (pow2 b (div e 2))* (pow2 b (div e 2)) else b* (pow2 b (e-1))