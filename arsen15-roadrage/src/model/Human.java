/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A vehicle subclass that holds the information for the Human vehicle.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public class Human extends AbstractVehicle {
    
    /**
     * Constant that holds the human death time.
     * Time for which it cannot move.
     */
    private static final int HUMAN_DEATH_TIME = 45;
    
    /**
     * Initializing the instance fields for human.
     * 
     * @param theX is the X coordinate.
     * @param theY is the Y coordinate.
     * @param theDir is the vehicle's direction.
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
    }
    
    /**
     * Returns a direction in which a human would like to move
     * based on the fact that humans move straight, left, right, only
     * on cross walks and grass.
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
            if (entry.getValue() == Terrain.CROSSWALK
                            && possibleDir != getDirection().reverse()) {
                move = possibleDir;                
            } else if (entry.getValue() == Terrain.GRASS 
                            && possibleDir != getDirection().reverse()) {
                canChoose.add(possibleDir);
            }
        }
        move = lastMove(canChoose, move);
        return move;
    }
    
    /**
     * Returns the randomly chosen human movement based on the fact
     * that human randomly selecting to go straight, turn left, or turn right, and 
     * reverse if none of the original options are available.
     * 
     * @param theList holds the list of possible moves.
     * @param theMove is the direction in which a vehicle decides to move.
     * @return the vehicle's move.
     * {@inheritDoc}
     */
    @Override
    protected Direction lastMove(final List<Direction> theList,
                                 final Direction theMove) {
        Direction move = theMove;
        
        if (theMove == null && theList.isEmpty()) {
            move = getDirection().reverse();
        } else if (theMove == null && !theList.isEmpty()) {
            move = theList.get(getRandom(theList.size()));
        }
        return move;
    }
    
    /**
     * Returns the death time of 45 for the human.
     * {@inheritDoc}
     */
    @Override
    public int getDeathTime() {
        return HUMAN_DEATH_TIME;
    }
    
    /**
     * This method determines that human can pass on grass; human can pass
     * on cross walk if the cross walk light is red or yellow. Human
     * does not pass on any other terrain or light states.
     * 
     * @param theTerrain is the type of terrain a vehicle passes through.
     * @param theLight is the light state that a vehicle passes through.
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        
        boolean checkStop = false;
        if (theTerrain == Terrain.GRASS) {
            checkStop = true;
        } else if (theTerrain == Terrain.CROSSWALK 
                        && (theLight == Light.RED || theLight == Light.YELLOW)) {
            checkStop = true;
        }
        return checkStop;
    }
}