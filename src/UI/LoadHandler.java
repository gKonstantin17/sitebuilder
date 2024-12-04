package UI;

import org.cef.browser.CefBrowser;
import org.cef.handler.CefLoadHandlerAdapter;

class LoadHandler extends CefLoadHandlerAdapter {
//    public void onLoadEnd(CefBrowser browser, int frameId, int status) {
//        // Выполнение JavaScript для получения данных элемента
//        String jscode = 
//            "(function() {" +
//            "    var element = document.querySelector('.example');" +
//            "    if (element) {" +
//            "        var styles = window.getComputedStyle(element);" +
//            "        var result = JSON.stringify({" +
//            "            tagName: element.tagName," +
//            "            textContent: element.textContent," +
//            "            styles: {" +
//            "                backgroundColor: styles.backgroundColor," +
//            "                padding: styles.padding," +
//            "                margin: styles.margin" +
//            "            }" +
//            "        });" +
//            "        cefQueryCallback(result); // Отправляем результат" +
//            "    } else {" +
//            "        cefQueryCallback(JSON.stringify({ error: 'Element not found' }));" +
//            "    }" +
//            "})();";
//        browser.executeJavaScript(jscode, browser.getURL(), 0);
//    }
}

