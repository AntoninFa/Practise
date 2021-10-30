-- fakulty in haskell
-- not checking for negative inputs etc.
--naive recursive implementation of fakulty without an accu
fakN n = if (n==0) then 1 else n * fak(n-1)
-- hat O(n) rekursive Aufrufe und O(n) Speicherverbrauch
-- => 3*(2*(1*(fak 0)))
-- => 3*(2*(1*1))

-- mit Akku:
fakAcc n acc = if (n==0) then acc else fakAcc (n-1) (n*acc)

fak n = fakAcc n 1
-- rekursive Aufrufe O(n), aber Speicherverbrauch O(1)^1
-- fak 3 => fakAcc 3 1 => fakAcc 2 3 => fakAcc 1 6 => fakAcc 0 6




