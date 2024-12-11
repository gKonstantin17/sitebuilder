package UI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExportSite {
	static String htmlFilePath = "templates/forExport/index.html";
	static String jsonFilePath;
	static String outputFilePath;

	public static void setJsonFilePath(String _jsonFilePath) {
		jsonFilePath = _jsonFilePath;
	}
    
	public static void setOutputFilePath() { // название экпортированного сайта
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
	    String fileName = "sites/index_" + formatter.format(new Date()) + ".html";
		outputFilePath = fileName;
	}
	public static void generate()
	{
		if (jsonFilePath == null)
		{
			System.out.println("Не выбран проект");
			return;
		}
		setOutputFilePath();
		generating();
			
	}
	private static void generating()
	{
		StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(htmlFilePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        StringBuilder jsonBuilder = new StringBuilder();
        try (BufferedReader jsonReader = new BufferedReader(new FileReader(jsonFilePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = jsonReader.readLine()) != null) {
                jsonBuilder.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
        
        StringBuilder generatedBlocks = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);

            // Извлекаем данные из JSON
            String tag = element.getString("tag");
            JSONObject styles = element.getJSONObject("styles");
            String content = element.getString("content");
           // String id = element.getString("id");

            // Формируем строку стилей
            StringBuilder styleString = new StringBuilder();
            for (String key : styles.keySet()) {
            	String cssKey = convertToKebabCase(key);
            	styleString.append(cssKey).append(": ").append(styles.getString(key)).append("; ");
            }

            // добавить вместе с id, ещё расккоментировать строку String id = element.getString("id");
//            generatedBlocks.append("<").append(tag)
//                    .append(" id=\"").append(id).append("\"")
//                    .append(" style=\"").append(styleString.toString()).append("\"")
//                    .append(">").append(content).append("</").append(tag).append(">\n");
            
            // без id
            generatedBlocks.append("<").append(tag)
            .append(" style=\"").append(styleString.toString()).append("\"")
            .append(">").append(content).append("</").append(tag).append(">\n");
            
            
        }

        
     // Вставляем сгенерированные блоки в контейнер
        String finalHtml = htmlContent.toString().replace("<div class=\"container\"></div>",
                "<div class=\"container\">\n" + generatedBlocks.toString() + "\n</div>");
        

        
        // Сохраняем финальный HTML в новый файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8))) {
            writer.write(finalHtml);
            System.out.println("Файл успешно сохранен как: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
	       
	}
	
	private static StringBuilder readHtml()
	{
		StringBuilder htmlContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(htmlFilePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlContent;
	}
	
	private static JSONArray readJson()
	{
		String filePath = jsonFilePath;
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	stringBuilder.append(line);  // Добавляем строку и новую строку
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Возвращаем всю строку
		JSONArray jsonArray = new JSONArray(stringBuilder.toString());
        String json = stringBuilder.toString();
        System.out.println(json);
        return jsonArray;
	}
	private static void parseJson(JSONArray jsonArray)
	{
		StringBuilder generatedBlocks = new StringBuilder();

        // Генерация HTML блоков
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject element = jsonArray.getJSONObject(i);

            // Извлекаем данные из JSON
            String tag = element.getString("tag");
            JSONObject styles = element.getJSONObject("styles");
            String content = element.getString("content");
            String id = element.getString("id");

            // Формируем строку стилей
            StringBuilder styleString = new StringBuilder();
            for (String key : styles.keySet()) {
                styleString.append(key).append(": ").append(styles.getString(key)).append("; ");
            }

            // Генерируем HTML тег с аттрибутами и содержимым
            generatedBlocks.append("<").append(tag)
                    .append(" id=\"").append(id).append("\"")
                    .append(" style=\"").append(styleString.toString()).append("\"")
                    .append(">").append(content).append("</").append(tag).append(">\n");
        }
        
        
	}
	public static String convertToKebabCase(String camelCase) 
	{ // в кебаб-кейс (например, backgroundColor -> background-color)
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                result.append('-').append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
