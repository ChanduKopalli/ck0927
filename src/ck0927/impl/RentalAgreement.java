package ck0927.impl;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalAgreement {
    private Tool tool;
    private LocalDate checkoutDate;
    private int rentalDays;
    private LocalDate dueDate;
    private int chargeDays;
    private double dailyRentalCharge;
    private double preDiscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement(Tool tool, LocalDate checkoutDate, int rentalDays, int discountPercent) {
        this.tool = tool;
        this.checkoutDate = checkoutDate;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        
        calculateRentalDetails();
    }

    public Tool getTool() {
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getRentalDays() {
		return rentalDays;
	}

	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public int getChargeDays() {
		return chargeDays;
	}

	public void setChargeDays(int chargeDays) {
		this.chargeDays = chargeDays;
	}

	public double getDailyRentalCharge() {
		return dailyRentalCharge;
	}

	public void setDailyRentalCharge(double dailyRentalCharge) {
		this.dailyRentalCharge = dailyRentalCharge;
	}

	public double getPreDiscountCharge() {
		return preDiscountCharge;
	}

	public void setPreDiscountCharge(double preDiscountCharge) {
		this.preDiscountCharge = preDiscountCharge;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getFinalCharge() {
		return finalCharge;
	}

	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
	}

	private void calculateRentalDetails() {
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.dailyRentalCharge = tool.getType().getDailyCharge();
        this.chargeDays = calculateChargeDays();
        this.preDiscountCharge = Math.round(chargeDays * dailyRentalCharge * 100.0) / 100.0;
        this.discountAmount = Math.round(preDiscountCharge * discountPercent) / 100.0;
        this.finalCharge = preDiscountCharge - discountAmount;
    }

    private int calculateChargeDays() {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);  // Start from the day after checkout
        while (!currentDate.isAfter(dueDate)) {
            if (isChargeable(currentDate)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return chargeDays;
    }

    private boolean isChargeable(LocalDate date) {
        boolean isWeekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        boolean isHoliday = HolidayCalculator.isHoliday(date);

        return (tool.getType().isWeekdayCharge() && !isWeekend && !isHoliday) ||
               (tool.getType().isWeekendCharge() && isWeekend) ||
               (tool.getType().isHolidayCharge() && isHoliday);
    }

    public void printAgreement() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        System.out.println("Tool code: " + tool.getCode());
        System.out.println("Tool type: " + tool.getType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + checkoutDate.format(dateFormatter));
        System.out.println("Due date: " + dueDate.format(dateFormatter));
        System.out.printf("Daily rental charge: $%.2f%n", dailyRentalCharge);
        System.out.println("Charge days: " + chargeDays);
        System.out.printf("Pre-discount charge: $%.2f%n", preDiscountCharge);
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.printf("Discount amount: $%.2f%n", discountAmount);
        System.out.printf("Final charge: $%.2f%n", finalCharge);
    }

    // Getters for all fields
}
