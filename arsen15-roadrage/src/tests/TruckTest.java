/*
 * TCSS 305 - Road Rage
 */

package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Car;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Test;

/**
 * Unit test for the Truck class.
 * 
 * @author Arsen
 * @version Winter 2021
 */
public class TruckTest {
    
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    
    /** Test method for Truck constructor. */
    @Test
    public void testTruckConstructor() {
        final Truck t = new Truck(19, 9, Direction.SOUTH);
        
        assertEquals("Truck x coordinate not initialized correctly!", 19, t.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 9, t.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.SOUTH, t.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, t.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", t.isAlive());
    }
    
    /** Test method for Truck setters. */
    @Test
    public void testTruckSetters() {
        
        final Truck t = new Truck(19, 9, Direction.SOUTH);
        t.setX(12);
        assertEquals("Truck setX failed!", 12, t.getX());
        t.setY(13);
        assertEquals("Truck setY failed!", 13, t.getY());
        t.setDirection(Direction.EAST);
        assertEquals("Truck setDirection failed!", Direction.EAST, t.getDirection());        
    }
    
    /**
     * Test method for {@link Truck#canPass(Terrain, Light)}.
     */
    @Test
    public void testCanPass() {
       
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.CROSSWALK);
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        for (final Terrain destinationTerrain : Terrain.values()) {
            
            for (final Light currentLight : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                    assertTrue("Truck should be able to pass STREET"
                                    + ", with light " + currentLight,
                                    truck.canPass(destinationTerrain, currentLight));
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                    if (currentLight == Light.RED) {
                        assertFalse("Truck cannot pass crosswalk"
                                    + ", with light " + currentLight,
                                    truck.canPass(destinationTerrain, currentLight));
                    } else {
                        assertTrue("Truck can pass crosswalk" + destinationTerrain
                                   + ", with light " + currentLight,
                                   truck.canPass(destinationTerrain, currentLight));
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
                    assertFalse("Truck should not be able to pass " 
                                 + destinationTerrain + ", with light " + currentLight,
                                 truck.canPass(destinationTerrain, currentLight));
                }
            }
        }
    }
    
    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionOnStreet() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.SOUTH) {
                seenSouth = true;
            }
        }
        
        assertTrue("Truck choseDirection() fails to select randomly "
                   + "among all possible valid choices!", 
                   seenWest && seenNorth && seenEast);
        assertFalse("Truck chooseDirection() reversed direction when not necessary!", 
                    seenSouth);
    }
    
    /**
     * Test method for {@link Truck#chooseDirection(java.util.Map)}.
     */
    @Test
    public void testChooseDirectionForReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK
                            && t != Terrain.LIGHT) {
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, truck.chooseDirection(neighbors));
            }
        }
    }
    
    /**
     * Test method for {@link Truck#getDeathTime()}. 
     */
    @Test
    public void testGetDeathTime() {
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        assertEquals("Wrong death time.", 0, truck.getDeathTime());        
    }
    
    /**
     * Test method for {@link Truck#getImageFileName()}. 
     */
    @Test
    public void testGetImageFileName() {
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        assertEquals("The image name is incorrect.", "truck.gif", truck.getImageFileName());
    }
    
    /**
     * Test method for {@link Truck#getDirection()}. 
     */
    @Test
    public void testGetDirection() {
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        assertEquals("The direction is incorrect.", Direction.NORTH, truck.getDirection());
    }
    
    /**
     * Test method for {@link Truck#getX()}. 
     */
    @Test
    public void testGetX() {
        final Truck truck = new Truck(10, 15, Direction.NORTH);
        assertEquals("The X coordinate is wrong.", 10, truck.getX());
    }
    
    /**
     * Test method for {@link Truck#getY()}. 
     */
    @Test
    public void testGetY() {
        final Truck truck = new Truck(10, 15, Direction.NORTH);
        assertEquals("The Y coordinate is wrong.", 15, truck.getY());
    }
    
    /**
     * Test method for {@link Truck#isAlive()}.
     */
    @Test
    public void testIsAlive() {
        final Truck truck = new Truck(10, 15, Direction.NORTH);
        assertEquals("The vehicle status is wrong.", true, truck.isAlive());
    }
    
    /**
     * Test method for {@link Truck#collide()}.
     */
    @Test
    public void testCollide() {
        final Truck truck = new Truck(12, 1, Direction.SOUTH);
        final Car car = new Car(12, 5, Direction.NORTH);
        
        truck.collide(car);
        assertEquals("The result from the collision is wrong.", true, truck.isAlive());
        car.collide(truck);
        assertEquals("The result from the collision is wrong.", false, car.isAlive());
    }
    
    /**
     * Test method for {@link Truck#poke()}.
     */
    @Test
    public void testPoke() {
        final Truck truck = new Truck(12, 1, Direction.SOUTH);
        final Car car = new Car(12, 5, Direction.NORTH);
        
        truck.collide(car);
        truck.poke();
        assertEquals("The result from the collision is wrong.", true, truck.isAlive());
        car.collide(truck);
        car.poke();
        assertEquals("The result from the collision is wrong.", false, car.isAlive());
        for (int i = 0; i < 50; i++) {
            car.poke();
        }
        assertEquals("The result from the collision is wrong.", true, car.isAlive());
    }
    
    /**
     * Test method for {@link Truck#reset()}.
     */
    @Test
    public void testReset() {
        final Truck truck = new Truck(12, 1, Direction.SOUTH);
        
        truck.setX(20);
        truck.setY(50);
        truck.setDirection(Direction.NORTH);
        truck.reset();
        assertEquals("The x coordinate is wrong.", 12, truck.getX());
        assertEquals("The y coordinate is wrong.", 1, truck.getY());
        assertEquals("The direction coordinate is wrong.", 
                     Direction.SOUTH, truck.getDirection());
    }
    
    /**
     * Test method for {@link Truck#setX()}.
     */
    @Test
    public void testSetX() {
        final Truck truck = new Truck(12, 1, Direction.SOUTH);
        
        truck.setX(20);
        assertEquals("The x coordinate is wrong.", 20, truck.getX());
    }
    
    /**
     * Test method for {@link Truck#setY()}.
     */
    @Test
    public void testSetY() {
        final Truck truck = new Truck(12, 1, Direction.SOUTH);
        
        truck.setY(20);
        assertEquals("The y coordinate is wrong.", 20, truck.getY());
    }
    
    /**
     * Test method for {@link Truck#setDirection()}.
     */
    @Test
    public void testSetDirection() {
        final Truck truck = new Truck(12, 1, Direction.SOUTH);
        
        truck.setDirection(Direction.WEST);
        assertEquals("The direction is wrong.", Direction.WEST, truck.getDirection());
    }
    
    
    
}