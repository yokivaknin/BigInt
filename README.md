# BigInt - Big Integer Arithmetic Library

This Java library provides a BigInt class for performing arithmetic operations on arbitrarily large integers. The BigInt class represents a big integer using an ArrayList of digits and a boolean flag to indicate the sign (positive or negative).

# Features

1. Supports basic arithmetic operations: addition, subtraction, multiplication, and division.
2. Handles positive and negative numbers.
3. Throws an IllegalArgumentException for invalid input strings.
4. Throws an ArithmeticException for division by zero.

# Usage
1. Create BigInt objects:
    BigInt num1 = new BigInt("12345");
    BigInt num2 = new BigInt("-6789");
   
2. Perform arithmetic operations:
   BigInt sum = num1.plus(num2);
   BigInt difference = num1.minus(num2);
   BigInt product = num1.multiply(num2);
   BigInt quotient = num1.divide(num2); // May throw ArithmeticException for division by zero

3. Print the results:
   System.out.println("Sum: " + sum.toString());
   System.out.println("Difference: " + difference.toString());
   System.out.println("Product: " + product.toString());
   System.out.println("Division product: " + quotient.toString());
   
