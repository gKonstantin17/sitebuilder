package UI.Controllers;

import UI.CEFWebView;
import UI.Tools;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ToolbarController {
 	@FXML private ImageView createDivImg;
    @FXML private ImageView pageReloadImg;
    @FXML private ImageView pageLoadImg;
    @FXML private ImageView browserReloadImg;
    
    @FXML
    private void initialize() 
    {
    	 Image image = new Image("file:src/resources/icons/tools/block_icon.png"); 
         createDivImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/clear_icon.png"); 
         pageReloadImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/load_icon.png"); 
         pageLoadImg.setImage(image);
         image = new Image("file:src/resources/icons/tools/reload_icon.png"); 
         browserReloadImg.setImage(image);
    }
    
    public void createDiv()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_DIV.getPath());
    }
    
    
    public void pageReload()
	{
		CEFWebView.Reload();
	}

	public void pageLoad()
	{
        CEFWebView.LoadURL();
    }
	
	public void browserLoad()
	{
		browserReload();
	}

	public static void browserReload()
	{
		CEFWebView.RestartBrowser();
	}
	
}
