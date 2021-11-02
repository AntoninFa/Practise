lu = [4,2,15, 14, 55, 1, 0, 99]

-- length of a list
len [] = 0
len (x:xs) = 1 + len xs

length list = foldr (+) 0 (map (\x -> 1) list)

-- RIP
length1 = foldr (\x n -> n +1) 0
