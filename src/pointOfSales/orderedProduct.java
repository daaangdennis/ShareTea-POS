package pointOfSales;

import java.util.ArrayList;

/**
 * The orderedProduct class is a format class used to store the information that
 * the backend needs.
 * 
 * @author Sam Trythall
 * @version v0.0.3
 * @since v0.0.2
 */

public class orderedProduct {
    private String teaType;
    private int quantity;
    private ArrayList<String> toppings = new ArrayList<>();
    private String price;
    private Double sugarLevel;
    private String iceLevel;
    private String note;
    private String pid;

    /**
     * Default constructor for the orderedProduct class. Initializes the tea
     * type, quantity, and price to default values.
     */
    public orderedProduct() {
        teaType = "NULL";
        quantity = 0;
        price = "NULL";
    }

    /**
     * Retrieves the type of tea of this ordered product.
     *
     * @return The type of tea.
     */
    public String getTeaType() {
        return this.teaType;
    }

    /**
     * Retrieves the quantity of the ordered product.
     *
     * @return The quantity ordered.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Retrieves the list of toppings for this ordered product.
     *
     * @return An ArrayList of toppings.
     */
    public ArrayList<String> getToppings() {
        return this.toppings;
    }

    /**
     * Retrieves the price of the ordered product.
     *
     * @return The price of the product.
     */
    public String getPrice() {
        return this.price;
    }

    /**
     * Retrieves the sugar level for this ordered product.
     *
     * @return The sugar level, represented as a Double.
     */
    public Double getSugar() {
        return this.sugarLevel;
    }

    /**
     * Retrieves the ice level for this ordered product.
     *
     * @return The ice level as a String.
     */
    public String getIce() {
        return this.iceLevel;
    }

    /**
     * Retrieves any additional notes for this ordered product.
     *
     * @return The notes associated with the product.
     */
    public String getNote() {
        return this.note;
    }

    /**
     * Retrieves the product identifier of this ordered product.
     *
     * @return The product ID.
     */
    public String getId() {
        return this.pid;
    }

    /**
     * Sets the tea type for this ordered product.
     *
     * @param type The type of tea to set.
     */
    public void setTeaType(String type) {
        teaType = type;
    }

    /**
     * Sets the quantity for this ordered product.
     *
     * @param quant The quantity to set.
     */
    public void setQuantity(int quant) {
        quantity = quant;
    }

    /**
     * Adds a topping to the list of toppings for this ordered product.
     *
     * @param value The topping to add.
     */
    public void addToList(String value) {
        toppings.add(value);
    }

    /**
     * Sets the price for this ordered product.
     *
     * @param money The price to set.
     */
    public void setPrice(String money) {
        price = money;
    }

    /**
     * Clears the list of toppings for this ordered product.
     */
    public void clearList() {
        toppings.clear();
    }

    /**
     * Sets the sugar level for this ordered product.
     *
     * @param sugar The sugar level to set, represented as a Double.
     */
    public void setSugar(Double sugar) {
        sugarLevel = sugar;
    }

    /**
     * Sets the ice level for this ordered product.
     *
     * @param ice The ice level to set.
     */
    public void setIce(String ice) {
        iceLevel = ice;
    }

    /**
     * Sets a note for this ordered product.
     *
     * @param message The note to set.
     */
    public void setNote(String message) {
        note = message;
    }

    /**
     * Sets the product identifier for this ordered product.
     *
     * @param id The product ID to set.
     */
    public void setId(String id) {
        pid = id;
    }
}
