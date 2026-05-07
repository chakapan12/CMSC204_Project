import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoadTestStudent {

	private Town rockville;
	private Town potomac;
	private Town bethesda;
	private Road road1; // Rockville -> Potomac, 9, "I270-S"
	private Road road2; // Potomac -> Rockville, 9, "I270-S" (reverse of road1)
	private Road road3; // Bethesda -> Potomac, 7, "MD190E"

	@BeforeEach
	void setUp() throws Exception {
		rockville = new Town("Rockville");
		potomac = new Town("Potomac");
		bethesda = new Town("Bethesda");
		road1 = new Road(rockville, potomac, 9, "I270-S");
		road2 = new Road(potomac, rockville, 9, "I270-S");
		road3 = new Road(bethesda, potomac, 7, "MD190E");
	}

	@AfterEach
	void tearDown() throws Exception {
		rockville = null;
		potomac = null;
		bethesda = null;
		road1 = null;
		road2 = null;
		road3 = null;
	}

	@Test
	public void testConstructorFourArgs() {
		assertEquals("I270-S", road1.getName());
		assertEquals(9, road1.getWeight());
		assertEquals(rockville, road1.getSource());
		assertEquals(potomac, road1.getDestination());
	}

	@Test
	public void testConstructorThreeArgsDefaultWeight() {
		Road road = new Road(rockville, potomac, "I270-S");
		assertEquals(1, road.getWeight());
	}

	@Test
	public void testContainsSource() {
		assertTrue(road1.contains(rockville));
	}

	@Test
	public void testContainsDestination() {
		assertTrue(road1.contains(potomac));
	}

	@Test
	public void testContainsTownNotOnRoad() {
		assertFalse(road1.contains(bethesda));
	}

	@Test
	public void testEqualsForwardAndReverse() {
		// Rockville->Potomac should equal Potomac->Rockville (undirected graph)
		assertEquals(road1, road2);
	}

	@Test
	public void testEqualsSameObject() {
		assertEquals(road1, road1);
	}

	@Test
	public void testEqualsDifferentRoad() {
		assertNotEquals(road1, road3);
	}

	@Test
	public void testHashCodeSymmetric() {
		// road1 (Rockville->Potomac) and road2 (Potomac->Rockville) must have same hash
		assertEquals(road1.hashCode(), road2.hashCode());
	}

	@Test
	public void testHashCodeDifferentRoads() {
		assertNotEquals(road1.hashCode(), road3.hashCode());
	}
	
	@Test
    public void testCompareToLessThan() {
        // "I270-S" < "MD190E" alphabetically
        assertTrue(road1.compareTo(road3) < 0);
    }
 
    @Test
    public void testCompareToGreaterThan() {
        assertTrue(road3.compareTo(road1) > 0);
    }
 
    @Test
    public void testCompareToEqual() {
        assertEquals(0, road1.compareTo(road2));
    }
    
    @Test
    public void testGetName() {
        assertEquals("I270-S", road1.getName());
    }
 
    @Test
    public void testGetWeight() {
        assertEquals(9, road1.getWeight());
    }
 
    @Test
    public void testGetSource() {
        assertEquals(rockville, road1.getSource());
    }
 
    @Test
    public void testGetDestination() {
        assertEquals(potomac, road1.getDestination());
    }
    
    
    @Test
    public void testToStringContainsRoadName() {
        assertTrue(road1.toString().contains("I270-S"));
    }
 
    @Test
    public void testToStringContainsTownNames() {
        assertTrue(road1.toString().contains("Rockville"));
        assertTrue(road1.toString().contains("Potomac"));
    }
    
    

}
