import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class VehicleTest {
    private static Vehicle vehicle=null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //test if no input for constructor
    @Test
    public void testConstructor(){
        try{
            vehicle=new Vehicle();
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(),is("parameter required"));
        }
    }


    //test if wrong velocity
    @Test
    public void testConstructorFormat1(){
        try{
            vehicle=new Vehicle(-100,2);
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(),is("invalid negative speed"));
        }
    }

    //test if wrong direction
    @Test
    public void testConstructorFormat2(){
        try{
            vehicle=new Vehicle(100,3);
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(),is("invalid direction"));
        }
    }




}