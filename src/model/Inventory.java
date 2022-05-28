package model;

import javafx.collections.ObservableList;

import static javafx.collections.FXCollections.observableArrayList;

/** Sets, Gets, updates, and deletes the current inventory of Parts and Products. */
public class Inventory {

    /** List of all Parts. */
    private static ObservableList<Part> allParts = observableArrayList();

    /** List of all Products. */
    private static ObservableList<Product> allProducts = observableArrayList();

    /**
     * Adds a part to the inventory.
     * @param newPart New part to add to inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to the inventory.
     * @param newProduct New product to add to inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up part based on part ID.
     * @param partId ID of the part to lookup.
     * @return part identified by ID.
     */
    public static Part lookupPart(int partId) {
        Part retPart = null;

        for(Part p : allParts) {
            if (p.getId() == partId) {
                retPart = p;
            }
        }
        return retPart;
    }

    /**
     * Looks up Product based on product ID.
     * @param productId ID of product to lookup.
     * @return Product identified by ID.
     */
    public static Product lookupProduct(int productId) {
        Product retProduct = null;

        for(Product pr : allProducts) {
            if (pr.getId() == productId) {
                retProduct = pr;
            }
        }
        return retProduct;
    }

    /**
     * Looks up Part(s) based on name.
     * @param partName Name of part to search.
     * @return List of Part(s) from search.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part>  searchedParts = observableArrayList();

        for(Part p : allParts){
            if(p.getName().contains(partName)){
                searchedParts.add(p);
            }
        }
        return searchedParts;
    }

    /**
     * Looks up Product(s) based on name.
     * @param productName Name of product to search.
     * @return List of Product(s) from search.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product>  searchedProducts = observableArrayList();

        for(Product pr : allProducts){
            if(pr.getName().contains(productName)){
                searchedProducts.add(pr);
            }
        }
        return searchedProducts;
    }

    /**
     * Updates selected part.
     * @param index Index of info to update.
     * @param selectedPart Selected Part to update.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates selected Product.
     * @param index Index of info to update.
     * @param newProduct Selected Product to update.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a Part.
     * @param selectedPart Selected part to delete.
     * @return 'True' once part has been deleted.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a Product.
     * @param selectedProduct Selected Product to delete.
     * @return 'True' once Product has been deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets list of all Parts.
     * @return List of all Parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets list of all Products.
     * @return List of all Products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
