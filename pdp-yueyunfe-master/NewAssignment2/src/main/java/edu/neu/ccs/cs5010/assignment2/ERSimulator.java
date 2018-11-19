package edu.neu.ccs.cs5010.assignment2;


public class ERSimulator {

  private static final String MESSAGE_ROOM_NUMBER = "How many rooms are in the hospital?";
  private static final String MESSAGE_RANDOM_OR_PRESET = "1.Random         2.Preset";
  //frequency for patient coming in millisec
  private static final long PATIENT_FREQUENCY = 3 * 60 * 1000;
  //simulation time in millisecond
  private static final long SIMULATION_TIME = 8 * 3600 * 1000;
  //simulation time in millisecond
  private static final long MILLITOHOURS = 1000 * 3600;

  private static int countExaming = 0;         //count the number of treatment taken
  private static long totaltime = 0;
  private static long start;

  private static Hospital hospital;

  public static void main(String[] arg) {
    int roomtotal;
    int option;

    System.out.println(MESSAGE_ROOM_NUMBER);
    System.out.println(MESSAGE_RANDOM_OR_PRESET);

    if (arg.length != 2) {
      throw new IllegalArgumentException("Need two parameters");
    }
    roomtotal = toInteger(arg[0]);
    option = toInteger(arg[1]);
    if (roomtotal < 0 || option > 2 || option < 1) {
      throw new IllegalArgumentException("illegal input");
    }
    hospital = new Hospital(roomtotal, option);

    //preset mode for option 2, if goes with option , it will run 8 hours simulation
    if (option == 1) {
      generateSimulator();
      if (hospital.getLastInUseRoom() == null) {
        totaltime = (SIMULATION_TIME) / MILLITOHOURS;
      } else {
        totaltime = (hospital.getLastInUseRoom().getEndingTime() - start) / MILLITOHOURS;
      }
    } else {
      hospital.presetMode(roomtotal);
      totaltime = SIMULATION_TIME / MILLITOHOURS;
      countExaming = hospital.getAssignNumber();
    }

    System.out.println("There are " + roomtotal + " rooms in the system");


    //total time

    //calculate and set the waiting time for those whos still waiting
    hospital.setPatientWaitingTime();

    System.out.println("The simulation runs " + totaltime + " hours");

    System.out.println("The hospital has " + countExaming + " people done for treatment");
    //System.out.println("There are "+count_examed+" people finished the treatment");

    //average
    System.out.println(String.format("The overall average for waiting is "
            + "%.3f minutes", hospital.averageTime()));
    System.out.println(String.format("The average of waiting for high emergency is %.3f "
            + "minutes", hospital.getHighemergencytimeave()));
    System.out.println(String.format("The average of waiting for low emergency "
            + "is %.3f minutes", hospital.getLowemergencytimeave()));

    //treatment
    System.out.println(String.format("The average treatment duration "
            + "is %.3f minutes", hospital.averageTreatmentTime()));


    for (int i = 0; i < roomtotal; i++) {
      Room room = hospital.getRoomById(i);
      System.out.println("For room " + i + "," + room.getNumberPatient() + " people were "
              + "treated and the busy percentage is "
              + String.format("%.1f%%", room.getPercentageBusy()));
    }
  }


  /**
   * Simulate the event that patients coming instantly as the defined frequency
   * Only be used when running the random simulation
   */
  public static void generateSimulator() {
    start = System.currentTimeMillis();
    long end = start + SIMULATION_TIME;
    long counting = start;
    long expected = start;

    while (counting <= end) {
      //register patient at this frequency
      if (expected == counting) {
        hospital.registerPatient();
        expected += PATIENT_FREQUENCY;
      }

      //there is both room and patient
      if (hospital.assignPatient()) {
        countExaming++;
      }
      counting = System.currentTimeMillis();
    }
  }

  /**
   * Check if a string can be convert to an integer.
   *
   * @param input string to convert
   * @return integer converted
   */
  public static int toInteger(String input) {
    try {
      int number = Integer.parseInt(input);
      return number;
    } catch (NumberFormatException e) {
      return -1;
    }
  }


}
