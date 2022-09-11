

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;





public class MyController implements Initializable {
	
	@FXML
	private BorderPane mainbp;
    
	@FXML
	private BorderPane serverbp;
	
	@FXML
	private BorderPane clientbp;

	@FXML 
	private BorderPane clientbp2;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	
	

	
	public void servergui(ActionEvent e) throws IOException
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Serverfxml.fxml"));
		Parent serverbp = loader.load();
		serverbp.getStylesheets().add("/styles/style1.css");
		mainbp.getScene().setRoot(serverbp);
		
	}
	//STARTING CLIENT GUI
	
	public void clientgui(ActionEvent e) throws IOException
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Clientfxml.fxml"));
		Parent clientbp = loader.load();
		clientbp.getStylesheets().add("/styles/style1.css");
		mainbp.getScene().setRoot(clientbp);
	}
	
	
	
	


	
	

	

}
