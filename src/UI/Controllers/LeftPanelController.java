package UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LeftPanelController {
	
	@FXML private VBox leftContent;
	@FXML private ImageView visibleLeftImg;
	
	@FXML
	    private void initialize() {
		
		leftContent.setVisible(false);
		Image image = new Image("file:src/resources/icons/show.png"); 
		visibleLeftImg.setImage(image);
	 }
	
	@FXML
    private VBox vboxVisibleLeft;
	public void visibleLeft()
	{
		leftContent.setVisible(!leftContent.isVisible());
        // Обновление текста кнопки в зависимости от текущего состояния
        if (leftContent.isVisible()) {
        	Image image = new Image("file:src/resources/icons/hide.png"); 
        	visibleLeftImg.setImage(image);
            vboxVisibleLeft.setMinWidth(225);
            vboxVisibleLeft.setMaxWidth(225);
        } else {
        	Image image = new Image("file:src/resources/icons/show.png"); 
            visibleLeftImg.setImage(image);
            vboxVisibleLeft.setMinWidth(15);
            vboxVisibleLeft.setMaxWidth(15);
        }
	}
}
