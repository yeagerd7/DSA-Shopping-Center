package dsa;
/**
 * Customer class that represents/creates Customer objects that contain instance variables of name
 * (String), shoppingCartSize and timeSpentInShoppingCenter.  This class is used to represent customers
 * in a Shopping Center
 * @author David Yeager & Stephanie Canada
 * @version 12/06/2016
 */
public class Customer {
    //Instance variable for the customer's name
    private String name;
    //Instance variable for the number of items currently in the customers shopping cart
    private int shoppingCartSize;
    //Instance variable for the amount of time spent that customer has spent in the Shopping Center.
    private int timeSpentInShoppingCenter;
    /**
     * Constructor for the Customer class that creates and initializes new Customer objects.
     * @param name (Representing the new Customer object's name)
     */
    public Customer(String name) {
        this.name = name;
        shoppingCartSize = 0;
        timeSpentInShoppingCenter = 0;
    }
    /**
     * Getter Method that returns the current Customer object's name instance variable value
     * @return name (customer's name)
     */
    public String getName() {
        return name;
    }
    /**
     * Setter Method that sets the current Customer object's name instance variable to a String passed
     * as a parameter.
     * @param name (Representing the value to be set to the name instance variable)
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter Method that returns the current Customer object's shoppingCartSize instance variable value
     * @return name (customer's name)
     */
    public int getShoppingCartSize() {
        return shoppingCartSize;
    }
    /**
     * Setter Method that sets the current Customer object's shoppingCartSize instance variable to
     * an int value passed as a parameter.
     * @param name (Representing the int value to be set to the shoppingCartSize instance variable)
     */
    public void setShoppingCartSize(int shoppingCartSize) {
        this.shoppingCartSize = shoppingCartSize;
    }
    /**
     * Getter Method that returns the current Customer object's timeSpentInShoppingCenterinstance
     * variable value
     * @return name (customer's name)
     */
    public int getTimeSpentInShoppingCenter() {
        return timeSpentInShoppingCenter;
    }
    /**
     * Setter Method that sets the current Customer object's timeSpentInShoppingCenter instance variable
     * to an int value passed as a parameter.
     * @param name (Representing the int value to be set to the timeSpentInShoppingCenter instance variable)
     */
    public void setTimeSpentInShoppingCenter(int timeSpentInShoppingCenter) {
        this.timeSpentInShoppingCenter = timeSpentInShoppingCenter;
    }
    /**
     * Mutator Method that increments the current Customer object's timeSpentInShoppingCenter instance
     * variable by 1.
     */
    public void incrementTimeSpent() {
        timeSpentInShoppingCenter++;
    }
    /**
     * Mutator Method that increments the current Customer object's shoppingCartSize instance variable
     *  by 1.
     */
    public void addItemToShoppingCart() {
        shoppingCartSize++;
    }
    /**
     * Mutator Method that decrements the current Customer object's shoppingCartSize instance variable
     * by 1.
     */
    public void removeItemToShoppingCart() {
        shoppingCartSize--;
    }
    /**
     * Overridden toString Method that returns a certain String
     */
    public String toString() {
        if(shoppingCartSize > 1) {
            return "Customer " + name + " has " + shoppingCartSize +
                    " items in their shopping cart \n" + "     ";
        }
        else {
            return "Customer " + name + " has 1 item in their shopping cart \n" + "     ";
        }
    }
}