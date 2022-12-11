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
            Limit lowestSell = book.sellTree.getSmallestLimit();
            LimitTree limitTree = book.sellTree;
            if (lowestSell != null && request.limitPrice >= lowestSell.limitPrice) {
                // start trading
                while (request.Cryptos != 0 && lowestSell != null && request.limitPrice >= lowestSell.limitPrice) {
                    if (lowestSell.totalVolume <= request.Cryptos) {
                        limitTree.deleteLimit(lowestSell);
                        // request.Cryptos = request.Cryptos - lowestSell.totalVolume;
                        System.out.println("**** Sell *****");
                        System.out.println("Price: " + request.limitPrice);
                        System.out.println("Order Price: " + lowestSell.limitPrice);
                        System.out.println("Cryptos: " + lowestSell.totalVolume);
                        lowestSell = limitTree.getSmallestLimit();
                    } else {
                        OrderList orderList = lowestSell.order;
                        int isOrderEmpty = 1;
                        while (request.Cryptos > 0) {
                            Order order = orderList.getHead();
                            if (order.cryptos <= request.Cryptos) {
                                isOrderEmpty = orderList.deleteOrder(order);
                                request.Cryptos = request.Cryptos - order.cryptos;
                                lowestSell.totalVolume = lowestSell.totalVolume - order.cryptos;
                            } else {
                                order.cryptos = order.cryptos - request.Cryptos;
                                lowestSell.totalVolume = lowestSell.totalVolume - request.Cryptos;
                                request.Cryptos = 0;
                            }
                            System.out.println("**** Sell *****");
                            System.out.println("Request Price: " + request.limitPrice);
                            System.out.println("Order Price: " + lowestSell.limitPrice);
                            System.out.println("Cryptos: " + order.cryptos);
                            if(isOrderEmpty == 0) {
                                limitTree.deleteLimit(lowestSell);
                            }
                        }
                    }
                }
            } else {
                // add to queue
                addToQueue(request, book);
            }
        } else {
            Limit highestBuy = book.buyTree.getHighestLimit();
            LimitTree limitTree = book.buyTree;
            if (highestBuy != null && request.limitPrice <= highestBuy.limitPrice) {
                // start trading
                while (request.Cryptos != 0 && highestBuy != null && request.limitPrice <= highestBuy.limitPrice) {
                    if (highestBuy.totalVolume <= request.Cryptos) {
                        limitTree.deleteLimit(highestBuy);
                        // request.Cryptos = request.Cryptos - lowestSell.totalVolume;
                        System.out.println("**** Buy *****");
                        System.out.println("Price: " + request.limitPrice);
                        System.out.println("Order Price: " + highestBuy.limitPrice);
                        System.out.println("Cryptos: " + highestBuy.totalVolume);
                        highestBuy = limitTree.getHighestLimit();
                    } else {
                        OrderList orderList = highestBuy.order;
                        while (request.Cryptos > 0) {
                            Order order = orderList.getHead();
                            int isOrderEmpty = 1;
                            if (order.cryptos <= request.Cryptos) {
                                isOrderEmpty = orderList.deleteOrder(order);
                                request.Cryptos = request.Cryptos - order.cryptos;
                                highestBuy.totalVolume = highestBuy.totalVolume - order.cryptos;
                            } else {
                                order.cryptos = order.cryptos - request.Cryptos;
                                highestBuy.totalVolume = highestBuy.totalVolume - request.Cryptos;
                                request.Cryptos = 0;
                            }
                            System.out.println("**** Buy *****");
                            System.out.println("Price: " + request.limitPrice);
                            System.out.println("Order Price: " + highestBuy.limitPrice);
                            System.out.println("Cryptos: " + order.cryptos);
                            if(isOrderEmpty == 0) {
                                limitTree.deleteLimit(highestBuy);
                            }
                        }
                    }
                }
            } else {
                // add to queue
                addToQueue(request, book);
            }
        }
    }

    public void addToQueue(Request request, Book book) {
        Limit limit;
        if (request.buyOrSell) {
            boolean isLimitExist = book.buyTreeMap.containsKey(request.limitPrice);

            if (isLimitExist) {
                // Create a order and add it to the orderlist
                limit = book.buyTreeMap.get(request.limitPrice);

            } else {
                // Create a limit add to limit tree and also create hte order and add to its order list 
                limit = new Limit();
                limit.limitPrice = request.limitPrice;
                limit.totalVolume = 0;
                limit.size = 0;
                limit.order = new OrderList();
                book.buyTree.insert(limit);
            }

        } else {
            boolean isLimitExist = book.sellTreeMap.containsKey(request.limitPrice);

            if (isLimitExist) {
                // Create a order and add it to the orderlist
                limit = book.sellTreeMap.get(request.limitPrice);

            } else {
                // Create a limit add to limit tree and also create hte order and add to its order list 
                limit = new Limit();
                limit.limitPrice = request.limitPrice;
                limit.totalVolume = 0;
                limit.size = 0;
                limit.order = new OrderList();
                book.sellTree.insert(limit);
            }
        }
        Order order = new Order();
        order.buyOrSell = request.buyOrSell;
        order.cryptos = request.Cryptos;
        // TODO: Enter the entrytime and eventtime
        order.idNumber = 1; // TODO: Change the id accordingly
        order.parentLimit = limit;
        limit.order.insertOrder(order);
    }
}
