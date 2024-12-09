(function() {
    var container = document.querySelector('.container'); // Находим контейнер
    const id = crypto.randomUUID(); // Для выбора конкретного элемента
    if (container) { // Проверяем, существует ли контейнер
        var newDiv = document.createElement('div');
        newDiv.id = id; // Присваиваем уникальный ID
        newDiv.textContent = 'Hello, I am a new div!';
        newDiv.style.backgroundColor = '#add8e6';
        newDiv.style.padding = '10px';
        newDiv.style.width = '200px';
        newDiv.style.height = '150px';
        newDiv.style.position = 'absolute'; // Для правильного позиционирования ручек
        
        // Добавляем "ручки" для изменения размера
        const resizers = [
            'top', 'bottom', 'left', 'right', // Стороны
            'top-left', 'top-right', 'bottom-left', 'bottom-right' // Углы
        ];

        resizers.forEach(resizer => {
            var resizerDiv = document.createElement('div');
            resizerDiv.classList.add('resizer', resizer);
            resizerDiv.dataset.id = id; // Привязываем ID к каждой "ручке"
            newDiv.appendChild(resizerDiv);
        });

        container.appendChild(newDiv); // Добавляем новый div в контейнер
    } else {
        console.error('Container with class "container" not found!');
    }
})();
