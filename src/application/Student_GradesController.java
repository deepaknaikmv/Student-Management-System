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
public class Student_GradesController implements Initializable {

	private String sid;
	@FXML
    private AnchorPane gradePane;

    @FXML
    private Label stud_id;

    @FXML
    private ComboBox<Integer> sem;

    @FXML
    private TableView<Grade> gradeTable;

    @FXML
    private TableColumn<Grade, String> col_courseId;

    @FXML
    private TableColumn<Grade, String> col_courseName;

    @FXML
    private TableColumn<Grade, String> col_grade;

    @FXML
    private Label semStatus;

    
    @FXML
    private Label sgpaLabel;

    @FXML
    private Button getButton;


	public void set(String id)	{
		sid=id;
		stud_id.setText(sid);
	}
	
	public ObservableList<Grade>  GradeList= FXCollections.observableArrayList();;
	Connection connection=null;
  	Statement s1=null;
  	ResultSet rs1=null;
  	Statement s2=null;
  	ResultSet rs2=null;
 	Statement s0=null;
 	ResultSet rs0=null;
	
  	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<Integer> semList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
    	sem.setItems(semList);

    	initTable();		
	}
  	
  	
  	private void initTable() {
		initCols();
	}


	private void initCols() {
		col_courseId.setCellValueFactory(cellData-> cellData.getValue().getCourseId());
		col_courseName.setCellValueFactory(cellData-> cellData.getValue().getCourseName());
		col_grade.setCellValueFactory(cellData-> cellData.getValue().getGrade());
	}
	
	@FXML
    public void getButtonOnAction(ActionEvent event) throws IOException
    {
		gradeTable.getItems().removeAll(gradeTable.getItems());
		semStatus.setText("");
		double sgpa = 0;
		double totalCredit = 0;
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			String sid=stud_id.getText();
			String s=String.valueOf(sem.getValue());
			String y = "";
			String query0 = "select sem_year from student_sem_year where student_id = '" + sid +"' and semester = "+s+";";
			s0=connection.createStatement();
			rs0=s0.executeQuery(query0);
			int flag = 0;
			while(rs0.next()) {
				flag++;
				y=rs0.getString("sem_year");
			}
			if(flag==0){
				semStatus.setText("Invalid Semster");
			}
			
			String query1="select * from marksheet_table where student_id= '" + sid + "' and semester=" + s + "and sem_year='" + y+ "';";
			s1=connection.createStatement();
			rs1=s1.executeQuery(query1);
			
			while(rs1.next()) {
				String cid = rs1.getString("course_id");
				String grade = rs1.getString("grade");
				String query2 ="select name,credit from course_table where course_id= '"+ cid +"';";
				s2=connection.createStatement();
				rs2 = s2.executeQuery(query2);
				String cname = null;
				int credit = 0;
				while(rs2.next()) {
					cname = rs2.getString("name");
					credit = rs2.getInt("credit");
					System.out.println(cname);
					System.out.println(credit);
				}
				if (grade.equals("A"))
				{
					sgpa = sgpa + credit;
					totalCredit = totalCredit + credit;
				}
				else if (grade.equals("B"))
				{
					sgpa = sgpa + (0.9 * credit);
					totalCredit = totalCredit + credit;
				}
				else if (grade.equals("C"))
				{
					sgpa = sgpa + (0.8 * credit);
					totalCredit = totalCredit + credit;
				}
				else if (grade.equals("D"))
				{
					sgpa = sgpa + (0.7 * credit);
					totalCredit = totalCredit + credit;
				}
				else if (grade.equals("E"))
		        {
		          sgpa = sgpa + (0.6 * credit);
		          totalCredit = totalCredit + credit;
		        }else if (grade.equals("F")){
		          sgpa = sgpa + (0.5 * credit);
		          totalCredit = totalCredit + credit;
		        }else{
		          sgpa =0.0;
		          totalCredit = 1.0;
		        }      
		        GradeList.add(new Grade(cid,cname,grade));
		      }
		      int result =(int)((sgpa/totalCredit) * 1000);
		      System.out.println(result);
		      double dResult = result/100.0;
		      System.out.println(dResult);
		      sgpaLabel.setText(String.valueOf(dResult));
		      gradeTable.setItems(GradeList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

    }
	
}
