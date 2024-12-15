package UI;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Tools {
	CREATE_DIV("createDiv.js"),
	SAVE("serialization.js"),
	LOAD("loaderElements.js");
	
	
	String path;
	
	Tools(String path) {
		this.path = path;
	}

	public String getPath() {
		String filePath = "templates/tools/" + path;
		Path absolutePath = Paths.get(filePath).toAbsolutePath();
        return absolutePath.toString();
	}

	public void setPath(String path) {
		this.path = path; // файл в папке tools
	}
}
