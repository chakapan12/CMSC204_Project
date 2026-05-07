/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: Represents a town in a graph.
 * Due: 05/8/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

import java.util.ArrayList;

/**
 * Represents a town (vertex) in the Town Graph.
 * 
 * @author Chakapan Kanchana
 */
public class Town implements Comparable<Town> {

	private String name;
	private ArrayList<Town> adjacentTowns;

	/**
	 * Constructs a Town object with the given name.
	 * 
	 * @param name the name of the town
	 */
	public Town(String name) {
		this.name = name;
		adjacentTowns = new ArrayList<>();

	}

	/**
	 * Copy constructor. Creates a new Town object using another Town object.
	 * 
	 * @param templateTown the town to copy
	 */
	public Town(Town templateTown) {
		this.name = templateTown.name;
		adjacentTowns = new ArrayList<Town>(templateTown.adjacentTowns);

	}

	/**
	 * Returns the name of the town.
	 * 
	 * @return the town name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the list of adjacent towns.
	 * 
	 * @return an ArrayList of adjacent towns
	 */
	public ArrayList<Town> getAdjacentTowns() {
		return adjacentTowns;
	}

	/**
	 * Sets the name of the town.
	 * 
	 * @param name the new town name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the list of adjacent towns.
	 * 
	 * @param adjacentTowns the new list of adjacent towns
	 */
	public void setAdjacentTowns(ArrayList<Town> adjacentTowns) {
		this.adjacentTowns = adjacentTowns;
	}

	/**
	 * Adds an adjacent town to this town's adjacency list. The town is only added
	 * if it is not already in the list.
	 * 
	 * @param adjTown the adjacent town to add
	 */
	public void addAdjTown(Town adjTown) {
		if (!adjacentTowns.contains(adjTown)) {
			adjacentTowns.add(adjTown);
		}
	}

	/**
	 * Compares this town with another town by name.
	 * 
	 * @param other the town to compare to
	 * @return a negative number, zero, or a positive number based on alphabetical
	 *         order
	 */
	@Override
	public int compareTo(Town other) {
		return this.name.compareTo(other.name);
	}

	/**
	 * Returns the hash code for this town. The hash code is based on the town name.
	 * 
	 * @return the hash code of the town name
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Checks whether this town is equal to another object. Two towns are equal if
	 * they have the same name.
	 * 
	 * @param obj the object to compare with this town
	 * @return true if the towns have the same name, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Town other = (Town) obj;
		return this.name.equals(other.name);
	}

	/**
	 * Returns the town name as a String.
	 * 
	 * @return the town name
	 */
	@Override
	public String toString() {
		return name;
	}

}
