function loaderFromJava(id, stylesJson) {
    // Находим элемент по id
    var element = document.getElementById(id);

    if (element) {
        if (stylesJson.text !== undefined) {
            element.textContent = stylesJson.text;
        }
        // Применяем стили к элементу
        Object.keys(stylesJson).forEach(function(styleProperty) {
            element.style[styleProperty] = stylesJson[styleProperty];
        });
    } else {
        console.error("Элемент с id " + id + " не найден.");
    }
}