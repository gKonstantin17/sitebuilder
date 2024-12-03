package UI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import javafx.stage.Stage;


public class CEFWebView extends JFrame{
	private static final long serialVersionUID = 1L;
	
	String[] _args;
	Stage _stage;
	JFrame frame;
	CefBrowser browser;
	String URL = "https://google.com";
	final boolean OFFSCREEN = false;
    final boolean TRANSPARENT = false;
	
	
    public void start(String[] args, Stage stage) {
    	_args = args;
		_stage = stage;
        Web();
        View();
    }
	
	private void Web() {  
	    if (!CefApp.startup(_args)) {
            System.out.println("Ошибка инициализации JCEF!");
            return;
        }

        // Настройки для JCEF
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;

        // Инициализация CefApp один раз
        CefApp cefApp = CefApp.getInstance(settings);
        CefClient client = cefApp.createClient();
        client.addMessageRouter(CefMessageRouter.create());
        
        // Создание браузера
        this.browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT); 
	}
	private void View() {
	
      this.frame = new JFrame();
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
          }
          else 
          	frame.setAlwaysOnTop(false);
      });
      frame.add(browser.getUIComponent(), BorderLayout.CENTER);
      frame.setVisible(true);
      
	}
}