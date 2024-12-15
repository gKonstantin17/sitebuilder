let previouslySelectedElement = null; // Хранит ранее выбранный элемент

function handleElementSelector(event) {
    const element = event.target;
    const id = element.id;
    const tagName = element.tagName;
    const textContent = element.textContent.trim();
    // const styles = window.getComputedStyle(element);
    

    // // Получаем только стили без префиксов
    // const styleList = {};
    // for (let i = 0; i < styles.length; i++) {
    //     const styleName = styles[i];
    //     if (!styleName.startsWith('-')) { // Исключаем свойства с префиксом
    //         styleList[styleName] = styles.getPropertyValue(styleName);
    //     }
    // }
    const cssText = element.style.cssText;
    const styleList = {};
    if (cssText) {
        // Разбиваем cssText на отдельные пары "ключ: значение"
        const styles = cssText.split(';').map(style => style.trim()).filter(style => style);
    
        // Парсим каждую пару и добавляем в объект styleList
        styles.forEach(style => {
            const [key, value] = style.split(':').map(part => part.trim());
            if (key && value) {
                styleList[key] = value;
            }
        });
    }
    if (previouslySelectedElement) {
        previouslySelectedElement.style.outline = ''; // Сбрасываем стиль рамки
    }
    element.style.outline = '2px solid #007BFF';
    previouslySelectedElement = element;

    // Создаем объект с информацией об элементе
    const elementInfo = {
        id: id,
        tagName: tagName,
        text: textContent || "(пусто)",
        styles: styleList
    };

    // Отправляем JSON-объект в Java через CEF
    if (window.cefQuery) {
        window.cefQuery({
            request: "logMessage: " + JSON.stringify(elementInfo),
            onSuccess: function(response) {
                // Успешная отправка
            },
            onFailure: function(error_code, error_message) {
                console.error("Ошибка отправки в Java:", error_message);
            }
        });
    }
}

// Инициализация события клика
function initElementSelector() {
    document.addEventListener('click', handleElementSelector);
}
