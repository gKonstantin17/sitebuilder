package UI;

public class ProjectManager {
	static String projectName;
	static String jsonFile;
	public static String getProjectName() {
		return projectName;
	}
	public static void setProjectName(String projectName) {
		ProjectManager.projectName = projectName;
		jsonFile = projectName + ".json";
	}
	public static String getJsonFile() {
		return jsonFile;
	}
	public static void setJsonFile(String jsonFilePath) {
		ProjectManager.jsonFile = jsonFilePath;
	}
	
}
