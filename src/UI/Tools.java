package UI;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Tools {
	CREATE_DIV("templates/tools/createDiv.js");
	
	String path;
	
	Tools(String path) {
		this.path = path;
	}

	public String getPath() {
		Path absolutePath = Paths.get(path).toAbsolutePath();
        return absolutePath.toString();
	}

	public void setPath(String path) {
		this.path = path;
	}
}
