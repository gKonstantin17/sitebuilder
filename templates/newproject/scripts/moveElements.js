(function() {
    // Функция для перемещения элементов
    function moveElements(containerSelector) {
        const container = document.querySelector(containerSelector);
        if (!container) {
            console.error(`Container with selector "${containerSelector}" not found!`);
            return;
        }

        let isDragging = false;
        let offsetX, offsetY, currentElement;

        const onMouseMove = (e) => {
            if (!isDragging) return;

            // Вычисляем новое положение элемента
            currentElement.style.left = `${e.clientX - offsetX}px`;
            currentElement.style.top = `${e.clientY - offsetY}px`;
        };

        const onMouseUp = () => {
            // Завершаем перетаскивание
            isDragging = false;
            window.removeEventListener('mousemove', onMouseMove);
            window.removeEventListener('mouseup', onMouseUp);
        };

        const onMouseDown = (e) => {
            // Предотвращаем выделение текста
            e.preventDefault();

            // Проверяем, что клик был по элементу, который должен двигаться
            currentElement = e.target;

            // Сохраняем начальное положение мыши относительно элемента
            offsetX = e.clientX - currentElement.getBoundingClientRect().left;
            offsetY = e.clientY - currentElement.getBoundingClientRect().top;

            // Инициализируем флаг перетаскивания
            isDragging = true;

            // Добавляем обработчики для перемещения и завершения перетаскивания
            window.addEventListener('mousemove', onMouseMove);
            window.addEventListener('mouseup', onMouseUp);
        };

        // Слушаем событие mousedown на контейнере
        container.addEventListener('mousedown', (e) => {
            if (e.target && e.target.id) { // Проверяем, что это элемент с id
                onMouseDown(e);
            }
        });
    }

    // Активируем функциональность на контейнере
    moveElements('.container');
})();
