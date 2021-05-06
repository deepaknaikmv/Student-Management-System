package application;

import javafx.beans.property.SimpleStringProperty;

public class Attendance {
	public SimpleStringProperty CourseId, CourseName;
	public int attended,total ;
	
	public Attendance(String CourseId, String CourseName, int attended, int total ) {
		this.CourseId = new SimpleStringProperty(CourseId);
		this.CourseName = new SimpleStringProperty(CourseName);
		this.attended = attended;
		this.total = total;
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

	public int getAttended() {
		return attended;
	}

	public void setAttended(int attended) {
		this.attended = attended;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


}
