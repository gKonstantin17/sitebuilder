module Prototype {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.web;
	requires javafx.base;
	requires java.desktop;
	requires jcef;
	requires org.junit.jupiter.api;
	requires javafx.swing;
	requires org.json;

	exports UI.Controllers; // Экспортируем пакет с контроллерами
    opens UI.Controllers to javafx.fxml; // Открываем пакет для рефлексии FXMLLoader
	opens UI to javafx.graphics, javafx.fxml,javafx.web,javafx.base,jcef,org.junit.jupiter.api,org.json;
}
