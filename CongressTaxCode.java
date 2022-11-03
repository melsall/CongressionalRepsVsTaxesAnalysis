import java.io.*;
import java.util.*;

public class CongressTaxCode {
    public static void main (String [] args)throws FileNotFoundException{
        File houseStatistics = new File("House_Dataset.csv");
        File taxStatistics = new File("Tax_Dataset.csv");

        // Testing 2 overarching methods
        compareTaxesBasedOnParty(houseStatistics, taxStatistics, 1946, 2012, "Individual Income Taxes");
        findCorrelationBetweenRepresentativesAndTaxes (houseStatistics, taxStatistics, 1946, 2012, "Corporate Income Taxes");
    }
    /* Question 1: 
    The democratic party is generally the party associated with higher income taxes. 
    Is the average percentage of federal revenue from income tax actually higher when democrats have a house majority?  */
    /* Question 2: Is there a positive correlation between amount of Republicans and power and the percent of federal revenue from
    corporate income taxes ? */
    // The Following method was used for research question 1
    /* Used the houseStatistics and the taxStatistics arrays to compare the percent of federal revenue from a certain type of taxes within a set number of years */
    public static void compareTaxesBasedOnParty(File houseStatistics, File taxStatistics, int minYear, int maxYear, String typeOfTax) throws FileNotFoundException{
      // Using the splitIntoRepubAndDemYears method, save two arraylists as repubYears and demYears
      ArrayList <ArrayList<String>> splitDataset = splitIntoRepubAndDemYears(houseStatistics, minYear, maxYear);
      ArrayList <String> demYears = splitDataset.get(0);
      ArrayList <String> repubYears = splitDataset.get(1);

      // Initialize some variables that will be used as running totals: demTotalPercentages, repubTotalPercentages, demsInPower
      double demTotalPercentages = 0;
      double repubTotalPercentages = 0;
      boolean demsInPower = true;
      // Open a Scanner to access taxStatistics file
      Scanner taxScanner = new Scanner(taxStatistics);
      // Save the header of the taxStatistics file (save the index of "Year" column to a variable, save the index of  typeOfTax to a variable)
      String [] header = taxScanner.nextLine().split(",");
      ArrayList <String> headerAsList = new ArrayList <String> (Arrays.asList(header));
      int indexOfTaxType = headerAsList.indexOf(typeOfTax);
      int indexOfYears = headerAsList.indexOf("Fiscal Year");
      System.out.println("Index of years " + indexOfYears);
      // Use a scanner/loop to iterate through lines of taxStatistics, stop when the tax dataset runs out of years
      while(taxScanner.hasNextLine()){
        //Isolate each line
        String [] lineOfTaxData = taxScanner.nextLine().split(",");
        ArrayList <String> lineOfTaxDataAL = new ArrayList <String> (Arrays.asList(lineOfTaxData));
        // For each line, save the element in the year column to a variable currentYear
        String currentYear = lineOfTaxDataAL.get(indexOfYears);
        double percentTaxFromTypeColumn = Double.parseDouble(lineOfTaxDataAL.get(indexOfTaxType));
        boolean validYear = currentYear.indexOf("estimate") == -1 && Integer.parseInt(currentYear) >= minYear && Integer.parseInt(currentYear) <= maxYear;
        // check whether the year appears in the democrat array, the republican array, or neither
        if(validYear && demYears.indexOf(currentYear) != -1){
            demsInPower = true;
            demTotalPercentages = demTotalPercentages + percentTaxFromTypeColumn;
        }
        else if (validYear && repubYears.indexOf(currentYear) != -1){
            demsInPower = false;
            repubTotalPercentages = repubTotalPercentages + percentTaxFromTypeColumn;
        }
        else if (validYear){
            // if it appears in neither but is a valid year, that is becasue it was not an election year, thus the party that was in the previous year is still the party in power
            if (demsInPower){
                demTotalPercentages = demTotalPercentages + percentTaxFromTypeColumn;
            }
            else{
                repubTotalPercentages = repubTotalPercentages + percentTaxFromTypeColumn;
            }
        }
      }
      taxScanner.close();
      /* Calculate two variables: averageRevenueFromTaxInDemYears would be calcluating by doing demTotalPercentages/demYears.size();, the republican equivalent
      would be calculated in the same manner */
      double averageRevenueFromTaxInDemYears = demTotalPercentages/demYears.size();
      double averageRevenueFromTaxInRepubYears = repubTotalPercentages/repubYears.size();
      // Print out a statement specifiying the type of tax, the percent from democratic party and the percent from the republican party
      System.out.println("The Republican Party's average percent federal revenue from " + typeOfTax + " is: " + averageRevenueFromTaxInRepubYears);
      System.out.println("The Democratic Party's average percent federal revenue from " + typeOfTax + " is: " + averageRevenueFromTaxInDemYears);
    }

