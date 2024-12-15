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
		var htmlContent = readHtml();
		var jsonContent = readJson();
		JSONArray jsonArray = toJSON(jsonContent);
		var generatedBlocks = generateBlocks(jsonArray);
		createHtml(htmlContent, generatedBlocks);
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
	
	private static StringBuilder readJson()
	{
		String filePath = jsonFilePath;
		StringBuilder jsonContent = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	jsonContent.append(line);  // Добавляем строку и новую строку
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return jsonContent;        
	}
	
	private static JSONArray toJSON(StringBuilder jsonContent)
	{
		JSONArray jsonArray = new JSONArray(jsonContent.toString());
		return jsonArray;
	}
	
	private static StringBuilder generateBlocks(JSONArray jsonArray)
	{
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
        return generatedBlocks;
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
	
	private static void createHtml(StringBuilder htmlContent,StringBuilder generatedBlocks)
	{
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
}
