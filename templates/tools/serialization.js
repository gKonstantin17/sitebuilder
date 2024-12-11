(function serializeBlocks() {
    // Находим все div с уникальными ID, созданные ранее
    const divs = document.querySelectorAll('.container > div[id]');

    // Создаем массив для хранения данных блоков
    const serializedBlocks = [];

    divs.forEach(div => {
        const { id, style, textContent, tagName, classList } = div;

        // Собираем данные о блоке
        serializedBlocks.push({
            id,
            tag: tagName.toLowerCase(), // Название тега в нижнем регистре
            classes: Array.from(classList), // Преобразуем DOMTokenList в массив
            content: textContent.trim(),
            styles: {
                width: style.width,
                height: style.height,
                top: style.top,
                left: style.left,
                backgroundColor: style.backgroundColor,
                position: style.position
            }
        });
    });

    // Преобразуем массив объектов в JSON-строку
    const serializedJSON = JSON.stringify(serializedBlocks, null, 2);

    // Выводим результат в консоль
    console.log(serializedJSON);

    // Отправляем данные через window.cefQuery
    if (window.cefQuery) {
        window.cefQuery({
            request: "serMessage: " + JSON.stringify(serializedBlocks),
            onSuccess: function(response) {
            },
            onFailure: function(error_code, error_message) {
                console.error("Ошибка отправки в Java:", error_message);
            }
        });
    } else {
        console.log("window.cefQuery не доступен. JSON данные:", serializedJSON);
    }
})();
