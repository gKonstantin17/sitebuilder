package UI;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;



import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application{
    
    public static void main(String[] args) {

        launch();
    }
     

    @Override
    public void start(Stage stage) {
    
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/mainScene.fxml"));
//		loader.setController(new MainSceneController());
//		
//		MainSceneController controller = loader.getController();
//		controller.setHostServices(getHostServices());
		
    	
    	try {
			Parent root = loader.load();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
        stage.show();
    	
    	
    	
    }
    
    
    

}
