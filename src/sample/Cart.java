package sample;

import java.util.HashMap;

/** A cart for each customer consisting of items from
 *  the store.
 *  @author Michelle Fae D'Souza
 */
public class Cart {

    /** @param item and its
     * @param quantity are added to cart. **/
    private void add(Item item, Integer quantity) {
        items.put(item, quantity);
        if (item.isType("DVD")) {
            numDVD += 1;
        } else if (item.isType("Blu-Ray")) {
            numBlu += 1;
        }
    }

    /** @param quantity of
     *  @param item in cart is changed. **/
    public void changeQuantity(Item item, Integer quantity) {
        if (items.containsKey(item)) {
            items.replace(item, quantity);
            if (quantity == 0) {
                remove(item);
            }
        } else if (quantity != 0) {
            add(item, quantity);
        }
    }

    /** @param item - item in cart.
     *  @return quantity of an item in cart. */
    public int getQuantity(Item item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return 0;
    }

    /** @return quantity of total items in cart. */
    public int getQuantity() {
        int total = 0;
        for (int itemQuantity : items.values()) {
            total += itemQuantity;
        }
        return total;

    }

    /** @return whether cart has at least one of each item. */
    private boolean hasAllItems() {
        return Item.getAllItems().size() == items.size();
    }

    /** @return total price of items in cart. */
    public double getPrice() {
        double total = 0.0;
        allDVDDiscount = 0;
        allBluDiscount = 0;
        for (Item item: items.keySet()) {
            double discount = discount(item);

            total += item.getCost() * items.get(item) - discount;
        }

        _nonBulkDiscountTotal = total;


        total -= getBulkDiscount(total);
        return total;
    }

    /** @return allDVDDiscount - assumes getPrice() called before.
     * I did not assume that all DVD's cost $20 as this
     * seemed to easy, even though the example had only
     * $20 DVD's*/
    public double getDVDDiscount() {

        return allDVDDiscount;
    }

    /** @return allBluDiscount - assumes getPrice() called before.
     * I did not assume that all Blu-Ray's cost $25 as this
     * seemed to easy, even though the example had only
     * $25 Blu-Ray's*/
    public double getBluDiscount() {

        return allBluDiscount;
    }

    /** @param item is an item for sale.
     * @return discount, based on type of item - DVD or Blu-Ray. */
    private double discount(Item item) {
        if (numDVD == Item.getNumDVD() && item.isType("DVD")) {
            allDVDDiscount += item.getCost() * DVDDISC * items.get(item);
            return item.getCost() * DVDDISC * items.get(item);
        } else if (numBlu == Item.getNumBlu() && item.isType("Blu-Ray")) {
            allBluDiscount += item.getCost() * BLUDISC * items.get(item);
            return item.getCost() * BLUDISC * items.get(item);
        }
        return 0.0;
    }

    /** @param total is price without bulk discount.
     * Return value of bulk discount ( >=100 times ).
     * Assumes getPrice has been called before.*/
    private double getBulkDiscount(double total) {
        if (getQuantity() >= 100) {
            return BULKDISC * total;

        }
        return 0.0;
    }

    /** return value of bulk discount ( >=100 times ).
     * Assumes getPrice has been called before.*/
    public double getBulkDiscount() {
        return getBulkDiscount(_nonBulkDiscountTotal);

    }


    /** @param item removed from cart entirely. */
    private void remove(Item item) {
        if (items.containsKey(item)) {
            if (item.isType("DVD")) {
                numDVD -= 1;

            } else if (item.isType("Blu-Ray")) {
                numBlu -= 1;
            }

            items.remove(item);
        }
    }

    /** Hashmap of each item with its quantity in the cart. **/
    private HashMap<Item, Integer> items = new HashMap<>();

    /** Number of item names of type DVD's in cart. */
    private int numDVD;

    /** Number of item names of type Blu-Ray in cart. */
    private int numBlu;

    /** Discount for having one of each type of DVD from store in cart. */
    private double allDVDDiscount;

    /** Discount for having each type of Blu-Ray from store in cart. */
    private double allBluDiscount;

    /** Total price without bulk discount. */
    private double _nonBulkDiscountTotal;

    /** discount percentage if all Blu-Ray types are bought. */
    private static final double BLUDISC = 0.15;

    /** discount percentage if all DVD types are bought. */
    private static final double DVDDISC = 0.1;

    /** discount percentage if bulk in cart (>100 items). */
    private static final double BULKDISC = 0.05;


}