    /* This is a helper method that will determine the years within the house file that are "democrat years " and the ones that are "republican years" and 
    will return an arrayList holding an arrayList of these years separately. 

    Note: this method returns an arraylist of arraylists of Strings rather than of Integers because these years will be compared to the years ine tax dataset. 
    Within the tax dataset, some of the years are estimates for example, the year 2018 is listed in the year column as "2018 estimate." Therefore, by 
    saving the years as ints, the .equals method can be used. In the case of this data, we do not want to utilize estimated values. So, 2018 would not be equal 
    to "2018 estimate," preserving the years as strings allows that distinction to be evaluated using the .equals method */
    public static ArrayList <ArrayList<String>> splitIntoRepubAndDemYears(File houseStatistics, int minYear, int maxYear) throws FileNotFoundException{
        // Create the main scanner that will be used throughout the method
        Scanner houseScanner = new Scanner(houseStatistics);
        // Instantiate two ArrayLists, one to hold years with a democratic majority, one to hold years with a republican majority
        ArrayList <String> demYears = new ArrayList<>();
        ArrayList <String> repubYears = new ArrayList <>();
        // Creating/Utilizing header so that indexes of the "Year" column and the "Percentage of seats wonc" can be saved
        houseScanner.nextLine();
        houseScanner.nextLine();
        houseScanner.nextLine();
        String [] header = houseScanner.nextLine().split(",");
        ArrayList <String> headerAsList = new ArrayList <String> (Arrays.asList(header));
        // Saved these indexes as yearIndex and percentageDemSeatsWonIndex
        int yearIndex = headerAsList.indexOf("Year");
        int percentageDemSeatsWonIndex = headerAsList.indexOf("Percentage of seats wonc");
        // Begin iterating through the house dataset
        while(houseScanner.hasNextLine()){
            // Save each line as an array of strings using split method
            String [] lineOfData = houseScanner.nextLine().split(",");
            ArrayList <String> currentLineAsList = new ArrayList <String> (Arrays.asList(lineOfData));
            //Saving each year to the variable currentYear
            String currentYear = currentLineAsList.get((yearIndex));
            // if currentYear is in the range, you want to add the year to one of the arraylists
            if (Integer.parseInt(currentYear) >= minYear && (Integer.parseInt(currentYear)) <= maxYear){
                // Determine whether it is a "democrat year" or "republican year" based on if the percentageDemSeatsWon is > 0 or not
                double percentDemocrats = Double.valueOf(currentLineAsList.get((percentageDemSeatsWonIndex)));
                // If it is a democratic year, add the currentYear to the democraticYears arraylist
                if (percentDemocrats >= 50){
                    demYears.add(currentYear);
                }
                else{
                    // if it is a republican year, add the currentYear to the republicanYears arraylist
                    repubYears.add(currentYear);
                }
            }
        }
        houseScanner.close();
        // At the end, return an ArrayList with democraticYears array as index1, republicanYears array as index 2
        ArrayList <ArrayList<String>> toRet = new ArrayList<ArrayList<String>> ();
        toRet.add(demYears);
        toRet.add(repubYears);
        return toRet;
    }
    // Finds correlation coefficient between the percentage of representatives across a span of years and a certain tax type, used for Research question 2
    public static void findCorrelationBetweenRepresentativesAndTaxes (File houseStatistics, File taxStatistics, int minYear, int maxYear, String correlatedTaxType) throws FileNotFoundException{
        //Create Scanner of the HouseStatistics File
        Scanner houseScanner = new Scanner(houseStatistics);
        // Getting ready to access the columns of the house file and to save the years column and the percentage of seats won column as two separate arraylists
        houseScanner.nextLine();
        houseScanner.nextLine();
        houseScanner.nextLine();
        // Creating header of the house dataset and saving the year and the representation column as different variables
        String [] houseHeader = houseScanner.nextLine().split(",");
        ArrayList <String> houseHeaderAsList = new ArrayList <String> (Arrays.asList(houseHeader));
        int yearIndex = houseHeaderAsList.indexOf("Year");
        int indexOfRepresentationColumn = houseHeaderAsList.indexOf("Percentage of seats wonc");
        // The two array lists that will hold the columns 
        ArrayList <String> relevantYearsAL = new ArrayList <>();
        ArrayList <String> relevantRepsColumnAL = new ArrayList <> ();
        // going through house scanner array and creating parallell arrays holding th eyears and the data relating to the specified tax type
        while(houseScanner.hasNextLine()){
            String [] lineOfData = houseScanner.nextLine().split(",");
            ArrayList <String> currentLineAsList = new ArrayList <String> (Arrays.asList(lineOfData));
            int currentYear = Integer.parseInt(currentLineAsList.get(yearIndex));
            // want to check and make sure the year is in range
            if (currentYear >= minYear && currentYear <= maxYear){
                //adding each year/piece of tax information to thte two parallel arrays
                relevantYearsAL.add(currentLineAsList.get((yearIndex)));
                relevantRepsColumnAL.add(currentLineAsList.get((indexOfRepresentationColumn)));
            }
        }
        houseScanner.close();
        // Create Scanner to go through tax statistics
        // Save header, and correlatedTaxType index as a varaible: taxTypeIndex
        Scanner taxScanner = new Scanner(taxStatistics);
        String [] header = taxScanner.nextLine().split(",");
        ArrayList <String> taxHeaderAsList = new ArrayList <String> (Arrays.asList(header));
        int indexOfTaxYearColumn = taxHeaderAsList.indexOf("Fiscal Year");
        int indexOfTaxTypeColumn = taxHeaderAsList.indexOf(correlatedTaxType);
        String [] taxAveragesColumn = new String[relevantYearsAL.size()];
        // going through the tax data to use the correct years to put the correct tax percentages in the correct slots of the taxAverages column so that it is parallel to the years and representatives arraylists
        while (taxScanner.hasNextLine()){
            String [] lineOfTaxData = taxScanner.nextLine().split(",");;
            ArrayList <String> lineOfTaxDataAL = new ArrayList <String> (Arrays.asList(lineOfTaxData));
            String currentTaxYear = lineOfTaxDataAL.get(indexOfTaxYearColumn);
            // get the tax rate corresponding to the current year in the taxes array
            String taxRateForCurrentYear = lineOfTaxDataAL.get(indexOfTaxTypeColumn);
            int positionInParallelYearsArray = relevantYearsAL.indexOf(currentTaxYear);
            // if the year from the taxes array exists within the relevant years arraylist, that information is needed, so it should be added to the taxAveragesColumnArray
            if(positionInParallelYearsArray != -1){
                taxAveragesColumn[positionInParallelYearsArray] = taxRateForCurrentYear;
            }
    }
    ArrayList <String> taxAveragesColumnAL = new ArrayList <String> (Arrays.asList(taxAveragesColumn));
    taxScanner.close();
    // Now we have 3 parallel arraylists, one corresponds to the years, one corresponds to the percentage of democrats for those years, one corresponds to the percentage federal revenue from the tax type in those years 
    // Calculate numerator of the correlation coefficient
    double numerator = calculateCorrelationNumerator(relevantRepsColumnAL, taxAveragesColumnAL);
    System.out.println("made it out of numerator");
    double denominator = calculateCorrelationDenominator(relevantRepsColumnAL, taxAveragesColumnAL);
    System.out.println("The Correlation between democrats in power (in the range of years " + minYear + "-" + maxYear + " for " + correlatedTaxType +  " is " + numerator/denominator);
    }
    // Sub method that uses two array lists to calculate the numerator of the correlation coefficient for those array lists
    public static double calculateCorrelationNumerator(ArrayList <String> xValues, ArrayList <String> yValues){
        System.out.println("Made it to numerator method");
        double [] barValues = getBarValues(xValues, yValues);
        // Save the bar values so they can be used later
        double xBar = barValues[0];
        double yBar = barValues[1];
        double totalNumerator = 0;
        for(int i = 0; i < xValues.size(); i++){
            double currentX = Double.parseDouble(xValues.get(i)) - xBar;
            double currentY = Double.parseDouble(yValues.get(i)) - yBar;
            // keeping the running total of the numerator
            totalNumerator = totalNumerator + currentX * currentY;
        } 
        return totalNumerator;
    }
    // Sub method that uses two array lists to calculate the denominator of the correlation coefficient for those array lists
    public static double calculateCorrelationDenominator(ArrayList <String> xValues, ArrayList <String> yValues){
        double currentXSum = 0; 
        double currentYSum = 0;
        double [] barValues = getBarValues(xValues, yValues);
        // Save the bar values so they can be used later
        double xBar = barValues[0];
        double yBar = barValues[1];
        for(int i = 0; i < xValues.size(); i++){
            currentXSum = currentXSum + Math.pow(Double.parseDouble(xValues.get(i)) - xBar, 2);
            currentYSum = currentYSum + Math.pow(Double.parseDouble(yValues.get(i)) - yBar, 2);
        }
        return Math.sqrt(currentXSum * currentYSum);
    }
    // takes two array lists and gets their "bar" or average values
    public static double [] getBarValues(ArrayList <String> xValues, ArrayList <String> yValues){
        double yRunningTotal = 0;
        double xRunningTotal = 0;
        // Iterate through x and y and get x and y bar values
        // Get sizes to figure out how long the loop needs to iterate for
        int xSize = xValues.size();
        int ySize = yValues.size();
        int numIterations = 0;
        // need to iterate through enough times so that the last element of the bigger array list is reached
        if (xSize > ySize){
            numIterations = xSize;
        }
        else{
            numIterations = ySize;
        }
        int counter = 0;
        while(counter < numIterations){
            if(xSize > counter){
                xRunningTotal = xRunningTotal + Double.parseDouble(xValues.get(counter));
            }
            if(ySize > counter){
                yRunningTotal = yRunningTotal + Double.parseDouble(yValues.get(counter));
            }
            counter ++;
        }
        // calculate bar values 
        double xBar = xRunningTotal/xValues.size();
        double yBar = yRunningTotal/yValues.size();
        double [] barValues = {xBar, yBar};
        return barValues;
    }
 }