package org.example.payment;

import org.example.model.PaymentResult;

public class CreditCardPayment extends PaymentMethod {
    private final String cardNumber;
    private final String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        super("CreditCard");
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public PaymentResult processPayment(double amount) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return new PaymentResult(false, "Card number cannot be empty.");
        }

        String cleanCardNumber = cardNumber.replaceAll("\\s+|-", "");
        if (cleanCardNumber.length() != 16) {
            return new PaymentResult(false, "Invalid card number length. Must be 16 digits.");
        }

        if (cardHolderName == null || cardHolderName.trim().isEmpty()) {
            return new PaymentResult(false, "Card holder name cannot be empty.");
        }

        return new PaymentResult(true, "Paid " + amount +
                " using credit card ending with " +
                cleanCardNumber.substring(cleanCardNumber.length() - 4));
    }
}

