import java.io.*;
import java.util.*;

public class CongressTaxCode {
    public static void main (String [] args)throws FileNotFoundException{
        File houseStatistics = new File("House_Dataset.csv");
        File taxStatistics = new File("Tax_Dataset.csv");
        splitIntoRepubAndDemYears(houseStatistics, 1946, 2012);
    }
    /* Question 1: 
    The democratic party is generally the party associated with higher income taxes. 
    Is the average percentage of federal revenue from income tax actually higher when democrats have a house majority?  */
    /* Question 2: During Republican dominated years, is the average percentage of revenue from corporate income taxes a
    ctually lower than in democratic years? */

    /* Used the houseStatistics and the taxStatistics arrays to compare the percent of federal revenue from a certain type of taxes within a set number of years */
    public static void compareTaxesBasedOnParty(File houseStatistics, File taxStatistics, int minYear, int maxYear, String typeOfTax) throws FileNotFoundException{
      // Using the splitIntoRepubAndDemYears method, save two arraylists as repubYears and demYears
      ArrayList <ArrayList<String>> splitDataset = splitIntoRepubAndDemYears(houseStatistics);
      ArrayList <String> demYears = splitDataset.get(0);
      ArrayList <String> repubYears = splitDataset.get(1);

      // Initialize some variables that will be used as running totals: demTotalPercentages, repubTotalPercentages, demsInPower
      double demTotalPercentages = 0;
      int repubTotalPercentages = 0;
      boolean demsInPower = true;
      // Open a Scanner to access taxStatistics file
      Scanner taxScanner = new Scanner(taxStatistics);
      // Save the header of the taxStatistics file (save the index of "Year" column to a variable, save the index of  typeOfTax to a variable)
      String [] header = taxScanner.nextLine().split(",");
      ArrayList <String> headerAsList = new ArrayList <String> (Arrays.asList(header));
      int indexOfTaxType = headerAsList.indexOf(typeOfTax);
      int indexOfYears = headerAsList.indexOf("Year");
      // Use a scanner/loop to iterate through lines of taxStatistics, stop when the tax dataset runs out of years
      while(taxScanner.hasNextLine()){
        //Isolate each line
        String [] lineOfTaxData = taxScanner.nextLine().split(",");
        ArrayList <String> lineOfTaxDataAL = new ArrayList <String> (Arrays.asList(lineOfTaxData));
        // For each line, save the element in the year column to a variable currentYear
        String currentYear = lineOfTaxData.get(indexOfYears);
        double percentTaxFromTypeColumn = lineOfTaxData.get(indexOfTaxType);
        boolean validYear = currentYear.indexOf("estimate") == -1;
        if(validYear && demYears.indexOf(currentYear) != -1){
            demsInPower = true;
            demTotalPercentages = demTotalPercentages + percentTaxFromTypeColumn;
        }

      }
      // check if currentYear appears in the democratic years or republican years arraylist (if it appears in neither, it is not an election year, so it should be added to the previous total that was incremented)
      // changer demsInPower boolean in accordance with if the year is a democrat or republican year, also check if it contains "estimate" in it, if it does do not count it
      // depending on the arraylist that the currentYear appears in, add the value of the typeOfTax column to the total for that political party
      // Access taxStatistics, get each year
      /* Calculate two variables: averageRevenueFromTaxInDemYears would be calcluating by doing demTotalPercentages/demYears.size();, the republican equivalent
      would be calculated in the same manner */
      // Print out a statement specifiying the type of tax, the percent from democratic party and the percent from the republican party
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
        // At the end, return an ArrayList with democraticYears array as index1, republicanYears array as index 2
        ArrayList <ArrayList<String>> toRet = new ArrayList<ArrayList<String>> ();
        toRet.add(demYears);
        toRet.add(repubYears);
        return toRet;
    }

    // Test to see if numbers are in the right range
    // Put it in the array list if it is
    // Go through first dataset and make an arraylist of all of the years
    // arraylist of integers and arraylist of arrays
}