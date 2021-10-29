module Sort where

-- for testing :)
l = [1,4,5,14,16]
l2 = [3, 4, 8, 20]
lu = [4,2,15, 14, 55, 1, 0, 99]

-- sorts an int into a sorted list
insert e [] = [e]
insert e (x:xs)
 | e < x = e:x:xs
 | otherwise = x:insert e xs

-- takes a list of integers, and returns a sorted list of integers
insertSort [] = []
insertSort [x] = [x]
insertSort (x:xs) = insert x (insertSort xs)

-- merges two sorted lists into one sorted list
merge (x:xs) [] = x:xs
merge [] (x:xs) = x:xs
merge (x:xs) (y:ys)
 | (x <= y) = x:merge (xs) (y:ys)
 | otherwise = y:merge (x:xs) (ys)


mergeSort :: Ord x => [x] -> [x]
mergeSort [] = []
mergeSort [x] = [x]
mergeSort (x:xs) = let getFirstHalf (x:xs) = take (div (length (x:xs)) 2)
 in merge (mergeSort (getFirstHalf (x:xs))) (mergeSort (getFirstHalf(x:xs)))

-- end
