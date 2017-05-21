import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * One object of this class reads the cellular subscription data, then instantiates
 * and adds the graph panels.
 */
public class GraphFrame2 extends JFrame {
    final static int NUM_RAND_COUNTRIES = 11;

    /**
     * Constructor.
     * @param title to set the frame title.
     * @param width to set the frame width.
     * @param height to set the frame height.
     */
    public GraphFrame2(String title, int width, int height) {
        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);

        LinkedList<Country> selectedCountries = buildSelectedCountryList();
        GraphView2 myGraphView = new GraphView2(width, height, selectedCountries);
        add(myGraphView);
        pack(); // sets display size based on setPreferredSize()
    }

    /**
     * Returns a LinkedList of randomly selected Country objects read from the
     * cellular.csv file.
     * @return specified LinkedList.
     */
    private LinkedList<Country> buildSelectedCountryList() {
        // Get array of countries and pick a random few for LinkedList
        final String FILENAME = "resources/cellular.csv";	// Directory path for Mac OS X
        // final String FILENAME = "resources/cellular_short_oneDecade.csv";	// Directory path for Mac OS X
        CSVReader parser = new CSVReader(FILENAME);
        String [] countryNames = parser.getCountryNames();
        int [] yearLabels = parser.getYearLabels();
        double [][] parsedTable = parser.getParsedTable();

        // convert parser into a country array
        Country current;
        Country [] allCountries = new Country[countryNames.length];
        for (int countryIndex = 0; countryIndex < allCountries.length; countryIndex++) {
            int numberOfYears = yearLabels.length;
            current = new Country(countryNames[countryIndex], numberOfYears);

            // Go through each year of cellular data read from the CSV file.
            for (int yearIndex = 0; yearIndex < numberOfYears; yearIndex++)
            {
                double [] allSubscriptions = parsedTable[countryIndex];
                double countryData = allSubscriptions[yearIndex];
                current.addSubscriptionYear(yearLabels[yearIndex], countryData);
            }
            allCountries[countryIndex] = current;
        }
        return selectRandomCountries(allCountries);
    }

    /**
     * Returns LinkedList of countries selected at random from allCountries array.
     * @param allCountries array of Country objects.
     * @return specified LinkedList.
     */
    private LinkedList<Country>  selectRandomCountries(Country [] allCountries) {
        // select random countries
        Random random = new Random();
        LinkedList<Country> selectedCountries = new LinkedList<>();
        for (int i = 0; i < NUM_RAND_COUNTRIES; i++) {
            int selectedIndex = random.nextInt(allCountries.length);
            // int selectedIndex = i;                                             // FOR DEBUGGING
            Country countryToAdd = allCountries[selectedIndex];
            System.out.printf("Adding country with name %s to the end of the list.\n", countryToAdd.getName());
            selectedCountries.add(countryToAdd);
        }
        return selectedCountries;
    }
}
