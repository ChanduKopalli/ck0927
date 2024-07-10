package ck0927.junit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ck0927.impl.Checkout;
import ck0927.impl.RentalAgreement;


class CheckoutTest {

    @Test
    void test1() {
        assertThrows(IllegalArgumentException.class, () -> 
            Checkout.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3)),
            "Should throw exception for invalid discount percentage"
        );
    }

    @Test
    void test2() {
        RentalAgreement agreement = Checkout.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        assertEquals("LADW", agreement.getTool().getCode());
        assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(10, agreement.getDiscountPercent());
        // Add more specific assertions based on expected calculations
    }

    @Test
    void test3() {
        RentalAgreement agreement = Checkout.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        assertNotNull(agreement);
        assertEquals("CHNS", agreement.getTool().getCode());
        assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(25, agreement.getDiscountPercent());
        // Add more specific assertions
    }

    @Test
    void test4() {
        RentalAgreement agreement = Checkout.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        assertNotNull(agreement);
        assertEquals("JAKD", agreement.getTool().getCode());
        assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(0, agreement.getDiscountPercent());
        // Add more specific assertions
    }

    @Test
    void test5() {
        RentalAgreement agreement = Checkout.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        assertNotNull(agreement);
        assertEquals("JAKR", agreement.getTool().getCode());
        assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
        assertEquals(9, agreement.getRentalDays());
        assertEquals(0, agreement.getDiscountPercent());
        // Add more specific assertions
    }

    @Test
    void test6() {
        RentalAgreement agreement = Checkout.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        assertNotNull(agreement);
        assertEquals("JAKR", agreement.getTool().getCode());
        assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
        assertEquals(4, agreement.getRentalDays());
        assertEquals(50, agreement.getDiscountPercent());
        // Add more specific assertions
    }
}