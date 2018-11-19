/**
 * This is part of Assignment 1: Environment Setup and Review of Java for PDP, Fall 2017.
 */

/**
 * Vehicle is a simple object that has a velocity and a direction.
 */
// You may not make Ball implement the Comparable interface.
public class Vehicle {

    private double velocity;
    private int direction;

    /**
     * Constructor that creates a new vehicle object with the specified velocity and direction.
     * @param velocity Velocity of the new object.
     * @param direction Direction of the new object, where 1 represents eastbound direction, and 2 westbound direction.
     */
    public Vehicle(double velocity, int direction) {
        if(velocity<0){
            throw new IllegalArgumentException("invalid negative speed");
        }else{
            this.velocity = velocity;
        }
        if(direction<0||direction>2){
            throw new IllegalArgumentException("invalid direction");
        }else{
            this.direction = direction;
        }
    }

    public Vehicle(){
       throw new IllegalArgumentException("parameter required");
    }


    /**
     * Returns the velocity of the Vehicle.
     * @return the velocity of the Vehicle.
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Returns the direction of the Vehicle.
     * @return the direction of the Vehicle.
     */
    public int getDirection() {
        return direction;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
}
