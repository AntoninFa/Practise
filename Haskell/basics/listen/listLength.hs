lu = [4,2,15, 14, 55, 1, 0, 99]

-- length of a list
len [] = 0
len (x:xs) = 1 + len xs

length list = foldr (+) 0 (map (\x -> 1) list)

listlength:: [a]-> Int
listlength xs = foldr (\ x -> (+) 1) 0 xs


listlength1:: [a]-> Int
listlength1 = foldr (\ x -> (+) 1) 0
