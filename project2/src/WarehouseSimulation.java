/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: This class simulates a warehouse order processing system.
 * Due: 02/22/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/
public class WarehouseSimulation implements SimulationInterface {

	private final Order[] orders;
	private int nextReleaseIndex;

	private final MyPriorityQueue<Order> pq;
	private final MyStack<Order> returns;

	private int currentMinute;
	private int totalArrived;
	private int totalShipped;
	private int totalLate;

	/**
	 * Creates a WarehouseSimulation with the given array of orders.
	 *
	 * @param orders the array of orders to simulate
	 * @throws IllegalArgumentException if orders is null
	 */
	public WarehouseSimulation(Order[] orders) {
		if (orders == null) {
			throw new IllegalArgumentException("orders cannot be null");
		}
		this.orders = orders;
		this.nextReleaseIndex = 0;

		this.pq = new MyPriorityQueue<>(new OrderComparator());
		this.returns = new MyStack<>();

		this.currentMinute = 0;
		this.totalArrived = 0;
		this.totalShipped = 0;
		this.totalLate = 0;
	}

	/**
	 * Advances the simulation by one minute.
	 * 
	 * During each minute: 1) At most one order is released into the priority queue.
	 * 2) At most one order is shipped. 3) If the shipped order is late, it is
	 * pushed onto the return stack. 4) The current minute increases by one.
	 */
	@Override
	public void tick() {

		// 1) Release at most one order per minute
		if (nextReleaseIndex < orders.length) {
			Order arriving = orders[nextReleaseIndex++];
			if (arriving == null) {
				throw new IllegalArgumentException("orders contains null");
			}
			arriving.setArrivalMinute(currentMinute);
			pq.enqueue(arriving);
			totalArrived++;
		}

		// 2) Ship at most one order per minute
		if (!pq.isEmpty()) {
			Order shipped = pq.dequeue();
			totalShipped++;

			// 3) If late, push to returns stack
			if (currentMinute > shipped.getDeadlineMinute()) {
				returns.push(shipped);
				totalLate++;
			}
		}

		// 4) Advance time by one minute
		currentMinute++;

	}

	/**
	 * Checks whether the simulation is finished.
	 * 
	 * The simulation is finished when all orders have been released and the
	 * priority queue is empty.
	 *
	 * @return true if simulation is complete, false otherwise
	 */
	@Override
	public boolean isFinished() {
		return nextReleaseIndex >= orders.length && pq.isEmpty();
	}

	/**
	 * Checks whether the simulation is finished.
	 * 
	 * The simulation is finished when all orders have been released and the
	 * priority queue is empty.
	 *
	 * @return true if simulation is complete, false otherwise
	 */
	@Override
	public int getCurrentMinute() {
		return currentMinute;
	}

	/**
	 * Returns the total number of orders that have arrived.
	 *
	 * @return total arrived orders
	 */
	@Override
	public int getTotalArrived() {
		return totalArrived;
	}

	/**
	 * Returns the total number of orders that have been shipped.
	 *
	 * @return total shipped orders
	 */
	@Override
	public int getTotalShipped() {
		return totalShipped;
	}

	/**
	 * Returns the total number of orders that have been shipped.
	 *
	 * @return total shipped orders
	 */
	@Override
	public int getTotalLate() {
		return totalLate;
	}

}
