import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("1. Encrypt Message");
            System.out.println("2. Decrypt Message");
            System.out.println("3. Find Remainder");
            System.out.println("4. Exit");
            System.out.print("> ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: encryptMessage(); break;
                case 2: decryptMessage(); break;
                case 3: findRemainder(); break;
                case 4: System.exit(0);
            }

        } while (choice != 3);
    }

    public static void encryptMessage() {
        System.out.print("Message to Encrypt: ");
        String message = scanner.nextLine();

        System.out.print("n: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        BigInteger mod = BigInteger.valueOf(n);

        System.out.print("e: ");
        int e = scanner.nextInt();
        scanner.nextLine();

        BigInteger exponent = BigInteger.valueOf(e);

        message = message.toLowerCase();

        ArrayList<BigInteger> encryptedCharacters = new ArrayList<>();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (!(97 <= c && c <= 122)) continue;

            int charIndex = c - 97;

            BigInteger base = BigInteger.valueOf(charIndex);
            BigInteger encryptedChar = NumberTheory.modularExponentiation(base, exponent, mod);

            encryptedCharacters.add(encryptedChar);
        }

        System.out.println(encryptedCharacters);
    }

    public static void decryptMessage() {
        System.out.print("Message to Decrypt: ");
        String message = scanner.nextLine();

        System.out.print("n: ");
        BigInteger n = BigInteger.valueOf(scanner.nextInt());
        scanner.nextLine();

        System.out.print("e: ");
        BigInteger e = BigInteger.valueOf(scanner.nextInt());
        scanner.nextLine();

        BigInteger d = NumberTheory.modularInverse(e, NumberTheory.eulerTotient(n));
        System.out.println(d);

        StringBuilder decryptedMessage = new StringBuilder();

        String[] characters = message.split(" ");
        for (String character : characters) {
            BigInteger charValue = BigInteger.valueOf(Integer.parseInt(character));
            BigInteger decryptedCharValue = NumberTheory.modularExponentiation(charValue, d, n);
            decryptedMessage.append((char) (decryptedCharValue.intValue() + 97));
        }

        System.out.println(decryptedMessage);
    }

    public static void findRemainder() {
        System.out.print("A: ");
        BigInteger a = BigInteger.valueOf(scanner.nextInt());
        scanner.nextLine();

        System.out.print("B: ");
        BigInteger b = BigInteger.valueOf(scanner.nextInt());
        scanner.nextLine();

        System.out.println(a.remainder(b));
    }

}