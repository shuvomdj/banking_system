package twentyfive.fall.oop.group2.lesson1.class6;

import java.time.LocalDate;  // Class to call local date not time
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BankingSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // taking input in the system

        // Account Holder will Input Name to make more familiar to him
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();

        // Account Holder will Input Acc. No.
        System.out.print("Account No: ");
        String accountNumber = scanner.nextLine();

        // I Fixed a Default Account Balance is 1000.00 Yen
        final double DEFAULT_BALANCE = 1_000.00;
        double balance = DEFAULT_BALANCE;

        boolean running = true;  // Script Running True or False

        while (running) {
            //  Account Statement & Activity Menu:
            printStatement(accountNumber, name, balance);

            System.out.println();
            System.out.println("Menu: ");   //  Select item from menu
            System.out.println("1) Withdraw");
            System.out.println("2) Deposit");
            System.out.println("3) Know Interest Rate on Balance");
            System.out.println("0) Exit");
            System.out.print("Select option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine(); // Clear Input
                System.out.println("Invalid input. Try again.");
                continue;
            }
            // Java Switch Statement
            switch (choice) {
                case 1: {
                    // For Withdraw
                    System.out.println();
                    System.out.println("Your Current Balance: " + formatMoney(balance));
                    System.out.print("Withdrawal Amount: ");
                    double amount = scanner.nextDouble();  // Input withdraw amount
                    scanner.nextLine();

                    double previous = balance;
                    double updated = withdraw(balance, amount);

                    if (updated != previous) {
                        System.out.println();
                        System.out.println("--------- After Withdrawal ---------");
                        System.out.println("Your Previous Balance: " + formatMoney(previous));
                        System.out.println("Withdrawal Amount: " + formatMoney(amount));
                        System.out.println("Current Balance: " + formatMoney(updated));
                    }
                    balance = updated;
                    break;
                }

                case 2: {
                    // For Deposit
                    System.out.println();
                    System.out.println("Your Current Balance: " + formatMoney(balance));
                    System.out.println("* Current Interest Rate: 5%");
                    System.out.print("Deposit Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    double previous = balance;
                    double updated = deposit(balance, amount);

                    if (updated != previous) {
                        System.out.println();
                        System.out.println("--------- After Deposit ---------");
                        System.out.println("Your Previous Balance: " + formatMoney(previous));
                        System.out.println("Deposit Amount: " + formatMoney(amount));
                        System.out.println("Current Balance: " + formatMoney(updated));
                    }
                    balance = updated;
                    break;
                }

                case 3: {
                    // For Interest info (5% annually)
                    final double rate = 0.05;

                    System.out.println();
                    System.out.println("3) Know Interest Rate on Balance");
                    System.out.println("--------------------------------");
                    System.out.println("Current Balance (CB): " + formatMoney(balance));
                    System.out.println("Interest Rate on Balance: 5% (Annually)");

                    // 1 year - Simple Interest
                    double annualInterest = calculateInterest(balance, rate);
                    double oneYearTotal = balance + annualInterest;

                    System.out.println("Annual - (Simple)");
                    System.out.println("Interest Amount on CB: " + formatMoney(annualInterest));
                    System.out.println("Total A/C Value with Interest: " + formatMoney(oneYearTotal));

                    // 3 years - Compound
                    double threeYearFuture = calculateInterest(balance, rate, 3);
                    double threeYearInterest = threeYearFuture - balance;

                    System.out.println("3 Years - (Compound)");
                    System.out.println("Interest Amount on CB: " + formatMoney(threeYearInterest));
                    System.out.println("Total A/C Value with Interest: " + formatMoney(threeYearFuture));

                    System.out.println();
                    System.out.print("Enter 0 to back in ACCOUNT STATEMENT: ");
                    while (true) {
                        if (scanner.hasNextInt()) {
                            int back = scanner.nextInt();
                            scanner.nextLine();
                            if (back == 0) break;
                            System.out.print("Please press 0 to go back: ");
                        } else {
                            scanner.nextLine();
                            System.out.print("Please press 0 to go back: ");
                        }
                    }
                    break;
                }

                case 0:
                    running = false;
                    System.out.println("Thank you for using Banking System.");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
 /*
 Putting My Methods Here: Deposit, Withdrawal, Interest & Compound
 */
    //////////////////// Deposit
    public static double deposit(double balance, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return balance;
        }
        return balance + amount;
    }

    //////////////////// Withdraw
    public static double withdraw(double balance, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return balance;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal cannot be processed.");
            return balance;
        }
        return balance - amount;
    }

    ///////////////////// Interest for one year (simple interest amount)
    public static double calculateInterest(double balance, double rate) {
        return balance * rate;
    }

    //////////////////// Future value after given years (compound annually)
    public static double calculateInterest(double balance, double rate, int years) {
        return balance * Math.pow(1 + rate, years);
    }

    //////////////////// Account statement
    public static void printStatement(String accountNumber, String name, double balance) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        System.out.println();
        System.out.println("ACCOUNT STATEMENT");
        System.out.println("-----------------");
        System.out.println("Date: " + today.format(formatter));
        System.out.println("Account Name: " + name);
        System.out.println("Account No: " + accountNumber);
        System.out.println();
        System.out.println("Current Balance: " + formatMoney(balance));
    }

    //////////////////////// Recurring compound interest (kept for completeness)
    public static double compoundInterest(double principal, double rate, int years) {
        if (years == 0) {
            return principal;
        }
        return compoundInterest(principal * (1 + rate), rate, years - 1);
    }

    ////////////////////// Format Yen
    private static String formatMoney(double amount) {
        return String.format("¥%,.2f", amount);
    }
}

