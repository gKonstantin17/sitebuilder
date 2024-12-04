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
	requires jogl.all;
	requires org.json;

	opens application to javafx.graphics, javafx.fxml;
	opens UI to javafx.graphics, javafx.fxml,javafx.web,javafx.base,jcef,org.junit.jupiter.api,org.json;
}
