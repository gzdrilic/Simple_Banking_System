package banking;

import java.util.*;

import static banking.Bank.accountList;

public class Main {
    static boolean loginMenu;

    public static Scanner sc = new Scanner(System.in);



    public static void main(String[] args) {
       // makeDBConnection(args[1]);
       CardRepository cardRepository;
        cardRepository = new CardRepository(args[1]);
        bankingAppStartMenu(cardRepository);

    }
/*
    private static void makeDBConnection(String fileName) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
*/
    private static void doTransfer(Account newAccount, CardRepository cardRepository) {
        System.out.println("Enter card number:");
        String transferCardNumberString = sc.next();
        if (!Account.checkCardNumberFormat(transferCardNumberString)) {
            System.out.println("Probably you made a mistake in the card number.\nPlease try again!\n");
        }
        Account accountToTransfer = Bank.findAccount(transferCardNumberString);
        if (accountToTransfer == null) {
            System.out.println("Such a card does not exist.");
        }
        if (accountToTransfer == newAccount) {
            System.out.println("You can't transfer money to the same account!");
        }
        System.out.println("Enter how much money you want to transfer:");
        int money = sc.nextInt();
        if (money > newAccount.getBalance()) {
            System.out.println("Not enough money!");
        }
        if (accountToTransfer != null) {
            accountToTransfer.setBalance(accountToTransfer.getBalance() + money);
        }
        newAccount.setBalance(newAccount.getBalance() - money);
        cardRepository.updateAccount(newAccount);
        if (accountToTransfer != null) {
            cardRepository.updateAccount(accountToTransfer);
        }
        System.out.println("Success!");
    }


    static void bankingAppStartMenu(CardRepository cardRepository) {
        int choice = 10;
        Account newAccount;
        while (choice != 0) {
            Bank.showStartMenu();
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    newAccount = new Account();
                    newAccount.createCard();
                    newAccount.createPin();
                    cardRepository.insert(newAccount);
                    accountList.add(newAccount);
                    Bank.printCardAndPin(newAccount);
                }
                case 2 -> {
                    String cardNumber;
                    String pin;
                    System.out.println("Enter your card number:");
                    cardNumber = sc.next();
                    System.out.println("Enter your pin:");
                    pin = sc.next();
                    newAccount = Bank.findAccount(cardNumber, pin);
                    if (newAccount != null) {
                        bankingAppLoginMenu(newAccount, cardRepository);
                    } else {
                        System.out.println("Wrong input!");
                    }
                }
                case 0 -> {
                    choice = 0;
                    cardRepository.close();
                    System.exit(0);
                }
                default -> System.out.println("Wrong input!");
            }
        }
    }


    static void bankingAppLoginMenu(Account newAccount, CardRepository cardRepository) {
        loginMenu = true;
        System.out.println("You have successfully logged in!");
        int ch = 10;
        while (true) {
            Bank.showLoginMenu();
            ch = sc.nextInt();
            switch (ch) {
                case 1 -> cardRepository.showBalance(newAccount);
                case 2 -> {
                    System.out.println("Enter income:");
                    double income = sc.nextDouble();
                    newAccount.addIncometoAccount(income);
                    cardRepository.updateBalance(newAccount);
                    System.out.println("Income was added!");
                    cardRepository.showBalance(newAccount);
                }
                case 3 -> doTransfer(newAccount, cardRepository);
                case 4 -> {
                    cardRepository.deleteAccount(newAccount);
                    System.out.println("\n" + "The account has been closed!");
                }
                case 5 -> {
                    System.out.println("You have successfully logged out!");
                    Bank.showStartMenu();
                }
                case 0 -> {
                    System.out.println("Bye!");
                    cardRepository.close();
                    System.exit(0);
                }
                default -> System.out.println("Wrong enter");
            }
        }



    }


}
















