 /*
  * @author Caleb Warburton
  * @version 1.0.0
  */
 

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UserImage extends Image {
	private String defaultFilename;
    private String[] premadeImages = {"ProfilePic1.jpg","ProfilePic2.jpg","ProfilePic3.jpg","ProfilePic4.jpg"};
	
	public UserImage(String fileName) {
		super(fileName);
	}
	
	public void createImage() {
		
	}
	
	public void saveImage() {
		
	}
	
	public void addShape(String type, String colour, double originX, double originY, double height, double width, boolean isFilled) {
		//if (type == "Circle") {
			//Circle shape = new Circle(colour, originX, originY, isFilled, height)
		//}
		//else if (type == "Rectangle") {
			//Rectangle shape = new Rectangle(colour, originX, originY, isFilled, width, height) 
		//}
		//else if (type == "Triangle") {
			//Triangle shape = new Triangle(colour, originX, originY, isFilled, width, height)
		//}
		//else if (type == "Line") {
			//Line shape = new Line(colour, originX, originY, width, height) 
		//}
		
		//shape.draw();
	}
	
	public void selectImage(int Selection) {
		setImage(premadeImages[Selection]);
	}
}