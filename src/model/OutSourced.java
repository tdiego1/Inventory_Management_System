package model;

/**
 * Sets/gets Company Name for Part object.
 */
public class OutSourced extends Part{

    /**
     * Company Name string for Part object
     */
    private String companyName;

    /**
     * Creates a new Part with the companyName included.
     * @param id Part ID
     * @param name Part name
     * @param price Price of part
     * @param stock number of parts in inventory
     * @param min minimum number of parts that should be available
     * @param max maximum number of parts that can be stored
     * @param companyName name of company that makes the part
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the companyName.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the companyName.
     * @return {@link OutSourced#companyName}
     */
    public String getCompanyName(){
        return this.companyName;
    }
}
