/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: Represents a weighted, undirected graph of Towns (vertices) connected by
 * 				Roads (edges). Implements GraphInterface and includes Dijkstra's Shortest
 * 				Path algorithm.
 * Due: 05/8/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Represents a weighted, undirected graph of Towns (vertices) connected by
 * Roads (edges). Implements GraphInterface and includes Dijkstra's Shortest
 */
public class Graph implements GraphInterface<Town, Road> {

	// Each town maps to the set of roads connected to it
	private Map<Town, Set<Road>> adjacencyMap;

	private Map<Town, Integer> distanceTo;

	private Map<Town, Town> previousTown;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		adjacencyMap = new HashMap<>();
	}

	// -------------------------------------------------------------------------
	// Vertex operations
	// -------------------------------------------------------------------------

	/**
	 * Adds a town to the graph.
	 * 
	 * @param v the town to add
	 * @return true if the town was added, false if it was null or already exists
	 */
	@Override
	public boolean addVertex(Town v) {
		if (v == null || adjacencyMap.containsKey(v)) {
			return false;
		}
		adjacencyMap.put(v, new HashSet<>());
		return true;
	}

	/**
	 * Checks whether the graph contains a town.
	 * 
	 * @param v the town to check
	 * @return true if the town exists in the graph, false otherwise
	 */
	@Override
	public boolean containsVertex(Town v) {
		return adjacencyMap.containsKey(v);

	}

	/**
	 * Removes a town and all roads connected to it.
	 * 
	 * @param v the town to remove
	 * @return true if the town was removed, false otherwise
	 */
	@Override
	public boolean removeVertex(Town v) {

		if (!adjacencyMap.containsKey(v)) {
			return false;
		}

		// Make a copy of the road set before looping
		Set<Road> roadsToRemove = new HashSet<>(adjacencyMap.get(v));
		// Remove every road that touches this town from neighboring towns' sets
		for (Road road : roadsToRemove) {
			Town neighbor = road.getSource().equals(v) ? road.getDestination() : road.getSource();
			adjacencyMap.get(neighbor).remove(road);
		}
		adjacencyMap.remove(v);
		return true;
	}

	/**
	 * Returns all towns in the graph.
	 * 
	 * @return a set of all towns
	 */
	@Override
	public Set<Town> vertexSet() {
		return adjacencyMap.keySet();
	}

	// -------------------------------------------------------------------------
	// Edge operations
	// -------------------------------------------------------------------------

	/**
	 * Adds a road between two towns.
	 * 
	 * Both towns must already exist in the graph.
	 * 
	 * @param sourceVertex      the first town
	 * @param destinationVertex the second town
	 * @param weight            the distance of the road
	 * @param description       the road name
	 * @return the road that was added
	 * @throws NullPointerException     if either town is null
	 * @throws IllegalArgumentException if either town is not in the graph
	 */
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {

		if (sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException("Source and destination towns cannot be null.");
		}

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
			throw new IllegalArgumentException("Both towns must already exist in the graph.");
		}

		Road road = new Road(sourceVertex, destinationVertex, weight, description);
		adjacencyMap.get(sourceVertex).add(road);
		adjacencyMap.get(destinationVertex).add(road);

		return road;
	}

	/**
	 * Returns the road between two towns.
	 * 
	 * @param sourceVertex      the first town
	 * @param destinationVertex the second town
	 * @return the road connecting the two towns, or null if no road exists
	 */
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null) {
			return null;
		}

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
			return null;
		}

		for (Road road : adjacencyMap.get(sourceVertex)) {
			if (road.contains(destinationVertex)) {
				return road;
			}
		}
		return null;
	}

	/**
	 * Checks whether a road exists between two towns.
	 * 
	 * @param sourceVertex      the first town
	 * @param destinationVertex the second town
	 * @return true if a road exists, false otherwise
	 */
	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		return getEdge(sourceVertex, destinationVertex) != null;
	}

	/**
	 * Checks whether a road exists between two towns.
	 * 
	 * @param sourceVertex      the first town
	 * @param destinationVertex the second town
	 * @return true if a road exists, false otherwise
	 */
	@Override
	public Set<Road> edgeSet() {
		Set<Road> allRoads = new HashSet<>();
		for (Set<Road> roads : adjacencyMap.values()) {
			allRoads.addAll(roads);
		}
		return allRoads;
	}

	/**
	 * Returns all roads connected to a town.
	 * 
	 * @param vertex the town
	 * @return a set of roads connected to the town
	 * @throws IllegalArgumentException if the town is not in the graph
	 */
	@Override
	public Set<Road> edgesOf(Town vertex) {
		if (!containsVertex(vertex)) {
			throw new IllegalArgumentException("Town not found in graph: " + vertex);
		}
		return adjacencyMap.get(vertex);
	}

	/**
	 * Removes a road between two towns.
	 * 
	 * @param sourceVertex      the first town
	 * @param destinationVertex the second town
	 * @param weight            the road distance
	 * @param description       the road name
	 * @return the removed road, or null if no matching road is found
	 */
	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {

		Road target = getEdge(sourceVertex, destinationVertex);

		if (target == null) {
			return null;
		}

		// verify weight and description
		if (weight > 0 && target.getWeight() != weight) {
			return null;
		}

		if (description != null && !target.getName().equals(description)) {
			return null;
		}

		adjacencyMap.get(sourceVertex).remove(target);
		adjacencyMap.get(destinationVertex).remove(target);

		return target;
	}

	/**
	 * Finds the shortest path from one town to another.
	 * 
	 * @param sourceVertex      the starting town
	 * @param destinationVertex the ending town
	 * @return an ArrayList of strings describing the shortest path
	 */
	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException("Source and destination towns cannot be null.");
		}

		if (!containsVertex(sourceVertex) || !containsVertex(destinationVertex)) {
			throw new IllegalArgumentException("Both towns must be in the graph.");
		}

		dijkstraShortestPath(sourceVertex);

		ArrayList<String> path = new ArrayList<>();

		// No path found
		if (previousTown.get(destinationVertex) == null && !sourceVertex.equals(destinationVertex)) {
			return path;
		}

		// Trace back from destination to source
		LinkedList<Town> townPath = new LinkedList<>();
		Town current = destinationVertex;

		while (current != null) {
			townPath.addFirst(current);
			current = previousTown.get(current);
		}

		// Build path string
		for (int i = 0; i < townPath.size() - 1; i++) {
			Town from = townPath.get(i);
			Town to = townPath.get(i + 1);

			Road road = getEdge(from, to);
			path.add(from + " via " + road.getName() + " to " + to + " " + road.getWeight() + " mi");
		}
		return path;
	}

	/**
	 * Uses Dijkstra's algorithm to find the shortest paths from the source town to
	 * all other towns in the graph.
	 * 
	 * @param sourceVertex the starting town
	 * @throws NullPointerException     if the source town is null
	 * @throws IllegalArgumentException if the source town is not in the graph
	 */
	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		if (sourceVertex == null) {
			throw new NullPointerException("Source town cannot be null.");
		}

		if (!containsVertex(sourceVertex)) {
			throw new IllegalArgumentException("Source town must be in the graph.");
		}

		distanceTo = new HashMap<>();
		previousTown = new HashMap<>();

		// Initialize all distances to infinity
		// Set all previous towns to null
		for (Town town : adjacencyMap.keySet()) {
			distanceTo.put(town, Integer.MAX_VALUE);
			previousTown.put(town, null);
		}
		// Set source to 0
		distanceTo.put(sourceVertex, 0);

		// Create a comparator so the priority queue chooses the town
		// with the smallest current distance first.
		Comparator<Town> comp = (o1, o2) -> Integer.compare(distanceTo.getOrDefault(o1, Integer.MAX_VALUE),
				distanceTo.getOrDefault(o2, Integer.MAX_VALUE));

		PriorityQueue<Town> pq = new PriorityQueue<>(comp);

		pq.add(sourceVertex);

		// Create visited vertex set
		Set<Town> visited = new HashSet<>();

		while (!pq.isEmpty()) {
			Town current = pq.poll();

			if (visited.contains(current)) {
				continue;
			}
			visited.add(current);

			// Visit all neighbors of the current town.
			for (Road road : adjacencyMap.get(current)) {
				Town neighbor = road.getSource().equals(current) ? road.getDestination() : road.getSource();

				if (visited.contains(neighbor)) {
					continue;
				}

				int newDistance = distanceTo.get(current) + road.getWeight();
				if (newDistance < distanceTo.get(neighbor)) {
					distanceTo.put(neighbor, newDistance);
					previousTown.put(neighbor, current);
					pq.add(neighbor);
				}
			}

		}
	}

}
