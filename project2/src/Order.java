/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class represents a warehouse order with an order id, arrival time and deadline.
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * This class represents a warehouse order with an order id, arrival time and
 * deadline.
 * 
 * @author Chakapan Kanchana
 */
public class Order {

	private String orderId;
	private int deadlineMinute;
	private int arrivalMinute;

	/**
	 * Creates an Order with the given ID and deadline. Arrival time is set to -1 by
	 * default.
	 * 
	 * @param orderId  the order ID
	 * @param deadline the deadline time (in minutes)
	 * @throws IllegalArgumentException if the deadline is negative, or if the
	 *                                  orderId is null or blank
	 */
	public Order(String orderId, int deadlineMinute) {
		if (deadlineMinute < 0) {
			throw new IllegalArgumentException("Deadline cannot be negative.");
		}
		if (orderId == null || orderId.isBlank()) {
			throw new IllegalArgumentException("Order ID cannot be blank or null.");
		}
		this.orderId = orderId;
		this.deadlineMinute = deadlineMinute;
		arrivalMinute = -1;
	}

	/**
	 * Sets the arrival time of the order.
	 * 
	 * @param arrival the arrival time in minutes
	 * @throws IllegalArgumentException if arrival is negative
	 */
	public void setArrivalMinute(int arrivalMinute) {
		if (arrivalMinute < 0) {
			throw new IllegalArgumentException("Arrival time cannot be negative.");
		}
		this.arrivalMinute = arrivalMinute;
	}

	/**
	 * Returns the order ID.
	 * 
	 * @return the order ID
	 */
	public String getId() {
		return orderId;
	}

	/**
	 * Returns the deadline time.
	 * 
	 * @return the deadline minute
	 */
	public int getDeadlineMinute() {
		return deadlineMinute;
	}

	/**
	 * Returns the arrival time.
	 * 
	 * @return the arrival minute
	 */
	public int getArrivalMinute() {
		return arrivalMinute;
	}

}
