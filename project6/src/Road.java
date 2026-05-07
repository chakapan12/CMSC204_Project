/*
 * Class: CMSC204 
 * Instructor: Farnaz Eivazi
 * Description: Represents a road (edge) connecting two towns in the Town Graph.
 * Due: 05/8/2026
 * Platform/compiler MacOS
 * I pledge that I have completed the programming assignment 
 * independently. I have not copied the code from a student or   
 * any source. I have not given my code to any student.
 * 
 * Print your Name here: Chakapan Kanchana
*/

/**
 * Represents a road (edge) connecting two towns in the Town Graph. Because the
 * graph is undirected, Road(A, B) is equal to Road(B, A).
 *
 * @author Chakapan Kanchana
 */

public class Road implements Comparable<Road> {

	private Town source;
	private Town destination;
	private int weight; // distances in miles
	private String name;

	/**
	 * Constructs a Road object with a source town, destination town, distance, and
	 * road name.
	 * 
	 * @param source      the starting town of the road
	 * @param destination the ending town of the road
	 * @param weight    the distance of the road in miles
	 * @param name        the name of the road
	 */
	public Road(Town source, Town destination, int weight, String name) {

		if (source == null) {
			throw new IllegalArgumentException("Source town cannot be null.");
		}

		if (destination == null) {
			throw new IllegalArgumentException("Destination town cannot be null.");
		}

		if (name == null) {
			throw new IllegalArgumentException("Road name cannot be null.");
		}

		if (weight < 0) {
			throw new IllegalArgumentException("Road distance cannot be negative.");
		}

		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.name = name;
	}

	/**
	 * Constructs a Road object with a default distance of 1.
	 * 
	 * @param source      the starting town of the road
	 * @param destination the ending town of the road
	 * @param name        the name of the road
	 */
	public Road(Town source, Town destination, String name) {
		this(source, destination, 1, name);
	}

	/**
	 * Compares this road with another road by road name.
	 * 
	 * @param other the road to compare to
	 * @return a negative number, zero, or a positive number based on alphabetical
	 *         order
	 * 
	 */
	@Override
	public int compareTo(Road other) {
		if (other == null) {
			throw new IllegalArgumentException("Road to compare cannot be null.");
		}
		return this.name.compareTo(other.name);
	}

	/**
	 * Checks whether this road contains the given town.
	 * 
	 * @param town the town to check
	 * @return true if the town is either the source or destination, false otherwise
	 */
	public boolean contains(Town town) {

		return source.equals(town) || destination.equals(town);
	}

	/**
	 * Returns the hash code for this road.
	 * 
	 * Since roads are undirected, the hash code must be the same whether the road
	 * is source-to-destination or destination-to-source.
	 * 
	 * @return the hash code for this road
	 */
	@Override
	public int hashCode() {
		return source.hashCode() + destination.hashCode();
	}

	/**
	 * Checks whether this road is equal to another object.
	 * 
	 * Two roads are equal if they connect the same two towns (in either order) and
	 * share the same name. Because the graph is undirected, A→B equals B→A.
	 *
	 * 
	 * @param obj the object to compare
	 * @return true if the roads are equivalent, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Road other = (Road) obj;

		boolean sameDirection = this.source.equals(other.source) && this.destination.equals(other.destination);
		boolean reverseDirection = this.source.equals(other.destination) && this.destination.equals(other.source);

		return (sameDirection || reverseDirection) && this.name.equals(other.name);
	}

	/**
	 * Returns the source town of this road.
	 * 
	 * @return the source town
	 */
	public Town getSource() {
		return source;
	}

	/**
	 * Returns the destination town of this road.
	 * 
	 * @return the destination town
	 */
	public Town getDestination() {
		return destination;
	}

	/**
	 * Returns the distance of this road.
	 * 
	 * @return the distance in miles
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Returns the name of this road.
	 * 
	 * @return the road name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a string representation of the road.
	 * 
	 * @return a string representation of the road
	 */
	@Override
	public String toString() {
		return name + "," + weight + ";" + source + ";" + destination;
	}

}
