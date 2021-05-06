package application;

import java.sql.Date;

public class Admin {

	String admin_id;
	String name;
	String email;
	String gender;
	String ph_no;
	String dob;
	
	public Admin(String admin_id, String name, String email, String gender, String ph_no, String date) {
		super();
		this.admin_id = admin_id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.ph_no = ph_no;
		this.dob = date;
	}

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPh_no() {
		return ph_no;
	}

	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String date) {
		this.dob = date;
	}

	
	
}
