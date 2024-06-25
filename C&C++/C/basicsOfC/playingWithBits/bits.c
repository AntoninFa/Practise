#include <stdint.h>

#include "bits.h"

/**
 * implemementation of floor, cause I didn't knew if I could use math.h 
 *  
 */
int floorImpl(int n) {
    return(n - (n % 1));
}

/*
 * Returns the nth bit of the array A.
 */
int getN(uint64_t *A, size_t n)
{
    int index = (int)floorImpl(n/64);
    int bitNum = n%64;
    uint64_t num = A[index];
    return ((num >> bitNum) & (uint64_t)1);

}

/*
 * Sets the n'th bit of the array A.
 */
void setN(uint64_t *A, size_t n)
{
    int index = (int)floorImpl(n/64);
    int bitNum = n%64;
    A[index] = (A[index] | (((uint64_t)1) << bitNum));
}

/*
 * Clears the n'th bit of the array A.
 */
void clrN(uint64_t *A, size_t n)
{
    int index = (int)floorImpl(n/64);
    int bitNum = n%64;
    uint64_t num = A[index];
    A[index] = (num & (~(((uint64_t)1) << (bitNum ))));
}

/**
 * Rotates the bits of an unsigned 64-bit integer n times to the right
 */
uint64_t rar64(uint64_t i, int n) {
    return (i >> n)|(i << (64 - n)); 
}

/**
 * Rotates the bits of an unsigned 64-bit integer n times to the left
 */
uint64_t ral64(uint64_t i, int n) {
    return (i << n)|(i >> (64 - n)); 
}

/*
 * Rotates the integer i n bits to the right, if n is >= 0 
 * and to the left if n is < 0
 */
uint64_t rot(uint64_t i, int n)
{
    if (n >= 0) {
    return rar64(i, n);
} else {
    return ral64(i, -n);
}

}