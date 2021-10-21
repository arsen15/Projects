/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.Map;

/**
 * A vehicle subclass that holds the information for the Car vehicle.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class Car extends AbstractVehicle {
    
    /**
     * Constant that holds the car death time.
     * Time for which it cannot move.
     */
    private static final int CAR_DEATH_TIME = 15;
    
    /**
     * Initializing the instance fields for car.
     * 
     * @param theX is the X coordinate.
     * @param theY is the Y coordinate.
     * @param theDir is the vehicle's direction.
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /**
     * Returns the direction in which a car would like to move.
     * 
     * @param theNeighbors is the map the neighboring terrain.
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return possibleMove(theNeighbors);
    }
    
    /**
     * Returns the death time of 15 for the car.
     * {@inheritDoc}*/
    @Override
    public int getDeathTime() {
        return CAR_DEATH_TIME;
    }
    
    /**
     * This method determines that car cannot pass on red lights, and
     * car cannot pass on cross walks on red or yellow lights, and a car cannot pass
     * on grass, walls, or trails.
     * 
     * @param theTerrain is the type of terrain a vehicle passes through.
     * @param theLight is the light state that a vehicle passes through.
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean checkStop = true;
        if (theLight == Light.RED && theTerrain == Terrain.LIGHT) {
            checkStop = false;
        } else if (theTerrain == Terrain.CROSSWALK 
                        && (theLight == Light.RED || theLight == Light.YELLOW)) {
            checkStop = false;
        } else if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL 
                        || theTerrain == Terrain.TRAIL) {
            checkStop = false;
        }
        return checkStop;
    }
}