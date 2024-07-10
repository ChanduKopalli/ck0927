package ck0927.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Checkout {
    private static final Map<String, Tool> toolInventory = new HashMap<>();

    static {
        toolInventory.put("CHNS", new Tool("CHNS", ToolType.CHAINSAW, "Stihl"));
        toolInventory.put("LADW", new Tool("LADW", ToolType.LADDER, "Werner"));
        toolInventory.put("JAKD", new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt"));
        toolInventory.put("JAKR", new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid"));
    }

    public static RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range 0-100");
        }

        Tool tool = toolInventory.get(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Invalid tool code");
        }

        // Calculate due date, charge days, pre-discount charge, discount amount, and final charge
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = calculateChargeDays(tool, checkoutDate, dueDate);
        double dailyRentalCharge = tool.getType().getDailyCharge();
        double preDiscountCharge = Math.round(chargeDays * dailyRentalCharge * 100.0) / 100.0;
        double discountAmount = Math.round(preDiscountCharge * discountPercent) / 100.0;
        double finalCharge = preDiscountCharge - discountAmount;

        // Create and return RentalAgreement
        return new RentalAgreement(tool, checkoutDate, rentalDays, discountPercent);

    }

    private static int calculateChargeDays(Tool tool, LocalDate checkoutDate, LocalDate dueDate) {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);  // Start from the day after checkout
        while (!currentDate.isAfter(dueDate)) {
            if (isChargeable(tool, currentDate)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return chargeDays;
    }

    private static boolean isChargeable(Tool tool, LocalDate date) {
        boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean isHoliday = HolidayCalculator.isHoliday(date);

        return (tool.getType().isWeekdayCharge() && !isWeekend && !isHoliday) ||
               (tool.getType().isWeekendCharge() && isWeekend) ||
               (tool.getType().isHolidayCharge() && isHoliday);
    }
}
