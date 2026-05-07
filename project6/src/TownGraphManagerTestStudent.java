import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TownGraphManagerTestStudent {

	private TownGraphManager manager;

	@BeforeEach
	void setUp() throws Exception {
		manager = new TownGraphManager();

		manager.addTown("Richmond");
		manager.addTown("Atlanta");
		manager.addTown("Dallas");
		manager.addTown("New Orleans");
		manager.addTown("Miami");

		manager.addRoad("Richmond", "Atlanta", 533, "I-85");
		manager.addRoad("Atlanta", "Miami", 664, "I-75");
		manager.addRoad("Atlanta", "Dallas", 782, "I-20");
		manager.addRoad("Dallas", "New Orleans", 506, "I-49");
		manager.addRoad("New Orleans", "Miami", 866, "I-10");

		// @formatter:off
	    /*
	     * Test graph layout:
	     *
	     *   Miami --I-10(866)--> New Orleans
	     *      |                     |
	     *   I-75(664)            I-49(506)
	     *      |                     |
		 *	Atlanta --I-20(782)--> Dallas
	     *      |
	     *   I-85(533)
	     *      |
	     *   Richmond
	     */
		// @formatter:on
	}

	@AfterEach
	void tearDown() throws Exception {
		manager = null;
	}

	// -------------------------------------------------------------------------
	// addTown / containsTown / allTowns
	// -------------------------------------------------------------------------

	@Test
	public void testAddTown() {
		assertTrue(manager.addTown("Orlando"));
		assertTrue(manager.containsTown("Orlando"));
	}

	@Test
	public void testAddDuplicateTown() {
		assertFalse(manager.addTown("Richmond"));
	}

	@Test
	public void testAddNullTown() {
		assertFalse(manager.addTown(null));
	}

	@Test
	public void testContainsTownTrue() {
		assertTrue(manager.containsTown("Richmond"));
	}

	@Test
	public void testContainsTownFalse() {
		assertFalse(manager.containsTown("Chicago"));
	}

	@Test
	public void testContainsTownNull() {
		assertFalse(manager.containsTown(null));
	}

	@Test
	public void testAllTownsSorted() {
		ArrayList<String> towns = manager.allTowns();

		assertEquals("Atlanta", towns.get(0));
		assertEquals("Dallas", towns.get(1));
		assertEquals("Miami", towns.get(2));
		assertEquals("New Orleans", towns.get(3));
		assertEquals("Richmond", towns.get(4));
	}

	@Test
	public void testAddRoad() {
		manager.addTown("Washington DC");

		assertTrue(manager.addRoad("Richmond", "Washington DC", 109, "I-95"));
		assertTrue(manager.containsRoadConnection("Richmond", "Washington DC"));
		assertEquals("I-95", manager.getRoad("Richmond", "Washington DC"));
	}

	@Test
	public void testAddRoadMissingTownReturnsFalse() {
		assertFalse(manager.addRoad("Miami", "New York", 300, "I-XX"));
		assertFalse(manager.containsTown("New York"));
		assertFalse(manager.containsRoadConnection("Miami", "New York"));
	}

	@Test
	public void testGetRoadExists() {
		assertEquals("I-85", manager.getRoad("Richmond", "Atlanta"));
	}

	@Test
	public void testGetRoadReverseDirection() {
		assertEquals("I-85", manager.getRoad("Atlanta", "Richmond"));
	}

	@Test
	public void testGetRoadMissingTownReturnsNull() {
		assertNull(manager.getRoad("Richmond", "Washington DC"));
	}

	@Test
	public void testContainsRoadConnectionTrue() {
		assertTrue(manager.containsRoadConnection("Richmond", "Atlanta"));
	}

	public void testAllRoadsSorted() {
		ArrayList<String> roads = manager.allRoads();

		assertEquals(5, roads.size());
		assertEquals("I-10", roads.get(0));
		assertEquals("I-20", roads.get(1));
		assertEquals("I-49", roads.get(2));
		assertEquals("I-75", roads.get(3));
		assertEquals("I-85", roads.get(4));
	}

	// -------------------------------------------------------------------------
	// deleteRoadConnection
	// -------------------------------------------------------------------------

	@Test
	public void testDeleteRoadConnection() {
		assertTrue(manager.deleteRoadConnection("Richmond", "Atlanta", "I-85"));
		assertFalse(manager.containsRoadConnection("Richmond", "Atlanta"));
	}

	@Test
	public void testDeleteRoadConnectionReverseDirection() {
		assertTrue(manager.deleteRoadConnection("Atlanta", "Richmond", "I-85"));
		assertFalse(manager.containsRoadConnection("Richmond", "Atlanta"));
	}

	// -------------------------------------------------------------------------
	// deleteTown
	// -------------------------------------------------------------------------

	@Test
	public void testDeleteTown() {
		assertTrue(manager.deleteTown("Richmond"));
		assertFalse(manager.containsTown("Richmond"));
	}

	@Test
	public void testDeleteTownCleansRoads() {
		assertTrue(manager.deleteTown("Atlanta"));

		assertFalse(manager.containsTown("Atlanta"));
		assertFalse(manager.containsRoadConnection("Richmond", "Atlanta"));
		assertFalse(manager.containsRoadConnection("Atlanta", "Dallas"));
		assertFalse(manager.containsRoadConnection("Atlanta", "Miami"));
	}

	@Test
	public void testDeleteTownNotFound() {
		assertFalse(manager.deleteTown("Washington DC"));
	}

	// -------------------------------------------------------------------------
	// getPath
	// -------------------------------------------------------------------------

	@Test
	public void testGetPathExists() {
		ArrayList<String> path = manager.getPath("Richmond", "Miami");

		assertFalse(path.isEmpty());
	}

	@Test
	public void testGetPathCorrectRoute() {
		ArrayList<String> path = manager.getPath("Richmond", "Miami");

		assertEquals(2, path.size());
		assertEquals("Richmond via I-85 to Atlanta 533 mi", path.get(0));
		assertEquals("Atlanta via I-75 to Miami 664 mi", path.get(1));
	}

	@Test
	public void testGetPathSameTown() {
		ArrayList<String> path = manager.getPath("Richmond", "Richmond");

		assertTrue(path.isEmpty());
	}

	// -------------------------------------------------------------------------
	// getTown
	// -------------------------------------------------------------------------

	@Test
	public void testGetTownExists() {
		Town town = manager.getTown("Richmond");

		assertNotNull(town);
		assertEquals("Richmond", town.getName());
	}

	@Test
	public void testGetTownNotFound() {
		assertNull(manager.getTown("Chicago"));
	}

	@Test
	public void testGetTownNull() {
		assertNull(manager.getTown(null));
	}

	// -------------------------------------------------------------------------
	// populateTownGraph
	// -------------------------------------------------------------------------

	@Test
	public void testPopulateTownGraph() throws IOException {
		// Write a temp data file and load it into a fileManager
		File tempFile = File.createTempFile("testGraph", ".txt");
		tempFile.deleteOnExit();

		try (PrintWriter writer = new PrintWriter(tempFile)) {
			writer.println("I-20,782;Atlanta;Dallas ");
			writer.println("I-10,866;New Orleans;Miami");
			writer.println("I-75(E),664;Atlanta;Miami");
		}

		TownGraphManager fileManager = new TownGraphManager();
		fileManager.populateTownGraph(tempFile);

		assertTrue(fileManager.containsTown("New Orleans"));
		assertTrue(fileManager.containsTown("Atlanta"));
		assertTrue(fileManager.containsTown("Miami"));
		assertTrue(fileManager.containsTown("Dallas"));

		assertTrue(fileManager.containsRoadConnection("Dallas", "Atlanta"));
		assertEquals("I-20", fileManager.getRoad("Dallas", "Atlanta"));

		assertEquals(4, fileManager.allTowns().size());
		assertEquals(3, fileManager.allRoads().size());

	}

	@Test
	public void testPopulateTownGraphWithInvalidLines() throws IOException {
		// Write a temp data file and load it into a fileManager
		File tempFile = File.createTempFile("testInvalidData", ".txt");
		tempFile.deleteOnExit();

		try (PrintWriter writer = new PrintWriter(tempFile)) {
			writer.println("I-20;Atlanta;Dallas ");
			writer.println("I-10,866;New Orleans;Miami");
			writer.println("Atlanta;Miami");
		}

		TownGraphManager fileManager = new TownGraphManager();
		fileManager.populateTownGraph(tempFile);

		assertTrue(fileManager.containsTown("New Orleans"));
		assertTrue(fileManager.containsTown("Miami"));
		assertFalse(fileManager.containsTown("Atlanta"));
		assertFalse(fileManager.containsTown("Dallas"));

		assertTrue(fileManager.containsRoadConnection("New Orleans", "Miami"));
		assertFalse(fileManager.containsRoadConnection("Atlanta", "Miami"));

	}

}
