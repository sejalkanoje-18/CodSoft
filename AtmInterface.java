import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean authenticate(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addToTransactionHistory("Deposit", amount);
            System.out.println("Deposit successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addToTransactionHistory("Withdrawal", amount);
            System.out.println("Withdrawal successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            addToTransactionHistory("Transfer to " + recipient.getUserId(), amount);
            System.out.println("Transfer successful. Current balance: " + balance);
        } else {
            System.out.println("Invalid transfer amount or insufficient funds.");
        }
    }

    public void addToTransactionHistory(String type, double amount) {
        Transaction transaction = new Transaction(type, amount);
        transactionHistory.add(transaction);
    }

    public void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("Transaction History is empty.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction.getType() + ": " + transaction.getAmount());
            }
        }
    }
}

public class AtmInterface {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Account userAccount = new Account("12345", "5678", 1000.0);
        Account anotherAccount = new Account("67890", "4321", 500.0);

        System.out.println("Enter user ID: ");
        String enteredUserId = sc.nextLine();

        System.out.println("Enter Pin: ");
        String enteredPin = sc.nextLine();

        if (enteredUserId.equals(userAccount.getUserId()) && userAccount.authenticate(enteredPin)) {
            System.out.println("Login successful");

            int choice;
            do {
                printMenu();
                System.out.println("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        userAccount.displayTransactionHistory();
                        break;
                    case 2:
                        performWithdrawal(sc, userAccount);
                        break;
                    case 3:
                        performDeposit(sc, userAccount);
                        break;
                    case 4:
                        performTransfer(sc, userAccount, anotherAccount);
                        break;
                    case 5:
                        System.out.println("Exiting ATM. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } while (choice != 5);
        } else {
            System.out.println("Login failed. Invalid user Id or PIN");
        }

        sc.close(); // Close the scanner to prevent resource leak
    }

    private static void printMenu() {
        System.out.println("\n1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

    private static void performWithdrawal(Scanner sc, Account userAccount) {
        System.out.println("Enter withdrawal amount: ");
        double withdrawalAmount = sc.nextDouble();
        userAccount.withdraw(withdrawalAmount);
    }

    private static void performDeposit(Scanner sc, Account userAccount) {
        System.out.println("Enter deposit amount: ");
        double depositAmount = sc.nextDouble();
        userAccount.deposit(depositAmount);
    }

    private static void performTransfer(Scanner sc, Account userAccount, Account recipientAccount) {
        System.out.println("Enter recipient's user ID: ");
        String recipientUserId = sc.next().trim();
        System.out.println("Enter transfer amount: ");
        double transferAmount = sc.nextDouble();

        if (recipientUserId.equals(recipientAccount.getUserId())) {
            userAccount.transfer(recipientAccount, transferAmount);
        } else {
            System.out.println("Recipient user ID does not match. Transfer aborted.");
        }
    }
}
