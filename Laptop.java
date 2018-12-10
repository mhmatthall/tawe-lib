/**
 * models laptop in library
 * 
 * @author Ben Rochford
 */
public class Laptop extends Resource {
	protected static double fineDay = 10.00;
	protected static double fineMax = 100.00;
	private String manufacturer;
	private String model;
	private String operatingSys;

	/**
	 * Creates a new laptop
	 *
	 * @param title the name of a laptop
	 * @param year of manufacture
	 * @param thumbnail of the laptop
	 * @param manufacturer of the laptop
	 * @param model of the laptop
	 * @param operatingSys of a laptop
	 */
	public Laptop(String title, int year, Thumbnail thumbnail, String manufacturer, 
			String model, String operatingSys) {
		super(title, year, thumbnail);
		this.manufacturer = manufacturer;
		this.model = model;
		this.operatingSys = operatingSys;
	}

	/**
	 * Laptop's constructor for DatabaseRequest to initialise laptop 
	 *
	 * @param resourceID of the laptop
	 * @param title the name of the laptop
	 * @param year of manufacture
	 * @param thumbnail of the laptop
	 * @param queue of users waiting for the laptop
	 * @param manufacturer of the laptop
	 * @param model of the laptop
	 * @param operatingSys of the laptop
	 */
	public Laptop(String resourceID, String title, int year, Thumbnail thumbnail,
			RequestQueue queue, String manufacturer, String model, String operatingSys) {
		super(resourceID, title, year, thumbnail, queue);
		this.manufacturer = manufacturer;
		this.model = model;
		this.operatingSys = operatingSys;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String x;
		x = ("Title: " + title + " \nYear: " + year + " \nManufacturer: " + manufacturer + " \nModel: " + model
				+ " \nOperating System: " + operatingSys);
		return x;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Gets the model.
	 *
	 * @return model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Gets the operating system.
	 *
	 * @return operating system
	 */
	public String getOperatingSys() {
		return operatingSys;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer to be 
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	} 

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Sets the operating sys.
	 *
	 * @param operatingSys the new operating sys
	 */
	public void setOperatingSys(String operatingSys) {
		this.operatingSys = operatingSys;
	}

	/**
	 * gets fine per day when overdue.
	 *
	 * @return fine per day
	 */
	public static double getFineDay() {
		return fineDay;
	}

	/**
	 * Gets the maximum fine fine
	 *
	 * @return max fine
	 */
	public static double getFineMax() {
		return fineMax;
	}
}
