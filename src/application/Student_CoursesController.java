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
public class Student_CoursesController implements Initializable{
	
	private String sid;
	
	  @FXML
	    private AnchorPane coursePane;

	    @FXML
	    private Label student_id;

	    @FXML
		 private ComboBox<Integer> semester;
	    
	    @FXML
	    private Label validity;

	    @FXML
	    public TableView<Courses> courseTable1;

	    @FXML
	    public TableColumn<Courses,String> Course_ID;

	    @FXML
	    public TableColumn<Courses,String> Course_Name;

	    @FXML
	    public TableColumn<Courses,Integer> Credits;
	    
	    @FXML
	    private Button details;
	    
	    public ObservableList<Courses>  CourseList= FXCollections.observableArrayList();
	    Connection connection=null;
	  	Statement s1=null,s2=null;
	  	ResultSet rs1=null,rs2=null;
	  	
	  	@Override
		public void initialize(URL url, ResourceBundle rb)
		{
	  		semester.setPromptText("Select Semester");
	  		ObservableList<Integer> list2 = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
	    	semester.setItems(list2);
	  		Course_ID.setCellValueFactory(cellData-> cellData.getValue().getCourse_id());
	  		Course_Name.setCellValueFactory(cellData-> cellData.getValue().getName());
	  		Credits.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getCredit()).asObject());
		}
	  	
	  	@FXML
		public void detailsOnAction(ActionEvent event) throws IOException
		{
	  		validity.setText("");
	  		courseTable1.getItems().removeAll(courseTable1.getItems());
	  		connectDB obj = new connectDB();
			connection = obj.get_connection();
			try
			{
				String query1="select course_id from student_course_table "+"where student_id= '"+student_id.getText()+"' and semester= "+String.valueOf(semester.getValue())+";";
				s1=connection.createStatement();
				rs1=s1.executeQuery(query1);
				int f=0;
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
						validity.setText("Invalid semester");
						courseTable1.getItems().removeAll(courseTable1.getItems());
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
		System.out.println(sid);
	}

	
}
