package UI;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BrowserWeb {
	static final boolean OFFSCREEN = false;
	static final boolean TRANSPARENT = false;
	static CefBrowser browser;
	static CefClient client;
	static CefSettings settings;
	static CefMessageRouter messageRouter;
	
	public static void createBrowser(String URL,Stage _stage)
	{
		setSetting();
        initClient();
        initMessageRouter();
        configureMessageRouter(_stage); // нужно получить stage
        runBrowser(URL);
	}
	
	private static void setSetting()
	{
		settings = new CefSettings();
		settings.windowless_rendering_enabled = OFFSCREEN;
	}
	
	private static void initClient()
	{
		CefApp cefApp = CefApp.getInstance(settings);
		client = cefApp.createClient();
	}
	
	private static void initMessageRouter()
	{
		messageRouter = CefMessageRouter.create();
		client.addMessageRouter(messageRouter);
	}
	
	public static void runBrowser(String URL)
	{
		browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);
	}
	
	public static CefBrowser getBrowser()
	{
		return browser;
	}
	private static void configureMessageRouter(Stage _stage) // нужно получить stage
	{
		messageRouter.addHandler(new CefMessageRouterHandler() {

			@Override
			public long getNativeRef(String arg0) {
				return 0;
			}
			@Override
			public void setNativeRef(String arg0, long arg1) {
			}

			@Override
			public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent,
					CefQueryCallback callback) {
				// Проверяем запрос от JavaScript
				if (request.startsWith("logMessage:")) {
					getWebElement(request,callback, _stage);
					return true; 
				}
				if (request.startsWith("serMessage:")) {
					getSerializatedElements(request, callback);
					return true; 
				}
				
				// Если это не наш запрос, возвращаем false
				return false;
			}

			@Override
			public void onQueryCanceled(CefBrowser arg0, CefFrame arg1, long arg2) {
			}
		}, false);
	}
	
	private static void getWebElement(String request, CefQueryCallback callback,Stage _stage)
	{
		
			// Извлекаем сообщение из запроса
			String message = request.substring("logMessage:".length());

			JSONObject json = new JSONObject(message);
			System.out.println(json);
			String id = json.getString("id");
			String tagName = json.getString("tagName");
			String text = json.getString("text");
			JSONObject styles = json.getJSONObject("styles");


			String fontSize = styles.optString("font-size", "16px");
			String colorText = styles.optString("color", "#000000");
			String backgroundColor = styles.optString("background-color", "#FFFFFF");

			Scene scene = _stage.getScene();
			Platform.runLater(() -> {
				// попытка передавать по fx:id, но fxml элементы is null
//                	MainSceneController msc = new MainSceneController();
//					msc.idSelected.setText(id);
//					msc.areaProperty.setText(text);
//					msc.fontSizeProperty.setText(fontSize);
//					msc.colorTextProperty.setValue(Color.web(colorText));
//					msc.colorBackgroundProperty.setValue(Color.web(backgroundColor));

				// передача в fxml элементы по styleClass
				Label labelId = (Label) scene.lookup(".id-selected");
				labelId.setText(id);
				Label labelTag = (Label) scene.lookup(".tag-selected");
				if (id != "")
				{
					labelTag.setText(tagName);
				} else {
					labelTag.setText("не выбран");
				}
				// Устанавливаем текст в TextArea
				TextArea textArea = (TextArea) scene.lookup(".area-property");
				textArea.setText(text);

				// Устанавливаем размер шрифта в TextField
				TextField textField = (TextField) scene.lookup(".font-size-property");
				textField.setText(fontSize);

				// Устанавливаем цвет текста в ColorPicker
				ColorPicker textColorPicker = (ColorPicker) scene.lookup(".color-text-property");
				textColorPicker.setValue(Color.web(colorText));

				// Устанавливаем цвет фона в ColorPicker
				ColorPicker backgroundColorPicker = (ColorPicker) scene.lookup(".color-background-property");
				backgroundColorPicker.setValue(Color.web(backgroundColor));
				
				
				// Показываем правую часть окна
				if (id != "")
				{
					VBox rightContent = (VBox) scene.lookup(".vbox-right-content");
					rightContent.setVisible(true);
					
					VBox vboxVisibleRight = (VBox) scene.lookup(".visible-right");
					ImageView visibleRightImg = (ImageView) scene.lookup(".visible-right-img");
					Image image = new Image("file:src/resources/icons/hide.png"); 
			    	visibleRightImg.setImage(image);
			        vboxVisibleRight.setMinWidth(225);
			        vboxVisibleRight.setMaxWidth(225);
				}
				
				
			});
			// Отправляем успешный ответ
			callback.success(""); // Пустой ответ, можно добавить данные, если нужно
			
			
			
	}
	private static void getSerializatedElements(String request,CefQueryCallback callback)
	{
		// Извлекаем сообщение из запроса
		String message = request.substring("serMessage:".length());
		// System.out.println("Перехвачено сообщение от JavaScript: " + message);

		JSONArray json = new JSONArray(message);
		System.out.println(json);
		// Генерация имени файла с текущей датой и временем
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        //String fileName = "saves/output_" + formatter.format(new Date()) + ".json";
        String fileName = "saves/" + ProjectManager.getJsonFile();
        // Запись в файл
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json.toString(4)); // С отступами для удобочитаемости
            System.out.println("JSON был успешно записан в файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
		

		// Отправляем успешный ответ
		callback.success(""); // Пустой ответ, можно добавить данные, если нужно
	}
}
