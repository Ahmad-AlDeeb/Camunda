package org.paymentprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    public void processPayment(double amount) {
        System.out.println("Payment of $" + amount + " processed successfully! ✅");
        LOGGER.info("Payment of ${} processed successfully! ✅", amount);
    }
}
