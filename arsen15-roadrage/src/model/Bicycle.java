/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A vehicle subclass that holds the information for the Bicycle vehicle.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class Bicycle extends AbstractVehicle {
    
    /**
     * Constant that holds the bicycle death time.
     * Time for which it cannot move.
     */
    private static final int BICYCLE_DEATH_TIME = 35;
    
    /**
     * Initializing the instance fields for bicycle.
     * 
     * @param theX is the X coordinate.
     * @param theY is the Y coordinate.
     * @param theDir is the vehicle's direction.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /**
     * Returns the direction in which a bicycle would like to move based on the 
     * fact that bicycles prefer to move on trails, but can travel on streets, and
     * through lights, and through cross walk lights. Bicycle prefers
     * to go straight, or left, or right, and reverse if none of these
     * directions are available.
     * 
     * @param theNeighbors is the map the neighboring terrain.
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction move = null;
        
        final List<Direction> canChoose = new LinkedList<>();
        
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            final Direction possibleDir = entry.getKey();
            if (entry.getValue() == Terrain.TRAIL
                            && possibleDir != getDirection().reverse()) {
                move = possibleDir;                
            } else if ((entry.getValue() != Terrain.GRASS && entry.getValue() != Terrain.WALL)
                            && possibleDir != getDirection().reverse()) {
                canChoose.add(possibleDir);                
            }
        }
        move = lastMove(canChoose, move);
        return move;
    }
    
    /**
     * Returns the death time of 35 for the bicycle.
     * {@inheritDoc}
     */
    @Override
    public int getDeathTime() {
        return BICYCLE_DEATH_TIME;
    }
    
    /**
     * This method determines that bicycle can pass on green light or trail, but not on 
     * walls; and bicycle also can pass on any non-light terrain. Bicycle
     * cannot pass on any other light or terrain, meaning it stops.
     * 
     * @param theTerrain is the type of terrain a vehicle passes through.
     * @param theLight is the light state that a vehicle passes through.
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean checkStop = false;
        if ((theLight == Light.GREEN || theTerrain != Terrain.WALL) 
                        || theTerrain == Terrain.TRAIL) {
            checkStop = true;
        } else if (theTerrain != Terrain.LIGHT) {
            checkStop = true;
        }
        return checkStop;
    }
}