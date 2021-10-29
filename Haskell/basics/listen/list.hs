a = [1, 2, 5, 7]

-- length of a list
len [] = 0
len (x:xs) = 1 + len xs

-- is in List

isIn a [] = "False"
isIn a (x:xs) = if (x==a) then "true" else isIn a xs

