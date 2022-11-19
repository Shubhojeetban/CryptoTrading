package cryptotrading;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class Limit {
    double limitPrice;
    int size;
    double totalVolume;
    Limit parent ;
    Limit leftChild ;
    Limit rightChild ;
    Order headOrder ;
    Order tailOrder ;
}
