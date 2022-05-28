package model;

/** Sets/gets Machine ID for Inhouse parts */
public class InHouse extends Part{

    /** Machine ID integer for InHouse parts */
    private int machineId;
    /** Inisitalizes id value to 0 */
    public static int id = 0;

    /**
     * Creates a new Part with the Machine ID included.
     * @param id Part ID
     * @param name Part name
     * @param price Price of part
     * @param stock number of parts in inventory
     * @param min minimum number of parts that should be available
     * @param max maximum number of parts that can be stored
     * @param machineId ID of machine that makes the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machineId for a Part object.
     * @param machineId ID of machine that makes the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Returns the machineId of a Part object.
     * @return {@link InHouse#machineId}
     */
    public int getMachineId(){
        return this.machineId;
    }
}
