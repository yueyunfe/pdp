import java.util.Scanner;

/**
 * ReverseNumber reverse a number that user input
 */

public class ReverseNumber {

    private static String message=null;
    private static Boolean test=false;


    /**
     * Since I have a while loop, it is hard to jump out when testing.
     * Therefore, it is used to jump out of the loop if test is set
     * to be true
     *
     * @param test controller
     */
    public static void setTest(Boolean test) {
        ReverseNumber.test = test;
    }

    public static String getMessage() {
        return message;
    }

    /**
     * To print a random course name to the console
     * @param argv default var for main
     */
    public static void main(String[] argv){
        message="Please input an integer?";
        System.out.println(message);
        processInput();

        System.out.println(message);
    }


    /**
     * Doing the precess of reverse
     */
    public static void processInput(){
        Scanner scanner=new Scanner(System.in);

        String n=scanner.nextLine();

        while(!isInteger(n)){
            message="Please try a valid interger";
            if(test){
                return;
            }
            System.out.println(message);
            n=scanner.nextLine();
        }
        reverse(n);
    }



    /**
     * Used to determine if the user input is valid or not
     * @param n user input value
     * @return ture if it is a valid integer
     */
    public static boolean isInteger(String n){
        try{
            int number=Integer.parseInt(n);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    /**
     * Doing the process of reverse number
     * @param n user input number
     *
     */
    public static void reverse(String n){
        String number=n;
        int size=number.length();
        String newnumber="";
        if(number.charAt(0)=='-'){
            for(int i=size-1;i>0;i--){
                newnumber+=number.charAt(i);
            }
            newnumber="-"+newnumber;
        }else{
            for(int i=size-1;i>=0;i--){
                newnumber+=number.charAt(i);
            }
        }

        try{
            Integer.parseInt(newnumber);
        }catch (NumberFormatException ex){
            message="The number is overflow";
            return;
        }



        message="The revered number is: "+newnumber;

    }
}
