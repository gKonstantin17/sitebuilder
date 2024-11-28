package UI;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class DialogSceneController {
	@FXML
    private TextField textField;  // Привязываем поле ввода

    private String result;  // Результат ввода
    public String getResult() {
        return result;
    }

    public void processResult(Dialog<ButtonType> dialog) {
        // Устанавливаем конвертер результатов
        dialog.setResultConverter(button -> {
            // Если кнопка "OK" была нажата
            if (button.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                result = textField.getText(); // Возвращаем текст из поля
                return button;  // Возвращаем кнопку OK
            }
            return null;  // Если нажата другая кнопка, возвращаем null
        });
    }
    
    public void showDialog(Dialog<ButtonType> dialog) {
        dialog.showAndWait().ifPresent(result -> {
            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                System.out.println("Введенный текст: " + getResult());  // Выводим результат
            }
        });
    }
}
