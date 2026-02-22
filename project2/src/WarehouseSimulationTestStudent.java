import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WarehouseSimulationTestStudent {

	private WarehouseSimulation sim;

	@BeforeEach
	void setUp() throws Exception {
		Order[] orders = { new Order("O1", 2), new Order("O2", 5), new Order("O3", 3) };

		sim = new WarehouseSimulation(orders);
	}

	@AfterEach
	void tearDown() throws Exception {
		sim = null;
	}

	@Test
	void testConstructorNullOrdersThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> new WarehouseSimulation(null));
	}

	@Test
	void testInitialState() {
		assertEquals(0, sim.getCurrentMinute());
		assertEquals(0, sim.getTotalArrived());
		assertEquals(0, sim.getTotalShipped());
		assertEquals(0, sim.getTotalLate());
		assertFalse(sim.isFinished());
	}

	@Test
	void testTickReleasesOneOrderPerMinute() {
		sim.tick(); // minute 0

		assertEquals(1, sim.getTotalArrived());
		assertEquals(1, sim.getTotalShipped());
		assertEquals(1, sim.getCurrentMinute());
	}

	@Test
	public void basicFlowNoLate() {
		// id deadline
		// O1 	2 	// t=0, ships at t=0, on time
		// O2 	5	// t=1, ships at t=1, on time
		// O3 	3	// t=2, ships at t=1, on time

		while (!sim.isFinished()) {
			sim.tick();
		}

		assertEquals(3, sim.getTotalArrived());
		assertEquals(3, sim.getTotalShipped());
		assertEquals(0, sim.getTotalLate());
		assertEquals(3, sim.getCurrentMinute()); // 0→1→2→3
	}

	@Test
	void testTick() {
		sim.tick();
	}

	@Test
	void testLateOrderCountedCorrectly() {
		Order[] lateOrders = new Order[] { 
				new Order("A01", 0), 	// t=0, ships at t=0, on time
				new Order("L01", 0), 	// t=1, ships at t=1, late
				new Order("L02", 0) }; 	// t=2, ships at t=2, late

		WarehouseSimulation lateSim = new WarehouseSimulation(lateOrders);
		while (!lateSim.isFinished()) {
			lateSim.tick();
		}
		assertEquals(2, lateSim.getTotalLate());

	}

	@Test
	void testIsFinished() {
		while (!sim.isFinished()) {
			sim.tick();
		}

		assertTrue(sim.isFinished());
	}

}
