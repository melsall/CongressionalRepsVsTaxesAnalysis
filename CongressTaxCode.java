import java.io.*;
import java.util.*;

public class CongressTaxCode {
    public static void main (String [] args)throws FileNotFoundException{
        File houseStatistics = new File("House_Dataset.csv");
        File taxStatistics = new File("Tax_Dataset.csv");
    }
    /* Question 1: 
    The democratic party is generally the party associated with higher income taxes. 
    Is the average percentage of federal revenue from income tax actually higher when democrats have a house majority?  */
    /* Question 2: During Republican dominated years, is the average percentage of revenue from corporate income taxes a
    ctually lower than in democratic years? */

    /* Used the houseStatistics and the taxStatistics arrays to compare the percent of federal revenue from a certain type of taxes within a set number of years */
    public static void compareTaxesBasedOnParty(File houseStatistics, File taxStatistics, int minYear, int maxYear, String typeOfTax) throws FileNotFoundException{
      // Using the splitIntoRepubAndDemYears method, save two arraylists as repubYears and demYears
      // Initialize some variables that will be used as running totals: demTotalPercentages, repubTotalPercentages, currentPartyInPower
      // Open a Scanner to access taxStatistics file
      // Save the header of the taxStatistics file (save the index of "Year" column to a variable, save the index of  typeOfTax to a variable)
      // Use a scanner to iterate through lines of taxStatistics, stop when the tax dataset runs out of years
      // Use a line scanner to isolate each line
      // For each line, save the element in the year column to a variable currentYear
      // check if currentYear appears in the democratic years or republican years arraylist (if it appears in neither, it is not an election year, so it should be added to the previous total that was incremented)
      // change currentPartyInPower in accordance with if the year is a democrat or republican year, also check if it contains "estimate" in it, if it does do not count it
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
    public static ArrayList <ArrayList<String>> splitIntoRepubAndDemYears(File houseStatistics, int minYear, int maxYear) throws fileNotFoundException{
        // Instantiate two ArrayLists, one to hold years with a democratic majority, one to hold years with a republican majority
        // Use the header so that the "Year" column and the "Percentage of seats wonc" can be indexed
        // Saved these indexes as yearIndex and percentageDemSeatsWonIndex
        // Open a Scanner to iterate through the "House Dataset" file
        // Scan each line individually, and save each line as an array of strings using split method
        // For each line, get the year and save to currentYear variable
        // if currentYear is in the range of >= min, <= max, do the following steps (have to use Integer.parseInt() to make this comparison)
        // Determine whether it is a "democrat year" or "republican year" based on if the percentageDemSeatsWon is > 0 or not
        // If it is a democratic year, add it to the democraticYears array
        // If it is a republican year, add it to the republican years array
        // At the end, return an ArrayList with democraticYears array as index1, republicanYears array as index 2
    }

    // Test to see if numbers are in the right range
    // Put it in the array list if it is
    // Go through first dataset and make an arraylist of all of the years
    // arraylist of integers and arraylist of arrays
}