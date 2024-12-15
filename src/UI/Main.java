package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
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

// #ExportSite разбить на функции
// #сделать javafx поверх webview => 1 п. из редизайна
// #webview разбить на функции (мб сделать подклассы для настройки, действий и получения запросов
// #удалить Browser.java, LoadHandler, MainFrame, MyConsoleMessageHandler, Person, Sss
// #Оставить Tools? 
// #в Main убрать args? => убрать из webview
// #в Main вынести настройки stage?
// #перезагружать браузер при запуске с помощью 9 п. из редизайна
// #папку resources привести в порядок
// #вынести большие блоки кода из контроллера => контроллер разделить на классы 
// убрать ошибки

// редизайн javafx:
//   #1.	<center> <StackPane alignment="CENTER"> сделать прозрачным, клики не будут на него попадать?
//   #1.1	стили для строки состояния
// 	 #2.	вместо кнопок иконки, панели одного цвета, мб разделить границы
//   #2.1	переименовать кнопки? добавить в меню
// 	 #3. прятать боковые панели
// 	 #4.	в свойство элемента (справа) указать тег, а не id
//	 #5.	аккардион - хуйня
//   #6. по клику на элемент рамка остается, #появляется правая панель
//   #7. вместо блока c кнопками оформить toolbar 
//   #8. строка состояние отлеживает проект(DAL)?
//   #9. добавить выбор нового или существующего проекта при запуске
//   #9.1	проект при создании называть
//  #10.	что пихать в строку состояния? счетчики элементов? текущий проект, сохранен ли
//  #11. чтобы запускать во весь экран обязательно ли указывать размер экрана? нет, но чтобы отображался JFrame - да
//  #12. выделять контейнер или хуй забить?
//  #13. написать название окна

// в общем, если где-то написан комментарий, то запихнуть в метод и в названии описать комментарий
// убрать лишние выводы в консоль
// разделить на 3 слоя: UI, Browser, DAL
// DAL должен следить за текущим проектом
// ну и по идее контроллер связывает все 3 слоя

// 

   
public class Main extends Application{
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
    	StageSingleton.setStage(stage);
    	stage.setWidth(1550); // размер экрана по умолчанию
        stage.setHeight(880);
        stage.initStyle(StageStyle.TRANSPARENT); // Полностью прозрачное окно
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/mainScene.fxml"));
    	MainSceneController controller = new MainSceneController();
    	loader.setController(controller);
    	try {
			Parent root = loader.load();
	        Scene scene = new Scene(root, Color.TRANSPARENT);
	        stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	stage.setMaximized(true);
    	
    	//stage.setOnShown(event -> controller.postInitialize());
        stage.show();
        CEFWebView.start(stage);
        
        
    }
}
	












