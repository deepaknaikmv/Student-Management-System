package application;

import java.sql.Date;

public class Student {

	String student_id,name,email,branch,dob,ph_no,currentSemester,gender,section,startYear;

	public Student(String student_id, String name, String email, String gender, String ph_no, String date) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.ph_no = ph_no;
		this.dob = date;
	}
	public Student(String student_id, String name, String email, String branch, String dob, String ph_no,
			String currentSemester, String gender, String section, String startYear) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.email = email;
		this.branch = branch;
		this.dob = dob;
		this.ph_no = ph_no;
		this.currentSemester = currentSemester;
		this.gender = gender;
		this.section = section;
		this.startYear = startYear;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPh_no() {
		return ph_no;
	}

	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}

	public String getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(String currentSemester) {
		this.currentSemester = currentSemester;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	

	
}
