/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.Map;

/**
 * A vehicle subclass that holds the information for the Taxi vehicle.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class Taxi extends AbstractVehicle {
    
    /**
     * Constant that holds the taxi death time.
     * Time for which it cannot move.
     */
    private static final int TAXI_DEATH_TIME = 15;
    
    /**
     * Constant that holds the number of cycles a taxi waits for red light
     * to turn green.
     * Time for which it does not move.
     */
    private static final int STOP_COUNT = 3;
    
    /**
     * Counts the cycles for which the taxi is stopped.
     */
    private int myStopCount;
    
    /**
     * Initializing the instance fields for Taxi.
     * 
     * @param theX is the X coordinate.
     * @param theY is the Y coordinate.
     * @param theDir is the vehicle's direction.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
        myStopCount = 0;
    }
    
    /**
     * Returns the direction in which a taxi would like to move.
     * 
     * @param theNeighbors is the map the neighboring terrain.
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return possibleMove(theNeighbors);
    }
    
    /**
     * Returns the death time of 15 for the taxi.
     * {@inheritDoc}
     */
    @Override
    public int getDeathTime() {
        return TAXI_DEATH_TIME;
    }
    
    /**
     * This method determines that taxi cannot pass on red lights, and
     * taxi cannot pass on cross walks on red light and either 
     * waits 3 clock cycles or until light turns green; and a taxi cannot pass
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
        } else if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            checkStop = false;
            myStopCount++;
        } else if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL
                        || theTerrain == Terrain.TRAIL) {
            checkStop = false;
        }
        if (myStopCount == STOP_COUNT) {
            checkStop = true;
            myStopCount = 0;
        }
        return checkStop;
    }
    
    
}