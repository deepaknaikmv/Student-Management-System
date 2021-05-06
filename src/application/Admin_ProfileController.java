package application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;

public class Admin_ProfileController implements Initializable{

    @FXML
    private AnchorPane profilePane;

    @FXML
    private Label id;
    
    @FXML
    private Label passLabel;

    @FXML
    private Label name;

    @FXML
    private Label gender;

    @FXML
    private Label dob;

    @FXML
    private Label email;

    @FXML
    private Label pno;

    @FXML
    private TextField upname;

    @FXML
    private ComboBox<String>  upgender;

    @FXML
    private TextField updob;

    @FXML
    private TextField upemail;

    @FXML
    private TextField uppno;

    @FXML
    private Button updetails;

    @FXML
    private TextField cpass;

    @FXML
    private TextField npass;

    @FXML
    private Button uppass;

    @FXML
    private TextField rnpass;
    
    Connection connection=null;
	Statement s1=null,s2=null;
	ResultSet rs1=null;
    private Admin admin=null;
    
    
    
	public void setProfile(String tid)
	{
		id.setText(tid);
		connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			String query1="select * from admin_table";
			s1=connection.createStatement();
			rs1=s1.executeQuery(query1);
			while(rs1.next())
			{
				if(rs1.getString(1).equals(tid))
				{
					admin=new Admin(rs1.getString(1),rs1.getString(2),rs1.getString(4),rs1.getString(5),rs1.getString(6),String.valueOf(rs1.getDate(7)));
					name.setText(admin.getName());
					gender.setText(admin.getGender());
					dob.setText(String.valueOf(admin.getDob()));
					email.setText(admin.getEmail());
					pno.setText(admin.getPh_no());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<String> list = new ArrayList<String>();
        list.add("M");
        list.add("F");
        ObservableList<String> obList = FXCollections.observableList(list);
        upgender.getItems().clear();
        upgender.setItems(obList);

    }
	@FXML
	private void updetailsOnAction(ActionEvent event) throws IOException
	{
		//name,gender,date of birth,email,ph_no
		if(upname.getText().isBlank()==false)
			admin.setName(String.valueOf(upname.getText()));
		if(upgender.getSelectionModel().isEmpty()==false)
			admin.setGender(String.valueOf(upgender.getValue()));
		if(updob.getText().isBlank()==false)
			admin.setDob(updob.getText());
		if(upemail.getText().isBlank()==false)
			admin.setEmail(upemail.getText());
		if(uppno.getText().isBlank()==false)
			admin.setPh_no(uppno.getText());
		
		connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			String aid=admin.getAdmin_id();
			String query1="update admin_table set name= '"+admin.getName()+"',gender= '"+admin.getGender()+"',email= '"+admin.getEmail()+"',ph_no= '"+admin.getPh_no()+"',dob= '"+admin.getDob()+"' where admin_id= '"+aid+"';";
			s1=connection.createStatement();
			name.setText(admin.getName());
			gender.setText(admin.getGender());
			email.setText(admin.getEmail());
			pno.setText(admin.getPh_no());
			if(upname.getText().isBlank()==false || upgender.getSelectionModel().isEmpty()==false || upemail.getText().isBlank()==false || uppno.getText().isBlank()==false || updob.getText().isBlank()==false)
			{
				try {
					AnchorPane root =(AnchorPane) FXMLLoader.load(getClass().getResource("popup.fxml"));
					Stage popup = new Stage();
					Scene scene = new Scene(root,373,218);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					popup.setScene(scene);
					popup.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			upname.setText("");
			upgender.valueProperty().set(null);
			updob.setText("");
			upemail.setText("");
			uppno.setText("");
			s1.executeQuery(query1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void uppassOnAction(ActionEvent event) throws IOException
	{
		String aid= admin.getAdmin_id();
		//cpass,npass,rnpass,uppass
		//String query1="select * from admin_table where admin_id= '"+aid+"';";
		//String query2="update admin_table set password= '"+String.valueOf(npass.getText())+"' where admin_id= '"+aid+"';";
		if(cpass.getText().isBlank()==false && npass.getText().isBlank()==false && rnpass.getText().isBlank()==false)
		{	
			if(npass.getText().equals(rnpass.getText()))
			{	
				
				connectDB obj = new connectDB();
				connection = obj.get_connection();
				try
				{
					String query1="select * from admin_table";
					s1=connection.createStatement();
					rs1=s1.executeQuery(query1);
					while(rs1.next())
					{
						if(rs1.getString(1).equals(aid))
						{
							if(rs1.getString(3).equals(cpass.getText()))
							{	
								if(!rs1.getString(3).equals(npass.getText())){
									String np=npass.getText();
									String query2="update admin_table set password= '"+np+"' where admin_id= '"+aid+"';";
									s2=connection.createStatement();
									try {
										AnchorPane root =(AnchorPane) FXMLLoader.load(getClass().getResource("popup.fxml"));
										Stage popup = new Stage();
										Scene scene = new Scene(root,373,218);
										scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
										popup.setScene(scene);
										popup.show();
									} catch(Exception e) {
										e.printStackTrace();
									}
									s2.executeQuery(query2);
								}
								else {
									cpass.setText("");
									passLabel.setText("Please type a new password");
								}
								
							}
							else
							{
								cpass.setText("");
								passLabel.setText("Current password not matching");
							}
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				rnpass.setText("");
				passLabel.setText("New & retyped password not matching");
			}
		}
		else
		{
			cpass.setText("");
			npass.setText("");
			rnpass.setText("");
			passLabel.setText("Please fill all the fields and try again");
		}
	}
	
	
}
