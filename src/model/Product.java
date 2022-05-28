package model;

import javafx.collections.ObservableList;

import static javafx.collections.FXCollections.observableArrayList;

/** Sets, gets, adds, and deletes Product objects. */
public class Product {

    /** List of all associated Parts. */
    private ObservableList<Part> associatedParts = observableArrayList();
    /** ID of Product. */
    private int id;
    /** Name of Product. */
    private String name;
    /** Price of Product. */
    private double price;
    /** Current stock of Product. */
    private int stock;
    /** Minimum stock of Product. */
    private int min;
    /** Maximum stock of product. */
    private int max;

    /**
     * Creates a new Product.
     * @param id ID of new Product
     * @param name Name of new Product
     * @param price Price of new Product
     * @param stock Current stock of new Product
     * @param min Minimum stock of new Product
     * @param max Maximum stock of new Product
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the ID of Product.
     * @param id ID of Product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of Product.
     * @param name Name of Product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of Product.
     * @param price Price of Product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the stock of the Product.
     * @param stock Current stock of Product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum stock of Product.
     * @param min Minimum stock of Product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets teh maximum stock of Product.
     * @param max Maximum stock of Product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the ID of Product.
     * @return ID of Product
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of Product.
     * @return Name of Product
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of Product.
     * @return Price of Product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the stock of Product.
     * @return Current stock of Product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum stock of Product.
     * @return Minimum stock of Product
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum stock of Product.
     * @return Maximum stock of Product
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds a part to {@link Product#associatedParts} list.
     * @param part Associated part to add
     */
    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from list of associated parts.
     * @param selectedAssociatedPart Part to be deleted
     * @return 'True' if part was deleted from list
     */
    public boolean deleteAssociatedPart (Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * Gets all associated parts from list.
     * @return List of associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
