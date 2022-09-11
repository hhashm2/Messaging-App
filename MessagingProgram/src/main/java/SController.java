

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;





public class SController implements Initializable {
	
	public  Server sc;
	
	//for changing the listview
	private String clientsstr="Clients";
	private String messagesstr="Messages";
	
    @FXML
    private static Stage p;
	
	@FXML
	private BorderPane serverbp;
	
	@FXML
	private  Label label1;
	
	@FXML
	private Button change;
	
	@FXML
	ListView<String>  listItems=new ListView<String>();
	
	//Will include the clients that are connected
	ListView<String>  clists=new ListView<String>();
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
		p.setTitle("SERVER");
		servers();
		clists.setBlendMode(BlendMode.MULTIPLY);
		clists.setStyle("-fx-background-color: #fcfcf1; -fx-border-color:Black");
		clists.setTranslateY(-50);
		
	}
	
	
	public static void getstage(Stage ps) 
	{
		p=ps;
		
	}
	
	
	public void servers() 
	{
		
		sc = new Server(data -> {
			Platform.runLater(()->{
				listItems.getItems().add(data.toString());
				
			});

		},data->{
			Platform.runLater(()->{
				
				clists.getItems().add(data.toString());
			});
			
		},data->{
			Platform.runLater(()->{
				
				clists.getItems().clear();
			});
			
		}
		
				
				
				);
		
		
	}
	

	
	
	

	
	
//	//TRYING TO SEND MESSAGES TO THE SERVER
//	public void sendmsg(ActionEvent e) throws IOException
//	{
//		
//		System.out.println("SENDING this"+cc.x);
//		String x=tf.getText();
//		tf.clear();
//		cc.send(x);
//		
//	}

	
	public void stest() 
	{
		
		String str=change.getText();
		if(str.equals(clientsstr)) 
		{
			serverbp.setCenter(clists);
			change.setText("Messages");
			
		}
		
		
		else if(str.equals(messagesstr)) 
		{
			serverbp.setCenter(listItems);
			change.setText("Clients");
			
		}
		
	}
	
	
	
	//EXAMPLE BELOW
//	public void setText(String x){
//	        textfield.setText(x);
//	      
//	    }
//	
//	
//	public void continue_click(ActionEvent e) throws IOException 
//	{
//		
//		
//		
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/final.fxml"));
//		String b="";
//		Parent root3= loader.load();
//	     MyControllerserver x = loader.getController();
//	    x.setText(b);
//		root3.getStylesheets().add("/styles/style1.css");
//		serverbp.getScene().setRoot(root3);
//		
//		
//		
//		
//	}

	
	

	

}
