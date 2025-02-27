package com.example.finostra.Utils.BankCards;

import java.util.Random;

public class BankCardUtil {

    public static String generateCardNumber() {
        return helperGenerateCardNumber("VISA");
    }
    public static String generateIBAN() {

        return "";
    }



    private static String helperGenerateCardNumber(String cardType) {
        StringBuilder cardNumber = new StringBuilder();
        short cardNumberLength = 0;

        Random rand = new Random();

        switch (cardType.toUpperCase()) {
            case "VISA":
                cardNumberLength = 16;
                cardNumber.append(4);
                break;
            case "MASTERCARD":
                cardNumberLength = 16;
                cardNumber.append(5);
                break;
        }

        // Luhn algorithm
        while(cardNumber.length() < cardNumberLength - 1) {
            cardNumber.append(rand.nextInt(10));
        }

        // Starting to check control digit, the last number
        int controlDigit = getControlDigit(cardNumber.toString());

        cardNumber.append(controlDigit);

        return cardNumber.toString();
    }
    private static int getControlDigit(String numbers) {
        int controlDigit;
        int sum = 0;

        // Finding sum
        for(short i = 0; i < numbers.length(); i++) {
            int num = Integer.parseInt(numbers.substring(i, i + 1));
            if(i % 2 == 0) {
                num *= 2;
                if(num > 9){
                    num -= 9;
                }
            }
            sum += num;
        }

        if(sum % 10 == 0){
            controlDigit = 0;
        } else{
            controlDigit = 10 - (sum % 10);
        }
        return controlDigit;
    }

}
