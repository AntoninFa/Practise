module Fibs where 

fibs :: [Integer]
fibs = [a + b | (a,b) <- zip (1: fibs) (0:1:fibs) ]

fiboTest :: [Integer]
fiboTest = zipWith (+) (1:fiboTest) (0:1:fiboTest)

