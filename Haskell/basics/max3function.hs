--implementations of a max function with 3 params
-- disclaimer: again no checks for cases like negative inputs etc.
-- naive with if clauses:
-- this pre def with types isn't needed in haskell, but might help the programmer understand
max3 :: Int -> Int -> Int -> Int
max3 x y z = if(x > y && x > z) then x else if (y>x && y > z) then y else if (z> x && z > y) then z else error "not possible" 

max3Guards x y z
  | x >= y && x >= z = x
  | y >=x && y >= z = y
  | z >= x && z >= y = z
  | otherwise = error "not possible"

-- shorter and more elegant implementation that uses recursion
maxR x y z = if x > y && x > z then x else maxR z x y

-- implementation using the max(x,y) function of hakell
maxC x y z = max x $ max y z



