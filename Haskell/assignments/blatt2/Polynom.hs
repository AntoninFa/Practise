module Polynom where 
   
polynom = [1, 0, 3, 5]

cmult [] n = []
cmult (x:xs) n = map (\x -> n*x) (x:xs)



-- habe nicht ganz verstanden wie ich n dranmultipliziere :)
eval [] = 0
eval list = evalh list 1

evalh list n = foldr f (last list) (init list)
   where f a b = a+b*n






derh:: [Integer]-> Integer -> [Integer]
derh [] n = []
derh (x:xs) 0 = derh (xs) 1
derh (x:xs) n = (n*x) : (derh xs (n+1))


deriv [] = []
deriv (x:xs) = derh (x:xs) 0

