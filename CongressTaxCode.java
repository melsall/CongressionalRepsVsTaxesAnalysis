import java.io.*;
import java.util.*;

public class CongressTaxCode {
    public static void main (String [] args)throws FileNotFoundException{
        File houseStatistics = new File("House_Dataset.csv");
        File taxStatistics = new File("Tax_Dataset.csv");
        compareIncomeTaxesBasedOnParty(houseStatistics, taxStatistics);

    }
    /* Question 1: 
    The democratic party is generally the party associated with higher income taxes. 
    Is there actually a positive correlation between the percentage of democrats in power in the house and 
    an increase in individual income taxes from 1946-2018? */

    // pseudo code question 1
    /* 
    1. Read in Congressional dataset
    2. Identify each year as democrat/republican based on percentage of seats won by democrats
    3. make corresponding arraylist in tax dataset that groups 2 years together and finds averages

    */
    public static void compareIncomeTaxesBasedOnParty(File houseStatistics, File taxStatistics) throws FileNotFoundException{
        // Create Scanners 
        Scanner houseScanner = new Scanner(houseStatistics);
        Scanner taxScanner = new Scanner(taxStatistics);
        // Get the header of the congressional dataset
        houseScanner.nextLine(); // this is necessary because there are two rows of headers in this particular dataset
        houseScanner.nextLine();
        houseScanner.nextLine();
        String [] houseHeader = houseScanner.nextLine().split(",");
        ArrayList <String> houseHeaderAL =  new ArrayList <> (Arrays.asList(houseHeader));
        int houseYearIndex = houseHeaderAL.indexOf("Year"); // also need to be able to access the year
        int demPercentSeatsWonIndex = houseHeaderAL.indexOf("Percentage of seats won"); // get index of democratic percentage of seats won column
        // Get header of tax dataset
        String [] taxHeader = taxScanner.nextLine().split(",");
        ArrayList <String> taxHeaderAL = new ArrayList <> (Arrays.asList(taxHeader));
        // Also need to know the index of the individual income tax bucket
        int incomeTaxIndex = taxHeaderAL.indexOf("Individual Income Taxes");
        // Need to move taxScanner to start at 1946 since the two have different start dates
        boolean keepIterating = true;
        int whatLine = 0;
        while(keepIterating){
            whatLine++;
            System.out.println("Line number " + whatLine);
            String [] taxLine = taxScanner.nextLine().split(",");
            ArrayList <String> taxLineAL = new ArrayList <> (Arrays.asList(taxLine));
            if (taxLineAL.indexOf("1946") != -1){
                System.out.println("BREAK");
                break;
            }
        }
        /* the years that be considered in this project are the years 1946-2012 although the house dataset contains data up to 2018. The tax dataset 
        has "estimates" of taxes up to 2018, but only has real data up to 2021. Since the tax dataset doesn't have real data up to that 2018, the boolean endYear must be created to ensure the 
        Scanner doesn't go past 2012 in the datsets and therefore treat estimated data as real data */
        int endYear = 2018;
        int currentYear = 0;
        // Variables used as running totals in loop prepared
        int repubYears = 0;
        int demYears = 0;
        double repubTotalPercents = 0;
        double demTotalPercents = 0;
        // Iterate through house dataset and tax datset, keep track of total # of democract years and republican years and the percentage of personal income tax for each
        while(!(currentYear == endYear)){
            String houseLine = houseScanner.nextLine();
            Scanner houseLineScanner = new Scanner (houseLine);
            String [] houseLineAA = houseLine.split(",");
            //updating currentYear so it can be checked in the condition of the while loop
            currentYear = Integer.parseInt(houseLineAA[houseYearIndex]);
            double demPercentSeatsWon = Double.parseDouble(houseLineAA[demPercentSeatsWonIndex]);
            // Go into tax datset and get the taxation percentage from the correct year
            double percentIncomeTaxOfNextYears = 0;
            for (int i = 0; i < 2; i++){
                String taxLine = taxScanner.nextLine();
                Scanner taxLineScanner = new Scanner(taxLine);
                String [] taxLineAA = taxLine.split(",");
                percentIncomeTaxOfNextYears = percentIncomeTaxOfNextYears + Double.parseDouble(taxLineAA[incomeTaxIndex]);
                taxLineScanner.close();
            }
            
            // now according to whether or not it's a democrat majority year, the general variables are updated
            if ((double)demPercentSeatsWon > 50.0){
                demYears++;
                demTotalPercents = demTotalPercents + percentIncomeTaxOfNextYears;
            }
            else{
                repubYears++;
                repubTotalPercents = repubTotalPercents + percentIncomeTaxOfNextYears;
            }
        }
        double averagePercentFromIncomeTaxRepublican = repubTotalPercents/repubYears;
        double averagePercentFromIncomeTaxDemocrat = demTotalPercents/demYears;
        System.out.println("Republican Average Percent from Individual Income Taxes:" + averagePercentFromIncomeTaxRepublican + "/n" + "Democrat Average Percent From Individual Income Taxes: " + averagePercentFromIncomeTaxDemocrat);
    }

    // Test to see if numbers are in the right range
    // Put it in the array list if it is
    // Go through first dataset and make an arraylist of all of the years
    // arraylist of integers and arraylist of arrays
}