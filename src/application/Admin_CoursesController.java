package application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
public class Admin_CoursesController implements Initializable{

	private String sid;
	
		@FXML
	    public TableView<Courses> courseTable1;
	    
		 @FXML
		 private ComboBox<Integer> semester;

		 @FXML
		 private ComboBox<String> branch;

		 @FXML
		 private TextField year;
		 
		 @FXML
		 private TextField cid;
		 @FXML
		 private TextField cname;
		 @FXML
		 private TextField ccredits;
		
		 @FXML
		 private Button confirm;
		 
		 @FXML
		 private AnchorPane addPane;
		 @FXML
		 private AnchorPane offerPane;
		 @FXML
		 private AnchorPane coursesPane;
		 
		 @FXML
		 private Button addCourseButton;
		 
		 @FXML
		 private Button rightShift;
		 
		 @FXML
		 private Button leftShift;
		 
		 @FXML
		 private Button remove;
		 
		 @FXML
		 private Label condition;

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

	    public ObservableList<Courses>  CourseList= FXCollections.observableArrayList(),CourseList2= FXCollections.observableArrayList();
	    Connection connection=null;
	  	Statement s1=null,s2=null,s3=null,s4=null;
	  	ResultSet rs1=null,rs2=null;
	  	int i,f=0;
	  	
	  	@Override
		public void initialize(URL url, ResourceBundle rb)
		{
	  		Course_ID.setCellValueFactory(cellData-> cellData.getValue().getCourse_id());
	  		Course_Name.setCellValueFactory(cellData-> cellData.getValue().getName());
	  		Credits.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getCredit()).asObject());
	  		connectDB obj = new connectDB();
			connection = obj.get_connection();
			try
			{
				String query1="select * from course_table";
				s1=connection.createStatement();
				rs1=s1.executeQuery(query1);
				while(rs1.next())
				{
					 CourseList.add(new Courses(rs1.getString("course_id"),rs1.getString("name"),rs1.getInt("credit")));
				}
				courseTable1.setItems(CourseList);
				Course_ID1.setCellValueFactory(cellData-> cellData.getValue().getCourse_id());
		  		Course_Name1.setCellValueFactory(cellData-> cellData.getValue().getName());
		  		Credits1.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getCredit()).asObject());
		  		i=courseTable1.getSelectionModel().getSelectedIndex();
		  		if(i>=0)
		  		{
		  		String r1=Course_ID.getCellData(i).toString(),r2=Course_Name.getCellData(i).toString();
		  		int r3=Credits.getCellData(i);
		  		courseTable2.getItems().add(new Courses(r1,r2,r3));
		  		}
		  		else
		  			f++;
		  		branch.setPromptText("Select Branch");
		    	semester.setPromptText("Select Semester");
		    	ObservableList<String> list1 = FXCollections.observableArrayList("CSE","MEC","ECE","EEE");
		    	branch.setItems(list1);
		    	ObservableList<Integer> list2 = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
		    	semester.setItems(list2);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	  	 
	  	public void get()
	  	{
	  		i=courseTable1.getSelectionModel().getSelectedIndex();
	  		if(i>=0)
	  		{
	  		String r1=Course_ID.getCellData(i).toString(),r2=Course_Name.getCellData(i).toString();
	  		int r3=Credits.getCellData(i);
	  		courseTable2.getItems().add(new Courses(r1,r2,r3));
	  		}
	  	}
	  	@FXML
		public void rightShiftOnAction(ActionEvent event) throws IOException
		{
	  		if(f!=0)
	  			get();
	  		f++;
		}
	  	
	  	@FXML
		public void leftShiftOnAction(ActionEvent event) throws IOException
		{
	  		courseTable2.getItems().removeAll(courseTable2.getSelectionModel().getSelectedItem());
		}
		
		@FXML
		public void removeOnAction(ActionEvent event) throws IOException
		{
			courseTable2.getItems().removeAll(courseTable2.getItems());
		}
	  	
		@FXML
		public void addCourseButtonOnAction(ActionEvent event) throws IOException
		{
			connectDB obj = new connectDB();
			connection = obj.get_connection();
			int h=0;
			try
			{
				String query2="select * from course_table";
				s2=connection.createStatement();
				rs2=s2.executeQuery(query2);
				while(rs2.next())
				{
					if(rs2.getString(1).equals(cid.getText()))
					{
						condition.setText("This course ID already exists");
						cid.setText("");
						h=1;
					}
				}
				if(cid.getText().isBlank()==false && cname.getText().isBlank()==false && ccredits.getText().isBlank()==false)
				{
					if(h==0)
					{
						String query3="insert into course_table (course_id,name,credit) "+"values ('"+cid.getText()+"','"+cname.getText()+"','"+ccredits.getText()+"');";
						s3=connection.createStatement();
						s3.executeQuery(query3);
						condition.setText("Succesfully added new Course");
					}
				}
				else
				{
					cid.setText("");
					cname.setText("");
					ccredits.setText("");
					condition.setText("All of the fields where not entered");
					if(h==1)
					{
						condition.setText("This course ID already exists");
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		@FXML
		public void confirmOnAction(ActionEvent event) throws IOException, SQLException
		{
			for(int x=0;x<courseTable2.getChildrenUnmodifiable().size();x++)
			{
				Courses c = courseTable2.getItems().get(x);
				String ci=c.getCourse_id().get();
				System.out.println(ci);
				String query4="insert into course_sem_year (course_id,semester,branch,sem_year) "+"values ('"+ci+"',"+String.valueOf(semester.getValue())+",'"+branch.getValue()+"','"+year.getText()+"');";
				s4=connection.createStatement();
				s4.executeUpdate(query4);
			}
		}
		
		public void set(String id)
		{
			sid=id;
			System.out.println(sid);
		}
	
}
