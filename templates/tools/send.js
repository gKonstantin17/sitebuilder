(function() {
    var originalLog = console.log;
    console.log = function(message) {
        // Отправляем сообщение в Java через CEF, но без повторного вызова console.log
        if (window.cefQuery) {
            window.cefQuery({
                request: "logMessage: " + message,
                onSuccess: function(response) {
                    // Успешный ответ, но не вызываем console.log снова!
                    // Выводим информацию о том, что сообщение отправлено в Java
                }
            });
        }
        // Оставляем вывод только один раз в консоль
        originalLog.apply(console, arguments);
    };

    console.log("Ku-ku");
})();