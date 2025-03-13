package org.paymentprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentService {
    public void processPayment(Integer amount) {
        System.out.println("Payment of $" + amount + " processed successfully! ✅");
    }
}
