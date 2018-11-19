import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class HighwayTest {

    private static Highway highway=null;

    @BeforeClass
    public static void setupBeforeTests() throws Exception {
        highway=new Highway();

    }

    @Test
    public void add() throws Exception {
        highway.getContents().clear();
        assertEquals(0,highway.getContents().size());
        Vehicle vehicle=new Vehicle(10,1);
        highway.add(vehicle);
        assertEquals(1,highway.getContents().size());
        highway.add(vehicle);
        assertEquals(1,highway.getContents().size());
    }

    @Test
    public void remove() throws Exception {
        highway.getContents().clear();
        Vehicle vehicle=new Vehicle(10,1);
        highway.add(vehicle);
        Vehicle vehicle2=new Vehicle(10,1);
        highway.remove(vehicle2);
        assertEquals(1,highway.getContents().size());
        highway.remove(vehicle);
        assertEquals(0,highway.getContents().size());
    }

    @Test
    public void getVelocityEastbound() throws Exception {
        highway.getContents().clear();
        assertEquals(0.0,highway.getVelocityEastbound(),0.00001);
        Vehicle vehicle=new Vehicle(10,1);
        highway.add(vehicle);
        Vehicle vehicle2=new Vehicle(100,1);
        highway.add(vehicle2);
        Vehicle vehicle3=new Vehicle(9,2);
        highway.add(vehicle3);
        Vehicle vehicle4=new Vehicle(8,1);
        highway.add(vehicle4);
        assertEquals(8,highway.getVelocityEastbound(),0.00001);


    }

    @Test
    public void getVelocityWestbound() throws Exception {
        highway.getContents().clear();
        assertEquals(0.0,highway.getVelocityEastbound(),0.00001);
        Vehicle vehicle=new Vehicle(10,2);
        highway.add(vehicle);
        Vehicle vehicle2=new Vehicle(100,2);
        highway.add(vehicle2);
        Vehicle vehicle3=new Vehicle(9,1);
        highway.add(vehicle3);
        Vehicle vehicle4=new Vehicle(8,2);
        highway.add(vehicle4);
        assertEquals(8,highway.getVelocityWestbound(),0.00001);
    }

    @Test
    public void numberVehiclesEastbound() throws Exception {
        highway.getContents().clear();
        assertEquals(0.0,highway.getVelocityEastbound(),0.00001);
        Vehicle vehicle=new Vehicle(10,2);
        highway.add(vehicle);
        Vehicle vehicle2=new Vehicle(100,2);
        highway.add(vehicle2);
        Vehicle vehicle3=new Vehicle(9,1);
        highway.add(vehicle3);
        Vehicle vehicle4=new Vehicle(8,2);
        highway.add(vehicle4);
        assertEquals(1,highway.numberVehiclesEastbound(),0.00001);

    }

    @Test
    public void numberVehiclesWestbound() throws Exception {
        highway.getContents().clear();
        assertEquals(0.0,highway.getVelocityEastbound(),0.00001);
        Vehicle vehicle=new Vehicle(10,2);
        highway.add(vehicle);
        Vehicle vehicle2=new Vehicle(100,2);
        highway.add(vehicle2);
        Vehicle vehicle3=new Vehicle(9,1);
        highway.add(vehicle3);
        Vehicle vehicle4=new Vehicle(8,2);
        highway.add(vehicle4);
        assertEquals(3,highway.numberVehiclesWestbound(),0.00001);
    }

    @Test
    public void contains() throws Exception {
        highway.getContents().clear();
        assertEquals(0.0,highway.getVelocityEastbound(),0.00001);
        Vehicle vehicle=new Vehicle(10,2);
        highway.add(vehicle);
        Vehicle vehicle2=new Vehicle(100,2);
        highway.add(vehicle2);
        Vehicle vehicle3=new Vehicle(9,1);
        highway.add(vehicle3);
        Vehicle vehicle4=new Vehicle(8,2);
        highway.add(vehicle4);
        assertEquals(true,highway.contains(vehicle));
        assertEquals(true,highway.contains(vehicle2));
        assertEquals(true,highway.contains(vehicle3));
        assertEquals(true,highway.contains(vehicle4));

    }

}