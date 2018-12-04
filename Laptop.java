
public class Laptop extends Resource {
	protected static double fineDay = 10.00;
	protected static double fineMax = 100.00;
	private String manufacturer;
	private String model;
	private String operatingSys;
	
	public Laptop (String title, int year, Thumbnail thumbnail, String manufacturer, String model, String operatingSys) {
		super(title, year, thumbnail);
		this.manufacturer = manufacturer;
		this.model = model;
		this.operatingSys = operatingSys;

	}

	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getOperatingSys() {
		return operatingSys;
	}

	public void setManufacturer(String manufacturer) {
		
	}
	
	public void setModel(String model) {
		
	}
	
	public void setOperatingSys(String operatingSys) {
		
	}
}
