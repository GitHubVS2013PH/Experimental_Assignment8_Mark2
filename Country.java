/**
 * One object of this class holds a country name and a number of years of
 * subscription data.
 */
public class Country {
    private final String name;
    private final SubscriptionYear[] subscriptions;
    private int numSubscriptions = 0;

    /**
     * Constructor taking country name and number of years of data.
     * @param name Object's country name.
     * @param numYears number of years of subscription data for this country.
     */
    public Country(String name, int numYears) {
        if (name == null)
            name = "";
        this.name = name;

        if (numYears < 0)
            numYears = 0;
        subscriptions = new SubscriptionYear[numYears];
    }

    /**
     * Returns name of country.
     * @return specified String.
     */
    public String getName() { return name; }

    /**
     * Returns subscription array.
     * @return specified array reference.
     */
    public SubscriptionYear[] getSubscriptions() { return subscriptions; }

    /**
     * Returns beginning year of data.
     * @return specified year.
     * @throws IllegalArgumentException if there are no subscriptions.
     */
    public int getStartYear() throws IllegalArgumentException {
        if (subscriptions.length == 0)
            throw new IllegalArgumentException("getStartYear method has no subscriptions");
        return subscriptions[0].getYear(); }

    /**
     * Returns last year of data.
     * @return specified year.
     * @throws IllegalArgumentException if there are no subscriptions.
     */
    public int getEndYear() throws IllegalArgumentException {
        if (subscriptions.length == 0)
            throw new IllegalArgumentException("getEndYear method has no subscriptions");
        return subscriptions[subscriptions.length - 1].getYear(); }

    /**
     * Adds a year's subscription data to the country's object.
     * @param year Year of the subscription data.
     * @param subscription The subscription data.
     * @return true if subscription successfully added.
     */
    public boolean addSubscriptionYear(int year, double subscription) {
        if (numSubscriptions >= subscriptions.length || subscription < 0.0)
            return false;
        subscriptions[numSubscriptions++] = new SubscriptionYear(year, subscription);
        return true;
    }

    /**
     * Returns total subscriptions between startYear and endYear, inclusive.
     * Warning is displayed for years which are outside of date range.
     * Note: if startYear and endYear are outside the object's date range
     * then only the existing data within those years are totaled.
     * @param startYear Starting year for subscription total.
     * @param endYear Ending year for subscription total.
     * @return specified total.
     * @throws IllegalArgumentException if date range is invalid.
     */
    public double getNumSubscriptionsForPeriod(int startYear, int endYear) throws IllegalArgumentException {
        int beginYear = getStartYear(), lastYear = getEndYear(); // could throw IllegalArgumentException

        if (!startYearEndYearValid(startYear, endYear)) {
            double totalSubs = sumSubscriptionIndices(0, lastYear - beginYear);
            String yearStr = "[" + beginYear + ", " + lastYear + "]";
            String errMsg = "Invalid data range. " + name + " valid data years are: " + yearStr;
            errMsg += String.format("%nSubscription total between %s is %.2f", yearStr, totalSubs);
            throw new IllegalArgumentException(errMsg);
        }

        int startIndex = Math.max(startYear, beginYear) - beginYear;
        int endIndex = Math.min(endYear, lastYear) - beginYear;
        return sumSubscriptionIndices(startIndex, endIndex);
    }

    /**
     * Return total subscriptions based on startIndex, endIndex, inclusive. Client
     * responsible for checking startIndex and endIndex values.
     * @param startIndex Index in subscriptions array to start totaling from.
     * @param endIndex Index in subscriptions array to stop totaling at.
     * @return specified total.
     */
    private double sumSubscriptionIndices(int startIndex, int endIndex) {
        double total = 0.0;
        for (int i =  startIndex; i <= endIndex; ++i)
            total += subscriptions[i].getSubscriptions();

        return total;
    }

    /**
     * Returns boolean true if year is the database, or false otherwise.
     * @param year the year to be determined is in the database.
     * @return specified boolean.
     */
    private boolean yearInDatabase(int year) {
        if (subscriptions.length == 0)
            return false;

        int beginYear = subscriptions[0].getYear();
        int endYear = subscriptions[subscriptions.length - 1].getYear();
        return year >= beginYear && year <= endYear;
    }

    /**
     * Checks validity of startYear and lastYearDisplays by checking whether
     * they are in bounds of the data. Displays warning if either startYear
     * or lastYear are out of bounds of the data. Returns true if there is
     * data in range of [startYear,lastYear] and startYear less than/equal to
     * lastYear.
     * @param startYear beginning year of query.
     * @param lastYear ending year of query.
     * @return specified boolean.
     */
    private boolean startYearEndYearValid(int startYear, int lastYear) {
        if (startYear > lastYear)
            return false;

        boolean startWarning = false, lastWarning = false;
        int beginYear = getStartYear(), endYear = getEndYear();

        if (!yearInDatabase(startYear)) {
            System.out.println("Illegal Argument Request of \"start year\": " + startYear + ".");
            startWarning = true;
        }
        if (!yearInDatabase(lastYear)) {
            System.out.println("Illegal Argument Request of \"end year\": " + lastYear + ".");
            lastWarning = true;
        }
        if (startWarning || lastWarning)
            System.out.println("Valid period for " + name + " is " + beginYear + " to " + endYear + ".");

        boolean dataInRange = startYear <= beginYear && lastYear >= endYear;
        return !startWarning || !lastWarning || dataInRange;
    }

    /**
     * Returns String representation of Country object.
     * @return specified String.
     */
    @Override
    public String toString() {
        String rtnVal = String.format("%-16s",name);

        for (SubscriptionYear subscription : subscriptions) {
            if (subscription == null)
                continue;
            rtnVal += String.format("%-8.2f", subscription.getSubscriptions());
        }

        return rtnVal;
    }
}
