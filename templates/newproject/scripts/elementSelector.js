function handleElementSelector(event) {
    const element = event.target;
    const tagName = element.tagName;
    const textContent = element.textContent.trim();
    const styles = window.getComputedStyle(element);

    // Получаем только стили без префиксов
    const styleList = {};
    for (let i = 0; i < styles.length; i++) {
        const styleName = styles[i];
        if (!styleName.startsWith('-')) { // Исключаем свойства с префиксом
            styleList[styleName] = styles.getPropertyValue(styleName);
        }
    }

    // Вывод информации в консоль
    console.log('Название тега:', tagName);
    console.log('Текст:', textContent || '(пусто)');
    console.log('Стили:', styleList);


    const infoElement = document.querySelector('.info');
    infoElement.innerHTML = `
            Тег: ${tagName} <br>
            Текст:${textContent || '(пусто)'}`;
}

// Инициализация события клика
function initElementSelector() {
    document.addEventListener('click', handleElementSelector);
}