
public class Laptop extends Resource {
	protected static int fineDay;
	protected static int fineMax;
	private String manufacturer;
	private String model;
	private String operatingSys;
	
	public Laptop (String title, int year, Thumbnail thumbnail, String manufacturer, String model, String operatingSys) {
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
	
	public String operatingSys() {
		return operatingSys;
	}

	public void setManufacturer(String manufacturer) {
		
	}
	
	public void setModel(String model) {
		
	}
	
	public void setOperatingSys(String operatingSys) {
		
	}
}
