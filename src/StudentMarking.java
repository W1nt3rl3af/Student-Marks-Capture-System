import java.util.Scanner;

/**
 * This class forms Java Assignment 1, 2021
 */
public class StudentMarking {

    /**
     * The message that the main menu must display
     */
    public final String MENU_TEMPLATE =
            "%nWelcome to the Student System. Please enter an option 0 to 3%n"
                    + "0. Exit%n"
                    + "1. Generate a student ID%n"
                    + "2. Capture marks for students%n"
                    + "3. List student IDs and average mark%n";

    public final String NOT_FOUND_TEMPLATE =
            "No student id of %s exists";


    /** Do NOT change the two templates ABOVE this comment.
     DO CHANGE the templates BELOW.
     */

    /**
     * Complete these templates which will be used throughout the program
     */

    public final String ENTER_MARK_TEMPLATE = "Please enter mark %d for student %s%n";
    public final String STUDENT_ID_TEMPLATE = "%S%S%02d%s%s";
    public final String NAME_RESPONSE_TEMPLATE =
            "You entered a given name of %s and a surname of %s%n" +
                    "Your studID is %s";
    public final String LOW_HIGH_TEMPLATE = "Student %s has a lowest mark of %d%n" +
            "A highest mark of %d%n";
    public final String AVG_MARKS_TEMPLATE =
            "Student ***%s*** has an average of %5.2f%n";
    public final String COLUMN_1_TEMPLATE = "%4s";
    public final String COLUMN_2_TEMPLATE = "%12s";
    public final String CHART_KEY_TEMPLATE = "Highest%11s%n%4d%12d%n";
    public final String REPORT_PER_STUD_TEMPLATE = "| Student ID %3d is %-5s | Average is %5.2f |%n";


    /**
     * Creates a student ID based on user input
     *
     * @param sc Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @return a studentID according to the pattern specified in {@link StudentMarking#STUDENT_ID_TEMPLATE}
     */
    public String generateStudId(Scanner sc) {
        /**
         *  Complete the generateStudId method which will allow a user to generate a student id
         */
        String name, studId;
        String firstName = "";
        String lastName = "";
        char midOfFirst;
        char midOfLast;
        char firstnameInitial;
        char lastnameInitial;
        int lastnameLength = 0;


        System.out.printf("Please enter your given name and surname (Enter 0 to return to main menu)%n");
        name = sc.nextLine();
        if (name.equals("0")){
            return null;
        }

        for (int x = 0; x < name.length(); x++) {
            if (name.charAt(x) == ' ') {
                firstName = name.substring(0, x);
                lastName = name.substring(x + 1);
                lastnameLength = lastName.length();
            }
        }
        firstnameInitial = firstName.charAt(0);
        lastnameInitial = lastName.charAt(0);
        midOfFirst = firstName.charAt(firstName.length() / 2);
        midOfLast = lastName.charAt(lastnameLength / 2);

        studId = String.format(STUDENT_ID_TEMPLATE,
                firstnameInitial, lastnameInitial, lastnameLength, midOfFirst, midOfLast);
        System.out.printf(NAME_RESPONSE_TEMPLATE, firstName, lastName, studId);

        return studId;
    }

