package com.example.finostra.Utils;

import com.example.finostra.Entity.UserCard;
import com.example.finostra.Entity.UserCard;
import org.springframework.stereotype.Service;

import java.util.Locale;


public class UserCardUtils {
        private static final String COUNTRY_CODE = "UA";
        private static final String BANK_IDENTIFIER = "300000";

        public static String generateIBAN(UserCard userCard) {
            String accountNumber = String.format("%019d", userCard.getId());

            String ibanWithoutCheckDigits = BANK_IDENTIFIER + accountNumber + COUNTRY_CODE + "00";

            String numericIBAN = convertToNumericIBAN(ibanWithoutCheckDigits);

            int checkDigits = 98 - mod97(numericIBAN);

            return String.format("%s%02d%s%s", COUNTRY_CODE, checkDigits, BANK_IDENTIFIER, accountNumber);
        }

        private static String convertToNumericIBAN(String iban) {
            StringBuilder numericIBAN = new StringBuilder();
            for (char ch : iban.toCharArray()) {
                if (Character.isLetter(ch)) {
                    numericIBAN.append(Character.getNumericValue(ch));
                } else {
                    numericIBAN.append(ch);
                }
            }
            return numericIBAN.toString();
        }

        private static int mod97(String numericIBAN) {
            int remainder = 0;
            for (int i = 0; i < numericIBAN.length(); i++) {
                remainder = (remainder * 10 + (numericIBAN.charAt(i) - '0')) % 97;
            }
            return remainder;
        }

}
