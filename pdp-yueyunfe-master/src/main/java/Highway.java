/**
 * This is part of Assignment 1: Environment Setup and Review of Java for PDP, Fall 2017
 */

        import java.lang.Iterable;
        import java.util.Set;
        import java.util.LinkedHashSet;
        import java.util.Iterator;
        import java.util.Collections;

/**
 * This is a container that can be used to contain Vehicles.
 * A given Vehicle may only appear on a Highway once.
 */
public class Highway implements Iterable<Vehicle> {

    // Contents of the Highway.
    private Set<Vehicle> contents;

    /**
     * Constructor that creates a new Highway.
     */
    public Highway() {
        contents = new LinkedHashSet<Vehicle>();
    }

    /**
     * Implements the Iterable interface for this container.
     * @return an Iterator over the Vehicle objects contained
     * in this container.
     */
    public Iterator<Vehicle> iterator() {
        // If we just returned the iterator of "contents", a client
        // could call the remove() method on the iterator and modify
        // it behind our backs.  Instead, we wrap contents in an
        // "unmodifiable set"; calling remove() on this iterator
        // throws an exception.  This is an example of avoiding
        // "representation exposure."
        return Collections.unmodifiableSet(contents).iterator();
    }

    /**
     * Adds a vehicle on the Highway. This method returns <tt>true</tt>
     * if a Vehicle was successfully added on the Highway, i.e. vehicle is
     * not already on the Highway. (You are allowed to put
     * a Vehicle on a Highway only once. Hence, this method returns
     * <tt>false</tt>, if a Vehicle is already on the Highway).
     * @param v Vehicle to be added.
     * @requires v != null.
     * @return true if vehicle was successfully added on the highway,
     * i.e. vehicle is not already on the highway. Returns false, if vehicle is
     * already on the highway.
     */
    public boolean add(Vehicle v) {
        if(v!=null){
            // Your code goes here.  Remove the exception after you're done.
            if(!contents.contains(v)){
                contents.add(v);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Removes a vehicle from the highway. This method returns
     * <tt>true</tt> if vehicle was successfully removed from the
     * highway, i.e. vehicle is actually on the highway. You cannot
     * remove a Vehicle if it is not already on the Highway, and so ths
     * method will return <tt>false</tt>, otherwise.
     * @param v Vehicle to be removed.
     * @requires v != null.
     * @return true if vehicle was successfully removed from the Highway,
     * i.e. vehicle is actually on the highway. Returns false, if vehicle is not
     * on the highway.
     */
    public boolean remove(Vehicle v) {
        // Your code goes here.  Remove the exception after you're done.
        if(v!=null){
            if(contents.contains(v)){
                contents.remove(v);
                return true;
            }
            return false;
        }
        return false;

    }

    /**
     * Each Vehicle has a velocity. This method returns the velocity of the slowest vehicle in the Eastbound direction of the highway.
     * @return the velocity of the slowest eastbound vehicle
     */
    public double getVelocityEastbound() {

        Iterator<Vehicle> iter=iterator();
        double max=10000;
        while (iter.hasNext()){
            Vehicle vehicle=iter.next();
            if(vehicle.getDirection()==1&&vehicle.getVelocity()<max){
                max=vehicle.getVelocity();
            }
        }
        if(max==10000){
            max=0;
        }
        return max;
    }

    /**
     * Each Vehicle has a velocity. This method returns the velocity of the slowest vehicle in the Westbound direction of the highway.
     * @return the velocity of the slowest westbound vehicle
     */
    public double getVelocityWestbound() {
        // Your code goes here.  Remove the exception after you're done.
        Iterator<Vehicle> iter=iterator();
        double max=10000;
        while (iter.hasNext()){
            Vehicle vehicle=iter.next();
            if(vehicle.getDirection()==2&&vehicle.getVelocity()<max){
                max=vehicle.getVelocity();
            }
        }
        if(max==10000){
            max=0;
        }
        return max;
    }

    /**
     * Returns the number of Vehicles headed Eastbound.
     * @return the number of Vehicles headed Eastbound on the highway
     */
    public int numberVehiclesEastbound() {
        int n=0;
        Iterator<Vehicle> iter=iterator();
        while (iter.hasNext()){
            Vehicle vehicle=iter.next();
            if(vehicle.getDirection()==1){
                n++;
            }
        }
        return n;
    }

    /**
     * Returns the number of Vehicles headed Westbound.
     * @return the number of Vehicles headed Westbound on the highway
     */
    public int numberVehiclesWestbound() {
        int n=0;
        Iterator<Vehicle> iter=iterator();
        while (iter.hasNext()){
            Vehicle vehicle=iter.next();
            if(vehicle.getDirection()==2){
                n++;
            }
        }
        return n;
    }

    /**
     * This method returns <tt>true</tt> if a specific Vehicle is on the Highway.
     * It will return <tt>false</tt> otherwise.
     * @param v Vehicle to be checked if its on the Highway
     * @requires v != null.
     * @return true if this vehicle is on the Highway. Returns
     * false, otherwise.
     */
    public boolean contains(Vehicle v) {
        // Your code goes here.  Remove the exception after you're done.
        if(v!=null){
            if(contents.contains(v)){
                return true;
            }
            return false;
        }
        return false;
    }

    public Set<Vehicle> getContents() {
        return contents;
    }

}
