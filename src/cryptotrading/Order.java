package cryptotrading;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class Order {
    int idNumber;
    boolean buyOrSell;
    double cryptos;
    int limit;
    int entryTime;
    int eventTime;
    Order nextOrder;
    Order prevOrder;
    Limit parentLimit;
}
