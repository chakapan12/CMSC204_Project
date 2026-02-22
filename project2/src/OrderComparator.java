/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class compares two Order objects based on deadline and arrival time.
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.Comparator;

/**
 * Compares two Order objects. Orders are compared first by deadline minute. If
 * deadlines are equal, they are compared by arrival minute.
 * 
 * @author Chakapan
 */
public class OrderComparator implements Comparator<Order> {

	/**
	 * Compares two Order objects based on deadline and arrival time.
	 * 
	 * @param o1 the first Order
	 * @param o2 the second Order
	 * @return a negative integer if o1 has higher priority, zero if equal, a
	 *         positive integer if o2 has higher priority
	 */
	@Override
	public int compare(Order o1, Order o2) {

		// Compare by deadline
		int byDeadline = Integer.compare(o1.getDeadlineMinute(), o2.getDeadlineMinute());

		if (byDeadline != 0) {
			return byDeadline;
		}

		// If deadlines are the same, compare by arrival time
		return Integer.compare(o1.getArrivalMinute(), o2.getArrivalMinute());

	}

}
