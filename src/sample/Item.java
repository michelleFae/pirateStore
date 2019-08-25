package sample;


import java.util.HashMap;

/** An item available for sale in the store.
 * A separate class called store could have been
 * made in order to handle each item, but since this program
 * is just one store, I merged the item and store class.
 * Hence, each Item object is able to deal with components of
 * other items from the store too.
 *  @author Michelle Fae D'Souza
 */
public class Item {

    /** @param name of item
     *  @param cost of item - in (assumed) dollars
     * @param type of item - DVD or Blu-Ray
     *              Assumption: no limit on quantity
     *              of items available in store.*/
    public Item(String name, double cost, String type) {
        _name = name;
        _cost = cost;
        _allItems.put(name, this);
        _type = type;
        if (isType("DVD")) {
            numDVD++;
        } else if (isType("Blu-Ray")) {
            numBluRay++;
        }
    }

    /**@param type is the objects Type - DVD or Blu-Ray.
     * Return if parameter type is the same as items type. */
    public boolean isType(String type) {
        if (this.getType().compareTo(type) == 0) {
            return true;
        }
        return false;
    }


    /** @return _cost of item. */
    public double getCost() {
        return _cost;
    }


    /** @return _name of item. */
    public String getName() {
        return _name;
    }

    /** @param cost sets cost of item. */
    public void setCost(double cost) {
        _cost = cost;
    }

    /** @param name sets item name. */
    public void setName(String name) {
        _name = name;
    }

    /** @return _allItems in store. */
    public static HashMap<String, Item> getAllItems() {
        return _allItems;
    }

    /** @return type - Dvd or Blu-Ray. */
    public String getType() {
        return _type;
    }

    /** @param name of item is used to get item.
     * returns item from item name. Assumes all items have
     * unique names. */
    public static Item getItemFromName(String name) {
        if (_allItems.containsKey(name)) {
            return _allItems.get(name);
        }
        return null;
    }

    /**@return numDVD - number of items names of type DVD*/
    public static int getNumDVD() {
        return numDVD;
    }

    /**@return numBluRay - number of items names of type Blu-Ray*/
    public static int getNumBlu() {
        return numBluRay;
    }


    /** item name. */
    private String _name;

    /** item cost. */
    private double _cost;

    /** String type - DVD or Blu-Ray. */
    private String _type;

    /** static arraylist of all items in store. */
    private static HashMap<String, Item> _allItems = new HashMap<>();

    /** Number of item names of type DVD's in store. */
    private static int numDVD;

    /** Number of item names of type Blu-Ray's in store. */
    private static int numBluRay;


}
