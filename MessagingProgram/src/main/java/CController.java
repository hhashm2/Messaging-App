

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;





public class CController implements Initializable {
	
	 
	private static Stage p;
	public Client cc;
	
	@FXML
	private BorderPane clientbp;
	
	@FXML
	private VBox vbox;
	
	@FXML
	private Button change;
	
	@FXML
	private TextField tf;
	
	@FXML
	private TextField sendto;
	
	@FXML
	private static Label label1;
	@FXML
	 ListView<String>  clists=new ListView<String>();
	
	@FXML
	 ListView<String> listItems2=new ListView<String>();
	
	
	private String clientsstr="Show Clients";
	private String messagesstr="Show Messages";
	int counter=0;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		clients();
		String x=p.getTitle().toString();
		System.out.println(x);
		tf.setPromptText("Please enter the name of the client here and press send");
		clists.setBlendMode(BlendMode.MULTIPLY);
		clists.setPrefSize(600, 211);
		clists.setMaxSize(600, 211);
		//sendto.setPromptText("Please don't enter anything here yet!");
	}
	
	
	
	
	//USED TO NAME THE TTILE
	public static void getstage(Stage ps) 
	{
		p=ps;
		
	}
	
	
	
	
	public void clients() 
	{
		cc = new Client(data->{
		Platform.runLater(()->{listItems2.getItems().add(data.toString());
		});
		},data->{
			Platform.runLater(()->{clists.getItems().add(data.toString());
			});
			},data->{
				Platform.runLater(()->{clists.getItems().clear();
				});
				});
		
		cc.start();
	}
	
	public void clientsbutton()
	{
		String str=change.getText();
		if(str.equals(clientsstr)) 
		{
			vbox.getChildren().remove(0);
			vbox.getChildren().add(0, clists);
			change.setText("Show Messages");
			
		}
		
		
		else if(str.equals(messagesstr)) 
		{
			vbox.getChildren().remove(0);
			vbox.getChildren().add(0, listItems2);
			change.setText("Show Clients");
			
		}
		
		
	}

	
	//TRYING TO SEND MESSAGES TO THE SERVER
	public void sendmsg(ActionEvent e) throws IOException
	{
		String x=tf.getText();
		
		if(x.equals("")) 
		{
			
			
		}
		else {
		tf.clear();
		//sendto.clear();
		if(counter==0)
		{
			
			p.setTitle(x);
			tf.setPromptText("\"Hassan:Hello Hassan\" or Hassan,Ali:Hello Hassan and Ali or put all to send to all");
			sendto.setPromptText("Please put the msg after a : and put the names before the colon e.g below. Pls dont forget :");
			cc.send(x);
			counter++;
		}
		else 
		{
		cc.send(x);
		}
		}
	}



	

	

}
