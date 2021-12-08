module Fibo where 

fix :: (a->a) -> a 
fix = \f -> f $ fix f


fib :: Int-> Int 
fib = \n -> fix (\f n -> if n <=2 then 1 else f (n-2) + f (n-1)) n
