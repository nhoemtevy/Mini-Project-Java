
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HallBookingSystem {
            private static String[] bookingIDs = new String[0];
            private static int[] bookingSessions = new int[0];
            private static String[][] bookingSeats = new String[0][0];
            private static int bookingCount = 0;
            public static void pressToContinue(){
                Scanner input = new Scanner(System.in);
                System.out.println("Press Any key to Continue!");
                input.nextLine();
            }
            public static void main(String[] args) {
                Scanner input = new Scanner(System.in);
                int rows;
                int cols = 0;
                String[][] mornings = new String[0][];
                String[][] afternoons = new String[0][];
                String[][] evenings = new String[0][];
                int options = 0;
                boolean isRowValid = false, isColsValid = false, isInputValid;
                do {
                    System.out.println("=========================================================");
                    System.out.println("\t\t\t CSTAD HALL BOOKING SYSTEM");
                    System.out.println("=========================================================");
                    System.out.print("CONFIG TOTAL HALL OF ROW: ");
                    String rowStr = input.nextLine();
                    if (rowStr.matches("^[0-9]*$") && Integer.parseInt(rowStr) > 0) {
                        isRowValid = true;
                        System.out.print("CONFIG TOTAL SEATS PER ROW IN HALL: ");
                        String colStr = input.nextLine();
                        if (colStr.matches("^[0-9]*$") && Integer.parseInt(colStr) > 0) {
                            isColsValid = true;
                            rows = Integer.parseInt(rowStr);
                            cols = Integer.parseInt(colStr);
                            mornings = new String[rows][cols];
                            afternoons = new String[rows][cols];
                            evenings = new String[rows][cols];
                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < cols; j++) {
                                    mornings[i][j] = "AV";
                                    afternoons[i][j] = "AV";
                                    evenings[i][j] = "AV";
                                }
                            }
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                    }

                } while (!isRowValid || !isColsValid);
                do {
                    System.out.println("=================[[APPLICATION MENU]]================");
                    System.out.println("1. Hall Booking");
                    System.out.println("2. Hall Checking");
                    System.out.println("3. ShowTime");
                    System.out.println("4. Reboot Showtime");
                    System.out.println("5. History Booking");
                    System.out.println("6. Exit");
                    do {
                        System.out.print("Please Choose option from 1-6 :");
                        String optionsStr = input.nextLine();
                        if (optionsStr.matches("^[1-6]$")) {
                            options = Integer.parseInt(optionsStr);
                            isInputValid = true;
                        } else {
                            System.out.println("Please Input the value 1-6!!");
                            isInputValid = false;
                        }
                    } while (!isInputValid);
                    switch (options) {
                        case 1:
                            Booking(mornings, afternoons, evenings);
                            break;
                        case 2:
                            HallChecking(mornings, afternoons, evenings);
                            break;
                        case 3:
                            showTime();
                            break;
                        case 4:
                            rebootHall(mornings, afternoons, evenings);
                            break;
                        case 5:
                            bookingHistory();
                            break;
                        case 6:
                            System.exit(0);
                            break;
                    }
                    pressToContinue();
                } while (true);
            }
            public static void Booking(String[][] mornings, String[][] afternoons, String[][] evenings) {
                Scanner input = new Scanner(System.in);
                System.out.println("=========================================");
                System.out.println("Select the session:");
                System.out.println("=========================================");
                System.out.println("1. Morning");
                System.out.println("2. Afternoon");
                System.out.println("3. Evening");
                int session=0;
                boolean validInput;
                do {
                    System.out.print("Please Choose option from 1-3:");
                    String sessionStr = input.nextLine();
                    if (sessionStr.matches("^[1-3]$")){
                        session = Integer.parseInt(sessionStr);
                        validInput = true;
                    }else {
                        System.out.println("Please input the valid option from 1-3!");
                        validInput = false;
                    }
                }while (!validInput);
                String[][] selectedSession;
                switch (session) {
                    case 1:
                        selectedSession = mornings;
                        break;
                    case 2:
                        selectedSession = afternoons;
                        break;
                    case 3:
                        selectedSession = evenings;
                        break;
                    default:
                        System.out.println("Invalid session choice.");
                        return;
                }
                System.out.println("Available seats in the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session:");
                char seatRow = 65;
                for (int i = 0; i < selectedSession.length; i++) {
                    for (int j = 0; j < selectedSession[i].length; j++) {
                        System.out.print("|" + (char) (seatRow + i) +"-" + (j+1) + " ::" + selectedSession[i][j] + "| ");
                    }
                    System.out.println();
                }
                System.out.println("====================|Booking Instruction|================");
                System.out.println("For single seat booking you can input A-1");
                System.out.println("For multi seat booking you can input A-1,A-2");
                System.out.println("==========================================================");
                System.out.print("Enter the seat numbers: ");
                String seatInput = input.next();
                String[] seatNumbers = seatInput.split(",");
                System.out.print("Enter the booking ID: ");
                String id = input.next();
                System.out.print("Are you sure you want to book these seats? (y/n): ");
                String confirmation = input.next();
                if ("y".equalsIgnoreCase(confirmation)) {
                    for (String seatNumber : seatNumbers) {
                        int seat = Integer.parseInt(seatNumber.trim().substring(2));
                        int row = (seat - 1) / selectedSession[0].length;
                        int col = (seat - 1) % selectedSession[0].length;
                        if (row >= 0 && row < selectedSession.length && col >= 0 && col < selectedSession[row].length) {
                            if (selectedSession[row][col].equals("AV")) {
                                selectedSession[row][col] = "BO";
                                System.out.println("Seat successfully booked with ID " + id + " in the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session!");
                                bookingIDs = Arrays.copyOf(bookingIDs, bookingCount + 1);
                                bookingSessions = Arrays.copyOf(bookingSessions, bookingCount + 1);
                                bookingSeats = Arrays.copyOf(bookingSeats, bookingCount + 1);
                                bookingIDs[bookingCount] = id;
                                bookingSessions[bookingCount] = session;
                                bookingSeats[bookingCount] = new String[]{String.join(",", seatNumbers)};
                                bookingCount++;
                            } else {
                                System.out.println("This seat is already booked in the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session.");
                            }
                        } else {
                            System.out.println("Invalid seat number for the " + (session == 1 ? "morning" : session == 2 ? "afternoon" : "evening") + " session.");
                        }
                    }
                } else {
                    System.out.println("Booking cancelled.");
                }
            }
            public static void HallChecking(String[][] mornings, String[][] afternoons, String[][] evenings){
                char seat = 65;
                System.out.println("Hall MORNING");
                for(int i=0; i<mornings.length; i++){
                    for(int j=0; j<mornings[i].length ; j++){
                        System.out.print("|" + (char)(seat+i)+  "-" + (j+1) + " ::" + mornings[i][j] + "| ");
                    }
                    System.out.println();
                }
                System.out.println("Hall AFTERNOON");{
                    for(int i=0; i<afternoons.length; i++){
                        for(int j=0; j<afternoons[i].length ; j++){
                            System.out.print("|" + (char)(seat+i)+  "-" + (j+1) + " ::" + afternoons[i][j] + "| ");
                        }
                        System.out.println();
                    }
                }
                System.out.println("Hall EVENING");{
                    for(int i=0; i<evenings.length; i++){
                        for(int j=0; j<evenings[i].length ; j++){
                            System.out.print("|" + (char)(seat+i)+  "-" + (j+1) + " ::" + evenings[i][j] + "| ");
                        }
                        System.out.println();
                    }
                }
            }
            public static void showTime(){
                System.out.println("======================================");
                System.out.println("#Showtime of Booking Hall :");
                System.out.println("# 1. Morning (10:00AM - 12:30PM)");
                System.out.println("# 2. Afternoon (3:00PM - 5:30PM");
                System.out.println("# 3. Evening (7:00PM - 9:30PM)");
                System.out.println("======================================");
            }
            public static void rebootHall(String[][] mornings, String[][] afternoons, String[][] evenings) {
                Scanner input = new Scanner(System.in);
                System.out.print("Are you sure you want to reboot the hall? This will clear all current bookings. (y/n): ");
                String confirmation = input.next();
                if ("y".equalsIgnoreCase(confirmation)) {
                    for (int i = 0; i < mornings.length; i++) {
                        for (int j = 0; j < mornings[i].length; j++) {
                            mornings[i][j] = "AV";
                        }
                    }
                    for (int i = 0; i < afternoons.length; i++) {
                        for (int j = 0; j < afternoons[i].length; j++) {
                            afternoons[i][j] = "AV";
                        }
                    }
                    for (int i = 0; i < evenings.length; i++) {
                        for (int j = 0; j < evenings[i].length; j++) {
                            evenings[i][j] = "AV";
                        }
                    }
                    bookingIDs = new String[0];
                    bookingSessions = new int[0];
                    bookingSeats = new String[0][0];
                    bookingCount = 0;
                    System.out.println("Hall has been rebooted. All current bookings have been cleared.");
                } else {
                    System.out.println("Reboot cancelled.");
                }
            }
            public static void bookingHistory() {
                if(bookingCount != 0) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String formattedDate = LocalDate.now().format(formatter);
                    System.out.println("=======================|History Booking|=================================");
                    System.out.println("Session\t\t\t\tID\t\t\t\tSeat\t\t\t\tDate");
                    if (bookingCount > 0) {
                        String session = bookingSessions[0] == 1 ? "morning" : bookingSessions[0] == 2 ? "afternoon" : "evening";
                        String seats = Arrays.toString(bookingSeats[0]).replaceAll("[\\[\\]]", "").replace(", ", ",");
                        System.out.println(String.format("%-10s%-10s%-15s%s", session,bookingIDs[0] , seats, formattedDate));
                        System.out.println("========================================================");
                    }
                } else {
                    System.out.println("You don't have any booking yet!");
                }
            }
}
