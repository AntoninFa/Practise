-- fakulty in haskell
-- not checking for negative inputs etc.
--naive recursive implementation of fakulty without an accu
fak n = if (n==0) then 1 else n* fak(n-1)

-- TODO fakulty with akku