// Programmer : Reilly Olmsted
// Class: CS &141
// Date: 02/17/2023
// Assignment: Calander3
 
import java.util.Scanner; // Program Uses the Scanner from the Library
import java.util.Calendar; // Program uses the calendar from the library
import java.util.Arrays; // Program imports the array utility from librRY
import java.io.*; 

public class Calendar3 // Begin Class 
{ 

   public static String[][] globalArray = new String[12][31];
   
   ////////////    The main method creates the and runs the menu    ////////////
   public static void main(String [] args) throws FileNotFoundException { 
      Scanner input = new Scanner(System.in);
      boolean exitMenu = false;
      eventsFileReader();
      
      PrintStream output = new PrintStream(System.out);
      
      
      output.println("Welcome to the Calendar Program.");
      output.println("Please Select from one of the Following Options.");
      output.println();
      int a = 1;
      int b = 7;  
   
      Calendar current = Calendar.getInstance();  
      int Cmonth = current.get(Calendar.MONTH)+1;
      int Cday = current.get(Calendar.DATE);
      
      while (!exitMenu) {
         output = getOutput(output, "console");
         
         output.println("Enter \"e\" " + "to enter a date and display the corresponding calendar");
         output.println("Enter \"t\" " + "to get todays date and display the today's calendar" );
         output.println("Enter \"n\" " + "to display the next month");
         output.println("Enter \"p\" " + "to display the previous month");
         output.println("Enter \"ev\" " + "to create an event");
         output.println("Enter \"fp\" " + "to print a month to a file");
         output.println("Enter \"q\" " + "to quit the program");
         
         String userMenu = input.next();
         String letter = userMenu.toLowerCase();
      
         switch (letter) {
            case "e":   
               output.println("What date are you looking for? (mm/dd)");
               String userDate = input.next();
               int month = processGetMonth(userDate);
               int day = processGetDay(userDate);    
               output = getOutput(output, "console");
               drawMonth(output, month, day, a, b);
               break;          
            case "t": 
               output = getOutput(output, "console");
               drawMonth(output, Cmonth, Cday, a, b);
               break;
            case "n":  
               current.add(Calendar.MONTH,1);
               Cmonth = current.get(Calendar.MONTH) + 1;
               output = getOutput(output, "console");
               drawMonth(output, Cmonth, Cday, a, b); 
               break;
            case "p":  
               current.add(Calendar.MONTH,-1);
               Cmonth = current.get(Calendar.MONTH) + 1;
               output = getOutput(output, "console");
               drawMonth(output, Cmonth, Cday, a, b);
               break;
            case "ev":
               output.println("(mm/dd) Event Name");
               eventMaker(output);          
               break;
            case "fp":
               output.println("Please enter a name for the new file");
               output = getOutput(output, "file");
               drawMonth(output, Cmonth, Cday, a, b);           
               break;
         
            case "q":  
               exitMenu = true;
               output.println("Exiting the Calendar Program. Thank You.");
               break;
               
            default: userMenu = "Invalid Entry";
               output.println("Invalid Entry, Please Try Again");
               break; 
         } // end switch statement      
      } // end while loop     
   } // End main method     

   ////////////    File Reader to Global Array    ////////////
   public static void eventsFileReader ()throws FileNotFoundException {        
      File file  = new File("calendarEvents.txt");
      Scanner fileReader = new Scanner(file);
      while (fileReader.hasNextLine()) {
         String events = fileReader.nextLine(); 
         int month = Integer.parseInt(events.substring(0,2));
         int day = Integer.parseInt(events.substring(3,5));
         String eventName = events.substring(6);
              
         globalArray[month-1][day-1] = eventName;    
      } // end while loop
             
   } // end of method 
   
   //////////// this method allows user to add their own events to calendar    ////////////
   public static void eventMaker(PrintStream output) {        
      Scanner input = new Scanner(System.in);
      String inputEvent = input.nextLine();
   
      int month = Integer.parseInt(inputEvent.substring(0,2));
      int day = Integer.parseInt(inputEvent.substring(3,5));
      String eventName = inputEvent.substring(6);
         
      globalArray[month-1][day-1] = eventName; 
                         
   } // end of method 

