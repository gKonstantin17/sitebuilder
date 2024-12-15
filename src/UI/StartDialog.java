package UI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import UI.Controllers.ToolbarController;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartDialog {
	Label projectName;
	Label projectSaveLabel;
	
	public StartDialog(Label projectName, Label projectSaveLabel)
	{
		this.projectName = projectName;
		this.projectSaveLabel = projectSaveLabel;
	}
	
	public void showMainDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Выбор проекта"); // Название окна

        // Настроим основные кнопки
        ButtonType createButtonType = new ButtonType("Создать проект", ButtonBar.ButtonData.LEFT);
        ButtonType selectButtonType = new ButtonType("Выбрать существующий проект", ButtonBar.ButtonData.RIGHT);
        ButtonType cancelButtonType = new ButtonType("", ButtonBar.ButtonData.CANCEL_CLOSE); // чтобы работал Х

        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, selectButtonType, cancelButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(cancelButtonType);
        cancelButton.setMaxWidth(0);
        cancelButton.setMaxHeight(0);
        cancelButton.setVisible(false);  // Скрыть кнопку
        
        // Создаём панель для отображения контента
        Label label = new Label("Выберите действие:");
        dialog.getDialogPane().setContent(label);

        // Обрабатываем нажатие кнопки
        dialog.setResultConverter(button -> {
            if (button == createButtonType) {
                return "create";
            } else if (button == selectButtonType) {
                return "select";
            }
            return null;
        });

        // Показать диалог и обработать результат
        dialog.showAndWait().ifPresent(choice -> {
            if ("create".equals(choice)) {
                // Создать новый проект
                showCreateProjectDialog();
            } else if ("select".equals(choice)) {
                // Выбор существующего проекта
                showSelectProjectDialog();
            } else {
                System.out.println("Действие отменено.");
            }
        });
    }
    
    public void showCreateProjectDialog() {
        Dialog<String> createDialog = new Dialog<>();
        createDialog.setTitle("Создать проект");

        // Настроим кнопки для диалога создания проекта
        ButtonType backButtonType = new ButtonType("Назад", ButtonBar.ButtonData.RIGHT);
        ButtonType okButtonType = new ButtonType("Ок", ButtonBar.ButtonData.LEFT);
        createDialog.getDialogPane().getButtonTypes().addAll(backButtonType, okButtonType);

        // Поле ввода для названия проекта
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Введите название проекта");

        createDialog.getDialogPane().setContent(projectNameField);

        createDialog.setResultConverter(button -> {
            if (button == okButtonType) {
                return projectNameField.getText();
            } else if (button == backButtonType) {
                return "back";
            }
            return null;
        });

        createDialog.showAndWait().ifPresent(result -> {
            if ("back".equals(result)) {
                showMainDialog(); // Возврат к главному диалогу
            } else if (result != null && !result.isBlank()) {
            		// действия при создании проекта
                ProjectManager.setProjectName(result); 
    	        projectName.setText(ProjectManager.getProjectName()); //label
    	       
                projectSaveLabel.setText("Не сохранен");
               
                ToolbarController.browserReload();
            } else {
                showMainDialog(); // Возврат к главному диалогу
            }
        });
    }
    private void showSelectProjectDialog() {
    	ToolbarController.browserReload();
    	boolean result = load();
    	if (!result)
    	{
    		showMainDialog();
    	}
    }
    
    public boolean load() {
	    // Создаем FileChooser
	    FileChooser fileChooser = new FileChooser();

	    // Устанавливаем заголовок окна
	    fileChooser.setTitle("Выберите файл");

	    // Устанавливаем начальную директорию (например, папка "saves")
	    fileChooser.setInitialDirectory(new java.io.File("saves"));

	    // Устанавливаем фильтры для файлов (например, только JSON файлы)
	    fileChooser.getExtensionFilters().add(
	        new FileChooser.ExtensionFilter("JSON Files", "*.json")
	    );

	    // Открываем диалог выбора файла
	    java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());

	    // Если файл выбран, обрабатываем его
	    if (selectedFile != null) {
	        String filePath = selectedFile.getAbsolutePath();
	        // ДЛЯ
	        // ЭКПОРТА
	        ExportSite.setJsonFilePath(filePath);
	        //
	        //
	        ProjectManager.setProjectName(selectedFile.getName().replaceAll("\\.json$", ""));
	        projectName.setText(ProjectManager.getProjectName()); //label
	        
            projectSaveLabel.setText("сохранен (с момента загрузки)"); //label
            
            
            
	        System.out.println(filePath);
	        StringBuilder stringBuilder = new StringBuilder();

	        // Открытие файла для чтения
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                stringBuilder.append(line).append("\n");  // Добавляем строку и новую строку
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        // Возвращаем всю строку
	        String json = stringBuilder.toString();
	        System.out.println(json);

	        // Вызов метода для работы с данными
	        CEFWebView.LoadElements(json);
	        return true;
	    } else {
	        System.out.println("Файл не выбран.");
	        return false;
	    }
	}
}
