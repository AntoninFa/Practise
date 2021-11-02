module Polynom where 
   
polynom = [1, 0, 3, 5]

cmult [] n = []
cmult (x:xs) n = map (\x -> n*x) (x:xs)


--eval saveee mit foldr