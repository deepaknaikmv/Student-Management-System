package application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.*;
public class Student_FeeStatusController {
	
	@FXML
    private TextField year_field;
	
	@FXML
    private Label status;
	
	Connection connection=null;
	Statement statement=null;
	ResultSet res_statement=null;
	
	String sid;
	void set(String ID) {
		this.sid = ID;
	}

	@FXML
    void Check_Status_Action(ActionEvent event) {
		connectDB obj = new connectDB();
		connection = obj.get_connection();

		try
		{	
			String year = year_field.getText();
			String query = "select status from fee_status_table where student_id=\'"+this.sid+"\' and year=\'"+year+"\';";
			statement = connection.createStatement();
			res_statement = statement.executeQuery(query);
			if (res_statement.next()) {
		        String s = res_statement.getString("status");
		        if(s.contentEquals("N"))
		        	status.setText("Fees Not Paid");
		        else
		        	status.setText("Fees Paid");
		      }
			else {
				status.setText("In-valid year");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
    }
}
