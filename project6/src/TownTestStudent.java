import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TownTestStudent {

	private Town town1;
	private Town town2;
	private Town town3;

	@BeforeEach
	void setUp() throws Exception {
		town1 = new Town("Maryland");
		town2 = new Town("New York");
		town3 = new Town("Maryland"); // same name as town1
	}

	@AfterEach
	void tearDown() throws Exception {
		town1 = null;
		town2 = null;
		town3 = null;
	}

	@Test
	public void testConstructorSetsName() {
		assertEquals("Maryland", town1.getName());
	}

	@Test
	public void testConstructorInitializesEmptyAdjacentList() {
		assertTrue(town1.getAdjacentTowns().isEmpty());
	}

	@Test
	public void testCopyConstructor() {
		town1.addAdjTown(town2);
		Town copy = new Town(town1);
		assertEquals(town1.getName(), copy.getName());
		assertEquals(town1.getAdjacentTowns().size(), copy.getAdjacentTowns().size());
	}

	@Test
	public void testSetName() {
		town1.setName("Germantown");
		assertEquals("Germantown", town1.getName());
	}

	@Test
	public void testAddAdjTown() {
		town1.addAdjTown(town2);
		assertTrue(town1.getAdjacentTowns().contains(town2));
	}

	@Test
	public void testAddAdjTownNoDuplicates() {
		town1.addAdjTown(town2);
		town1.addAdjTown(town2); // add same town twice
		assertEquals(1, town1.getAdjacentTowns().size());
	}

	@Test
	public void testAddMultipleAdjTowns() {
		Town town4 = new Town("Cleveland");
		town1.addAdjTown(town2);
		town1.addAdjTown(town4);
		assertEquals(2, town1.getAdjacentTowns().size());
	}

	@Test
	public void testEqualsSameName() {
		assertEquals(town1, town3); // both "Maryland"
	}

	@Test
	public void testEqualsDifferentName() {
		assertNotEquals(town1, town2);
	}

	@Test
	public void testHashCodeSameTowns() {
		// If two objects are equal, their hashCodes must match
		assertEquals(town1.hashCode(), town3.hashCode());
	}

	@Test
	public void testHashCodeDifferentTowns() {
		assertNotEquals(town1.hashCode(), town2.hashCode());
	}
	
    @Test
    public void testCompareToLessThan() {
        // "Maryland" < "New York" alphabetically
        assertTrue(town1.compareTo(town2) < 0);
    }
 
    @Test
    public void testCompareToGreaterThan() {
        assertTrue(town2.compareTo(town1) > 0);
    }
 
    @Test
    public void testCompareToEqual() {
        assertEquals(0, town1.compareTo(town3));
    }
    
    @Test
    public void testToString() {
        assertEquals("Maryland", town1.toString());
    }

}
