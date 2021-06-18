#include "calc.h"

#include <gtest/gtest.h>

TEST(Calc, err)
{
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "fix"));
    EXPECT_EQ("Unknown operation fix\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(11, process_line(11, "sqrt"));
    EXPECT_EQ("Unknown operation sqrt\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(17, process_line(17, "\\ 11"));
    EXPECT_EQ("Unknown operation \\ 11\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, set)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "0"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "0000"));
    EXPECT_DOUBLE_EQ(0, process_line(101, "0"));
    EXPECT_DOUBLE_EQ(1, process_line(101, "01"));
    EXPECT_DOUBLE_EQ(2, process_line(101, "02"));
    EXPECT_DOUBLE_EQ(3, process_line(101, "0003"));
    EXPECT_DOUBLE_EQ(13, process_line(0, "13"));
    EXPECT_DOUBLE_EQ(5, process_line(99, "5."));
    EXPECT_DOUBLE_EQ(1099, process_line(99, "1099"));
    EXPECT_DOUBLE_EQ(0.127, process_line(99, "0.127"));
    EXPECT_DOUBLE_EQ(0.0101, process_line(99, "0.0101"));
    EXPECT_DOUBLE_EQ(0.05625, process_line(1113, "0.05625"));
    EXPECT_DOUBLE_EQ(1234567890.0, process_line(1, "1234567890"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(1, process_line(1, "12345678900000"));
    EXPECT_EQ("Argument isn't fully parsed, suffix left: '0000'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(99, process_line(99, "5 "));
    EXPECT_EQ("Argument parsing error at 1: ' '\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "0.001 11"));
    EXPECT_EQ("Argument parsing error at 5: ' 11'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "123-456"));
    EXPECT_EQ("Argument parsing error at 3: '-456'\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, add)
{
    EXPECT_DOUBLE_EQ(7, process_line(0, "+7"));
    EXPECT_DOUBLE_EQ(7, process_line(5, "+ 2"));
    EXPECT_DOUBLE_EQ(7, process_line(5, "+ \t\t   2"));
    EXPECT_DOUBLE_EQ(2.34, process_line(1.5, "+ 0.84"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(9, process_line(9, "+    12345678900000"));
    EXPECT_EQ("Argument isn't fully parsed, suffix left: '0000'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(99, process_line(99, "+ 1 "));
    EXPECT_EQ("Argument parsing error at 3: ' '\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(5, process_line(5, "+ 1 2"));
    EXPECT_EQ("Argument parsing error at 3: ' 2'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "+0.001 11"));
    EXPECT_EQ("Argument parsing error at 6: ' 11'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(99, process_line(99, "+ -"));
    EXPECT_EQ("Argument parsing error at 2: '-'\nNo argument for a binary operation\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(113, process_line(113, "+"));
    EXPECT_EQ("No argument for a binary operation\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(113, process_line(113, "+) 10"));
    EXPECT_EQ("Argument parsing error at 1: ') 10'\nNo argument for a binary operation\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, sub)
{
    EXPECT_DOUBLE_EQ(-11, process_line(0, "- 11"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "-0"));
    EXPECT_DOUBLE_EQ(0, process_line(3, "-3"));
    EXPECT_DOUBLE_EQ(-3, process_line(7, "-10"));
    EXPECT_DOUBLE_EQ(-12344.6789, process_line(1, "- 12345.67890"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(77, process_line(77, "- 123a3"));
    EXPECT_EQ("Argument parsing error at 5: 'a3'\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, mul)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "* 0"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "*131"));
    EXPECT_DOUBLE_EQ(0, process_line(99, "* 0"));
    EXPECT_DOUBLE_EQ(8, process_line(2, "* 4"));
    EXPECT_DOUBLE_EQ(-16, process_line(-4, "*4"));
}

TEST(Calc, div)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "/ 11"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(11, process_line(11, "/ 0"));
    EXPECT_EQ("Bad right argument for division: 0\n", testing::internal::GetCapturedStderr());
    EXPECT_DOUBLE_EQ(3, process_line(6, "/ 2"));
    EXPECT_DOUBLE_EQ(0.7, process_line(7, "/ 10"));
    EXPECT_DOUBLE_EQ(0.3333333333333333, process_line(1, "/ 3"));
    EXPECT_DOUBLE_EQ(-0.5, process_line(-2, "/ 4"));
    EXPECT_DOUBLE_EQ(100, process_line(10, "/ 0.1"));
}

TEST(Calc, rem)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "% 3"));
    EXPECT_DOUBLE_EQ(0, process_line(4, "%4"));
    EXPECT_DOUBLE_EQ(0, process_line(-24, "%4"));
    EXPECT_DOUBLE_EQ(-3, process_line(-13, "%5"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(-1, process_line(-1, "%0"));
    EXPECT_EQ("Bad right argument for remainder: 0\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, neg)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "_"));
    EXPECT_DOUBLE_EQ(1, process_line(-1, "_"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(1, process_line(1, "_ "));
    EXPECT_EQ("Unexpected suffix for a unary operation: ' '\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(1, process_line(1, "_1"));
    EXPECT_EQ("Unexpected suffix for a unary operation: '1'\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, pow)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "^1"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "^2"));
    EXPECT_DOUBLE_EQ(1, process_line(119, "^0"));
    EXPECT_DOUBLE_EQ(37, process_line(37, "^1"));
    EXPECT_DOUBLE_EQ(25, process_line(-5, "^2"));
    EXPECT_DOUBLE_EQ(-27, process_line(-3, "^3"));
    EXPECT_DOUBLE_EQ(5, process_line(25, "^0.5"));
}

TEST(Calc, sqrt)
{
    EXPECT_DOUBLE_EQ(1, process_line(1, "SQRT"));
    EXPECT_DOUBLE_EQ(0.7, process_line(0.49, "SQRT"));
    EXPECT_DOUBLE_EQ(5, process_line(25, "SQRT"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(-1, process_line(-1, "SQRT"));
    EXPECT_EQ("Bad argument for SQRT: -1\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, bogus)
{
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "b1101"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b 1101"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b1101b"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b11012"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b1101.3"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b0.1101.1"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0bb1101"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "1b1101"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "01b1101"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "01.0b101"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0bFFF"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "03578"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0 1"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0357.8"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0357.0b1"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "07.357.5"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0b1+1"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "5EA"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0xFG"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0xh"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0.xf"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0.0xf"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "12a34"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0a34"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0ba34"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "01.0xab"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "011.FF"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "123x456"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0aeb1"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "0x aa"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "123.x"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "123.xaa"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "11xx"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "11x."));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, ".0b11"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, ".011"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, ".0xaa"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "x111"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "12ff"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "+.0123"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(997, process_line(997, "- x11"));
    EXPECT_FALSE(testing::internal::GetCapturedStderr().empty());
}

TEST(Calc, binary)
{
    EXPECT_DOUBLE_EQ(1, process_line(99, "0b1"));
    EXPECT_DOUBLE_EQ(1, process_line(99, "0b001"));
    EXPECT_DOUBLE_EQ(1, process_line(99, "0b1.00"));
    EXPECT_DOUBLE_EQ(0, process_line(99, "0b0"));
    EXPECT_DOUBLE_EQ(0, process_line(99, "0b0.00"));
    EXPECT_DOUBLE_EQ(5, process_line(99, "0b101"));
    EXPECT_DOUBLE_EQ(0.5, process_line(99, "0b0.1"));
    EXPECT_DOUBLE_EQ(0.625, process_line(99, "0b0.101"));
    EXPECT_DOUBLE_EQ(13.75, process_line(0, "0b1101.1100"));
    EXPECT_DOUBLE_EQ(1.748, process_line(1.123, "+0b0.101"));
    EXPECT_DOUBLE_EQ(-1.4, process_line(0.1, "-0b1.1"));
    EXPECT_DOUBLE_EQ(1.5, process_line(2.25, "^0b0.1"));
}

TEST(Calc, octal)
{
    EXPECT_DOUBLE_EQ(0, process_line(111, "00"));
    EXPECT_DOUBLE_EQ(0, process_line(111, "00.0"));
    EXPECT_DOUBLE_EQ(1, process_line(111, "01"));
    EXPECT_DOUBLE_EQ(1, process_line(111, "01.000"));
    EXPECT_DOUBLE_EQ(8, process_line(111, "010"));
    EXPECT_DOUBLE_EQ(121, process_line(111, "0171"));
    EXPECT_DOUBLE_EQ(0.125, process_line(111, "00.1"));
    EXPECT_DOUBLE_EQ(1.015625, process_line(111, "01.01"));
    EXPECT_DOUBLE_EQ(1871.84375, process_line(0, "03517.66"));
    EXPECT_DOUBLE_EQ(11.703125, process_line(1, "+012.55"));
    EXPECT_DOUBLE_EQ(-9.703125, process_line(1, "-012.55"));
    EXPECT_DOUBLE_EQ(1.5, process_line(2.25, "^00.4"));
}

TEST(Calc, hexal)
{
    EXPECT_DOUBLE_EQ(0, process_line(1001, "0x0"));
    EXPECT_DOUBLE_EQ(0, process_line(1001, "0x000.0"));
    EXPECT_DOUBLE_EQ(1, process_line(1001, "0x1"));
    EXPECT_DOUBLE_EQ(1, process_line(1001, "0x01"));
    EXPECT_DOUBLE_EQ(1, process_line(1001, "0x001.000"));
    EXPECT_DOUBLE_EQ(0.0625, process_line(1001, "0x0.1"));
    EXPECT_DOUBLE_EQ(0.00390625, process_line(1001, "0x0.01"));
    EXPECT_DOUBLE_EQ(0.625, process_line(1001, "0x0.a"));
    EXPECT_DOUBLE_EQ(0.5, process_line(1001, "0x0.8"));
    EXPECT_DOUBLE_EQ(0.9375, process_line(1001, "0x0.F"));
    EXPECT_DOUBLE_EQ(13, process_line(1001, "0xd"));
    EXPECT_DOUBLE_EQ(14, process_line(1001, "0x00e"));
    EXPECT_DOUBLE_EQ(215.625, process_line(0, "0xD7.a"));
    EXPECT_DOUBLE_EQ(215.625, process_line(0, "0xd7.A"));
    EXPECT_DOUBLE_EQ(21.85, process_line(1.1, "+0x14.C"));
    EXPECT_DOUBLE_EQ(-16.0328125, process_line(1.1, "-0x11.22"));
    EXPECT_DOUBLE_EQ(1.5, process_line(2.25, "^0x0.8"));
}
