const startupScripts = [ // скрипты в папке scripts и название функции
    {
      src: 'elementSelector.js',
      functionName: 'initElementSelector'
    },
    {
      src: 'send.js',
      functionName: 'send'
    },
    {
      src: 'loaderFromJava.js',
      functionName: 'loaderFromJava'
    },
    {
      src: 'changeSize.js',
      functionName: 'changeSize'
    }
];
function loadScript(src, callback) {
    const script = document.createElement('script');
    script.src = 'scripts/' + src;
    script.onload = callback; // Вызываем callback, когда скрипт загружен
    document.head.appendChild(script); // Добавляем скрипт в DOM
  }
function loadScriptsSequentially(scripts, index = 0) {
    if (index < scripts.length) {
      loadScript(scripts[index].src, function() {
        // После загрузки скрипта вызываем функцию, указанную в объекте
        window[scripts[index].functionName](); // Вызов функции из глобального пространства
        console.log(`${scripts[index].src} загружен, вызвана функция ${scripts[index].functionName}`);
        loadScriptsSequentially(scripts, index + 1); // Загружаем следующий скрипт
      });
    } else {
      console.log('Все скрипты загружены!');
    }
  }
  // Запускаем загрузку скриптов
  loadScriptsSequentially(startupScripts);