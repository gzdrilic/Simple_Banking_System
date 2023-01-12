package banking;

import java.util.Random;
import java.util.Scanner;
import java.util.random.RandomGenerator;

public class Account {
    Random r = new Random();
    private String cardNumber;
    private String pin;
    private double balance;
    public static  Scanner sc = new Scanner(System.in);

    public void addIncometoAccount(double income) {
            this.balance += income;
    }

    public String createCard() {
        Random random = new Random();
        String accountIdentifier = String.format("%09d", (long) (Math.random() * 999999999L));
        String bankIdentificationNumber = "400000";
        String cardNumberForCheckSum = bankIdentificationNumber + accountIdentifier;
        String cardNumber = bankIdentificationNumber + accountIdentifier + generateCheckSumDigit(cardNumberForCheckSum);
        this.cardNumber = cardNumber;
        return cardNumber;
    }



    //String pin = String.format("%04d", (long)(Math.random() * 9999L));
    public static int generateCheckSumDigit(String cardNumberForCheckSum) {
        int sum = 0;
        int remainder = (cardNumberForCheckSum.length() + 1) % 2;
        for (int i = 0; i < cardNumberForCheckSum.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(cardNumberForCheckSum.substring(i, (i + 1)));
            if ((i % 2) == remainder) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit - 9);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        int checkSumDigit = ((mod == 0) ? 0 : 10 - mod);

        return checkSumDigit;
    }


    public String createPin() {
        //String pin = String.valueOf(r.nextInt(10000));
        String pin = String.format("%04d", (long) (Math.random() * 9999L));
        this.pin = pin;
        return pin;
    }
    public static boolean checkCardNumberFormat(String cardNumber) {
        String cardNumberToTransferWithoutCheckDigit = cardNumber.substring(1, 15);
        Account.generateCheckSumDigit(cardNumberToTransferWithoutCheckDigit);
        String lastDigit = cardNumber.substring(16);
        if (cardNumber.length() != 16 ||
                (!cardNumber.startsWith("400000")) ||
                (!cardNumber.endsWith(Integer.toString(Account.generateCheckSumDigit(cardNumberToTransferWithoutCheckDigit))))) {
        } return false;
    }




    //geteri i seteri

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCardNumber(String number) {
    }

    public void setPin(String pin) {
    }
}