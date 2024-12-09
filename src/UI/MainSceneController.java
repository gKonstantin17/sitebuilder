package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefLoadHandler;
import org.cef.network.CefRequest.TransitionType;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class MainSceneController {
	
	
	@FXML
	private Button pageReloader;
	public void pageReload()
	{
		CEFWebView.Reload();
	}
	@FXML
	private Button pageLoader;
	public void pageLoad()
	{
        CEFWebView.LoadURL();
    }
	@FXML
	private Button browserReloader;
	public void browserReload()
	{
		CEFWebView.RestartBrowser();
	}
	
	@FXML
	private Button createrDiv;
	public void createDiv()
	{
        CEFWebView.ExecuteJS(Tools.CREATE_DIV.getPath());
    }
	
	@FXML
	private Label idSelected;
	@FXML
	private TextArea areaProperty;
	@FXML
	private TextField fontSizeProperty;
	@FXML
	private ColorPicker colorTextProperty;
	@FXML
	private ColorPicker colorBackgroundProperty;
	
	@FXML
	private Button changer;
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





