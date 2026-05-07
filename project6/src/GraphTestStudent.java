import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTestStudent {

	private Graph graph;
	private Town richmond;
	private Town atlanta;
	private Town dallas;
	private Town newOrleans;
	private Town miami;

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

	@BeforeEach
	void setUp() throws Exception {
		graph = new Graph();
		richmond = new Town("Richmond");
		atlanta = new Town("Atlanta");
		dallas = new Town("Dallas");
		newOrleans = new Town("New Orleans");
		miami = new Town("Miami");

		graph.addVertex(richmond);
		graph.addVertex(atlanta);
		graph.addVertex(dallas);
		graph.addVertex(newOrleans);
		graph.addVertex(miami);

		graph.addEdge(richmond, atlanta, 533, "I-85");
		graph.addEdge(atlanta, miami, 664, "I-75(E)");
		graph.addEdge(atlanta, dallas, 782, "I-20");
		graph.addEdge(dallas, newOrleans, 506, "I-49");
		graph.addEdge(newOrleans, miami, 866, "I-10");

	}

	@AfterEach
	void tearDown() throws Exception {
		graph = null;
		richmond = null;
		atlanta = null;
		dallas = null;
		newOrleans = null;
		miami = null;
	}

	// -------------------------------------------------------------------------
	// Vertex operations
	// -------------------------------------------------------------------------

	@Test
	public void testAddVertex() {
		Town newTown = new Town("Washington DC");
		assertTrue(graph.addVertex(newTown));
		assertTrue(graph.containsVertex(newTown));
	}

	@Test
	public void testAddVertexDuplicate() {
		assertFalse(graph.addVertex(richmond)); // already in graph
	}

	@Test
	public void testAddVertexNull() {
		assertFalse(graph.addVertex(null));
	}

	@Test
	public void testContainsVertexTrue() {
		assertTrue(graph.containsVertex(new Town("Miami")));
	}

	@Test
	public void testContainsVertexFalse() {
		assertFalse(graph.containsVertex(new Town("Virginia")));
	}

	@Test
	public void testVertexSetSize() {
		assertEquals(5, graph.vertexSet().size());
	}

	@Test
	public void testRemoveVertex() {
		assertTrue(graph.removeVertex(richmond));
		assertFalse(graph.containsVertex(richmond));
	}

	@Test
	public void testVertexSetContainsTowns() {
		Set<Town> towns = graph.vertexSet();

		assertTrue(towns.contains(richmond));
		assertTrue(towns.contains(atlanta));
		assertTrue(towns.contains(dallas));
		assertTrue(towns.contains(newOrleans));
		assertTrue(towns.contains(miami));
	}

	@Test
	public void testRemoveVertexCleansUpEdges() {
		graph.removeVertex(richmond);
		// Atlanta should no longer have an edge to Richmond
		assertFalse(graph.containsEdge(richmond, atlanta));
	}

	@Test
	public void testRemoveVertexNotFound() {
		assertFalse(graph.removeVertex(new Town("Virginia")));
	}

	@Test
	public void testRemoveVertexNull() {
		assertFalse(graph.removeVertex(null));
	}

	// -------------------------------------------------------------------------
	// Edge operations
	// -------------------------------------------------------------------------

	@Test
	public void testAddEdgeReturnsRoad() {
		Town dc = new Town("Washington DC");
		graph.addVertex(dc); // town must exist first
		Road road = graph.addEdge(richmond, dc, 109, "I-95");
		assertNotNull(road);
		assertEquals("I-95", road.getName());
	}

	@Test
	public void testAddEdgeMissingTownThrowsException() {
		Town dc = new Town("Washington DC");
		assertThrows(IllegalArgumentException.class, () -> graph.addEdge(richmond, dc, 109, "I-95"));
	}

	@Test
	public void testAddEdgeNullTownThrowsException() {
		assertThrows(NullPointerException.class, () -> graph.addEdge(richmond, null, 109, "I-95"));
	}

	@Test
	public void testGetEdgeReturnCorrectRoad() {
		Road road = graph.getEdge(richmond, atlanta);
		assertNotNull(road);
		assertEquals("I-85", road.getName());
		assertEquals(533, road.getWeight());
	}

	@Test
	public void testGetEdgeReverseDirection() {
		Road road = graph.getEdge(atlanta, richmond);

		assertNotNull(road);
		assertEquals("I-85", road.getName());
		assertEquals(533, road.getWeight());
	}

	@Test
	public void testGetEdgeNoRoadReturnsNull() {
		assertNull(graph.getEdge(richmond, miami));
	}

	@Test
	public void testGetEdgeWithMissingTownReturnsNull() {
		Town chicago = new Town("Chicago");

		assertNull(graph.getEdge(richmond, chicago));
	}

	@Test
	public void testContainsEdgeTrue() {
		assertTrue(graph.containsEdge(richmond, atlanta));
	}

	@Test
	public void testContainsEdgeReverse() {
		assertTrue(graph.containsEdge(atlanta, richmond));
	}

	@Test
	public void testContainsEdgeFalse() {
		assertFalse(graph.containsEdge(richmond, miami));
	}

	@Test
	public void testEdgeSetSize() {
		assertEquals(5, graph.edgeSet().size());
	}

	@Test
	public void testEdgesOfAtlanta() {
		Set<Road> roads = graph.edgesOf(atlanta);

		assertEquals(3, roads.size());
		// Atlanta connects to Richmond, Miami, and Dallas
	}

	@Test
	public void testEdgesOfRichmond() {
		Set<Road> roads = graph.edgesOf(richmond);

		assertEquals(1, roads.size());
		// Richmond connects to Atlanta only
	}

	@Test
	public void testEdgesOfInvalidTownThrowsException() {
		Town chicago = new Town("Chicago");
		assertThrows(IllegalArgumentException.class, () -> graph.edgesOf(chicago));
	}

	@Test
	public void testEdgesOfNullThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> graph.edgesOf(null));
	}

	@Test
	public void testRemoveEdge() {
		Road removed = graph.removeEdge(richmond, atlanta, 533, "I-85");

		assertNotNull(removed);
		assertEquals("I-85", removed.getName());
		assertFalse(graph.containsEdge(richmond, atlanta));
	}

	@Test
	public void testRemoveEdgeNotFound() {
		assertNull(graph.removeEdge(richmond, miami, 999, "I-XX"));
	}

	@Test
	public void testRemoveEdgeReverseDirection() {
		Road removed = graph.removeEdge(atlanta, richmond, 533, "I-85");

		assertNotNull(removed);
		assertFalse(graph.containsEdge(richmond, atlanta));
	}

	@Test
	public void testRemoveEdgeWrongWeight() {
		assertNull(graph.removeEdge(richmond, atlanta, 999, "I-85"));
		assertTrue(graph.containsEdge(richmond, atlanta));
	}

	@Test
	public void testRemoveEdgeWrongName() {
		assertNull(graph.removeEdge(richmond, atlanta, 533, "WrongName"));
		assertTrue(graph.containsEdge(richmond, atlanta));
	}

	// -------------------------------------------------------------------------
	// shortestPath / dijkstra
	// -------------------------------------------------------------------------

	@Test
	public void testShortestPathExists() {
		ArrayList<String> path = graph.shortestPath(richmond, miami);

		assertFalse(path.isEmpty());
	}

	@Test
	public void testShortestPathCorrectRoute() {
		/*
		 * Richmond -> Atlanta -> Miami = 533 + 664 = 1197 Richmond -> Atlanta -> Dallas
		 * -> New Orleans -> Miami = 533 + 782 + 506 + 866 = 2687
		 *
		 * Therefore, shortest path should be: Richmond -> Atlanta -> Miami
		 */
		ArrayList<String> path = graph.shortestPath(richmond, miami);
		String fullPath = path.toString();

		assertTrue(fullPath.contains("Richmond"));
		assertTrue(fullPath.contains("Atlanta"));
		assertTrue(fullPath.contains("Miami"));
		assertFalse(fullPath.contains("Dallas"));
		assertFalse(fullPath.contains("New Orleans"));
	}

	@Test
	public void testShortestPathStepCount() {
		ArrayList<String> path = graph.shortestPath(richmond, miami);

		assertEquals(2, path.size());
	}

	@Test
	public void testShortestPathStringFormat() {
		ArrayList<String> path = graph.shortestPath(richmond, atlanta);

		assertEquals("Richmond via I-85 to Atlanta 533 mi", path.get(0));
	}

	@Test
	public void testShortestPathSameTown() {
		ArrayList<String> path = graph.shortestPath(richmond, richmond);

		assertTrue(path.isEmpty());
	}

	@Test
	public void testShortestPathUnreachable() {
		Town dc = new Town("Washingto DC");
		graph.addVertex(dc);

		ArrayList<String> path = graph.shortestPath(richmond, dc);
		assertTrue(path.isEmpty());
	}

	@Test
	public void testDijkstraValidSourceDoesNotThrowException() {
		assertDoesNotThrow(() -> graph.dijkstraShortestPath(richmond));
	}

	@Test
	public void testDijkstraNullSourceThrowsException() {
		assertThrows(NullPointerException.class, () -> graph.dijkstraShortestPath(null));

	}

	@Test
	public void testDijkstraMissingTownThrowsException() {
		Town dc = new Town("Washingto DC");
		// Washingto DC does not in the graph
		assertThrows(IllegalArgumentException.class, () -> graph.dijkstraShortestPath(dc));

	}

	@Test
	public void testDijkstraPathDallasToMiami() {
		ArrayList<String> path = graph.shortestPath(dallas, miami);

		assertEquals(2, path.size());
		assertEquals("Dallas via I-49 to New Orleans 506 mi", path.get(0));
		assertEquals("New Orleans via I-10 to Miami 866 mi", path.get(1));
	}

}
