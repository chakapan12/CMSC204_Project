/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description:  Manages towns and roads for a town graph.
 * Due: 05/8/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Manages a Graph of Towns and Roads. Provides methods to add/remove towns and
 * roads, query the graph, read from a data file, and find shortest paths.
 *
 * Data file format per line: road-name,miles;town1;town2 Example:
 * I-94,282;Chicago;Detroit
 *
 * @author Chakapan Kanchana
 */
public class TownGraphManager implements TownGraphManagerInterface {

	private Graph graph;

	/**
	 * Constructs a TownGraphManager object with an empty graph.
	 */
	public TownGraphManager() {
		graph = new Graph();
	}

	/**
	 * Adds a road between two existing towns.
	 * 
	 * @param town1    the name of the first town
	 * @param town2    the name of the second town
	 * @param weight   the distance of the road
	 * @param roadName the name of the road
	 * @return true if the road was added, false otherwise
	 */
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town source = getTown(town1);
		Town destination = getTown(town2);

		if (source == null || destination == null) {
			return false;
		}

		Road road = graph.addEdge(source, destination, weight, roadName);
		return road != null;
	}

	/**
	 * Returns the name of the road connecting two towns.
	 * 
	 * @param town1 the name of the first town
	 * @param town2 the name of the second town
	 * @return the road name, or null if no road exists
	 */
	@Override
	public String getRoad(String town1, String town2) {
		Town source = getTown(town1);
		Town destination = getTown(town2);

		if (source == null || destination == null) {
			return null;
		}

		Road road = graph.getEdge(source, destination);

		if (road == null) {
			return null;
		}

		return road.getName();
	}

	/**
	 * Adds a town to the graph.
	 * 
	 * @param v the name of the town
	 * @return true if the town was added, false otherwise
	 */
	@Override
	public boolean addTown(String v) {
		if (v == null) {
			return false;
		}
		return graph.addVertex(new Town(v));
	}

	/**
	 * Checks whether the graph contains a town.
	 * 
	 * @param v the name of the town
	 * @return true if the town exists, false otherwise
	 */
	@Override
	public boolean containsTown(String v) {
		if (v == null)
			return false;

		return graph.containsVertex(new Town(v));
	}

	/**
	 * Checks whether a road connection exists between two towns.
	 * 
	 * @param town1 the name of the first town
	 * @param town2 the name of the second town
	 * @return true if a road exists, false otherwise
	 */
	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		Town source = getTown(town1);
		Town destination = getTown(town2);

		if (source == null || destination == null) {
			return false;
		}

		return graph.containsEdge(source, destination);
	}

	/**
	 * Returns all road names in alphabetical order.
	 * 
	 * @return an ArrayList of all road names
	 */
	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> roads = new ArrayList<>();

		for (Road road : graph.edgeSet()) {
			roads.add(road.getName());
		}

		Collections.sort(roads);
		return roads;
	}

	/**
	 * Deletes a road connection between two towns.
	 * 
	 * @param town1 the name of the first town
	 * @param town2 the name of the second town
	 * @param road  the name of the road to delete
	 * @return true if the road was deleted, false otherwise
	 */
	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Town source = getTown(town1);
		Town destination = getTown(town2);

		if (source == null || destination == null || road == null) {
			return false;
		}

		Road targetRoad = graph.getEdge(source, destination);

		if (targetRoad == null) {
			return false;
		}

		Road removedRoad = graph.removeEdge(source, destination, targetRoad.getWeight(), road);

		return removedRoad != null;
	}

	/**
	 * Deletes a town from the graph.
	 * 
	 * @param v the name of the town to delete
	 * @return true if the town was deleted, false otherwise
	 */
	@Override
	public boolean deleteTown(String v) {
		Town town = getTown(v);
		if (town == null) {
			return false;
		}

		return graph.removeVertex(town);
	}

	/**
	 * Returns all town names in alphabetical order.
	 * 
	 * @return an ArrayList of all town names
	 */
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> towns = new ArrayList<>();
		for (Town town : graph.vertexSet()) {
			towns.add(town.getName());
		}

		Collections.sort(towns);
		return towns;
	}

	/**
	 * Finds the shortest path between two towns.
	 * 
	 * @param town1 the starting town name
	 * @param town2 the destination town name
	 * @return an ArrayList of strings describing the shortest path
	 */
	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		Town source = getTown(town1);
		Town destination = getTown(town2);

		if (source == null || destination == null) {
			return new ArrayList<>();
		}
		return graph.shortestPath(source, destination);
	}

	/**
	 * Finds and returns a Town object from the graph by name.
	 * 
	 * @param name the town name to search for
	 * @return the Town object if found, or null if not found
	 */
	public Town getTown(String name) {
		if (name == null) {
			return null;
		}

		for (Town town : graph.vertexSet()) {
			if (town.getName().equals(name)) {
				return town;
			}
		}
		return null;
	}

	/**
	 * Reads town and road information from a file and adds it to the graph.
	 * 
	 * Each valid line should use this format:
	 * road-name,miles;town1;town2
	 * 
	 * @param selectedFile the file to read from
	 * @throws IOException if the file is missing, unreadable, or cannot be
	 *                     processed
	 */
	public void populateTownGraph(File selectedFile) throws IOException {
		if (selectedFile == null || !selectedFile.exists()) {
			throw new FileNotFoundException();
		}

		if (!selectedFile.canRead()) {
			throw new IOException();
		}
		try (Scanner sc = new Scanner(selectedFile)) {
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();

				if (line.isEmpty()) {
					continue;
				}

				String[] parts = line.split(";");

				if (parts.length != 3) {
					continue;
				}

				String roadInfo = parts[0].trim();
				String town1 = parts[1].trim();
				String town2 = parts[2].trim();

				String[] roadParts = roadInfo.split(",");
				if (roadParts.length != 2) {
					continue;
				}

				String roadName = roadParts[0].trim();
				int distance = Integer.parseInt(roadParts[1].trim());

				addTown(town1);
				addTown(town2);
				addRoad(town1, town2, distance, roadName);

			}
		}

	}

}
