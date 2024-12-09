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
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefMessageRouter.CefMessageRouterConfig;
import org.cef.callback.CefCommandLine;
import org.cef.callback.CefQueryCallback;
import org.cef.callback.CefSchemeRegistrar;
import org.cef.handler.CefAppHandler;
import org.cef.handler.CefMessageRouterHandler;
import org.cef.handler.CefMessageRouterHandlerAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import UI.MainSceneController;

public class CEFWebView extends JFrame {
	private static final long serialVersionUID = 1L;

	static String[] _args;
	static Stage _stage;
	static JFrame frame;
	static CefBrowser browser;
	static CefClient client;
	// static String URL = "https://google.com";
	static String URL = "file:///C:/Users/kocm8/eclipse-workspace/Prototype/templates/newproject/index.html";
	static final boolean OFFSCREEN = false;
	static final boolean TRANSPARENT = false;

	public static void start(String[] args, Stage stage) {
		_args = args;
		_stage = stage;
		Web();
		View();
	}

	public static void startOver() {
		if (_args == null || _stage == null)
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

		// #
		// ПОПЫТКА СДЕЛАТЬ РОУТЕР
		// #
//        CefMessageRouter.CefMessageRouterConfig config = new CefMessageRouter.CefMessageRouterConfig(
//        	    "sendMessageToJavaFunction", // Имя функции для отправки сообщений
//        	    "cancelMessageToJavaFunction" // Имя функции для отмены сообщений (опционально)
//        	);
//
//        	CefMessageRouter router = CefMessageRouter.create(config);
//
//        	// Добавляем обработчик
//        	router.addHandler(new CefMessageRouterHandlerAdapter() {
//        	    @Override
//        	    public boolean onQuery(
//        	            CefBrowser browser,
//        	            CefFrame frame,
//        	            long queryId,
//        	            String request,
//        	            boolean persistent,
//        	            CefQueryCallback callback) {
//
//        	        // Парсим запрос из JavaScript
//        	        try {
//        	            JSONObject jsonRequest = new JSONObject(request);
//        	            String action = jsonRequest.getString("action");
//
//        	            // Обработка конкретного действия
//        	            if ("getData".equals(action)) {
//        	                // Выполнение логики
//        	                JSONObject response = new JSONObject();
//        	                response.put("message", "Hello from Java!");
//        	                response.put("id", jsonRequest.getJSONObject("params").getInt("id"));
//
//        	                // Отправляем успешный результат обратно
//        	                callback.success(response.toString());
//        	                return true;
//        	            } else {
//        	                // Неизвестное действие
//        	                callback.failure(400, "Unknown action");
//        	                return false;
//        	            }
//        	        } catch (Exception e) {
//        	            // Обработка ошибок парсинга или логики
//        	            callback.failure(500, "Internal Server Error: " + e.getMessage());
//        	            return false;
//        	        }
//        	    }
//        	}, true); // passThrough = true, чтобы запросы могли быть переданы следующему обработчику, если текущий не справился.
		// БЫЛО РАНЬШЕ

		// client.addMessageRouter(CefMessageRouter.create());

		CefMessageRouter messageRouter = CefMessageRouter.create();
		client.addMessageRouter(messageRouter);

		messageRouter.addHandler(new CefMessageRouterHandler() {

			@Override
			public long getNativeRef(String arg0) {
				// TODO Автоматически созданная заглушка метода
				return 0;
			}

			@Override
			public void setNativeRef(String arg0, long arg1) {
				// TODO Автоматически созданная заглушка метода

			}

			@Override
			public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent,
					CefQueryCallback callback) {
				// Проверяем запрос от JavaScript
				if (request.startsWith("logMessage:")) {
					// Извлекаем сообщение из запроса
					String message = request.substring("logMessage:".length());
					// System.out.println("Перехвачено сообщение от JavaScript: " + message);

					JSONObject json = new JSONObject(message);
					System.out.println(json);
					String id = json.getString("id");
					String tagName = json.getString("tagName");
					String text = json.getString("text");
					JSONObject styles = json.getJSONObject("styles");

					// Выводим информацию в консоль
//                        System.out.println("id " + id);
//                        System.out.println("Тег: " + tagName);
//                        System.out.println("Текст: " + text);
//                        System.out.println("Стили: " + styles.toString(2)); // 2 - отступ для форматирования

					String fontSize = styles.optString("font-size", "14px");
					String colorText = styles.optString("color", "#000000");
					String backgroundColor = styles.optString("background-color", "#FFFFFF");

					Scene scene = _stage.getScene();
					Platform.runLater(() -> {
						// попытка передавать по fx:id, но fxml элементы null
//                        	MainSceneController msc = new MainSceneController();
//							msc.idSelected.setText(id);
//							msc.areaProperty.setText(text);
//							msc.fontSizeProperty.setText(fontSize);
//							msc.colorTextProperty.setValue(Color.web(colorText));
//							msc.colorBackgroundProperty.setValue(Color.web(backgroundColor));

						// передача в fxml элементы по styleClass
						Label label = (Label) scene.lookup(".id-selected");
						label.setText(id);
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
					});
					// Отправляем успешный ответ
					callback.success(""); // Пустой ответ, можно добавить данные, если нужно
					return true; // Сообщаем, что запрос обработан
				}

				// Если это не наш запрос, возвращаем false
				return false;
			}

			@Override
			public void onQueryCanceled(CefBrowser arg0, CefFrame arg1, long arg2) {
				// TODO Автоматически созданная заглушка метода

			}

		}, false);
		// Создание браузера
		browser = client.createBrowser(URL, OFFSCREEN, TRANSPARENT);

	}

	private static void View() {

		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setSize(1104, 735);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(230, 745);
		frame.setLocation(226, 100);
		frame.setType(JFrame.Type.UTILITY); // Это позволяет скрыть окно из списка приложений

		// Привязка JFrame к Stage
		_stage.xProperty().addListener((obs, oldVal, newVal) -> {
			frame.setLocation(newVal.intValue() + 229, frame.getY());
		});
		_stage.yProperty().addListener((obs, oldVal, newVal) -> {
			frame.setLocation(frame.getX(), newVal.intValue() - 773 + (int) _stage.getHeight());
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
				// Когда JavaFX окно получает фокус, гарантируем, что JFrame остаётся на
				// переднем плане
				frame.setAlwaysOnTop(true);
				_stage.toFront();

			} else
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

	public static void ExecuteJS(String filePath) {
		String jsCode = ReadJS(filePath);
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}

	public static void ChangeElemets(String id, String request) {
		String jsCode = "loaderFromJava(\"" +id + "\"," + request + ");";
		System.out.println(jsCode);
		browser.executeJavaScript(jsCode, browser.getURL(), 0);
	}

}