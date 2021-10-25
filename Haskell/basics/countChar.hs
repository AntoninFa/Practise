-- DOESN't WORK YET: TODO: check how to define a list in haskell
-- count Chars of a string
--let num = [1, 4, 10]

--let two = ['H', 'I', 'D', 'U']

equal a b = if (a == b) then 1 else 0
countChar c [] = 0
countChar c (x:xs) = equal x c + countChar c xs

