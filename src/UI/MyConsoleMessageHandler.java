package UI;

import org.cef.browser.CefBrowser;

public class MyConsoleMessageHandler {

    
    public boolean onConsoleMessage(CefBrowser browser, String message, String source, int line) {
        // Проверяем, если это именно наше сообщение "Ku-ku"
        if ("Ku-ku".equals(message)) {
            System.out.println("Перехвачено сообщение: " + message);
            // Можете тут выполнить дополнительные действия
        }
        return false; // Возвращаем false, чтобы сообщения продолжали выводиться в консоль
    }
}
