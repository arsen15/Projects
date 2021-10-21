/*
 * TCSS 305 - Winter 2021
 * Assignment 2 - Road Rage
 */

package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * This class represents default behavior for Vehicle subclasses.
 * 
 * @author Arsen Shintemirov
 * @version Winter 2021
 */
public abstract class AbstractVehicle implements Vehicle {
    
    /**
     * The X coordinate for vehicles.
     */
    private int myX;
    
    /**
     * The Y coordinate for vehicles.
     */
    private int myY;
    
    /**
     * The initial X coordinate for vehicles.
     */
    private final int myInitialX;
    
    /**
     * The initial Y coordinate for vehicles.
     */
    private final int myInitialY;
    
    /**
     * The direction a vehicle moves.
     */
    private Direction myDirection;
    
    /**
     * The initial direction of vehicles.
     */
    private Direction myInitialDir;
    
    /**
     * Status of the vehicle's "life". 
     */
    private boolean myVehicleStatus;
    
    /**
     * Keeps track of how long vehicles have been dead.
     */
    private int myPoke;
    
    /**
     * Initializing the instance fields.
     * 
     * @param theX the X coordinate of a vehicle.
     * @param theY the Y coordinate of a vehicle.
     * @param theDir the direction of a vehicle.
     */
    public AbstractVehicle(final int theX, final int theY, final Direction theDir) {
        
        super();
        myX = theX;
        myY = theY;
        myDirection = theDir;
        myVehicleStatus = true;
        myPoke = 0;
        myInitialX = theX;
        myInitialY = theY;
        myInitialDir = theDir;
    }
    
    /**
     * This method returns if a certain vehicle can pass specific terrain,
     * when light is at a certain state.
     * 
     * @param theTerrain is the type of terrain a vehicle passes through.
     * @param theLight is the light state that a vehicle passes through.
     */
    @Override
    public abstract boolean canPass(Terrain theTerrain, Light theLight);
    
    /**
     * Returns the direction in which a certain vehicle would like to move.
     * Different for every vehicle.
     *
     * @param theNeighbors is a Map containing the types of terrain that neighbor this vehicle.
     */
    @Override
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);
    
    /**
     * This method notifies a vehicle that it has collided with the given other Vehicle object.
     * Works differently for every vehicle. Only works for vehicles that are alive.
     * 
     * @param theOther is the other vehicle that a vehicle collides with.
     */
    @Override
    public void collide(final Vehicle theOther) {
        if (getDeathTime() > theOther.getDeathTime() 
                        && myVehicleStatus && theOther.isAlive()) {
            myVehicleStatus = false;
        }
    }
    
    /**
     * Returns the death time of a vehicle. It is the time
     * for which a vehicle cannot move.
     */
    @Override
    public abstract int getDeathTime();
    
    /**
     * Returns the name of the image file that the GUI uses
     *  to draw a Vehicle object on the screen.
     */
    @Override
    public String getImageFileName() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName().toLowerCase(Locale.ENGLISH));
        if (!isAlive()) {
            sb.append("_dead");
        }
        sb.append(".gif");
        
        return sb.toString();
    }
    
    /** 
     * Returns the direction that a vehicle is facing.
     * 
     * {@inheritDoc} */
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /** 
     * Returns the X coordinate of a vehicle.
     * {@inheritDoc} */
    @Override
    public int getX() {
        return myX;
    }

    /** 
     * Returns the Y coordinate of a vehicle.
     * {@inheritDoc} */
    @Override
    public int getY() {
        return myY;
    }

    /** 
     * Returns whether a vehicle is alive if it has collided with a more 
     * powerful vehicle.
     * 
     * {@inheritDoc} 
     */
    @Override
    public boolean isAlive() {
        return myVehicleStatus;
    }
    
    /** 
     * This method is called whenever the city animates one turn.
     * This allows dead vehicles to keep track of how long they have been dead and
     * when to revive themselves.
     *  
     * {@inheritDoc} */
    @Override
    public void poke() {
        if (!isAlive()) {
            myPoke++;
        }
        if (myPoke == getDeathTime()) {
            myVehicleStatus = true;
            myPoke = 0;
            myDirection = Direction.random();
        }
    }
    
    /** 
     * This method, when called, returns all vehicle objects to the initial state,
     * including position, direction, and makes them alive.
     * 
     * {@inheritDoc} */
    @Override
    public void reset() {
        myX = myInitialX;
        myY = myInitialY;
        myDirection = myInitialDir;
        myVehicleStatus = true;
    }
    
    /**
     *  This method sets the movement direction of a vehicle.
     *  
     *  @param theDir is the direction.
     *  {@inheritDoc} */
    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;
    }
    
    /** 
     * This method sets the X coordinate of a vehicle.
     * 
     * @param theX is the x coordinate.
     * {@inheritDoc} */
    @Override
    public void setX(final int theX) {
        myX = theX;
    }
    
    /** 
     * This method sets the Y coordinate of a vehicle.
     * 
     * @param theY is the y coordinate. 
     * {@inheritDoc} */
    @Override
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Returns the random number of vehicle moves.
     * 
     * @param theMoveNumber number of possible moves.
     * @return an integer.
     */
    protected int getRandom(final int theMoveNumber) {
        final Random rd = new Random();
        return rd.nextInt(theMoveNumber);
    }
    
    /**
     * Returns the last move from choices for a vehicle that goes straight, left, right, 
     * or reverse if no other option.
     * 
     * @param theList holds the list of possible moves.
     * @param theMove is the direction in which a vehicle decides to move.
     * @return the vehicle's move.
     */
    protected Direction lastMove(final List<Direction> theList,
                                 final Direction theMove) {
        Direction move = theMove;
        
        if (theMove == null && theList.isEmpty()) {
            move = getDirection().reverse();
        } else if (theMove == null && theList.contains(myDirection)) {
            move = myDirection;
        } else if (theMove == null && theList.contains(myDirection.left())) {
            move = myDirection.left();
        } else if (theMove == null && theList.contains(myDirection.right())) {
            move = myDirection.right();
        }
        return move;
    }
    
    /**
     * Returns the direction with preference of first going straight, or left, 
     * or right.
     * 
     * @param theNeighbors is the map the neighboring terrain.
     * @return the direction.
     */
    protected Direction possibleMove(final Map<Direction, Terrain> theNeighbors) {
        Direction direction = null;
        
        final List<Direction> canChoose = new LinkedList<>();
        for (final Map.Entry<Direction, Terrain> entry : theNeighbors.entrySet()) {
            final Direction availableDir = entry.getKey();
            if ((entry.getValue() != Terrain.GRASS && entry.getValue() != Terrain.WALL
                            && entry.getValue() != Terrain.TRAIL)
                            && availableDir != getDirection().reverse()) {
                canChoose.add(availableDir);                
            }
        }
        direction = lastMove(canChoose, direction);
        
        return direction;
    }
    
}