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
public class Admin_HomeController {

	private String sid;

	@FXML
	private Button profileButton;

	@FXML
	private Button studentButton;
	
	@FXML
	private Button studentButton1;
	
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
	private Button logoutButton;
	
	@FXML
	private AnchorPane homePane;
	
	
	@FXML
	public void initialize(URL url, ResourceBundle rb)
	{
		
	}
	
	public void set(String id)
	{
		sid=id;
	}
	
	@FXML
	private void profileButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin_Profile.fxml"));
		AnchorPane profilePane = loader.load();
		Admin_ProfileController c = loader.getController();
		c.setProfile(sid);
		homePane.getChildren().setAll(profilePane);
	}
	
	@FXML
	private void studentButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin_Student.fxml"));
		AnchorPane studentPane = loader.load();
		Admin_StudentController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(studentPane);
	}
	
	@FXML
	private void attendanceButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin_Attendance.fxml"));
		AnchorPane attendancePane = loader.load();
		Admin_AttendanceController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(attendancePane);
	}
	
	@FXML
	private void courseButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin_Courses.fxml"));
		AnchorPane coursesPane = loader.load();
		Admin_CoursesController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(coursesPane);
	}
	
	@FXML
	private void marksButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin_Marks.fxml"));
		AnchorPane marksPane = loader.load();
		Admin_MarksController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(marksPane);
	}
	
}
