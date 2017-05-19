import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * One object of this class contains a 2D matrix of cellular subscription data
 * by country and year. Class constructor reads data from a CVS file. Key values
 * in the file establish the number of countries and the years of subscription.
 * Faulty CVS files read by the constructor result in an object with 0 countries,
 * 0 number of years and arrays of zero length. If the input file is shorter
 * than the number of given countries, the arrays are shortened to hold the data
 * available in the file. The resultant length of the data table is given by the
 * getNumberOfCountries method.
 *
 * CVS file assumptions:
 * 1) The keyword phrase "Number of countries" is found in the first comma
 * delimited position of a line prior to all other data.
 * 2) The line with "Number of countries" is followed by a line with "Country
 * Name" in the first comma delimited position.
 * 3) The integers in comma delimited positions following "Country Name" contain
 * the years covering the subscription data.
 * 4) The number of years determines the number of subscription data points for
 * each country.
 * 5) Immediately following the line with "Country Name" are lines containing
 * each countries subscription data with the first comma delimited position
 * containing the country name and all the following comma delimited positions
 * containing numeric data representing subscriptions. The number of data points
 * following the country name is the same for each line and equal to the number
 * of years read earlier.
 * @author Paul Hayter
 */
class CSVReader {
    private int[] yearLabels;
    private String[] countryNames;
    private double[][] cellularDataTable; // row represents countries & column years
    private int numYears, numberOfCountries;

