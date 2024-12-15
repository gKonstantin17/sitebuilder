package UI.Controllers;

import UI.CEFWebView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RightPanelController {
	@FXML private ImageView visibleRightImg;
	@FXML  private VBox rightContent;
	@FXML private VBox vboxVisibleRight;
	
	
	@FXML private Label idSelected;
	@FXML private Label tagSelected;
	@FXML private TextArea areaProperty;
	@FXML private TextField fontSizeProperty;
	@FXML private ColorPicker colorTextProperty;
	@FXML private ColorPicker colorBackgroundProperty;
	@FXML private Button changer;

    @FXML
    private void initialize() {
    	rightContent.setVisible(false);
        Image image = new Image("file:src/resources/icons/show.png"); 
        visibleRightImg.setImage(image);
        
     // не показывать id
        idSelected.setVisible(false);
    }
    

	public void visibleRight()
	{
		rightContent.setVisible(!rightContent.isVisible());
        // Обновление текста кнопки в зависимости от текущего состояния
        if (rightContent.isVisible()) {
        	Image image = new Image("file:src/resources/icons/hide.png"); 
        	visibleRightImg.setImage(image);
            vboxVisibleRight.setMinWidth(225);
            vboxVisibleRight.setMaxWidth(225);
        } else {
        	Image image = new Image("file:src/resources/icons/show.png"); 
        	visibleRightImg.setImage(image);
            vboxVisibleRight.setMinWidth(15);
            vboxVisibleRight.setMaxWidth(15);
        }
	}
	public void visibleTrueRight()
	{
		rightContent.setVisible(true);
    	Image image = new Image("file:src/resources/icons/hide.png"); 
    	visibleRightImg.setImage(image);
        vboxVisibleRight.setMinWidth(225);
        vboxVisibleRight.setMaxWidth(225);
        
	}
	
	
	public void change()
	{
		String updatedText = areaProperty.getText();
		System.out.println(updatedText);
	    String fontSize = fontSizeProperty.getText();
	    String textColor = toRgbString((colorTextProperty.getValue()));
	    String backgroundColor = toRgbString(colorBackgroundProperty.getValue());
	    String id = idSelected.getText();
	    // Формируем строку JSON
	    String request = String.format(
	        "{\"text\":\"%s\",\"fontSize\":\"%s\",\"color\":\"%s\",\"backgroundColor\":\"%s\"}",
	         updatedText, fontSize, textColor, backgroundColor);
	    
	    // Отправляем данные через CEF
	    //String request = "updateElement:" + jsonData;
	    System.out.println(request);
        CEFWebView.ChangeElemets(id,request);
    
	}
	public String toRgbString(Color color) {
	    int red = (int)(color.getRed() * 255);
	    int green = (int)(color.getGreen() * 255);
	    int blue = (int)(color.getBlue() * 255);
	    return String.format("rgb(%d, %d, %d)", red, green, blue);
	}
	
}
