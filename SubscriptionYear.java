/**
 * One object of this class holds year and subscription data for that year.
 */
public class SubscriptionYear {
    private int year;
    private double subscriptions;

    /**
     * Constructor. If numSubscriptions is less than 0 then subscriptions is
     * set to 0.
     * @param year Year for the subscription data.
     * @param numSubscriptions Subscription date for year.
     */
    SubscriptionYear (int year, double numSubscriptions) {
        this.year = year;
        if (!setSubscriptions(numSubscriptions))
            subscriptions = 0.0;
    }

    /**
     * Returns object's year associated with its subscription data.
     * @return specified year.
     */
    public int getYear() { return year; }

    /**
     * Sets object's year associated with its subscription data.
     * @param year the year associated with subscription data.
     */
    public void setYear(int year) { this.year = year; }

    /**
     * Gets objects subscription data associated with its year.
     * @return specified subscription data.
     */
    public double getSubscriptions() { return subscriptions; }

    /**
     * Sets object's subscription data corresponding to its year.
     * Returns true if subscriptions is set (if 0 or greater), or returns
     * false otherwise.
     * @param numSubscriptions data to set subscription to.
     * @return true if subscriptions is set or false otherwise
     */
    public boolean setSubscriptions(double numSubscriptions) {
        if (numSubscriptions < 0.0)
            return false;
        subscriptions = numSubscriptions;
        return true;
    }

    /**
     * Returns string representing the object's subscription data.
     * @return specified String.
     */
    @Override
    public String toString () { return Double.toString(subscriptions); }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;
        if (otherObj == null)
            return false;
        if (getClass() != otherObj.getClass())
            return false;
        SubscriptionYear other = (SubscriptionYear)otherObj;
        return this.year == other.year && this.subscriptions == other.subscriptions;
    }
}
