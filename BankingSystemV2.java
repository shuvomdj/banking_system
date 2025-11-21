package twentyfive.fall.oop.group2.lesson1.class7;

import java.util.Scanner;  // Input Class

public class BankApp {

    // Insert or Input Data to System
    Scanner sc = new Scanner(System.in);

    // Account Storing Limits to Keep History utpo 1000
    Account[] accounts = new Account[1000];
    int accountCount = 0;

    // FIXED Bank Manager Access Permission by Making Constant
    final String ADMIN_ID = "secret";
    final String ADMIN_PW = "1234";


    public static void main(String[] args) {
        BankApp app = new BankApp();  // Starting the BankApp
        app.run();                    // Calling the run() Method :-)
    }

    // My Main Loop Here
    void run() {
        while (true) {
            System.out.println("\nBank Account Menu:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Check Balance");
            System.out.println("5. Account Info");
            System.out.println("6. Change PIN");
            System.out.println("7. Manager Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = readInt();
            // Java Switching Here
            switch (choice) {
                case 1:
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    transfer();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    accountInfo();
                    break;
                case 6:
                    changePin();
                    break;
                case 7:
                    adminMenu();
                    break;
                case 0:
                    System.out.println("Thank you for using the App!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // What We Will See or Happen as an Ordinary User

    // Deposit Money in Account
    void deposit() {
        int accNumber = askAccountNumber();
        Account acc = checkAccountAndPin(accNumber);
        if (acc == null) {
            return;
        }

        System.out.print("Amount to Deposit: ");
        double amount = readDouble();

        if (amount <= 0) {
            System.out.println("Amount must be Positive.");
            return;
        }

        double oldBalance = acc.balance;
        double newBalance = oldBalance + amount;

        acc.balance = newBalance;

        System.out.println("Deposit Done. New balance = " + newBalance);
    }

    // Withdraw Money from Account
    void withdraw() {
        int accNumber = askAccountNumber();
        Account acc = checkAccountAndPin(accNumber);
        if (acc == null) {
            return;
        }

        System.out.print("Amount to Withdraw: ");
        double amount = readDouble();

        if (amount <= 0) {
            System.out.println("Amount must be Positive.");
            return;
        }

        double oldBalance = acc.balance;

        if (oldBalance < amount) {
            System.out.println("Insufficient Account Balance.");
            return;
        }

        double newBalance = oldBalance - amount;
        acc.balance = newBalance;

        System.out.println("Withdrawal Done. New balance = " + newBalance);
    }

    // Transfer Balance from Account 2 Account
    void transfer() {
        System.out.print("From Account No: ");
        int fromNumber = readInt();

        Account fromAcc = checkAccountAndPin(fromNumber);
        if (fromAcc == null) {
            return;
        }

        System.out.print("To Account No: ");
        int toNumber = readInt();

        Account toAcc = findAccount(toNumber);
        if (toAcc == null) {
            System.out.println("Destination Account Not Found.");
            return;
        }

        if (fromAcc == toAcc) {
            System.out.println("Cannot Transfer to Own Account.");
            return;
        }

        System.out.print("Amount: ");
        double amount = readDouble();

        if (amount <= 0) {
            System.out.println("Amount Must be Positive.");
            return;
        }

        if (fromAcc.balance < amount) {
            System.out.println("Insufficient Account Balance.");
            return;
        }

        fromAcc.balance = fromAcc.balance - amount;
        toAcc.balance   = toAcc.balance + amount;

        System.out.println("Transfer Completed.");
        System.out.println("From Account New Balance = " + fromAcc.balance);
    }

    // Knowing Account Balance
    void checkBalance() {
        int accNumber = askAccountNumber();
        Account acc = checkAccountAndPin(accNumber);
        if (acc == null) {
            return;
        }

        System.out.println("Account: " + acc.number);
        System.out.println("Name   : " + acc.name);
        System.out.println("Balance: " + acc.balance);
    }

    // Show Account Information
    void accountInfo() {
        int accNumber = askAccountNumber();
        Account acc = checkAccountAndPin(accNumber);
        if (acc == null) {
            return;
        }

        System.out.println("\nAccount Information:");
        System.out.println("Account: " + acc.number);
        System.out.println("Name   : " + acc.name);
        System.out.println("Email  : " + acc.email);
        System.out.println("Phone  : " + acc.phone);
        System.out.println("Balance: " + acc.balance);
    }

    // Change PIN Code
    void changePin() {
        int accNumber = askAccountNumber();
        Account acc = checkAccountAndPin(accNumber);
        if (acc == null) {
            return;
        }

        System.out.print("New 4-digit PIN: ");
        String newPin = sc.nextLine();

        if (!isValidPin(newPin)) {
            System.out.println("PIN Must be 4 digits (0-9).");
            return;
        }

        acc.pin = newPin;
        System.out.println("PIN Successfully Changed.");
    }

    // Bank Manager Function

    void adminMenu() {
        System.out.print("Bank Manager ID: ");
        String id = sc.nextLine();

        System.out.print("Bank Manager Password: ");
        String pw = sc.nextLine();

        if (!ADMIN_ID.equals(id) || !ADMIN_PW.equals(pw)) {
            System.out.println("Wrong Manager Login.");
            return;
        }

        // Bank Manager Menu
        while (true) {
            System.out.println("\nBank Manager Menu: ");
            System.out.println("1. Create account");
            System.out.println("2. List all accounts");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt();

            if (choice == 1) {
                createAccount();
            } else if (choice == 2) {
                listAccounts();
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid Choice.");
            }
        }
    }

    // Bank Manager Account Creating Function
    void createAccount() {
        // simple check: array full
        if (accountCount >= accounts.length) {
            System.out.println("Account Creating Limit Reached, Please Contact Head Office.");
            return;
        }

        System.out.print("New Account Number: ");
        int number = readInt();

        Account existing = findAccount(number);
        if (existing != null) {
            System.out.println("This Account Already Exists.");
            return;
        }

        System.out.print("Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Set 4-digit PIN: ");
        String pin = sc.nextLine();

        if (!isValidPin(pin)) {
            System.out.println("PIN Must be 4 Digits (0-9).");
            return;
        }

        System.out.print("Opening Balance: ");
        double openingBalance = readDouble();

        Account acc = new Account();
        acc.number = number;
        acc.name = name;
        acc.email = email;
        acc.phone = phone;
        acc.pin = pin;
        acc.balance = openingBalance;

        accounts[accountCount] = acc;
        accountCount++;

        System.out.println("Account Created Successfully.");
    }

    // Bank Manager Can See Account List
    void listAccounts() {
        if (accountCount == 0) {
            System.out.println("No Accounts Registered yet.");
            return;
        }

        System.out.println("\nAll Account List:");
        for (int i = 0; i < accountCount; i++) {
            Account acc = accounts[i];
            System.out.println(
                    "Acc " + acc.number +
                            " | Name: " + acc.name +
                            " | Email: " + acc.email +
                            " | Phone: " + acc.phone +
                            " | Balance: " + acc.balance
            );
        }
    }

    /*
    Brain or Memory Function Here
    */

    // Requesting A/C No.
    int askAccountNumber() {
        System.out.print("Account number: ");
        return readInt();
    }

    // Make a Liner Account Search
    Account findAccount(int number) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].number == number) {
                return accounts[i];
            }
        }
        return null;
    }

    // Verifying Account is Available and Pin is Correct or Not
    Account checkAccountAndPin(int number) {
        Account acc = findAccount(number);
        if (acc == null) {
            System.out.println("Account Not Found.");
            return null;
        }

        System.out.print("Enter PIN: ");
        String inputPin = sc.nextLine();

        if (!acc.pin.equals(inputPin)) {
            System.out.println("Wrong PIN.");
            return null;
        }

        return acc;
    }

    // Pin Code Verification for 4 Digit and So on
    boolean isValidPin(String pin) {
        if (pin.length() != 4) {
            return false;
        }
        for (int i = 0; i < pin.length(); i++) {
            char c = pin.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // If Invalid Input
    int readInt() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Enter a Whole Number: ");
            }
        }
    }

    // If Invalid Input
    double readDouble() {
        while (true) {
            try {
                String s = sc.nextLine().trim();
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Enter a Valid Amount: ");
            }
        }
    }
}

// Account Holder Information Class
/*class Account {
    int number;
    String name;
    String email;
    String phone;
    String pin;
    double balance;
}*/
