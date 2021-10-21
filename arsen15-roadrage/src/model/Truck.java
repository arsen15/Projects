/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.List;
import java.util.Map;

/**
 * A vehicle subclass that holds the information for the Truck vehicle.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class Truck extends AbstractVehicle {
    
    /**
     * Constant that holds the truck's death time.
     * Time for which it cannot move.
     */
    private static final int TRUCK_DEATH_TIME = 0;
    
    /**
     * Initializing the instance fields for truck.
     * 
     * @param theX is the X coordinate.
     * @param theY is the Y coordinate.
     * @param theDir is the vehicle's direction.
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /**
     * Returns the death time of zero for the truck.
     * {@inheritDoc}*/
    @Override
    public int getDeathTime() {
        return TRUCK_DEATH_TIME;
    }
    
    /**
     * Returns the direction in which a truck would like to move.
     * 
     * @param theNeighbors is the map the neighboring terrain.
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        return possibleMove(theNeighbors);
    }
    
    /**
     * Returns the randomly chosen truck movement based on
     * truck randomly selecting to go straight, turn left, or turn right, and 
     * reverse if none of the original options are available.
     * 
     * @param theList holds the list of possible moves.
     * @param theMove is the direction in which a vehicle decides to move.
     * @return the vehicle's move.
     * {@inheritDoc}
     */
    @Override
    protected Direction lastMove(final List<Direction> theList, final Direction theMove) {
        final Direction move;
        if (theList.isEmpty()) {
            move = getDirection().reverse();
        } else {
            move = theList.get(getRandom(theList.size()));
        }
        return move;
    }
    
     /**
      * This method determines that truck cannot pass cross walk when the light is red, and
      * truck cannot pass on grass, or walls, or trails. Truck can pass on 
      * other terrain and light states.
      * 
      * @param theTerrain is the type of terrain a vehicle passes through.
      * @param theLight is the light state that a vehicle passes through.
      * {@inheritDoc}*/
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean checkStop = true;
        if (theLight == Light.RED && theTerrain == Terrain.CROSSWALK) {
            checkStop = false;
        } else if (theTerrain == Terrain.GRASS || theTerrain == Terrain.WALL 
                        || theTerrain == Terrain.TRAIL) {
            checkStop = false;
        }
        return checkStop;
    }
}