package UI;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Sss {
	public static void main(String[] args)
	{
		String path = "templates/toolse/createDiv.js";
		Path absolutePath = Paths.get(path).toAbsolutePath();
		System.out.println(absolutePath.toString());
	}
}
