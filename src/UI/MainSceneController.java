package UI;


import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class MainSceneController {
	@FXML private HBox header;
	@FXML private HBox toolbar;
	@FXML private VBox left;
	@FXML private VBox right;
   
    
    
    @FXML private Label projectName;
	@FXML private Label projectSaveLabel;
	private StartDialog startDialog;
    
    
    @FXML
    private void initialize() {
    	startDialog = new StartDialog(projectName,projectSaveLabel);
        Platform.runLater(() -> {
        	try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Автоматически созданный блок catch
				e.printStackTrace();
			}
        	startDialog.showMainDialog();
        	
        	
        });
        
    }
    
    // методы menu
	public void create()
	{
		startDialog.showCreateProjectDialog();
	}
	public void save()
	{
		 CEFWebView.ExecuteJS(Tools.SAVE.getPath());
		 projectSaveLabel.setText("сохранен (на момент нажатия 'Сохранить')");
		
	}
	public void load() {
	    startDialog.load();
	}
	public void export() {
		ExportSite.generate();
	}

	
}





