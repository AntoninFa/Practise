-- for testing :)
l = [1,4,5,14,16]
l2 = [3, 4, 8, 20]
lu = [4,2,15, 14, 55, 1, 0, 99]

-- Insertion Sort:

-- sorts an int into a sorted list
insert e [] = [e]
insert e (x:xs)
 | e < x = e:x:xs
 | otherwise = x:insert e xs

-- takes a list of integers, and returns a sorted list of integers
insertSort [] = []
insertSort (x:xs) = insert x (insertSort xs)


-- Merge Sort:

--merging two sorted lists into one big sorted list
merge xs [] = xs
merge [] ys = ys
merge (x:xs) (y:ys)
 | x <= y = x : merge xs (y:xs)
 | otherwise = y : merge (x:xs) ys

-- the actual MergeSort:
mergeSort [] = []
mergeSort [x] = [x]
mergeSort xs = merge
 (mergeSort (take (length xs `div` 2) xs))
 (mergeSort (drop (length xs `div` 2) xs))

-- quick Sort:
qsort :: (Ord t) => [t] -> [t]
qsort [] = []
qsort (p:ps) = (qsort [x | x <- ps, x <= p]) ++p: (qsort [x | x <- ps, x > p])

qSortZ [] = []
qSortZ (p:ps) = (qsort (filter (\x -> x <=p) ps)) ++p:(qsort (filter (\x -> x > p) ps))





qSortSZ [] = []
qSortSZ (p:ps) = (qsort (filter (<= p) ps)) ++p: (qsort (filter (> p) ps))


