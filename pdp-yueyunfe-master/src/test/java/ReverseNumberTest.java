import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class ReverseNumberTest {

    private static ReverseNumber reverseNumber=null;

    @BeforeClass
    public static void setupBeforeTests() throws Exception {
        reverseNumber=new ReverseNumber();
        reverseNumber.setTest(true);
    }

    @Test
    public void InputTest() throws Exception {
        //test a good positive number
        String input="546";
        InputStream in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("The revered number is: 645",reverseNumber.getMessage());

        //test a good negative number
        input="-546";
        in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("The revered number is: -645",reverseNumber.getMessage());

        //test a non integer number
        input="-1.03";
        in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("Please try a valid interger",reverseNumber.getMessage());

        //test a positive non integer number
        input="1.03";
        in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("Please try a valid interger",reverseNumber.getMessage());

        //test a string
        input="hellow world";
        in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("Please try a valid interger",reverseNumber.getMessage());

        //test a space
        input=" ";
        in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("Please try a valid interger",reverseNumber.getMessage());

        //test an overflow
        input="2147483647";
        in=new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        reverseNumber.processInput();
        assertEquals("The number is overflow",reverseNumber.getMessage());

    }


}