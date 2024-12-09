package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

// #выделять блоки, 
// #изменять размер и двигать
// 	#вытаскивать элементы из web в java (чтобы можно было редактаровать)
// #применять изменения ChangeElemets и loaderFromJava.js

// сериализовать всё в json, удалять id у элементов, удалить ручки для изменения размера
// генерация в html
// чтение страницы из json
public class Main extends Application{
	 private static String[] args; // Статическое поле для хранения аргументов
	 private static Stage _stage;
	    public static void main(String[] args) {
	        Main.args = args; // Сохраняем аргументы
	        launch(); // Запускаем JavaFX приложение
	    }

    @Override
    public void start(Stage stage) {
    	_stage = stage;
    	stage.setTitle("Site Builder");
    	stage.setWidth(1550);
        stage.setHeight(880);	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/mainScene.fxml"));
		loader.setController(new MainSceneController());
		MainSceneController controller = loader.getController();
		//controller.setHostServices(getHostServices());
    	try {
			Parent root = loader.load();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			e.printStackTrace();
		}	
    	stage.setMaximized(true);
        stage.show();
        CEFWebView.start(args, stage);
    }

}
	





//public class Main extends Application {
//	static String[] args;
//    @Override
//    public void start(Stage stage) {
//        // Главное окно JavaFX
//        stage.setTitle("JavaFX Stage");
//        stage.setWidth(1550);
//        stage.setHeight(880);
//        stage.show();
//
//        // Swing окно
//        JFrame frame = new JFrame();
//        frame.setUndecorated(true);
//        
//        frame.setSize(1100, 700);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setLocation(175, 750);
//     // Делаем окно невидимым в Alt+Tab
//     
//        frame.setType(JFrame.Type.UTILITY); // Это позволяет скрыть окно из списка приложений
//
//     
//        // Привязка JFrame к Stage
//        stage.xProperty().addListener((obs, oldVal, newVal) -> {
//            frame.setLocation(newVal.intValue()+175, frame.getY());
//        });
//        stage.yProperty().addListener((obs, oldVal, newVal) -> {
//            frame.setLocation(frame.getX(), newVal.intValue()-750 + (int) stage.getHeight());
//        });
//        stage.setMaximized(true);
//        
//     // Сворачивание JFrame вместе с окном JavaFX
//        stage.iconifiedProperty().addListener((obs, wasIconified, isNowIconified) -> {
//            if (isNowIconified) {
//                frame.setState(JFrame.ICONIFIED); // Сворачиваем JFrame
//            } else {
//                frame.setState(JFrame.NORMAL); // Разворачиваем JFrame
//            }
//        });
//        // Закрытие JFrame при закрытии Stage
//     // Обеспечиваем, чтобы JFrame всегда был поверх JavaFX окна
//        
//        stage.setOnShown(event -> frame.setVisible(true));
//        stage.setOnHidden(event -> frame.setVisible(false));
//        stage.setOnCloseRequest(event -> frame.dispose());
//        
//        stage.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal) {
//                // Когда JavaFX окно получает фокус, гарантируем, что JFrame остаётся на переднем плане
//                frame.setAlwaysOnTop(true);
//            }
//            else 
//            	frame.setAlwaysOnTop(false);
//        });
//        
//       
//        
//        
//        final String URL = "https://google.com";
//	    final boolean OFFSCREEN = false;
//	    final boolean TRANSPARENT = false;
//	    if (!CefApp.startup(args)) {
//            System.out.println("Ошибка инициализации JCEF!");
//            return;
//        }
//
//        // Настройки для JCEF
//        CefSettings settings = new CefSettings();
//        settings.windowless_rendering_enabled = OFFSCREEN;
//
//        // Инициализация CefApp один раз
//        CefApp cefApp = CefApp.getInstance(settings);
//        CefClient client = cefApp.createClient();
//        client.addMessageRouter(CefMessageRouter.create());
//        
//        // Создание браузера
//        CefBrowser browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
//
//        frame.add(browser.getUIComponent(), BorderLayout.CENTER);
// 
//        frame.setVisible(true);
//
//        
//        
//    }
//
//    public static void main(String[] args) {
//    	Main.args = args;
//        launch(args);
//    }
//}
//
//
//













