 package cryptotrading;

/**
 *
 * @author Shubhojeet Banerjee
 */
public class OrderList {

    private Order headOrder;
    private Order tailOrder;

    public OrderList() {
        headOrder = null;
        tailOrder = null;
    }

    public void insertOrder(Order order) {
        if (headOrder == null) {
            headOrder = order;
            tailOrder = order;
        } else {
            tailOrder.nextOrder = order;
            order.prevOrder = tailOrder;
            tailOrder = order;
        }
    }

    // return 0 when the list is empty -> so that the limit could be deleted
    // return 1 when there is still element in the list
    public int deleteOrder(Order order) {
        if (order == headOrder && order == tailOrder) {
            headOrder = null;
            tailOrder = null;
            return 0;
        }
        if (order == headOrder) {
            headOrder = headOrder.nextOrder;
            headOrder.prevOrder = null;
            //headOrder.parentLimit.headOrder = headOrder;
            return 1;
        }
        if (order == tailOrder) {
            tailOrder = tailOrder.prevOrder;
            tailOrder.nextOrder = null;
            //tailOrder.parentLimit.tailOrder = tailOrder;
            return 1;
        }
        order.prevOrder.nextOrder = order.nextOrder;
        order.nextOrder.prevOrder = order.prevOrder;
        
        return 1;
    }
    
    public Order getHead() {
        return headOrder;
    }
}
