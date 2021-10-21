/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A vehicle subclass that holds the information for the ATV vehicle.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class Atv extends AbstractVehicle {
    
    /**
     * Constant that holds the ATV death time.
     * Time for which it cannot move.
     */
    private static final int ATV_DEATH_TIME = 25;
    
    /**
     * Initializing the instance fields for ATV.
     * 
     * @param theX is the X coordinate.
     * @param theY is the Y coordinate.
     * @param theDir is the vehicle's direction.
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /**
     * Returns the random direction in which an ATV would like to move,
     * except reverse.
     * 
     * @param theNeighbors is the map the neighboring terrain.
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction move = null;
        
        final List<Direction> canChoose = new LinkedList<>();
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            if (entry.getValue() != Terrain.WALL 
                            && entry.getKey() != getDirection().reverse()) {
                canChoose.add(entry.getKey());
            }
        }
        move = canChoose.get(getRandom(canChoose.size()));
        return move;
    }
    
    /**
     * Returns the death time of 25 for the ATV.
     * {@inheritDoc}
     */
    @Override
    public int getDeathTime() {
        return ATV_DEATH_TIME;
    }
    
    /**
     * This method determines that an ATV can move on any terrain except the walls.
     * 
     * @param theTerrain is the type of terrain a vehicle passes through.
     * @param theLight is the light state that a vehicle passes through.
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean checkStop = true;
        
        if (theTerrain == Terrain.WALL) {
            checkStop = false;
        }
        return checkStop;
    }
}