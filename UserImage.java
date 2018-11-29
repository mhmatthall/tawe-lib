 /*
 * @author Caleb Warburton
 * @version 1.0.0
 */
 
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UserImage extends Image {
	String default
	String[4] premadeImages = ["ProfilePic1.jpg","ProfilePic2.jpg","ProfilePic3.jpg","ProfilePic4.jpg"] 
	
	public UserImage (String filename){
		super(filename)
	
	public createImage(){
		
	
	}
	
	public saveImage(){
		try {
			Robot robot = new Robot(); 
			String format = "jpg";
			String fileName = "CustomProfilePic." + format;
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle customPic = new Rectangle(0, 0, screenSize.width / 2, screenSize.height / 2);
			BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
			ImageIO.write(screenFullImage, format, new File(fileName));
			
			System.out.println("Profile Pic saved!");
		} catch (AWTException | IOException ex) {
			System.err.println(ex);
		}
	}
	
	public addShape(String type, String colour, double originX, double originY, double height, double width, boolean isFilled)
		if (type == "Circle"){
			Circle shape = new Circle(colour, originX, originY, isFilled, height)
		}
		else if (type == "Rectangle"){
			Rectangle shape = new Rectangle(colour, originX, originY, isFilled, width, height) 
		}
		else if (type == "Triangle"){
			Triangle shape = new Triangle(colour, originX, originY, isFilled, width, height)
		}
		else if (type == "Line"){
			Line shape = new Line(colour, originX, originY, width, height) 
		}
		
		shape.draw()
	}
	
	public selectImage(int Selection){
		setFilename(premadeImages[Selection]);
	}