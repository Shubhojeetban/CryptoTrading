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
        book.buyTreeMap = new HashMap<>();
        book.sellTreeMap = new HashMap<>();
        bookMap.put(pairSymbol, book);
    }

    // recieve a request for buy or sell
    public void addRequest(Request request) {
        if (!bookMap.containsKey(request.pairSymbol)) {
            addNewBook(request.pairSymbol);
        }

        Book book = bookMap.get(request.pairSymbol);

        if (request.buyOrSell) {
            Limit lowestSell = book.lowestSell;
            LimitTree limitTree = book.sellTree;
            if (request.limitPrice > lowestSell.limitPrice) {
                // start trading
                while (request.Cryptos != 0 && request.limitPrice > lowestSell.limitPrice) {
                    if (lowestSell.totalVolume <= request.Cryptos) {
                        limitTree.deleteLimit(lowestSell);
                        request.Cryptos = request.Cryptos - lowestSell.totalVolume;
                        lowestSell = limitTree.getSmallestLimit();
                    } else {
                        OrderList orderList = lowestSell.order;
                        while(request.Cryptos > 0) {
                            Order order = orderList.getHead();
                            if(order.cryptos > request.Cryptos) {
                                order.cryptos = order.cryptos - request.Cryptos;
                                lowestSell.totalVolume = lowestSell.totalVolume - request.Cryptos;
                            } else {
                                orderList.deleteOrder(order);
                                request.Cryptos = request.Cryptos - order.cryptos;
                                lowestSell.totalVolume = lowestSell.totalVolume - order.cryptos;
                            }
                        }
                    }
                }
            } else {
                // add to queue
                addToQueue(request, book);
            }
        } else {
            Limit highestBuy = book.highestBuy;
            if (request.limitPrice < highestBuy.limitPrice) {
                // start trading
            } else {
                // add to queue
            }
        }
    }
    
    public void addToQueue(Request request, Book book) {
        if(request.buyOrSell) {
           boolean isLimitExist = book.buyTreeMap.containsKey(request.limitPrice);
           if(isLimitExist) {
               // Create a order and add it to the orderlist
           }
           else {
               // Create a limit add to limit tree and also create hte order and add to its order list 
           }
        }
    }
}