   ////////////    this method draws the calendar    ////////////
   public static void drawMonth(PrintStream output, int month, int day, int a, int b) {
   
      output.println("     " + month);
      
      int firstDay = getFirstDay(month-1); 
      int lastDay = getLastDay(month); 
      for (int i = 1; i <= 6; i++) {    
         lineOne(output);
         lineTwo(output, month, a, b, firstDay, lastDay);
         lineThreeFour(output);      
         a += 7;
         b += 7;
      } // End of for loop
      lineOne(output); 
      // date and month after line one execution 
      output.println("Month: " + month);
      output.println("Day: " + day);
      output.println();
   } // end of method
   
   ////////////    this method writes the first line to each row of the calendar    ////////////
   public static void lineOne(PrintStream output) {
      for (int i = 1; i <= 218; i++) {
         output.print("=");
      } // End lineOne 
      output.println();          
      return;
   } // End of lineOne       
   
   ////////////    This method writes the second row of each row to the calandar containing the correct day    ////////////
   public static void lineTwo(PrintStream output, int month, int begin, int end, int firstDay, int lastDay){    
      for (int i = begin-firstDay+1; i <= end-firstDay+1; i++) {
         if(i >= 1 && i <= lastDay && globalArray[month - 1][i-1] != null){       
            String eventName = globalArray[month - 1][i - 1];
            output.printf("|%-2s%28s", i, eventName);   
         } else if(i >= 1 && i <= lastDay) {
            output.printf("|%-30s", i); 
         } else {
            output.printf("|%30s", " "); 
         
         } // end of else   
      } // end of for loop date line
      output.println("|                         ");
   } // End lineTwo method   
   
   ////////////    print rest of lines for calendar    ////////////
   public static void lineThreeFour(PrintStream output) { 
      for (int i = 1; i <= 7; i++) {
         output.print("|                              ");
      }  
      output.println("|                            ");        
      for (int i = 1; i <= 7; i++) {
         output.print("|                              "); 
      } 
      output.println("|                            "); 
      for (int i = 1; i <= 7; i++) {
         output.print("|                              ");  
      }  
      output.println("|                            "); 
      for (int i = 1; i <= 7; i++) {
         output.print("|                              ");  
      }  
      output.println("|                            ");  
      for (int i = 1; i <= 7; i++) {
         output.print("|                              ");  
      }  
      output.println("|                            ");         
   } // End lineThreeFour
    
   ////////////    get current month    ////////////
   public static int processGetMonth(String userDate) {
      int month;
      String stringMonth = userDate.substring(0,2);
      
      month = Integer.parseInt(stringMonth);
      return month;     
   } // end month method
    
   ////////////    get current day    ////////////
   public static int processGetDay(String userDate) {
      int day;
      String stringDay = userDate.substring(3,5);
      
      day = Integer.parseInt(stringDay);
      return day; 
   } // end day method
   
   ////////////    get first day    ////////////   
   public static int getFirstDay(int month) {
      Calendar current = Calendar.getInstance();
      current.set(2023, month, 1);
      int day = current.get(Calendar.DAY_OF_WEEK);
      return day;  
   } // end of method 
   
   ////////////    get last day    ////////////
   public static int getLastDay(int month) {
      Calendar current = Calendar.getInstance();
      current.set(2023, month, 0);
      int day = current.get(Calendar.DAY_OF_MONTH);
      return day;   
   } // end method 
    
    ////////////    print stream method    ////////////
   public static PrintStream getOutput(PrintStream output, String destination) throws FileNotFoundException {
      Scanner input = new Scanner(System.in);
   
      if (destination.equals("console")) {
         output = new PrintStream(System.out);
      } else if (destination.equals("file")) {
         String inputFile = input.next();
         output = new PrintStream(inputFile);    
      } else {
         throw new IllegalArgumentException("Invalid destination");
      }
      return output;
   }
} // End of Class
