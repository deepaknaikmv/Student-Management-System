package application;

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

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
public class Student_EnrollCoursesController implements Initializable{

	    private String sid,d="";
	
	    @FXML
	    private AnchorPane enrollPane;

	    @FXML
		private ComboBox<Integer> semester;

	    @FXML
	    public TableView<Courses> courseTable1;

	    @FXML
	    public TableColumn<Courses,String> Course_ID;

	    @FXML
	    public TableColumn<Courses,String> Course_Name;

	    @FXML
	    public TableColumn<Courses,Integer> Credits;

	    @FXML
	    private TableView<Courses> courseTable2;

	    @FXML
	    private TableColumn<Courses,String> Course_ID1;

	    @FXML
	    private TableColumn<Courses,String> Course_Name1;
	    
	    @FXML
	    public TableColumn<Courses,Integer> Credits1;

	    @FXML
	    private Button rightShift;

	    @FXML
	    private Button enroll;

	    @FXML
	    private Label student_id;

	    @FXML
	    private TextField sem_year;

	    @FXML
	    private Label sem_validity;

	    @FXML
	    private Label year_validity;

	    @FXML
	    private Button details;
	    
	    @FXML
	    private Label status1;

	    @FXML
	    private Label status2;
	    
	    public ObservableList<Courses>  CourseList= FXCollections.observableArrayList(),CourseList2= FXCollections.observableArrayList();
	    Connection connection=null;
	  	Statement s1=null,s2=null,s3=null,s4=null,s5=null,s6=null;
	  	ResultSet rs1=null,rs2=null,rs3=null,rs4=null,rs5=null,rs6=null;
	  	int i,f=0;
	  	
	  	
		@Override
		public void initialize(URL url, ResourceBundle rb)
		{
			semester.setPromptText("Select Semester");
			ObservableList<Integer> list2 = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
	    	semester.setItems(list2);
			Course_ID.setCellValueFactory(cellData-> cellData.getValue().getCourse_id());
	  		Course_Name.setCellValueFactory(cellData-> cellData.getValue().getName());
	  		Credits.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getCredit()).asObject());
	  		Course_ID1.setCellValueFactory(cellData-> cellData.getValue().getCourse_id());
	  		Course_Name1.setCellValueFactory(cellData-> cellData.getValue().getName());
	  		Credits1.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getCredit()).asObject());
	  		
		}
		
		@FXML
		public void detailsOnAction(ActionEvent event) throws IOException
		{
			status1.setText("");
			status2.setText("");
			sem_validity.setText("");
	  		courseTable1.getItems().removeAll(courseTable1.getItems());
	  		courseTable2.getItems().removeAll(courseTable2.getItems());
	  		connectDB obj = new connectDB();
			connection = obj.get_connection();
			try
			{
				String query1="select course_id from course_sem_year "+"where sem_year= '"+sem_year.getText()+"' and semester= "+String.valueOf(semester.getValue())+" and branch= '"+d+"';";
				s1=connection.createStatement();
				rs1=s1.executeQuery(query1);
				f=0;
					while(rs1.next())
					{
						f++;
						String query2="select * from course_table "+"where course_id= '"+rs1.getString(1)+"';";
						s2=connection.createStatement();
						rs2=s2.executeQuery(query2);
						while(rs2.next())
						{
							CourseList.add(new Courses(rs2.getString("course_id"),rs2.getString("name"),rs2.getInt("credit")));
						}
					}
					courseTable1.setItems(CourseList);
					if(f==0)
					{
						semester.setPromptText("Select Semester");
						sem_validity.setText("Invalid semester or year");
						courseTable1.getItems().removeAll(courseTable1.getItems());
					}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		@FXML
		public void rightShiftOnAction(ActionEvent event) throws IOException
		{
	  		CourseList2=courseTable1.getItems();
	  		courseTable2.setItems(CourseList2);
		}
	
		@FXML
		public void enrollOnAction(ActionEvent event) throws IOException
		{
			connectDB obj = new connectDB();
			connection = obj.get_connection();
			try
			{
				String query3="select * from student_course_table "+"where sem_year= '"+sem_year.getText()+"' and semester= "+String.valueOf(semester.getValue())+" and student_id= '"+student_id.getText()+"';";
				s3=connection.createStatement();
				rs3=s3.executeQuery(query3);
				f=0;
					while(rs3.next())
					{
						status1.setText("Already enrolled");
						courseTable2.getItems().removeAll(courseTable2.getItems());
						status2.setText("");
						f++;
					}
					if(f==0)
					{
						String query5="select course_id from course_sem_year "+"where sem_year= '"+sem_year.getText()+"' and semester= "+String.valueOf(semester.getValue())+" and branch= '"+d+"';";
						s5=connection.createStatement();
						rs5=s5.executeQuery(query5);
						while(rs5.next())
						{
							System.out.println(rs5.getString(1));
							String query4="insert into student_course_table (student_id,course_id,semester,sem_year) "+"values ('"+student_id.getText()+"','"+rs5.getString(1)+"',"+String.valueOf(semester.getValue())+",'"+sem_year.getText()+"');";
							s4=connection.createStatement();
							s4.executeUpdate(query4);
							String query6="insert into marksheet_table (student_id,course_id,semester,sem_year,attendance,p1,p2,end_sem,grade) "+"values ('"+student_id.getText()+"','"+rs5.getString(1)+"',"+String.valueOf(semester.getValue())+",'"+sem_year.getText()+"','NA',-1,-1,-1,'NA');";
							s6=connection.createStatement();
							s6.executeUpdate(query6);
						}
						status2.setText("Enrolled successfully");
						status1.setText("");
						courseTable1.getItems().removeAll(courseTable1.getItems());
						courseTable2.getItems().removeAll(courseTable2.getItems());
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
		student_id.setText(sid);
		d="";
		String[] w=sid.split("[.]");
		d=w[1];
		System.out.println(d);
	}
	
}
