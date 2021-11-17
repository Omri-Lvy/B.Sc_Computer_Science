public class Calendar {
	// Starting the calendar on 1/1/1900
	static int dayOfMonth = 1;
	static int month = 1;
	static int year = 1900;
	static int dayOfWeek = 2; // 1.1.1900 was a Monday
	static int nDaysInMonth = 31; // Number of days in January

	/**
	 * Prints the calendar of given year.
	 */
	public static void main(String args[]) {
		// Prints each date dd/mm/yyyy in a separate line. If the day is a Sunday,
		// prints "Sunday".
		int debugDaysCounter = 0; // Used for debugging purposes, counts how many days were advanced so far.
		int givenYear = Integer.parseInt(args[0]);
		while (year <= givenYear && month <= 12 && dayOfMonth <= 31) {
			if (year == givenYear) {
				if (dayOfWeek == 1) {
					System.out.println(dayOfMonth + " / " + month + " / " + year + " Sunday");
				} else {
					System.out.println(dayOfMonth + " / " + month + " / " + year);
				}
			}
			advance();
			debugDaysCounter++;
			// If you want to stop the loop after n days, replace the condition of the
			// if statement with the condition (debugDaysCounter == n)
			if (debugDaysCounter < 0) {
				break;
			}
		}
	}

	// Advances the date (day, month, year) and the day-of-the-week.
	private static void advance() {
		dayOfMonth++;
		if (nDaysInMonth(month, year) < dayOfMonth) {
			dayOfMonth = 1;
			month++;
		}
		if (month > 12) {
			month = 1;
			year++;
		}
		dayOfWeek++;
		if (dayOfWeek > 7) {
			dayOfWeek = 1;
		}
	}

	// Returns true if the given year is a leap year, false otherwise.
	private static boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// Returns the number of days in the given month and year.
	private static int nDaysInMonth(int month, int year) {
		switch (month) {
		case 2:
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		case 4:
			return 30;
		case 6:
			return 30;
		case 9:
			return 30;
		case 11:
			return 30;
		}
		return 31;
	}
}
