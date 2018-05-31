package dsa;
/**
 * Item class represents/creates CheckOutLine objects with instance variables name of type String, and
 * quantity of type int.  Class is used to represent item types in the Shopping Center inventory.
 * @author David Yeager & Stephanie Canada
 * @version 12.06.16
 */
public class Item {
    //Instance variable for the item's name
    private String name;
    //Instance variable for the item's specific quantity available in the Shopping Center
    private int quantity;
    /**
     * Constructor for the Item class that creates and initializes new Item objects.
     * @param name (Representing the new Customer object's name)
     */
    public Item(String type, int quantity) {
        this.name = type;
        this.quantity = quantity;
    }
    /**
     * Getter Method that returns the current Item object's name instance variable value
     * @return name (the item's name)
     */
    public String getName() {
        return name;
    }
    /**
     * Setter Method that sets the current Item object's name instance variable to a String passed
     * as a parameter.
     * @param name (Representing the value to be set to the name instance variable)
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter Method that returns the current Item object's name instance variable value
     * @return quantity (number of specific items in Shopping Center Inventory)
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Setter Method that sets the current Item object's quantity instance variable to a int value passed
     * as a parameter.
     * @param quantity (Representing the value to be set to the quantity instance variable)
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Mutator Method that increments the current Item object's quantity instance variable by an
     * int.value passed as a parameter
     */
    public void incrementQuantity(int byThisNum) {
        quantity += byThisNum;
    }
    /**
     * Mutator Method that decrements the current Item object's quantity instance variable by 1.
     */
    public void decrementQuantity() {
        quantity--;
    }
    /**
     * Overridden toString Method that returns a certain String
     */
    public String toString() {
        return name + " with " + quantity + " items";
    }
}
