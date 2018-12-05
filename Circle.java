/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userimage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
/**
 *
 * @author kevin
 */
public class UserImage extends Application {
    Circle circleR;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    @Override
    public void start(Stage primaryStage) {
        circleR = new Circle(70.0f, Color.RED);
        circleR.setCursor(Cursor.HAND);
        circleR.setCenterX(150);
        circleR.setCenterY(150);
        circleR.setOnMousePressed(circleOnMousePressedEventHandler);
        circleR.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        
        
        Group root = new Group();
        root.getChildren().addAll(circleR);
        
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400,350));
         
        primaryStage.setTitle("User Image");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
            orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
        }
        };
     EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
             
            ((Circle)(t.getSource())).setTranslateX(newTranslateX);
            ((Circle)(t.getSource())).setTranslateY(newTranslateY);
        }
    };
}