    /**
     * Constructor. Reads csv data from fileName: reading year labels, country names and
     * subscription data. The data stored is the lesser of the number of countries given
     * in file or the actual number of countries in file.
     * @param fileName csv file to read data from.
     */
    public CSVReader(String fileName) {
        clearMembers(); // initialize members in case of early return

        Scanner in;
        File inFile = new File(fileName);
        try {
            in = new Scanner(inFile);
        }
        catch(FileNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        // find line in file with string "Number of countries" and extract numberOfCountries value
        String lineIn;
        if ((lineIn = findToken("Number of countries", in)) == null)
            return; // expected string token missing from file
        if ((numberOfCountries = findNumCountries(lineIn)) == 0)
            return; // no legal value found for numberOfCountries

        // find (next) line in file with string "Country Name" and fill yearLabels array
        if ((lineIn = findToken("Country Name", in)) == null)
            return; // expected string token missing from file
        fillYearLabels(lineIn);

        fillCellularDataTable(in);

        in.close();
    }

    /**
     * Returns String array of countries in database.
     * @return specified String array.
     */
    public String[] getCountryNames() {
        return countryNames;
    }

    /**
     * Returns int array of years corresponding to data in database.
     * @return specified int array.
     */
    public int[] getYearLabels() {
        return yearLabels;
    }

    /**
     * Returns 2D array of doubles with subscription data with rows representing
     * countries and columns representing years.
     * @return specified 2D doubles array.
     */
    public double[][] getParsedTable() {
        return cellularDataTable;
    }

    /**
     * Returns number of years in database.
     * @return specified value.
     */
    public int getNumberOfYears() {
        return numYears;
    }

    /**
     * Returns number of countries in database. Note: this number may be less
     * than the number specified as Number of countries in the data file if there
     * is a mismatch in file size.
     * @return specified number of countries.
     */
    public int getNumberOfCountries() {
        return numberOfCountries;
    }

    /**
     * Returns line from inFile which contains the token at its beginning, or
     * returns null if token is not found in inFile at the beginning of a line
     * (including returning null if token is null).
     * @param token String token to find at beginning of a line in file inFile.
     * @param inFile file in which beginning of lines are examined to find token
     *              in the first position.
     * @return specified line as String.
     */
    private String findToken(String token, Scanner inFile) {
        if (token == null || inFile == null)
            return null;

        // find next line in file with token in first position
        while (inFile.hasNextLine()) {
            String temp = inFile.nextLine();
            String[] tokens = temp.split(",");
            if (token.equalsIgnoreCase(tokens[0].trim()))
                return temp;
        }
        return null; // indicates token not found
    }

    /**
     * Reads the first non-white-space token starting with the second token in
     * the String lineIn. Returns the integer value of this first non-white-space
     * token if it is positive otherwise returns 0. The 0 return includes when
     * first non-white-space value is not an integer or if it is negative or if
     * there is no integer or if lineIn is null. This method is intended for
     * use in returning the integer following the Number of countries token in
     * lineIn.
     * @param lineIn String with country number following the Number of countries token.
     * @return the specified integer value.
     */
    private int findNumCountries(String lineIn) {
        if (lineIn == null )
            return 0;

        String[] tokens = lineIn.split(",");

        for (int i = 1; i < tokens.length; ++i) {
            if (tokens[i].trim().length() == 0) // SKIP EMPTY TOKENS
                continue;
            try {
                int numCountries = Integer.parseInt(tokens[i]);
                if (numCountries >= 0)
                    return numCountries;
            }
            catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Fills yearLabels array with year values in lineIn. Clears member
     * attributes if lineIn is null or if lineIn has one or fewer tokens or if
     * there are non-numeric yearLabel tokens. This method is intended for use
     * in filling the yearLabels array with the year values following the
     * Country Name token.
     * @param lineIn String with year values starting at second token.
     * @return Returns true if fill was successful.
     */
    private boolean fillYearLabels(String lineIn) {
        if (lineIn == null) {
            clearMembers();
            return false;
        }

        String[] tokens = lineIn.split(",");
        if (tokens.length <= 1) {// effectively empty
            clearMembers();
            return false;
        }

        numYears = tokens.length - 1;
        yearLabels = new int[numYears];

        for (int i = 0; i < numYears && i <= tokens.length; ++i) {
            try {
                yearLabels[i] = Integer.parseInt(tokens[i + 1]);
            }
            catch (NumberFormatException e) {
                clearMembers();
                return false;
            }
        }
        return true;
    }

    /**
     * Fills cellularDataTable and countryName arrays with data from inFile.
     * Clears member attributes if inFile is null or if numberOfCountries
     * is less than/equal to 0 or if subscription data has incorrect amount of
     * data or if subscription data is non-numeric.
     * @param inFile file which contains subscription date with current file
     *               position being below the line with the Country Name token.
     * @return Returns true if fill was successful.
     */
    private boolean fillCellularDataTable(Scanner inFile) {
        if (inFile == null || !inFile.hasNext() || numberOfCountries <= 0 || numYears <= 0) {
            clearMembers();
            return false;
        }

        // read in country names and their data after allocating space for them
        countryNames = new String[numberOfCountries];
        cellularDataTable = new double[numberOfCountries][numYears];
        // regex pattern from http://stackoverflow.com/questions/1757065/
        // java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
        final String regexPattern = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        int i;
        for (i = 0; i < numberOfCountries && inFile.hasNextLine(); ++i) {
            String[] tokens = inFile.nextLine().split(regexPattern, -1);
            if (tokens.length != numYears + 1) { // incorrect amount of data
                clearMembers();
                return false;
            }
            countryNames[i] = tokens[0].replace("\"","");
            for (int j = 1; j < tokens.length && i < numberOfCountries; ++j) {
                try {
                    cellularDataTable[i][j - 1] = Double.parseDouble(tokens[j]);
                }
                catch(NumberFormatException e){
                    clearMembers();
                    return false;
                } // end try/catch
            } // end loop over data
        } // end loop over countries
        if (i < numberOfCountries)
            reduceSize(i); // since mismatch between file size and 'Number of countries'
        return true;
    }

    /**
     * Reduces number of rows in arrays and resets member attributes accordingly.
     * Note: intended for use in situation where number lines in input file is less
     * than then Number of Countries shown in file.
     * @param newSize size to reduce arrays to.
     */
    private void reduceSize(int newSize) {
        String[] newNames = new String[newSize];
        double[][] newTable = new double[newSize][numYears];

        for (int i = 0; i < newSize; ++i) {
            newNames[i] = countryNames[i];
            newTable[i] = cellularDataTable[i];
        }
        countryNames = newNames;
        cellularDataTable = newTable;
        numberOfCountries = newSize;
    }

    /**
     * Clears member attributes.
     */
    private void clearMembers() {
        numYears = 0;
        numberOfCountries = 0;
        yearLabels = new int[0];
        countryNames = new String[0];
        cellularDataTable = new double[0][0];
    }
}
