import java.util.Locale;
import java.util.Scanner;

/**
 * Task 4 - Currency Converter
 *
 * Converts between USD, INR, EUR, GBP and JPY using fixed sample rates. The
 * rates are relative to 1 USD and are not live market values.
 */
public class CurrencyConverter {

    static final String[] CODES = {"USD", "INR", "EUR", "GBP", "JPY"};
    static final String[] NAMES = {
        "US Dollar", "Indian Rupee", "Euro", "British Pound", "Japanese Yen"
    };
    static final double[] RATES_PER_USD = {1.00, 83.00, 0.92, 0.79, 150.00};
    static final int[] DECIMALS = {2, 2, 2, 2, 0};

    static final String AS_OF = "2026-10-26";
    static final double MAX_AMOUNT = 1000000000000.00;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            runDemo();
        } else {
            runConverter();
        }
        scanner.close();
    }

    static void runConverter() {
        System.out.println("=== Currency Converter ===");
        showTable();
        System.out.println("Sample rates as of " + AS_OF
                + ". They are not live market rates.");

        boolean again = true;
        while (again) {
            int from = chooseCurrency("Convert from");
            if (from == -1) {
                System.out.println("Input ended. Goodbye!");
                return;
            }

            int to = chooseCurrency("Convert to");
            if (to == -1) {
                System.out.println("Input ended. Goodbye!");
                return;
            }

            Double amount = readAmount("Amount in " + CODES[from] + ": ");
            if (amount == null) {
                System.out.println("Input ended. Goodbye!");
                return;
            }

            if (!validAmount(amount.doubleValue())) {
                System.out.println("Enter an amount from 0 to "
                        + formatAmount(MAX_AMOUNT, 0) + ".");
            } else {
                printConversion(amount.doubleValue(), from, to);
            }

            again = askYesNo("Convert another amount? (y/n): ");
        }

        System.out.println("Goodbye!");
    }

    static void printConversion(double amount, int from, int to) {
        double result = convert(amount, from, to);
        double rate = exchangeRate(from, to);

        System.out.println();
        System.out.println(formatAmount(amount, from) + " " + CODES[from]
                + " = " + formatAmount(result, to) + " " + CODES[to]);
        System.out.println("Rate: 1 " + CODES[from] + " = "
                + String.format(Locale.US, "%.6f", rate) + " " + CODES[to]);
        System.out.println();
    }

    static double convert(double amount, int from, int to) {
        double amountInUsd = amount / RATES_PER_USD[from];
        double converted = amountInUsd * RATES_PER_USD[to];
        return round(converted, DECIMALS[to]);
    }

    static double exchangeRate(int from, int to) {
        return round(RATES_PER_USD[to] / RATES_PER_USD[from], 6);
    }

    static double round(double value, int decimalPlaces) {
        double factor = Math.pow(10.0, decimalPlaces);
        return Math.round(value * factor) / factor;
    }

    static String formatAmount(double amount, int currency) {
        String pattern = "%,." + DECIMALS[currency] + "f";
        return String.format(Locale.US, pattern, amount);
    }

    static void showTable() {
        System.out.printf(Locale.US, "%-4s %-18s %14s %8s%n",
                "Code", "Currency", "Per 1 USD", "Decimals");
        for (int i = 0; i < CODES.length; i++) {
            System.out.printf(Locale.US, "%-4s %-18s %14s %8d%n",
                    CODES[i],
                    NAMES[i],
                    String.format(Locale.US, "%.2f", RATES_PER_USD[i]),
                    DECIMALS[i]);
        }
        System.out.println();
    }

    static int chooseCurrency(String label) {
        while (true) {
            String line = readLine(label + " (1-5): ");
            if (line == null) {
                return -1;
            }

            try {
                int choice = Integer.parseInt(line.trim());
                if (choice >= 1 && choice <= CODES.length) {
                    return choice - 1;
                }
            } catch (NumberFormatException e) {
                // The message below handles bad input.
            }

            System.out.println("Please enter a number from 1 to "
                    + CODES.length + ".");
        }
    }

    static boolean validAmount(double amount) {
        return !Double.isNaN(amount)
                && !Double.isInfinite(amount)
                && amount >= 0.0
                && amount <= MAX_AMOUNT;
    }

    /**
     * Reads an amount.
     *
     * @return the amount, or null when input ends
     */
    static Double readAmount(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return null;
            }

            try {
                return Double.valueOf(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount, for example 100.50.");
            }
        }
    }

    static boolean askYesNo(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (line == null) {
                return false;
            }

            String answer = line.trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }

            System.out.println("Please answer y or n.");
        }
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    static void runDemo() {
        System.out.println("=== Currency Converter (demo) ===");
        showTable();
        System.out.println("Sample rates as of " + AS_OF
                + ". They are not live market rates.");

        printConversion(100.00, 0, 1);
        printConversion(8300.00, 1, 0);
        printConversion(100.00, 2, 4);
        printConversion(100.00, 3, 2);
        printConversion(250.00, 3, 2);
        printConversion(123.456, 0, 0);
    }
}
