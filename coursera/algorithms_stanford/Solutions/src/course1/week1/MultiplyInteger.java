package course1.week1;

import java.math.BigInteger;

/**
 * Created by b06514a on 4/18/2017.
 */
public class MultiplyInteger {

    public static void main(String[] args) {

        BigInteger num1 = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
        BigInteger num2 = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");

        BigInteger result = multiply(num1, num2);

        System.out.println(result.toString());
        System.out.println(result.equals(num1.multiply(num2)));

    }

    private static BigInteger multiply(BigInteger number1, BigInteger number2) {
        int n = number1.toString().length();
        if (n % 2 != 0)
            n--;

        if (n <= 1) {
            return number1.multiply(number2);
        }
        BigInteger a = getUpperHalf(number1, n);
        BigInteger b = getLowerHalf(number1, n);
        BigInteger c = getUpperHalf(number2, n);
        BigInteger d = getLowerHalf(number2, n);

        BigInteger ac = multiply(a, c);
        BigInteger bd = multiply(b, d);
        BigInteger adbc = multiply(a.add(b), c.add(d)).subtract(ac).subtract(bd);

        return ac.multiply(BigInteger.TEN.pow(n)).
                add(adbc.multiply(BigInteger.TEN.pow(n / 2))).
                add(bd);
    }

    private static BigInteger getLowerHalf(final BigInteger number1, int n) {
        return number1.subtract(getUpperHalf(number1, n).multiply(BigInteger.TEN.pow(n / 2)));
    }

    private static BigInteger getUpperHalf(final BigInteger number1, int n) {
        return number1.divide(BigInteger.TEN.pow(n / 2));
    }

}