    /**
     * Reads three marks (restricted to a floor and ceiling) for a student and returns their mean
     *
     * @param sc     Scanner reading {@link System#in} re-used from {@link StudentMarking#main(String[])}
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @return the mean of the three marks entered for the student
     */
    public double captureMarks(Scanner sc, String studId) {
        /**
         * Complete the captureMarks method which will allow a user to input three mark for a chosen student
         * DO NOT change MAX_MARK and MIN_MARK
         */

        final int MAX_MARK = 100;
        final int MIN_MARK = 0;

        int[] marks = new int[3];
        int checkMark;
        int numOfMark = 0;
        int lowestMark;
        int highestMark;
        double total = 0;
        double avg;

        for (int x = 0; x < 3; x++) {
            System.out.printf(ENTER_MARK_TEMPLATE, (x + 1), studId);
            checkMark = sc.nextInt();
            sc.nextLine();
            if (checkMark >= MIN_MARK && checkMark <= MAX_MARK) {
                marks[numOfMark] = checkMark;
                total += checkMark;
                numOfMark++;
            } else
                x--;
        }

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                if (marks[y] > marks[y + 1]) {
                    int temp = marks[y];
                    marks[y] = marks[y + 1];
                    marks[y + 1] = temp;
                }
            }
        }
        lowestMark = marks[0];
        highestMark = marks[2];

        System.out.printf(LOW_HIGH_TEMPLATE, studId, lowestMark, highestMark);

        avg = total / marks.length;

        System.out.printf(AVG_MARKS_TEMPLATE, studId, avg);


        System.out.printf("Would you like to print a bar chart? [y/n]%n");
        if (sc.nextLine().equals("y")) {
            printBarChart(studId, highestMark, lowestMark);
        }
        return avg;
    }

    /**
     * outputs a simple character-based vertical bar chart with 2 columns
     *
     * @param studId a well-formed ID created by {@link StudentMarking#generateStudId(Scanner)}
     * @param high   a student's highest mark
     * @param low    a student's lowest mark
     */
    public void printBarChart(String studId, int high, int low) {
        /**
         * The printBarChart method which will print a bar chart of the highest and lowest results of a student
         */
        System.out.printf("Student id statistics: %s%n", studId);
        final char COLUMN_CHAR = '*';
        int printHigh = high, printLow = low;

        while ( printHigh > printLow || printLow > 0){
            if (printHigh > printLow){
                System.out.printf(COLUMN_1_TEMPLATE, COLUMN_CHAR);
                printHigh--;
            }else if (printLow > 0){
                System.out.printf(COLUMN_1_TEMPLATE + COLUMN_2_TEMPLATE,COLUMN_CHAR, COLUMN_CHAR);
                printLow--;
                printHigh--;
            }
            System.out.printf("%n");
        }

        System.out.printf(CHART_KEY_TEMPLATE,"Lowest", high, low);
    }

    /**
     * Prints a specially formatted report, one line per student
     *
     * @param studList student IDs originally generated by {@link StudentMarking#generateStudId(Scanner)}
     * @param count    the total number of students in the system
     * @param avgArray mean (average) marks
     */
    public void reportPerStud(String[] studList,
                              int count,
                              double[] avgArray) {
        /**
         * The reportPerStud method which will print all student IDs and average marks
         */
        if (studList != null) {
            for (int x = 0; x < count; x++) {
                if (studList[x] != null) {
                    System.out.printf(REPORT_PER_STUD_TEMPLATE, (x + 1), studList[x], avgArray[x]);
                }
            }
        }
    }

    /**
     * The main menu
     */
    public void displayMenu() {
        /**
         * The displayMenu method to use the appropriate template with printf
         */
        System.out.printf(MENU_TEMPLATE);
    }

    /**
     * The controlling logic of the program. Creates and re-uses a {@link Scanner} that reads from {@link System#in}.
     *
     * @param args Command-line parameters (ignored)
     */
    public static void main(String[] args) {
        /**
         * Complete the main method to give the program its core
         */

        // DO NOT change sc, sm, EXIT_CODE, and MAX_STUDENTS
        Scanner sc = new Scanner(System.in);
        StudentMarking sm = new StudentMarking();
        final int EXIT_CODE = 0;
        final int MAX_STUDENTS = 5;


        String[] keepStudId = new String[MAX_STUDENTS];
        double[] avgArray = new double[MAX_STUDENTS];

        //  Build a loop around displaying the menu and reading then processing input
        int choice = 1;
        int numOfStud = 0;
        int whereIsIt = 0;
        String insertStudId;
        boolean found = false;

        while (choice != EXIT_CODE) {
            sm.displayMenu();
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    if (numOfStud < 5) {
                        keepStudId[numOfStud] = sm.generateStudId(sc);
                        numOfStud++;
                    }else {
                        System.out.printf("You cannot add any more students to the array");
                    }
                    break;

                case 2:
                    System.out.printf("Please enter the studId to capture their marks (Enter 0 to return to main menu)%n");
                    insertStudId = sc.nextLine();
                    if (insertStudId.equals("0")) {
                        break;
                    }
                    for (int y = 0; y < MAX_STUDENTS; y++) {
                        if (insertStudId.equals(keepStudId[y])) {
                            whereIsIt = y;
                            found = true;
                        }
                    }
                    if (found)
                        avgArray[whereIsIt] = sm.captureMarks(sc, insertStudId);
                    else
                        System.out.printf(sm.NOT_FOUND_TEMPLATE, insertStudId);
                    break;

                case 3:
                    sm.reportPerStud(keepStudId, numOfStud, avgArray);
                    break;

                default:
                    // Handle invalid main menu input
                    System.out.printf(
                            "You have entered an invalid option. Enter 0, 1, 2 or 3%n");
                    break;

                case EXIT_CODE:
                    System.out.printf("Goodbye%n");
                    break;
            }
        }

    }
}