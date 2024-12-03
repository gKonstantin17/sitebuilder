package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.VBox;

public class Browser {
	private static final String URL = "https://google.com";
    private static final boolean OFFSCREEN = false;
    private static final boolean TRANSPARENT = false;
    	
    public static void main(String[] args) {
        if (!CefApp.startup(args)) {
            System.out.println("Startup initialization failed!");
            return;
        }			

        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = OFFSCREEN;
        CefApp cefApp = CefApp.getInstance(settings);
        CefClient client = cefApp.createClient();
        client.addMessageRouter(CefMessageRouter.create());
        CefBrowser browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);

       // JTextField address = new JTextField(URL);
        //address.addActionListener(e -> browser.loadURL(address.getText()));

        JFrame frame = new JFrame("JCEF");
       
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMinimumSize(new Dimension(1280, 900));
        panel.add(browser.getUIComponent(), BorderLayout.CENTER);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
//        CefSettings settings = new CefSettings();
//        settings.windowless_rendering_enabled = false; // Для отображения окна, можно включить или выключить
//
//        // Инициализируем CEF
//        CefApp cefApp = CefApp.getInstance(args, settings);
//
//        // Создаем клиента
//        CefClient client = cefApp.createClient();
//
//        // Создаем браузер с указанием URL (можно использовать локальный файл или URL)
//        CefBrowser browser = client.createBrowser("https://www.example.com", false, false);
//
//        // Запускаем приложение
//        System.out.println();
    }

}

//public class Browser {
//
//    private CefBrowser cefBrowser;
//    private SwingNode swingNode;
//
//    public Browser(VBox browserFrame) {
//    	browserFrame.setMinHeight(600);
//    	browserFrame.setMinWidth(800);
//    	browserFrame.setStyle("-fx-background-color: #33993a;");
//        // Create the SwingNode for embedding JCEF inside JavaFX VBox
//        swingNode = new SwingNode();
//        browserFrame.getChildren().add(swingNode);
//
//        // Initialize the JCEF browser in the SwingNode
//        SwingUtilities.invokeLater(() -> {
//            // Create and initialize JCEF
//            CefApp cefApp = CefApp.getInstance();
//            CefClient cefClient = cefApp.createClient();
//            cefBrowser = cefClient.createBrowser("https://www.google.com", false, false);
//            
//            // Create a JPanel to hold the JCEF browser
//            JPanel panel = new JPanel();
//            panel.add(cefBrowser.getUIComponent());
//
//            // Set the Swing panel to the SwingNode
//            swingNode.setContent(panel);
//        });
//    }
//
//    public CefBrowser getCefBrowser() {
//        return cefBrowser;
//    }
//}
