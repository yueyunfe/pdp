import static org.junit.Assert.*;

public class RandomHomeworkTest {
    @org.junit.Test
    public void getCourse() throws Exception {
        RandomHomework randomHomework=new RandomHomework();
        String [] course=new String[] {"Programming Design Paradigms","Object oriented Design","Data structures and Algorithms","Data Mining"};
        int n=randomHomework.getRandomNumber();
        assertEquals(randomHomework.getCourse(n),course[n]);
    }

}