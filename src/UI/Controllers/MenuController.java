package UI.Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import UI.CEFWebView;
import UI.ExportSite;
import UI.ProjectManager;
import UI.Tools;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {

	public void showCreateProjectDialog()
	{
		
	}
	
	public void save()
	{
		 CEFWebView.ExecuteJS(Tools.SAVE.getPath());
		// projectSaveLabel.setText("сохранен (на момент нажатия 'Сохранить')");
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
	      //  projectName.setText(ProjectManager.getProjectName()); //label
           // projectSaveLabel.setText("сохранен (с момента загрузки)"); //label
            
            
            
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
	public void export() {
		ExportSite.generate();
	}
}
