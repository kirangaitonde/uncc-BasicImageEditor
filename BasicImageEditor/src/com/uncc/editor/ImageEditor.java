/*
 * ImageEditor
 * 
 * Allows the user to select an image from the system.
 * 
 * Next it allows the user to select points on the image with  left click of mouse.
 * 
 * At right click it creates a new image having a polygon with vertices at selected points.
 * 
 * The polygon is filled with white and the rest of the image is filled with black
 *  
 * @author Kiran Gaitonde
 * 
 * References docs.oracle.com, stackoverflow.com
 * 
 */

package com.uncc.editor;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.canvas.*;

public class ImageEditor extends Application {
 
	private Image img;
	private ArrayList<Double> arrayList = new ArrayList<Double>();

    @Override public void start(Stage stage) throws Exception {        
    	 
    	final Button openButton = new Button("Open an Image");
    	final FileChooser fileChooser = new FileChooser();
    	final Screen        screen      = Screen.getPrimary();
    	final Rectangle2D   bounds      = screen.getVisualBounds();
    	
    	HBox hb = new HBox(10); 
    	
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().setAll(openButton, hb);
        VBox.setVgrow(hb, Priority.ALWAYS);
           
        final Scene scene = new Scene(layout);
        stage.setTitle("Image Editor");       
        stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth()-10);
		stage.setHeight(bounds.getHeight()-10);        
		stage.setScene(scene);
        stage.show();
        
        
        // button event
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(stage);
                        setImage(new Image(file.toURI().toString()));
                        arrayList.clear();
                        setHB(hb,getImage());
                    }
                });
        
        
        //mouse event
        hb.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	
            	
                if (event.isSecondaryButtonDown()) {
                	setNewHB(hb,getImage(),arrayList);
                	
                }
                else{
                	System.out.print("listened ");
                	Double x = event.getX();                	
                	Double y = event.getY();
                	
                	arrayList.add(x);                	
                	arrayList.add(y);  
                	
                }
            }
        });
      
    }
    
    // choosing file
    private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    // setting HBox with selected image
    private HBox setHB(HBox hb, Image img)
    {   
    	hb.getChildren().clear();
    	Canvas canvas1 = new Canvas(img.getWidth(), img.getHeight());
		GraphicsContext gc = canvas1.getGraphicsContext2D();
		gc.drawImage(img,0,0);	
		hb.getChildren().add(canvas1);
		VBox.setVgrow(hb, Priority.ALWAYS);
		HBox.setHgrow(canvas1, Priority.ALWAYS); 
    	
        return hb;
    }
    
    // setting HBox with edited image
    private HBox setNewHB(HBox hb, Image img, ArrayList<Double> a)
    {   
    	hb.getChildren().clear();
    	int n = a.size()/2;
    	double[] b = new double[n];
    	double[] c = new double[n];
    	double[] d = new double[a.size()];
    	int p=0;
		int q=0;
    	for (int i = 0; i < a.size(); i++)
    	{
    		b[p]= a.get(i);
    		i++;
    		p++;
    	}
    	for (int j = 1; j < a.size(); j++)
    	{
    		c[q]= a.get(j);
    		j++;
    		q++;
    	}
    	for (int k = 0; k < a.size(); k++)
    	{
    		d[k]= a.get(k);
    		
    	}    	
    	Canvas canvas2 = new Canvas(img.getWidth(), img.getHeight());
		GraphicsContext gc2 = canvas2.getGraphicsContext2D();
		gc2.setFill(Color.BLACK);
		gc2.fillRect(0,0,img.getWidth(),img.getHeight());
		gc2.setFill(Color.WHITE);
		gc2.fillPolygon(b,c, n);		
		hb.getChildren().add(canvas2);		
		HBox.setHgrow(canvas2, Priority.ALWAYS); 
    	
        return hb;
    }
    
    private void  displayArrayList(ArrayList<Double> a)
    {   
    	 
    	double[] b = new double[a.size()];
    	for (int i = 0; i < a.size(); i++)
    	{
    		b[i]= a.get(i);
    	}
    	 for (int i = 0; i < a.size(); i++)
		    {	
    		 System.out.println("visited for loop ");
		     System.out.print(b[i]+" ");
		     }   	
    	
    }    
    
    public void setImage(Image i)
	{
		this.img = i;
	}
	
	public Image getImage()
	{
		return img;
	}
	
   

    public static void main(String[] args) { launch(args); }
}