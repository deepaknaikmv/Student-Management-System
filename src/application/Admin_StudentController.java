package application;

import java.util.Arrays;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
public class Admin_StudentController implements Initializable{

	private String sid;


	@FXML
    private AnchorPane studentPane;

    @FXML
    private TextField stid;

    @FXML
    private TextField stname;

    @FXML
    private TextField year;

    @FXML
    private Button enrollButton;

    @FXML
    private PasswordField stpass;

    @FXML
    private RadioButton male;

    @FXML
    private RadioButton female;

    @FXML
    private ComboBox<String> branch;

    @FXML
    private ComboBox<String> section;
    
    @FXML
    private TextField ssd;

    @FXML
    private TextField fyear;
    
    @FXML
    private Button fstat;

    @FXML
    private ComboBox<String> selstat;

    @FXML
    private Button update;

    @FXML
    private Label res;
    
    @FXML
    private Label status_1;
    
    @FXML
    private Label status_2;
    
    
    @FXML
    private ComboBox<Integer> sem;
    
    @FXML
    private TextField ffyear;

    @FXML
    private TextField syear;

    @FXML
    private Button promoteButton;

    @FXML
    private Button addButton;


    Connection connection=null;
   	Statement s1=null;
   	Statement s2=null,s3=null,s4=null,s5=null;
   	ResultSet rs1=null,rs4=null,rs5=null;
   	int rs2,m=0;
    
    @Override
	public void initialize(URL url, ResourceBundle rb)
	{
    	branch.setPromptText("Select Branch");
    	section.setPromptText("Select Section");
    	selstat.setPromptText("Select Status");
    	sem.setPromptText("Select semester");
    	ObservableList<String> list1 = FXCollections.observableArrayList("CSE","MEC","ECE","EEE");
    	branch.setItems(list1);
    	ObservableList<String> list2 = FXCollections.observableArrayList("A","B");
    	section.setItems(list2);
    	ObservableList<String> list3 = FXCollections.observableArrayList("P","N");
    	selstat.setItems(list3);
    	ObservableList<Integer> list4 = FXCollections.observableArrayList(1,2,3,4,5,6,7);
    	sem.setItems(list4);
    	ToggleGroup tg = new ToggleGroup();
    	tg.getToggles().addAll(male,female);
	}
	
    @FXML
    public void enrollButtonOnAction(ActionEvent event) throws IOException
    {
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			String b=String.valueOf(branch.getValue());
			String s=String.valueOf(section.getValue());
			String g=null;
			if(male.isSelected())
			{
				g="M";
			}
			else if(female.isSelected())
			{
				g="F";
			}
			String query1="insert into student_table (student_id,name,password,branch,section,year_of_join,gender) "+"values ('"+stid.getText()+"','"+stname.getText()+"','"+stpass.getText()+"','"+b+"','"+s+"','"+year.getText()+"','"+g+"');";
			s1=connection.createStatement();
			rs1=s1.executeQuery(query1);
			stid.setText("");
			stname.setText("");
			stpass.setText("");
			branch.setPromptText("Select Branch");
	    	section.setPromptText("Select Section");
	    	year.setText("");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    

    @FXML
    void fstatOnAction(ActionEvent event) {
    	m=0;
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{	
			selstat.setPromptText("Select Status");
			if(ssd.getText().isBlank()==false && fyear.getText().isBlank()==false)
			{
				String query1="select status from fee_status_table where student_id = '"+ssd.getText()+"' and year = '"+fyear.getText()+"';";
				s1=connection.createStatement();
				rs1=s1.executeQuery(query1);
				while (rs1.next()) {
					m=1;
			        String s = rs1.getString("status");
			        if(s.equals("N"))
			        	res.setText("Not Paid");
			        else if(s.equals("P"))
			        	res.setText("Paid");
			      }
				if(m==0)
					res.setText("Invalid Student id or year");
			}
			else
				res.setText("Missing fields");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    
    @FXML
    void updateOnAction(ActionEvent event) {
    	if(m==1 && selstat.getValue().isBlank()==false)
    	{
    		connectDB obj = new connectDB();
            connection = obj.get_connection();
            try
            {
              String b=String.valueOf(selstat.getValue());
              String query1="Update fee_status_table set status = '"+b+"'Where student_id = '"+ssd.getText()+"' and year = '"+fyear.getText()+"';";           
              s1=connection.createStatement();
              rs2=s1.executeUpdate(query1);
              fyear.setText("");
              ssd.setText("");
              selstat.setPromptText("Select Status");
              m=0;
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
    	}
    	else
    		res.setText("Missing fields");
    }
    
    
    @FXML
    void promoteButton(ActionEvent event) {
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		
		try
		{
			status_2.setText("");
			String y = "";
			int curr_sem = -1;
			int new_sem = -1;
			
			
			if((sem.getValue()==null || syear.getText().isEmpty())){
				status_2.setText("Invalid Operation");
				
			}
			else {
				curr_sem = Integer.valueOf(sem.getValue());
				y = syear.getText();
				new_sem =curr_sem+1;
				String query5 = "select * from student_sem_year where semester="+curr_sem+" and sem_year=\'"+y+"\'";
				s5=connection.createStatement();
				rs5=s5.executeQuery(query5);
				int x=0;
				if(rs5.next())
				{
					status_2.setText("");
					String query = "select * from student_sem_year where semester="+curr_sem+" and sem_year=\'"+y+"\'";
					s1=connection.createStatement();
					rs1=s1.executeQuery(query);
					if((curr_sem!=1) && (new_sem%2!=0)){
						y = String.valueOf(Integer.valueOf(y)+1);
					}
					while(rs1.next()) {
						if((curr_sem!=1) && new_sem%2!=0) {
							String query2 = "INSERT INTO fee_status_table VALUES (\'"+rs1.getString(1)+"\', \'"+y+"\');";
							s3=connection.createStatement();
							s3.executeUpdate(query2);
						}
						String query1 = "INSERT INTO student_sem_year VALUES (\'"+rs1.getString(1)+"\',"+new_sem+", \'"+y+"\');";
						s2=connection.createStatement();
						s2.executeUpdate(query1);
					}
				}
				else {
					status_2.setText("Invalid Operation");
				}
				
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void addButton(ActionEvent event) {
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		
		try
		{
			
			String y = ffyear.getText();
			status_1.setText("");
			String query4="select * from student_sem_year where sem_year= '"+y+"';";
			s4=connection.createStatement();
			if(y.isEmpty()) {
				status_1.setText("Invalid Operation");
			}
			else {
				rs4=s4.executeQuery(query4);
				int x=0;
				while(rs4.next())
				{
					x++;
				}
				if(x==0)
				{	
					
					String query1="select student_id from student_table where (select extract(year from year_of_join)='"+ffyear.getText()+"');";
					s1=connection.createStatement();
					rs1=s1.executeQuery(query1);
					int h=0;
					while (rs1.next()) {
						h++;
						String query = "INSERT INTO fee_status_table VALUES (\'"+rs1.getString(1)+"\', \'"+y+"\');";
						s3=connection.createStatement();
						s3.executeUpdate(query);
				        String s = rs1.getString("student_id");
				        String query2="insert into student_sem_year"+" values ('"+s+"','"+1+"','"+y+"');";
				        s2=connection.createStatement();
				        s2.executeUpdate(query2);
				      }
				}
				else {
					status_1.setText("Invalid Operation");
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
	public void set(String id)
	{
		sid=id;
	}
	
}