package application;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Marksheet {

	public SimpleStringProperty CourseId, CourseName;
	public int P1,P2,End ;
	
	


	public Marksheet(String CourseId, String CourseName, int P1, int P2, int End) {
		this.CourseId = new SimpleStringProperty(CourseId);
		this.CourseName = new SimpleStringProperty(CourseName);
		this.P1 = P1;
		this.P2 = P2;
		this.End = End;
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



	public int getP1() {
		return P1;
	}



	public void setP1(int p1) {
		P1 = p1;
	}



	public int getP2() {
		return P2;
	}



	public void setP2(int p2) {
		P2 = p2;
	}



	public int getEnd() {
		return End;
	}



	public void setEnd(int end) {
		End = end;
	}





}
