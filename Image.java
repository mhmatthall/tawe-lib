/*
 * TODO add missing methods according to UML
 */

public class Image {
	private String defaultImage;
	private String filename;
	
	public Image (String filename){
		if (filename == null) {
			this.filename = defaultImage;
		} else {
			this.filename = filename;
		}
	}
	
	public String getImage(){
		return this.filename;
	}
	
	public void setImage(String filename){
		this.filename = filename;
	}

}