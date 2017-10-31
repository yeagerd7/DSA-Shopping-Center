package dsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * Purpose: Data Structure and Algorithms Semester Project
 * Status: Complete and thoroughly tested
 * Last update: 12/6/16
 * Submitted:  12/6/16
 * Comment: test suite and sample run attached
 * @author: David Yeager & Stephanie Canada
 * @version: 2016.12.06
 */
public class ShoppingCenter {
    //Global BufferedReader object that prompts inputs from the user.
    static BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
    /*
     * Main Method that runs the virtual Shopping Center.  Begins by welcoming the user to the
     * Shopping Center and asking the customer to specify stock.  The user inputs how many items
     * the ShoppingCenter's inventory will have, then specifies their minimum restocking amount for
     * all items.  The user then is prompted to enter each item's name and individual quantities.
     * After specifying the inventory (stock), the user is given a menu and prompted for a specific
     * menu function.  Then, the program truly begins.
     */
    public static void main(String args[]) throws IOException {
        ListArrayBasedPlus<Item> inventory = new ListArrayBasedPlus<Item>();
        ListArrayBasedPlus<Customer> customers = new ListArrayBasedPlus<Customer>();
        CheckOutLine line1 = new CheckOutLine("first");
        CheckOutLine line2 = new CheckOutLine("second");
        CheckOutLine expressLine = new CheckOutLine("express");
        System.out.println("Welcome to the Shopping Center!!!\n");
        System.out.println("Please specify stock.");
        System.out.print("How many items do you have?: ");
        int numItems = Integer.parseInt(user.readLine().trim());
        System.out.println(numItems);
        System.out.print("Please specify restocking amount: ");
        int restockLevel = Integer.parseInt(user.readLine().trim());
        System.out.println(restockLevel);
        populateInitialInventory(inventory, numItems);
        System.out.print("Please select the checkout line that should check out customers first "
                + "(regular1/regular2/express): " );
        String reply = user.readLine().trim();
        System.out.println(reply);
        int checkOutLineUpNext = 1;
        switch(reply) {
            case "regular1" : checkOutLineUpNext = 1;
                break;
            case "regular2" : checkOutLineUpNext = 2;
                break;
            case "express"  : checkOutLineUpNext = 3;
                break;
        }
        boolean processDone = false;
        while(processDone == false) {
            printMenu();
            int choiceNumber = Integer.parseInt(user.readLine().trim());
            System.out.println(choiceNumber);
            switch(choiceNumber){
                case 1: customerEntersShoppingCenter(customers, line1, line2, expressLine);
                    break;
                case 2: customerAddsItemToTheShoppingCart(customers, inventory, line1, line2,
                        expressLine);
                    break;
                case 3: customerRemovesItemFromShoppingCart(customers, line1, line2, expressLine);
                    break;
                case 4: customerIsDoneShopping(customers, line1, line2, expressLine);
                    break;
                case 5: if(line1.getCustomers().size() == 0 && line2.getCustomers().size() == 0
                        && expressLine.getCustomers().size() == 0) {
                    System.out.println("No customers in any line.");
                }
                else {
                    CustomerIsReadyToCheckOut(customers, line1, line2, expressLine,
                            checkOutLineUpNext);
                    if(checkOutLineUpNext == 3) {
                        checkOutLineUpNext = 1;
                    }
                    else {
                        checkOutLineUpNext++;
                    }
                }
                    break;
                case 6: printInfoAboutShoppingCustomers(customers);
                    break;
                case 7: printInfoAboutCustomersInCheckOutLine(line1);
                    printInfoAboutCustomersInCheckOutLine(line2);
                    printInfoAboutCustomersInCheckOutLine(expressLine);
                    break;
                case 8: printItemsAtOrBelowRestockLevel(inventory, restockLevel);
                    break;
                case 9: reOrderItem(inventory);
                    break;
                case 10: closeApplication();
                    processDone = true;
                    break;
                default:
                    System.out.println("Choice is not valid, carefully read the menu and try again!.");
            }
        }
    }
    /**
     * Static Method that prints the menu for the Shopping Center
     */
    public static void printMenu() {
        System.out.println("\nSelect from the following menu: ");
        System.out.println("                1. Customer enters Shopping Center.");
        System.out.println("                2. Customer picks an item and places it the shopping cart.");
        System.out.println("                3. Customer removes an item from the shopping cart.");
        System.out.println("                4. Customer is done shopping.");
        System.out.println("                5. Customer checks out.");
        System.out.println("                6. Print info about customers who are shopping.");
        System.out.println("                7. Print info about customers in checkout lines.");
        System.out.println("                8. Print info about items at or below re-stocking level.");
        System.out.println("                9. Reorder an item.");
        System.out.println("                10. Close the Shopping Center.");
        System.out.print("Make your menu selection now: ");
    }
    /**
     * Static Method that populates all of items in the Shopping Center's inventory.  The name and
     * quantity of each item is specified by the user.
     * @param inventory (Represents the current Shopping Center's inventory)
     * @param differentItemTypesInInventory (Represents the # of different item types in the Shopping
     * Center inventory)
     * @throws IOException
     */
    private static void populateInitialInventory(ListArrayBasedPlus<Item> inventory,
                                                 int differentItemTypesInInventory) throws IOException {
        for(int i = 0 ; i < differentItemTypesInInventory ; i++) {
            System.out.print("Enter item name: ");
            String item = user.readLine().trim();
            System.out.println(item);
            System.out.print("How many " + item + "s?: ");
            int numItems = Integer.parseInt(user.readLine().trim());
            System.out.println(numItems);
            Item newItem = new Item(item, numItems);
            inventory.add(i, newItem);
        }
    }
    /**
     * Static Method that simulates a new customer entering the Shopping Center.  The user is
     * prompted  to enter the customer's name.  A Customer object is then created, then inserted
     * into customer ListArrayBasedPlus object only if it passes a check to see if the customer
     * object already exists in the list (duplicate).  In that check, all the current shopping
     * customers list is searched, as well as the customers in each checkout line.  The user will
     * continue to be prompted for a customer name until is passes that duplicate check.
     * @param customers (Representing the customers currently shopping in the Shopping Center)
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the express checkout line in the Shopping Center)
     * @throws IOException
     */
    public static void customerEntersShoppingCenter(ListArrayBasedPlus<Customer> customers,
                                                    CheckOutLine line1, CheckOutLine line2, CheckOutLine expressLine)
            throws IOException {
        boolean searchFinished = false;
        String name = "";
        while(searchFinished == false) {
            System.out.print("Enter in customer name: ");
            name = user.readLine().trim();
            System.out.println(name);
            int index = searchCustomers(customers, name);
            Customer customer = searchCheckOutLines(line1, line2, expressLine, name);
            if(index != -1 || customer != null) {
                System.out.println("Customer " + name + " is already in the Shopping Center");
            }
            else {
                searchFinished = true;
            }
        }
        customers.add(customers.size(), new Customer(name));
        System.out.println("Customer " + name + " is now in the Shopping Center");
    }
    /**
     * Static Method that simulates a customer adding an item to their Shopping Cart.  Initially it
     * checks if the 	ListArrayBasedPlus of Customers object is empty.  If it is, the user is given
     * an error message specifying that 	no customers are currently in the shopping center.  If it
     * isn't empty, the user is prompted for a customer 	name.  The customer name entered is
     * searched.  If  the customer is not found, the user is given an error 	message either
     * specifying the customer entered doesn't exist in the shopping center or that the customer is
     * currently in a checkout line.  It will continue to prompt a customer name until it is found.
     * When the customer is found, the user is prompted for the item that the customer desires. The
     * item name entered is searched.  If it is not found, the user is given an error message
     * specifying that the item entered doesn't exist. If the item is found, it is added into the
     * customer's shopping cart, incrementing the Customer object's shoppingCartSize instance variable
     * by 1 as well incrementing all of the currently shopping Customer objects'
     * timeSpentInShoppingCenter instance variable by 1. The specific item object grabbed quantity
     * instance variable is also decremented by 1.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param inventory (Representing the list of items currently in the Shopping Center's inventory)
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the express checkout line in the Shopping Center)
     * @throws IOException
     */
    public static void customerAddsItemToTheShoppingCart(ListArrayBasedPlus<Customer> customers,
                                                         ListArrayBasedPlus<Item> inventory, CheckOutLine line1, CheckOutLine line2, CheckOutLine
                                                                 expressLine) throws IOException {
        if(customers.isEmpty() == true) {
            System.out.println("No one is in the Shopping Center!");
        }
        else {
            boolean properInput = false;
            int index1 = 0;
            while(properInput == false) {
                System.out.print("Enter customer name: ");
                String key = user.readLine().trim();
                System.out.println(key);
                index1 = searchCustomers(customers, key);
                if(index1 == -1) {
                    Customer customer = searchCheckOutLines(line1, line2, expressLine, key);
                    if(customer == null) {
                        System.out.println("Sorry but the customer " + key + " is not currently at "
                                + "the shopping center.");
                    }
                    else {
                        System.out.println("Customer " + key + " is in a checkout line!");
                    }
                }
                else {
                    properInput = true;
                }
            }
            Customer customer = customers.get(index1);
            System.out.print("What item does " + customer.getName() + " want: ");
            String itemToGrab = user.readLine().trim();
            System.out.println(itemToGrab);
            int index2 = searchInventory(inventory, itemToGrab);
            if(index2 == -1) {
                System.out.println("Sorry but the item " + itemToGrab + " is not sold"
                        + " at the shopping center.");
            }
            else {
                Item item = inventory.get(index2);
                if(item.getQuantity() == 0) {
                    System.out.println("Item " + itemToGrab + " is currently out of stock.");
                }
                else {
                    customer.addItemToShoppingCart();
                    item.decrementQuantity();
                    timeIncrementation(customers);
                    if(customer.getShoppingCartSize() > 1) {
                        System.out.println("Customer " + customer.getName() + " now has "
                                + customer.getShoppingCartSize() + " items in their shopping cart.");
                    }
                    else {
                        System.out.println("Customer " + customer.getName() + " now has "
                                + "1 item in their shopping cart.");
                    }
                }
            }
        }
    }
    /**
     * Static Method that simulates a customer removing an item from their shopping cart.  Initially
     * it checks if the ListArrayBasedPlus of Customer objects is empty.  If it is, the user is given
     * an error message specifying that no customers are currently in the shopping center.  If the
     * list of customers isn't empty, the user is prompted to enter a 	customer name.  The customer
     * name entered is searched.  If the customer is not found, the user given an error message
     * either specifying the customer entered doesn't exist in the shopping center or that the
     * customer is currently in 	a checkout line.  It will continue to prompt a customer name until
     * it is found.   When the customer is found, an item is 	removed into the customer's shopping
     * cart, decrementing the Customer object's shoppingCartSize instance variable by 1 as well
     * incrementing all of the currently shopping Customer objects‘ timeSpentInShoppingCenter instance
     * variable by 1.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the express checkout line in the Shopping Center)
     * @throws IOException
     */
    public static void customerRemovesItemFromShoppingCart(ListArrayBasedPlus<Customer> customers,
                                                           CheckOutLine line1, CheckOutLine line2, CheckOutLine expressLine) throws IOException {
        if(customers.isEmpty() == true) {
            System.out.println("No one is in the Shopping Center!\n");
        }
        else {
            boolean properInput = false;
            int index = 0;
            while(properInput == false) {
                System.out.print("Enter customer name: ");
                String key = user.readLine().trim();
                System.out.println(key);
                index = searchCustomers(customers, key);
                if(index == -1) {
                    Customer customer = searchCheckOutLines(line1, line2, expressLine, key);
                    if(customer == null) {
                        System.out.println("Sorry but the customer " + key + " is not currently at "
                                + "the shopping center.");
                    }
                    else {
                        System.out.println("Customer " + key + " is in a checkout line!");
                    }
                }
                else {
                    properInput = true;
                }
            }
            Customer customer = customers.get(index);
            if(customer.getShoppingCartSize() == 0) {
                System.out.println("Customer " + customer.getName() + " doesn't have any items in"
                        + " their shopping cart\n");
            }
            else {
                customer.removeItemToShoppingCart();
                timeIncrementation(customers);
                if(customer.getShoppingCartSize() > 1) {
                    System.out.println("Customer " + customer.getName() + " now has "
                            + customer.getShoppingCartSize() + " items in their shopping cart.");
                }
                else {
                    System.out.println("Customer " + customer.getName() + " now has "
                            + "1 item in their shopping cart.");
                }
            }
        }
    }
    /**
     * Helper Method that sequentially searches all three checkout lines for a customer based on a
     * given name. It starts by searching the first line, if it doesn't find the customer, it then
     * searches the second line.  If it doesn't find the customer, it then and finally searches the
     * express line. If it doesn't find the customer, it returns a null Customer object indicating
     * and unsuccessful search.  If it does find the customer, it returns it, indicating a successful
     * search.  The search will also cut out when it finds the customer, only searching what is need.
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the express checkout line in the Shopping Center)
     * @param key (Representing the customer name being searched for)
     * @return
     */
    private static Customer searchCheckOutLines(CheckOutLine line1, CheckOutLine line2, CheckOutLine
            expressLine, String key) {
        Customer customer = null;
        int index = 0;
        boolean searchFinished = false;
        while(searchFinished == false) {
            index = searchCustomers(line1.getCustomers(), key);
            if(index == -1) {
                index = searchCustomers(line2.getCustomers(), key);
                if(index == -1) {
                    index = searchCustomers(expressLine.getCustomers(), key);
                    if(index == -1) {
                        searchFinished = true;
                    }
                    else {
                        customer = expressLine.getCustomers().get(index);
                        searchFinished = true;
                    }
                }
                else {
                    customer = line2.getCustomers().get(index);
                    searchFinished = true;;
                }
            }
            else {
                customer = line1.getCustomers().get(index);
                searchFinished = true;
            }
        }
        return customer;
    }
    /**
     * Static Method that represents a customer finishing shopping and selecting a check out line to
     * enter. Initially it checks if the ListArrayBasedPlus of Customer objects is empty.  If it is,
     * the user is given an error message specifying that no customers are currently in the shopping
     * center.  If the list of 	customers isn't empty, a search occurs returning the Customer object
     * with the most amount of time spent in the store.  If the customer found has 0 items in their
     * cart,  the user is then prompted for a 	reply to 	whether the customer wants to enter a leave
     * or continue shopping.  If the customer returns 	to shopping, the Customer object's
     * timeSpentInShoppingCenter instance variable is reset to zero.  If 	the customer decides to
     * leave, they are removed from the Shopping Center entirely.  Based on how 	many items the
     * customer has in their shopping cart the customer enters either the first, second or 	express
     * checkout line. If the customer has <= 5 items in their shopping cart, the customer will enter
     * the express line but if the express line has more than double the amount of customers than
     * either the first or second regular checkout lines, they will enter either first or second
     * checkout line depending on 	which is shorter.  If the customer has > 5 items, then they will
     * enter either the first or second checkout line, again depending on which is short shorter.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the express checkout line in the Shopping Center)
     * @throws IOException
     */
    public static void customerIsDoneShopping(ListArrayBasedPlus<Customer> customers, CheckOutLine line1,
                                              CheckOutLine line2, CheckOutLine expressLine) throws IOException {
        if(customers.isEmpty() == true) {
            System.out.println("No one is in the Shopping Center!");
        }
        else {
            Customer customer = customers.get(0);
            String name = customer.getName();
            int shoppingCartSize = customer.getShoppingCartSize();
            if(shoppingCartSize == 0) {
                System.out.print("Should customer " + name + " leave or continue shopping?  "
                        + "Leave?[Y/N]: ");
                String reply = user.readLine().trim();
                System.out.println(reply);
                if(reply.equalsIgnoreCase("Y")) {
                    customers.remove(0);
                    System.out.println("Customer " + name + " with 0 items has left the Shopping "
                            + "Center.");
                }
                else {
                    System.out.println("Customer " + name + " with 0 items has returned to shopping.");
                    customers.remove(0);
                    customers.add(customers.size(), customer);
                    customer.setTimeSpentInShoppingCenter(0);
                }
            }
            else {
                int size1 = line1.getCustomers().size();
                int size2 = line2.getCustomers().size();
                int size3 = expressLine.getCustomers().size();
                if(shoppingCartSize <= 5) {
                    if((size3 > 2 * size1) || (size3 > 2 * size2)) {
                        if(size1 <= size2) {
                            customerCheckOutLineChoice(customers, line1);
                        }
                        else {
                            customerCheckOutLineChoice(customers, line2);
                        }
                    }
                    else {
                        customerCheckOutLineChoice(customers, expressLine);
                    }
                }
                else {
                    if(size1 <= size2) {
                        customerCheckOutLineChoice(customers, line1);
                    }
                    else {
                        customerCheckOutLineChoice(customers, line2);
                    }
                }
            }
        }
    }
    /**
     * Private helper method to customerIsDoneShopping() method whose sole purpose is to reduce code
     * duplication. The customer's choice of checkout line is passed as a parameter as well as the
     * index representing the resulting Customer object's position from the customer search in the
     * customerIsDoneShopping() method.  That Customer 	object is added (at size) into the CheckOutLine
     * object's List instance variable.  The customer object is also removed (at 0) from the currently
     * shopping customers list (also passed as a parameter).
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param line (Representing the customers checkout line choice)
     */
    private static void customerCheckOutLineChoice(ListArrayBasedPlus<Customer> customers,
                                                   CheckOutLine line) {
        Customer firstCustomer = customers.get(0);
        line.getCustomers().add(line.getCustomers().size(), firstCustomer);
        customers.remove(0);
        System.out.println("After " + firstCustomer.getTimeSpentInShoppingCenter() + " minutes in"
                + " the Shopping Center, customer " + firstCustomer.getName() + " with "
                + firstCustomer.getShoppingCartSize() + " items is now in"+ " the "
                + line.getLineNumber() + " checkout line.");
    }
    /**
     * Static Method that simulates a specific checkout line processing a customer.  Initially it
     * checks if all of the checkout lines are empty.  If they are, the user is given an error message
     * specifying that no customers are currently in any checkout lines.  If at least one of the
     * checkout lines isn't, a helper method is called to determine whether the first, second, or
     * express checkout line will process a customer.  Another helper method is used simulate a
     * customer either checking out and leaving the store, or deciding to return to shop.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the express checkout line in the Shopping Center)
     * @param lineUpNext (Representing which checkout line is next to process a customer, either 1,
     * 2, or 3)
     * @throws IOException
     */
    public static void CustomerIsReadyToCheckOut(ListArrayBasedPlus<Customer> customers,
                                                 CheckOutLine line1, CheckOutLine line2, CheckOutLine expressLine, int lineUpNext)
            throws IOException {
        int line = checkOutLineDetermination(line1, line2, expressLine, lineUpNext);
        if(line == 1) {
            customerCheckOut(customers, line1);
        }
        else if(line == 2) {
            customerCheckOut(customers, line2);
        }
        else if(line == 3) {
            customerCheckOut(customers, expressLine);
        }
    }
    /**
     * Helper method to customerIsReadyToCheckOut() that determines which checkout line will process
     * their next customer.   Customers check out in a fair manner: all three checkout lines take turns
     * in checking out customers.  If there is no customer in the line whose turn it is to check out a
     * customer, then the next checkout line that has customers in line will check out the first
     * customer in line.
     * @param line1 (Representing the first checkout line in the Shopping Center)
     * @param line2 (Representing the second checkout line in the Shopping Center)
     * @param expressLine (Representing the first third line in the Shopping Center)
     * @param lineNumber (Representing the check out line that will process their next customer)
     * @return
     */
    private static int checkOutLineDetermination(CheckOutLine line1, CheckOutLine line2,
                                                 CheckOutLine expressLine, int lineNumber) {
        int lineToReturn = 0;
        boolean searchFinished = false;
        while(searchFinished == false) {
            switch(lineNumber) {
                case 1 : if(line1.getCustomers().size() == 0) {
                    lineNumber++;
                }
                else {
                    lineToReturn = lineNumber;
                    searchFinished = true;
                }
                    break;
                case 2 : if(line2.getCustomers().size() == 0) {
                    lineNumber++;
                }
                else {
                    lineToReturn = lineNumber;
                    searchFinished = true;
                }
                    break;
                case 3:  if(expressLine.getCustomers().size() == 0) {
                    lineNumber = 1;
                }
                else {
                    lineToReturn = lineNumber;
                    searchFinished = true;
                }
                    break;
            }
        }
        return lineToReturn;

    }
    /**
     * Helper Method to customerIsReadyToCheckOut() method that exists to reduce code duplication and
     * complete the simulation of a customer checking out and leaving the Shopping Center.  The user is
     * prompted to decide if the 	customer wants to check out and leave the store, or get back to
     * shopping.  If the customer wants to leave, the specific Customer object is removed from the
     * CheckOutLine's List instance variable(at 0), and the user is given a message that the customer
     * has left the shopping center. If the customer wants to go back to shopping, that specific
     * Customer is removed from the CheckOutLine's List instance variable (at 0) then added back into
     * the list of 	currently shopping customers (at size) and the user is given a message that the
     * customer has return to shopping.  Also once the customer decides to go back to shopping, that
     * Customer object's timeSpentInShoppingCenter 	instance variable is reset to 0.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param line (Representing the checkout line that will process their next customer.)
     * @throws IOException
     */
    private static void customerCheckOut(ListArrayBasedPlus<Customer> customers, CheckOutLine line)
            throws IOException {
        Customer currentCustomer = line.getCustomers().get(0);
        System.out.print("Does customer " + currentCustomer.getName() + " want ot check out or continue"
                + " to shop more? [Y/N]: ");
        String reply = user.readLine().trim();
        System.out.println(reply);
        if(reply.equalsIgnoreCase("Y")) {
            line.getCustomers().remove(0);
            System.out.println("Customer " + currentCustomer.getName() + " has checked out and left the "
                    + "shopping center");
        }
        else {
            line.getCustomers().remove(0);;
            customers.add(customers.size(), currentCustomer);
            currentCustomer.setTimeSpentInShoppingCenter(0);
            System.out.println("Customer " + currentCustomer.getName() + " has decided to return shopping");
        }
    }
    /**
     * Static Method that prints out all of the currently shopping customers.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     */
    public static void printInfoAboutShoppingCustomers(ListArrayBasedPlus<Customer> customers) {
        if(customers.isEmpty()) {
            System.out.println("No customers are currently shopping");
        }
        else {
            int size = customers.size();
            if(size > 1) {
                System.out.println("There are " + size + " customers currently shopping: ");
            }
            else {
                System.out.println("There is one customer currently shopping");
            }
            for(int i = 0 ; i < size ; i++) {
                Customer customer = customers.get(i);
                int cartSize = customer.getShoppingCartSize();
                int timeSpent = customer.getTimeSpentInShoppingCenter();
                if(cartSize > 1) {
                    System.out.println("    Customer " + customer.getName() + " with " + cartSize
                            + " items present for " +  timeSpent + " minutes.");
                }
                else if(cartSize == 0) {
                    System.out.println("    Customer " + customer.getName() + " with 0 items present "
                            + "for " +  timeSpent + " minutes.");
                }
                else {
                    System.out.println("    Customer " + customer.getName() + " with 1 item present for "
                            + timeSpent + " minutes.");
                }
            }
        }
    }
    /**
     * Static Method that prints out all the customers in a specific checkoutLine.
     * @param line (Representing the specific checkout line's customers to be printed)
     */
    public static void printInfoAboutCustomersInCheckOutLine(CheckOutLine line) {
        int size = line.getCustomers().size();
        String lineNumber = line.getLineNumber();
        if(size == 0) {
            System.out.println("  No customers are in the " + lineNumber +" check out line.");
        }
        else {
            if(size == 1) {
                System.out.println("  The following customer is in the " + lineNumber
                        + " check out line.");
            }
            else {
                System.out.println("  The following " + size + " customers are in the " + lineNumber
                        + " check out line.");
            }
            System.out.println("      " + line.getCustomers().toString());

        }
    }
    /**
     * Static Method that prints out all of the items in the Shopping Center inventory that are equal
     * or below the restock level
     * @param inventory (Representing the currently inventory in the Shopping Center)
     * @param reStockLevel (Representing the restock level for all items)
     */
    public static void printItemsAtOrBelowRestockLevel(ListArrayBasedPlus<Item> inventory,
                                                       int reStockLevel) {
        System.out.println("Items at restock level: ");
        int size = inventory.size();
        boolean restockNeeded = false;
        for(int i = 0 ; i < size ; i++) {
            Item item = inventory.get(i);
            if(item.getQuantity() <= reStockLevel) {
                System.out.println(item.toString());
                restockNeeded = true;
            }
        }
        if(restockNeeded == false) {
            System.out.println("Good news!  No items are at the restock level");
        }
    }
    /**
     * Static Method that simulates the process of re-ordering an item in the Shopping Center's
     * inventory.  The user is prompted to enter the name of the item to re-order.  The item name
     * entered is searched.  If the item isn't found, the user is given an error message specifying
     * that the item requested doesn't exist in the Shopping Center inventory.  If the item is found,
     * then the user is prompted to enter in a specific amount of said items to be reOrdered.  The
     * found Item object's quantity instance variable is incremented by said amount.
     * object's quantity
     * @param inventory (Representing the currently inventory in the Shopping Center)
     * @throws IOException
     */
    public static void reOrderItem(ListArrayBasedPlus<Item> inventory) throws IOException {
        System.out.print("Enter item name to be re-ordered: ");
        String item = user.readLine().trim();
        System.out.println(item);
        int index = searchInventory(inventory, item);
        if(index == -1) {
            System.out.println("That item doesnt appear to be present in Inventory.");
        }
        else {
            Item a = inventory.get(index);
            System.out.print("Enter number of " + item + "s to be re-ordered: ");
            int orderNumber = Integer.parseInt(user.readLine().trim());
            System.out.println(orderNumber);
            a.incrementQuantity(orderNumber);
            System.out.println("Inventory now has " + a.getQuantity() + " " + a.getName() + "s.");
        }
    }
    /**
     * Static Method that ends the program and prompts a message to the user specifying exactly that
     */
    public static void closeApplication() {
        System.out.println("The Shopping Center is closing...come back tomorrow.");
    }
    /**
     * Static Method that runs a Sequential Search on an Unordered Customer Objects list that returns
     * either -1 (unsuccessful search) or a specified numerical index representing the found Customer
     *  object's position in the Customer object list (successful search).
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     * @param customerName (Representing the customer name being searched for)
     * @return index (-1 or any int from 0 - customers.size() - 1)
     */
    private static int searchCustomers(ListArrayBasedPlus<Customer> customers, String customerName) {
        int size = customers.size();
        int index = 0;
        boolean searchFinished = false;
        while(index < size && searchFinished == false) {
            Customer customer = customers.get(index);
            if(customerName.equalsIgnoreCase(customer.getName())) {
                searchFinished = true;
            }
            else {
                index++;
            }
        }
        if(searchFinished == false) {
            index = -1;
        }
        return index;
    }
    /**
     * Static Method that runs a Sequential Search on an Unordered Item objects list that returns either
     * -1 (unsuccessful search) or a specified numerical index representing the found ITem object's
     * position in the Item objects list (successful search).
     * @param inventory (Representing the currently inventory in the Shopping Center)
     * @param key (Representing the customer name being searched for)
     * @return index (-1 or any int from 0 - inventory.size() - 1)
     */
    private static int searchInventory(ListArrayBasedPlus<Item> inventory, String itemName) {
        int size = inventory.size();
        int index = 0;
        boolean searchFinished = false;
        while(index < size && searchFinished == false) {
            Item item = inventory.get(index);
            if(itemName.equalsIgnoreCase(item.getName())) {
                searchFinished = true;
            }
            else {
                index++;
            }
        }
        if(searchFinished == false) {
            index = -1;
        }
        return index;
    }
    /**
     * Static Helper Method used solely for code duplication.  It increments every Customer object's
     * timeSpentInShoppingCenter's instance variable by 1 in the customers list.
     * @param customers (Representing the list of customers currently shopping in the Shopping Center)
     */
    private static void timeIncrementation(ListArrayBased<Customer> customers) {
        int size = customers.size();
        for(int i = 0 ; i < size ; i++) {
            customers.get(i).incrementTimeSpent();
        }
    }
}