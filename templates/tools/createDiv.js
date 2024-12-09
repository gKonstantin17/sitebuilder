(function() {
    var container = document.querySelector('.container'); // Находим контейнер
    const id = crypto.randomUUID(); // для выбора конретного элемента
    if (container) { // Проверяем, существует ли контейнер
        var newDiv = document.createElement('div');
        newDiv.id = id;
        newDiv.textContent = 'Hello, I am a new div!';
        newDiv.style.backgroundColor = '#add8e6';
        newDiv.style.padding = '10px';
        newDiv.style.margin = '10px';
        newDiv.style.width = '200px';
        container.appendChild(newDiv); // Добавляем новый div в контейнер
    } else {
        console.error('Container with class "container" not found!');
    }
})();