package UI;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefCommandLine;
import org.cef.callback.CefSchemeRegistrar;
import org.cef.handler.CefAppHandler;

import javafx.stage.Stage;


public class CEFWebView extends JFrame{
	private static final long serialVersionUID = 1L;
	
	static String[] _args;
	static Stage _stage;
	static JFrame frame;
	static CefBrowser browser;
	static CefClient client;
	//static String URL = "https://google.com";
	static String URL = "file:///C:/Users/kocm8/eclipse-workspace/Prototype/templates/newproject/index.html";
	static final boolean OFFSCREEN = false;
	static final boolean TRANSPARENT = false;
	
	
    public static void start(String[] args, Stage stage) {
    	_args = args;
		_stage = stage;
        Web();
        View();
    }
    public static void startOver()
    {
    	if (_args == null || _stage==null)
    		throw new IllegalStateException("This first start!, need to call start(args,stage)");
    	browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
        View();
    }
	
	private static void Web() {  
	    if (!CefApp.startup(_args)) {
            System.out.println("Ошибка инициализации JCEF!");
            return;
        }

        // Настройки для JCEF
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;

        // Инициализация CefApp один раз
        CefApp cefApp = CefApp.getInstance(settings);
        client = cefApp.createClient();
        client.addMessageRouter(CefMessageRouter.create());

        // Создание браузера
        browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT); 
	}
	private static void View() {
	
      frame = new JFrame();
      frame.setUndecorated(true);
      frame.setSize(1104, 735);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setLocation(230, 745);
      frame.setLocation(226,100);
      frame.setType(JFrame.Type.UTILITY); // Это позволяет скрыть окно из списка приложений

      
      // Привязка JFrame к Stage
      _stage.xProperty().addListener((obs, oldVal, newVal) -> {
          frame.setLocation(newVal.intValue()+229, frame.getY());
      });
      _stage.yProperty().addListener((obs, oldVal, newVal) -> {
          frame.setLocation(frame.getX(), newVal.intValue()-773 + (int) _stage.getHeight());
      });
      
      
   // Сворачивание JFrame вместе с окном JavaFX
      _stage.iconifiedProperty().addListener((obs, wasIconified, isNowIconified) -> {
          if (isNowIconified) {
              frame.setState(JFrame.ICONIFIED); // Сворачиваем JFrame
          } else {
              frame.setState(JFrame.NORMAL); // Разворачиваем JFrame
          }
      });
      // Закрытие JFrame при закрытии Stage
   // Обеспечиваем, чтобы JFrame всегда был поверх JavaFX окна
      
      _stage.setOnShown(event -> frame.setVisible(true));
      _stage.setOnHidden(event -> frame.setVisible(false));
      _stage.setOnCloseRequest(event -> frame.dispose());
      
      
      
      _stage.focusedProperty().addListener((obs, oldVal, newVal) -> {
          if (newVal) {
              // Когда JavaFX окно получает фокус, гарантируем, что JFrame остаётся на переднем плане
              frame.setAlwaysOnTop(true);
              _stage.toFront();
        	  
          }
          else 
          	frame.setAlwaysOnTop(false);
      });
      
      frame.add(browser.getUIComponent(), BorderLayout.CENTER);
      frame.setVisible(true);
      
      
	}
	public static void RestartBrowser() {
		frame.dispose();
		startOver();


	}
	public static void Reload() {
		browser.reload();
	}
	public static void LoadURL() {
		browser.loadURL(URL);
	}
	public static String ReadJS(String filePath) {
		String fileURL = "file:///" + filePath;
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void ExecuteJS(String filePath)
	{
		String jsCode = ReadJS(filePath);
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}
	
}