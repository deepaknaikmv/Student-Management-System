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
public class Student_HomeController {

	private String sid;

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
	private AnchorPane homePane;
	
	@FXML
	public void initialize(URL url, ResourceBundle rb)
	{
		
	}
	
	
	public void profileButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Profile.fxml"));
		AnchorPane profilePane = loader.load();
		Student_ProfileController c = loader.getController();
		c.setProfile(sid);
		homePane.getChildren().setAll(profilePane);
	}
	
	public void feeButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_feeStatus.fxml"));
		AnchorPane feePane = loader.load();
		Student_FeeStatusController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(feePane);
	}
	
	public void gradeButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Grades.fxml"));
		AnchorPane gradePane = loader.load();
		Student_GradesController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(gradePane);
	}
	
	public void attendanceButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Attendance.fxml"));
		AnchorPane attendancePane = loader.load();
		Student_AttendanceController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(attendancePane);
	}
	
	public void coursesButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Courses.fxml"));
		AnchorPane coursesPane = loader.load();
		Student_CoursesController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(coursesPane);
	}
	
	public void marksButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Marks.fxml"));
		AnchorPane marksPane = loader.load();
		Student_MarksController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(marksPane);
	}
	
	public void enrollButtonOnAction(ActionEvent event) throws IOException
	{
		FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_EnrollCourses.fxml"));
		AnchorPane enrollstudentPane = loader.load();
		Student_EnrollCoursesController c = loader.getController();
		c.set(sid);
		homePane.getChildren().setAll(enrollstudentPane);
	}
	
	public void set(String id)
	{
		sid=id;
	}
	
}
