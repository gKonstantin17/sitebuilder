(function() {
    var container = document.querySelector('.container'); // Находим контейнер
    if (container) { // Проверяем, существует ли контейнер
        var newDiv = document.createElement('div');
        newDiv.textContent = 'Hello, I am a new div!';
        newDiv.style.backgroundColor = 'lightblue';
        newDiv.style.padding = '10px';
        newDiv.style.margin = '10px';
        container.appendChild(newDiv); // Добавляем новый div в контейнер
    } else {
        console.error('Container with class "container" not found!');
    }
})();