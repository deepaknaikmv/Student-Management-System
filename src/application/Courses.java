package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Courses {

	public SimpleStringProperty course_id;
	public SimpleStringProperty name;
	public SimpleIntegerProperty credit;
	
	public Courses(String string, String string2,int credit) {
		this.course_id =new SimpleStringProperty(string);
		this.name = new SimpleStringProperty(string2);
		this.credit =new SimpleIntegerProperty(credit);
	}
	
	
	public SimpleStringProperty getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = new SimpleStringProperty(course_id);
	}
	public SimpleStringProperty getName() {
		return name;
	}
	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}
	public int getCredit() {
		return credit.get();
	}
	public void setCredit(int credit) {
		this.credit = new SimpleIntegerProperty(credit);
	}
	
	
}
