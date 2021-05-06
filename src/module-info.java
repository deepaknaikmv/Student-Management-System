module Trial1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires java.sql;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
