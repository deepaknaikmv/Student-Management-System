package application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.*;
public class Student_MainController {

	@FXML
	public Label uid;
	
	
	@FXML
	private Button homeButton;
	
	@FXML
	private Button profileButton;

	@FXML
	private Button feeButton;
	
	@FXML
	private Button feeButton1;
	
	@FXML
	private Button gradeButton;
	
	@FXML
	private Button gradeButton1;
	
	@FXML
	private Button attendanceButton;
	
	@FXML
	private Button attendanceButton1;
	
	@FXML
	private Button courseButton;
	
	@FXML
	private Button courseButton1;
	
	@FXML
	private Button marksButton;
	
	@FXML
	private Button marksButton1;
	
	@FXML
	private Button enrollButton;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	private AnchorPane tempPane;
	
	@FXML
	public void initialize(URL url, ResourceBundle rb)
	{
		
	}
	
	public void homeButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Home.fxml"));
		AnchorPane homePane = loader.load();
		Student_HomeController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(homePane);
	}
	
	public void profileButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Profile.fxml"));
		AnchorPane profilePane = loader.load();
		Student_ProfileController c = loader.getController();
		c.setProfile(uid.getText());
		tempPane.getChildren().setAll(profilePane);
	}
	
	public void feeButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_feeStatus.fxml"));
		AnchorPane feePane = loader.load();
		Student_FeeStatusController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(feePane);
	}
	
	public void gradeButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Grades.fxml"));
		AnchorPane gradePane = loader.load();
		Student_GradesController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(gradePane);
	}
	
	public void attendanceButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Attendance.fxml"));
		AnchorPane attendancePane = loader.load();
		Student_AttendanceController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(attendancePane);
	}
	
	public void coursesButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Courses.fxml"));
		AnchorPane coursesPane = loader.load();
		Student_CoursesController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(coursesPane);
	}
	
	public void marksButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Marks.fxml"));
		AnchorPane marksPane = loader.load();
		Student_MarksController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(marksPane);
	}
	
	public void enrollButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_EnrollCourses.fxml"));
		AnchorPane enrollstudentPane = loader.load();
		Student_EnrollCoursesController c = loader.getController();
		c.set(uid.getText());
		tempPane.getChildren().setAll(enrollstudentPane);
	}
	
	public void logoutButtonOnAction(ActionEvent event)
	{
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Stage LoginStage = new Stage();
			Scene scene = new Scene(root,1048,897);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			LoginStage.setScene(scene);
			LoginStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void showInfo(String id)
	{
		uid.setText(id);
	}
	
}
