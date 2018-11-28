public class Image {
	abstract String default
	String filename
	
	public Image (String filename){
		if (filename == null){
			this.filename = default
		}
		else{
			this.filename = filename
		}
	
	}
	
	public String getImage(){
		return this.filename;
	}
	
	public setImage(String filename){
		this.filename = filename;
	}

}