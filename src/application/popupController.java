package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class popupController {

	@FXML
	private Button continuee;
	
	public void continueeButtonOnAction(ActionEvent event)
	{
		Stage stage = (Stage) continuee.getScene().getWindow();
		stage.close();
	}
}
