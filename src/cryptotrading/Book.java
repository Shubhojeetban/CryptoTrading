package cryptotrading;

import java.util.HashMap;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class Book {
    LimitTree buyTree ;
    LimitTree sellTree ;
    HashMap<Double, Limit> buyTreeMap;
    HashMap<Double, Limit> sellTreeMap;
    //Limit lowestSell ;
    //Limit highestBuy ;
}
