a = [1, 2, 5, 7]

-- length of a list
len [] = 0
len (x:xs) = 1 + len xs

-- is in List

isIn a [] = "False"
isIn a (x:xs) = if (x==a) then "true" else isIn a xs

-- app left right : Elemente aus left gefolgt von Elementen aus right
app [] r = r
app (x:xs) r = x:(app xs r)

-- Elemente aus list in umgedrehter Reihenfolge
rev [] = []
rev (x:xs) = app(rev xs) [x]

