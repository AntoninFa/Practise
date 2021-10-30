
-- fibonacci number, naive impl
fibN 0 = 0
fibN 1 = 1
fibN n = (fib (n-1)) + (fib (n-2))

-- rek. Aufrufe O(2^n), fib erzeugt Aufrufbaum 
-- (Ein Baum der tiefe h hat 2^(h-1) Blätter und 2^t Knoten

-- fibbo mit Akku:

fibAcc n n1 n2
 | (n== 0) =n1
 | (n== 1) = n2
 | otherwise = fibAcc (n-1) n2 (n1 * n2)

fib n = fibAcc n 0 1

-- O(n) rekursive Aufrufe 
-- Endrekursion + Akku ~ while + lokale Variable