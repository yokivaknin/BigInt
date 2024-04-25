import java.math.BigInteger;
import java.util.Scanner;

//The Main class contain just the main method witch get from the user 2 big numbers
// and present the results of basic arithmetical operation between them.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // get first number from user
        BigInt num1 = null;
        while (num1 == null) {
            System.out.print("Enter first number: ");
            String numString = scanner.nextLine();
            try {
                num1 = new BigInt(numString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid number.");
            }
        }

        // get second number from user
        BigInt num2 = null;
        while (num2 == null) {
            System.out.print("Enter second number: ");
            String numString = scanner.nextLine();
            try {
                num2 = new BigInt(numString);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid number");
            }
        }

        // perform arithmetic operations
        BigInt sum = new BigInt (num1.plus(num2));
        BigInt difference = new BigInt(num1.minus(num2)) ;
        BigInt product = new BigInt (num1.multiply(num2));

        // print results
        System.out.println("Sum: " + sum.toString());
        System.out.println("Difference: " + difference.toString());
        System.out.println("Product: " + product.toString());
        BigInt quotient = null;
        try {
            quotient = num1.divide(num2);
            System.out.println("Division product: " + quotient.toString());
        } catch (ArithmeticException e) {
            System.out.println( e.getMessage());
        }
    }
}

