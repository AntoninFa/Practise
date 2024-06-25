#include "testlib.h"
#include "bits.h"


int main()
{
    test_start("bits.c");

    uint64_t A[10] = {0};
    uint64_t Num[3] = {4,7,0};
    test_equals_int(getN(Num, 61), 0, "61 bit in 4");
    //2
    test_equals_int(getN(Num, 63), 0, "63 bit in 4");
    test_equals_int(getN(Num, 64), 1, "64 bit in second Cell");
    //4
    test_equals_int(getN(Num, 127), 0, "63 bit in 7");
    test_equals_int(getN(Num, 126), 0, "62 bit in 7");
    test_equals_int(getN(Num, 65), 1, "65 bit in 7");
    test_equals_int(getN(Num, 60), 0, "60 bit in 7");
    test_equals_int(getN(Num, 66), 1, "66 bit in 7");
    test_equals_int(getN(Num, 67), 0, "67 bit in 7");
    //10
    test_equals_int(getN(Num, 2), 1, "2 bit in 4");
    test_equals_int(getN(Num, 1), 0, "1 bit in 4");
    test_equals_int(getN(Num, 2), 1, "2 bit in 4");
    test_equals_int(getN(Num, 0), 0, "0 bit in 4");
    test_equals_int(getN(Num, 3), 0, "3 bit in 4");
    test_equals_int(getN(Num, 15), 0, "15 bit in 4");

    //16
    test_equals_int(getN(A, 100), 0, "bit 100 is initially 0");
    test_equals_int(A[1], 0, "is zero");
    setN(A, 100);
    test_equals_int(A[1], 0, "is zero");
    //17
    test_equals_int(getN(A, 100), 1, "bit 100 is then set to 1");
    clrN(A, 100);
    test_equals_int(getN(A, 100), 0, "bit 100 is finally cleared again");

    test_equals_int64(rot(0x1234, 8), 0x3400000000000012ULL, "rot(0x1234, 8)");
    test_equals_int64(rot(((uint64_t) 1), 63), 2, "rotate 63 times");
    test_equals_int64(rot(((uint64_t) 1), -4), 16, "rotate -4 times");


    return test_end();
}
