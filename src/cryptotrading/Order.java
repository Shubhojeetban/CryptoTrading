package cryptotrading;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class Order {
    String id;
    boolean buyOrSell;
    double cryptos;
    //int limit;
    int entryTime;
    int eventTime;
    Order nextOrder;
    Order prevOrder;
    Limit parentLimit;
}
