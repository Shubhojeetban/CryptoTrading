/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptotrading;

import java.util.HashMap;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class TradingMachine {
    HashMap<String, Book> bookMap;
    
    TradingMachine() {
        bookMap = new HashMap<>();
    }
    
    public void addNewBook(String pairSymbol) {
        Book book = new Book();
        book.buyTree = new LimitTree();
        book.sellTree = new LimitTree();
        bookMap.put(pairSymbol, book);
    }
    
    
}
