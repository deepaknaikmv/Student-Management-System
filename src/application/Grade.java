package application;

import javafx.beans.property.SimpleStringProperty;


public class Grade {
	public SimpleStringProperty CourseId, CourseName,Grade;
	
	public Grade(String CourseId, String CourseName, String grade) {
		this.CourseId = new SimpleStringProperty(CourseId);
		this.CourseName = new SimpleStringProperty(CourseName);
		this.Grade = new SimpleStringProperty(grade);
	}
	
	public SimpleStringProperty getCourseId() {
		return CourseId;
	}

	public void setCourseId(SimpleStringProperty courseId) {
		CourseId = courseId;
	}

	public SimpleStringProperty getCourseName() {
		return CourseName;
	}

	public void setCourseName(SimpleStringProperty courseName) {
		CourseName = courseName;
	}

	public SimpleStringProperty getGrade() {
		return Grade;
	}

	public void setGrade(SimpleStringProperty grade) {
		Grade = grade;
	}


}
