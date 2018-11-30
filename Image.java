public class Image {
	String defaultImage;
	String filename;
	
	public Image (String filename){
		if (filename == null){
			this.filename = defaultImage;
		}
		else{
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