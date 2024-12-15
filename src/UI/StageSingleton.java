package UI;

import javafx.stage.Stage;

public class StageSingleton {
	static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		StageSingleton.stage = stage;
	}
}
