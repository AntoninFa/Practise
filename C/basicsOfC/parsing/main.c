#include "testlib.h"
#include "parseint.h"

int main()
{
    test_start("parseint.c");

    test_equals_int(parseDecimalChar('0'), 0, "parseDecimalChar parses '0'");
    //2
    test_equals_int(parseDecimalChar('4'), 4, "parseDecimalChar parses '4'");
    test_equals_int(parseDecimalChar('9'), 9, "parseDecimalChar parses '9'");
    //4
    test_equals_int(parseInt("010"), 8, "parseInt parses octal number");
    test_equals_int(parseInt("01001"), 513, "parseInt parses octal number");
    //6
    test_equals_int(parseInt("10"), 10, "parseInt parses decimal number");
    test_equals_int(parseInt("1"), 1, "parseInt parses decimal number");
    //8
    test_equals_int(parseInt("1111"), 1111, "parseInt parses decimal number");
    test_equals_int(parseInt("1000"), 1000, "parseInt parses decimal number");
    //10
    test_equals_int(parseInt("11001"), 11001, "parseInt parses decimal number");
    test_equals_int(parseInt("?"), -1, "parseInt handles invalid input");
    //12
    test_equals_int(parseInt("0134"), 92, "parseInt parses octal number");
    test_equals_int(parseInt("53533"), 53533, "parseInt parses decimal number");
    test_equals_int(parseInt("1000101"), 1000101, "parseInt parses decimal number");
    test_equals_int(parseInt("?"), -1, "parseInt handles invalid input");
    test_equals_int(parseInt("³sfl4"), -1, "parseInt handles invalid input");
    test_equals_int(parseInt("*"), -1, "parseInt handles invalid input");
    test_equals_int(parseInt("  "), -1, "parseInt handles invalid input");
    test_equals_int(parseInt("   "), -1, "parseInt handles invalid input");

    return test_end();
}
