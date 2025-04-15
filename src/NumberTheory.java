import java.math.BigInteger;

public class NumberTheory {

    // Checks if two numbers are relatively prime
    public static boolean isRelativelyPrime(BigInteger a, BigInteger b) {
        return gcd(a, b).equals(BigInteger.ONE); // Uses GCD to check relative primality
    }

    // Computes the GCD of two numbers using the Euclidean algorithm
    public static BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    // Determines if a number is prime
    public static boolean isPrime(BigInteger n) {
        if (n.compareTo(BigInteger.TWO) < 0) return false;
        if (n.equals(BigInteger.TWO)) return true;
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        BigInteger sqrt = n.sqrt().add(BigInteger.ONE);
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(sqrt) <= 0; i = i.add(BigInteger.TWO)) {
            if (n.mod(i).equals(BigInteger.ZERO)) return false;
        }
        return true;
    }

    // Computes Euler's totient function φ(n)
    public static BigInteger eulerTotient(BigInteger n) {
        BigInteger result = n;
        BigInteger i = BigInteger.TWO;

        while (!n.equals(BigInteger.ONE) && i.multiply(i).compareTo(n) <= 0) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                while (n.mod(i).equals(BigInteger.ZERO)) {
                    n = n.divide(i);
                }
                result = result.subtract(result.divide(i));
            }
            i = i.add(BigInteger.ONE);
        }
        if (n.compareTo(BigInteger.ONE) > 0) {
            result = result.subtract(result.divide(n));
        }
        return result;
    }

    // Finds the modular inverse of a modulo n using the Extended Euclidean Algorithm
    public static BigInteger modularInverse(BigInteger a, BigInteger n) {
        BigInteger[] result = extendedEuclidean(a, n);
        BigInteger x = result[1];
        return x.mod(n).add(n).mod(n); // Ensure positive result
    }

    // Extended Euclidean Algorithm: returns {gcd, x, y}
    public static BigInteger[] extendedEuclidean(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
        }
        BigInteger[] values = extendedEuclidean(b, a.mod(b));
        BigInteger gcd = values[0];
        BigInteger x = values[2];
        BigInteger y = values[1].subtract(a.divide(b).multiply(values[2]));
        return new BigInteger[]{gcd, x, y};
    }

    // Performs modular exponentiation: (base^exp) % mod
    public static BigInteger modularExponentiation(BigInteger base, BigInteger exp, BigInteger mod) {
        BigInteger result = BigInteger.ONE;
        base = base.mod(mod);

        while (exp.compareTo(BigInteger.ZERO) > 0) {
            if (exp.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                result = result.multiply(base).mod(mod);
            }
            exp = exp.divide(BigInteger.TWO);
            base = base.multiply(base).mod(mod);
        }
        return result;
    }

    // Solves a linear congruence equation ax ≡ b (mod n)
    public static BigInteger solveLinearCongruence(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger gcd = gcd(a, n);
        if (!b.mod(gcd).equals(BigInteger.ZERO)) {
            throw new ArithmeticException("No solution exists for the given inputs.");
        }

        BigInteger aPrime = a.divide(gcd);
        BigInteger bPrime = b.divide(gcd);
        BigInteger nPrime = n.divide(gcd);

        BigInteger x = modularInverse(aPrime, nPrime).multiply(bPrime).mod(nPrime);
        return x;
    }
}