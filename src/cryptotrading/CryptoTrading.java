/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptotrading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Shubhojeet Banerjee
 */
class TradeDetails implements Comparable<TradeDetails> {
    double val;
    double qty;

    TradeDetails(double val, double qty) {
        this.val = val;
        this.qty = qty;
    }

    @Override
    public int compareTo(TradeDetails td) {
        if (val == td.val) {
            return 0;
        } else if (val > td.val) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return " [val=" + val + ", qty=" + qty + "]";
    }
}

class PairQueue {

    // For Key BTCETH baseQueue is for BTC and quoteQueue is for ETH
    PriorityQueue<TradeDetails> baseQueue;
    PriorityQueue<TradeDetails> quoteQueue;

    PairQueue() {
        baseQueue = new PriorityQueue<>(Collections.reverseOrder());
        quoteQueue = new PriorityQueue<>();
    }
}

public class CryptoTrading {

    static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    static HashMap<String, PairQueue> map;

    CryptoTrading() {
        CryptoTrading.map = new HashMap<>();
    }

    // Creates the queues for trading
    private static void createCheckPair(String coinPair) {
        if (!map.containsKey(coinPair)) {
            map.put(coinPair, new PairQueue());
        }
    }

    private static void requestForSell(String coinPair, double val, double qty) {
        // If the coinPair is BTCETH the sell is for the ETH
        PairQueue pd = map.get(coinPair);
        pd.quoteQueue.add(new TradeDetails(val, qty));
        map.put(coinPair, pd);
    }

    private static void requestForBuy(String coinPair, double val, double qty) {
        // If the coinPair is BTCETH the buy is for the BTC
        PairQueue pd = map.get(coinPair);
        pd.baseQueue.add(new TradeDetails(val, qty));
        map.put(coinPair, pd);
    }

    private static void enterCoin() throws IOException {
        System.out.println("Enter coin Base coin Symbol /space Quote coin Symbol");
        String coinPair = read.readLine();
        createCheckPair(coinPair);
    }

    private static void showAllCoinPairs() {
        System.out.println("List of Coins");
        map.entrySet().forEach(entry -> {
            System.out.println(entry.getKey());
        });
    }

    private static void showQueue() {
        System.out.println("Buy : ");
        map.entrySet().forEach(entry -> {
            entry.getValue().baseQueue.forEach(trade -> {
                System.out.println(trade);
            });
        });

        System.out.println("Sell : ");
        map.entrySet().forEach(entry -> {
            entry.getValue().quoteQueue.forEach(trade -> {
                System.out.println(trade);
            });
        });
    }

    private static void buy() throws IOException {
        System.out.println("Enter coinPair /space value /space quantity");
        String s = read.readLine();
        requestForBuy(s.split(" ")[0], Double.parseDouble(s.split(" ")[1]), Double.parseDouble(s.split(" ")[2]));
        trade(s.split(" ")[0]);
    }

    private static void sell() throws IOException {
        System.out.println("Enter coinPair /space value /space quantity");
        String s = read.readLine();
        requestForSell(s.split(" ")[0], Double.parseDouble(s.split(" ")[1]), Double.parseDouble(s.split(" ")[2]));
        trade(s.split(" ")[0]);
    }

    private static void trade(String coinPair) {
        PriorityQueue<TradeDetails> baseQueue = map.get(coinPair).baseQueue;
        PriorityQueue<TradeDetails> quoteQueue = map.get(coinPair).quoteQueue;

        if (baseQueue.isEmpty() || quoteQueue.isEmpty()) {
            return;
        }

        while (!baseQueue.isEmpty() && !quoteQueue.isEmpty() && baseQueue.peek().val >= quoteQueue.peek().val) {
            TradeDetails base = baseQueue.poll();
            TradeDetails quote = quoteQueue.poll();

            if (base.qty > quote.qty) {
                base.qty = base.qty - quote.qty;
                quote.qty = 0;
                baseQueue.add(base);
            } else if (base.qty < quote.qty) {
                quote.qty = quote.qty - base.qty;
                base.qty = 0;
                quoteQueue.add(quote);
            }
            else {
                base.qty = 0;
                quote.qty = 0;
            }
            String head = "****** Exchange Occuered ******";
            System.out.println(head);
            System.out.println("Base : " + base);
            System.out.println("Quote: " + quote);
            for(int i = 0; i < head.length(); i++)
                System.out.print("*");
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        CryptoTrading cp = new CryptoTrading();
        try {
//            System.out.println("-----------------Welcome to Crypto Exchange-----------------");
//            while (true) {
//                System.out.println("Press any key to exit");
//                System.out.println("1. Add Coin Pair");
//                System.out.println("2. View List of coin");
//                System.out.println("3. View Queue");
//                System.out.println("4. Buy Coin");
//                System.out.println("5. Sell Coin");
//                char c = read.readLine().charAt(0);
//                switch (c) {
//                    case '1':
//                        enterCoin();
//                        break;
//                    case '2':
//                        showAllCoinPairs();
//                        break;
//                    case '3':
//                        showQueue();
//                        break;
//                    case '4':
//                        buy();
//                        break;
//                    case '5':
//                        sell();
//                        break;
//                    default:
//                        System.exit(0);
//                }
//            }
              LimitTree limit = new LimitTree();
              
              Limit l1 = new Limit();
              l1.headOrder = null;
              l1.leftChild = null;
              l1.rightChild = null;
              l1.parent = null;
              l1.tailOrder = null;
              l1.size = 0;
              l1.limitPrice = 0.3;
              l1.totalVolume = 0.2;
              
              Limit l2 = new Limit();
              l2.headOrder = null;
              l2.leftChild = null;
              l2.rightChild = null;
              l2.parent = null;
              l2.tailOrder = null;
              l2.size = 0;
              l2.limitPrice = 0.2;
              l2.totalVolume = 0.2;
              
              Limit l3 = new Limit();
              l3.headOrder = null;
              l3.leftChild = null;
              l3.rightChild = null;
              l3.parent = null;
              l3.tailOrder = null;
              l3.size = 0;
              l3.limitPrice = 0.5;
              l3.totalVolume = 0.2;
              
              Limit l4 = new Limit();
              l4.headOrder = null;
              l4.leftChild = null;
              l4.rightChild = null;
              l4.parent = null;
              l4.tailOrder = null;
              l4.size = 0;
              l4.limitPrice = 0.4;
              l4.totalVolume = 0.2;
              
              
              limit.insert(l1);
              limit.insert(l2);
              limit.insert(l3);
              limit.insert(l4);
              
              // List<Limit> list = limit.getAllLimitsInAsc();
              List<Limit> list = limit.getAllLimitsInAsc();
              for(Limit l : list) 
              {
                  System.out.print(l.limitPrice+" ");
              }
              System.out.println();
              limit.deleteLimit(l2);
              
              list = limit.getAllLimitsInAsc();
              for(Limit l : list) 
              {
                  System.out.print(l.limitPrice+" ");
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
