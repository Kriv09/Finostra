package com.example.finostra.Controllers;
import com.example.finostra.Services.BonusCardService;

import java.util.Scanner;

public class BonusCardController {
    private BonusCardService cardService;
    private Scanner scanner;

    public BonusCardController() {
        this.cardService = new BonusCardService("John Doe", "12345ABC");
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Bonus Card System ---");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Card Details");
            System.out.println("3. View Bonus Balance");
            System.out.println("4. Redeem Cashback");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    handleAddTransaction();
                    break;
                case 2:
                    handleViewCardDetails();
                    break;
                case 3:
                    handleViewBonusBalance();
                    break;
                case 4:
                    handleRedeemCashback();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleAddTransaction() {
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        cardService.addTransaction(amount);
        System.out.println("Transaction added successfully.");
    }

    private void handleViewCardDetails() {
        System.out.println(cardService.getCardDetails());
    }

    private void handleViewBonusBalance() {
        System.out.println("Bonus balance: " + cardService.getBonusBalance());
    }

    private void handleRedeemCashback() {
        double cashback = cardService.redeemCashback();
        System.out.println("Cashback redeemed: " + cashback);
        System.out.println("Updated bonus balance: " + cardService.getBonusBalance());
    }
}
