package dsa;
/**
 * CheckOutLine class represents/creates CheckOutLine objects with instance variables ListArrayBasedPlus
 * <Customer> customer and lineNumber of type String.  Class is used to represent the checkout lines in
 * the shopping center.
 * @author David Yeager & Stephanie Canada
 * @version 12.06.16
 */
public class CheckOutLine {
    //Instance variable for the line of customers in the checkout line
    private ListArrayBasedPlus<Customer> customers;
    //Instance variable for the number of customers in the checkout line
    private String lineNumber;
    /**
     * Constructor for the CheckOutLine class that creates and initializes new CheckOutLine objects.
     * @param lineNumber (Representing the new CheckOutLine object's line number)
     */
    public CheckOutLine(String lineNumber) {
        customers = new ListArrayBasedPlus<Customer>();
        this.lineNumber = lineNumber;
    }
    /**
     * Getter Method that returns the current CheckOutLine object's customers instance variable value
     * @return customers (Representing the line of customers in the checkout line)
     */
    public ListArrayBasedPlus<Customer> getCustomers() {
        return customers;
    }
    /**
     * Setter Method that sets the current CheckOutLine object's customers instance variable to a
     * ListArrayBasedPlus<Customer> object passed as a parameter
     * as a parameter.
     * @param name (Representing the value to be set to the name instance variable)
     */
    public void setCustomers(ListArrayBasedPlus<Customer> customers) {
        this.customers = customers;
    }
    /**
     * Getter Method that returns the current CheckOutLine object's lineNumber instance variable value
     * @return lineNumber (Represents the lineNumber associated with the checkout line)
     */
    public String getLineNumber() {
        return lineNumber;
    }
    /**
     * Setter Method that sets the current CheckOutLine object's lineNumber instance variable to a String
     * passed as a parameter.
     * @param name (Representing the value to be set to the name instance variable)
     */
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }
}

