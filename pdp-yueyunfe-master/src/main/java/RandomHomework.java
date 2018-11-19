/**
 * RandomHomework selects a random course name to display to the user
 */


import java.util.Random;

public class RandomHomework {

    /**
     * To print a random course name to the console
     * @param argv default var for main
     */
    public static void main(String [] argv){
        RandomHomework randomHomework=new RandomHomework();
        int n=getRandomNumber();
        System.out.println(randomHomework.getCourse(n));
    }

    /**
     * Method used to randomly select a course
     * @param n random index number
     * @return
     */
    public String getCourse(int n){

        String [] course=new String[] {"Programming Design Paradigms","Object oriented Design","Data structures and Algorithms","Data Mining"};
        return course[n];
    }

    /**
     * generate random integer from 0-3
     * @return index integer
     */
    public static int getRandomNumber(){
        Random rand=new Random();
        int n=rand.nextInt(4);
        return n;
    }
}
