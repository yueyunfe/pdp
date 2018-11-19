import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ScenicRoadTest {
    @Test
    public void add() throws Exception {
        ScenicRoad scenicRoad=new ScenicRoad(1);

        //test west side
        Vehicle vehicle=new Vehicle(10,2);
        scenicRoad.add(vehicle);
        Vehicle vehicle2=new Vehicle(100,2);
        scenicRoad.add(vehicle2);
        Vehicle vehicle3=new Vehicle(9,2);
        scenicRoad.add(vehicle3);
        Vehicle vehicle4=new Vehicle(8,2);
        scenicRoad.add(vehicle4);
        Vehicle vehicle5=new Vehicle(10,2);
        scenicRoad.add(vehicle5);
        Vehicle vehicle6=new Vehicle(100,2);
        scenicRoad.add(vehicle6);
        Vehicle vehicle7=new Vehicle(9,2);
        scenicRoad.add(vehicle7);
        Vehicle vehicle8=new Vehicle(8,2);
        scenicRoad.add(vehicle8);
        Vehicle vehicle9=new Vehicle(9,2);
        scenicRoad.add(vehicle9);
        Vehicle vehicle10=new Vehicle(8,2);
        scenicRoad.add(vehicle10);

        Vehicle vehicle12=new Vehicle(8,1);
        scenicRoad.add(vehicle12);

        //max 10 cars, therefore, the speed should not be reduced
        Iterator<Vehicle> iter=scenicRoad.iterator();
        while (iter.hasNext()){
            Vehicle vehiclex=iter.next();
            assertTrue(vehiclex.getVelocity()>5);
        }
        Vehicle vehicle11=new Vehicle(8,2);
        scenicRoad.add(vehicle11);
        while (iter.hasNext()){
            Vehicle vehiclex=iter.next();
            assertEquals(5.0,vehiclex.getVelocity(),0.0005);
        }


        //test east side
        Vehicle vehicle13=new Vehicle(10,1);
        scenicRoad.add(vehicle13);
        Vehicle vehicle14=new Vehicle(100,1);
        scenicRoad.add(vehicle14);
        Vehicle vehicle15=new Vehicle(9,1);
        scenicRoad.add(vehicle15);
        Vehicle vehicle16=new Vehicle(8,1);
        scenicRoad.add(vehicle16);
        Vehicle vehicle17=new Vehicle(10,1);
        scenicRoad.add(vehicle17);
        Vehicle vehicle18=new Vehicle(100,1);
        scenicRoad.add(vehicle18);
        Vehicle vehicle19=new Vehicle(9,1);
        scenicRoad.add(vehicle19);
        Vehicle vehicle20=new Vehicle(8,1);
        scenicRoad.add(vehicle20);
        Vehicle vehicle21=new Vehicle(9,1);
        scenicRoad.add(vehicle21);
        //max 10 cars, therefore, the speed should not be reduced
        Iterator<Vehicle> iter2=scenicRoad.iterator();
        while (iter2.hasNext()){
            Vehicle vehicley=iter2.next();
            assertTrue(vehicley.getVelocity()>5);
        }
        Vehicle vehicle22=new Vehicle(8,1);
        scenicRoad.add(vehicle22);
        while (iter2.hasNext()){
            Vehicle vehicley=iter2.next();
            assertEquals(5.0,vehicley.getVelocity(),0.0005);
        }
    }

}