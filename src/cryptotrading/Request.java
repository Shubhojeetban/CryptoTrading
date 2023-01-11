/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptotrading;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class Request {
    String RequestId;
    double limitPrice;
    String pairSymbol;
    double Cryptos;
    boolean buyOrSell; // true -> buy, false -> sell
}
