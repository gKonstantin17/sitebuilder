function loaderElements(jsonArray) {
    var container = document.querySelector('.container'); // Находим контейнер

    jsonArray.forEach(item => {
        // Создаем элемент указанного тега (по умолчанию div)
        const elementTag = item.tag || 'div'; // Если тег не указан, используем div
        const element = document.createElement(elementTag);
        
        // Устанавливаем ID
        if (item.id) {
            element.id = item.id;
        }

        // Применяем стили из JSON
        if (item.styles) {
            Object.keys(item.styles).forEach(style => {
                element.style[style] = item.styles[style];
            });
        }

        // Добавляем контент
        if (item.content) {
            element.textContent = item.content;
        }

        // Добавляем указанные классы
        if (item.classes && Array.isArray(item.classes)) {
            item.classes.forEach(className => {
                element.classList.add(className);
            });
        }

        // Добавляем "ручки" для изменения размера
        const resizers = [
            'top', 'bottom', 'left', 'right', // Стороны
            'top-left', 'top-right', 'bottom-left', 'bottom-right' // Углы
        ];

        resizers.forEach(resizer => {
            const resizerDiv = document.createElement('div');
            resizerDiv.classList.add('resizer', resizer);
            if (item.id) {
                resizerDiv.dataset.id = item.id; // Привязываем ID к каждой "ручке"
            }
            element.appendChild(resizerDiv);
        });

        // Добавляем элемент в контейнер
        container.appendChild(element);
    });
}
