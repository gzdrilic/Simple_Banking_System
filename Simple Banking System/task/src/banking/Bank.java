package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    static List<Account> accountList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void printCardAndPin(Account account) {
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(account.getPin());
        System.out.println();
    }



    public static Account findAccount(String cardNumber, String pin) {
        for (Account account : accountList) {
            if (account.getCardNumber().equals(cardNumber) && account.getPin().equals(pin)) {
                return account;
            }
        }
        return null;
    }

    public static void showStartMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static void showLoginMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    public static Account findAccount(String cardNumber) {
        for (Account account : accountList) {
            if (account.getCardNumber().equals(cardNumber)) {
                return account;
            }
        }
        return null;
    }



}