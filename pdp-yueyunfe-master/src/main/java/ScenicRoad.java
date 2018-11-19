import java.util.Iterator;


/**
 * ScenicRoad inherited from Highway, has limited car speed when having more cars
 */


public class ScenicRoad extends Highway {

    private int max=0;    //max number of cars
    private double speed=0;   // max speed

    //constructor, and initial max number of cars and speed
    public ScenicRoad(int bandwidth){
        max=bandwidth*10;
        speed=5.0;
    }


    /**
     * Add cars to the direction but sonsidering the speed limit
     *
     * @param v Vehicle to be added.
     * @return true if added successfully. false otherwise
     */
    @Override
    public boolean add(Vehicle v) {
        if(v!=null){
            int direction=v.getDirection();
            if(!getContents().contains(v)){
                getContents().add(v);
                //east side
                if(direction==1){
                    if(numberVehiclesEastbound()>max){
                        reduceSpeed(1);
                    }
                }
                //west side
                else{
                    if(numberVehiclesEastbound()>max){
                        reduceSpeed(2);
                    }
                }

                //add successfully
                return true;
            }
            //already contains
            return false;
        }
        //null case
        return false;
    }

    /**
     * Reduce speed of all cars on this direction
     *
     * @param direction which direction the car heads to
     */
    public void reduceSpeed(int direction){
        Iterator<Vehicle> iter=iterator();
        while (iter.hasNext()){
            Vehicle vehicle=iter.next();
            if(direction==vehicle.getDirection()){
                if(vehicle.getVelocity()>5) {
                    vehicle.setVelocity(speed);
                }
            }
        }
    }
}
