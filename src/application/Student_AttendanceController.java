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
public class Student_AttendanceController implements Initializable {

	private String sid;
	
	@FXML
    private AnchorPane attendancePane;

    @FXML
    private Label stud_id;

    @FXML
    private ComboBox<Integer> sem;

    @FXML
    private TableView<Attendance> attendTable;

    @FXML
    private TableColumn<Attendance, String> col_courseId;

    @FXML
    private TableColumn<Attendance, String> col_courseName;

    @FXML
    private TableColumn<Attendance, Number> col_att;

    @FXML
    private TableColumn<Attendance, Number> col_total;
    
    @FXML
    private Label semStatus;

    @FXML
    private Button getButton;
    
    public void set(String id) {
    	sid=id;
    	stud_id.setText(sid);
    }
    
    public ObservableList<Attendance>  AttendanceList= FXCollections.observableArrayList();;
	Connection connection=null;
 	Statement s0=null;
 	ResultSet rs0=null;
 	Statement s1=null;
 	ResultSet rs1=null;
 	Statement s2=null;
 	ResultSet rs2=null;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		ObservableList<Integer> semList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
    	sem.setItems(semList);

    	initTable();
	}
	
	@FXML
    public void getButtonOnAction(ActionEvent event) throws IOException
    {
		attendTable.getItems().removeAll(attendTable.getItems());;
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
			String query1="select * from marksheet_table where student_id= '" + sid + "' and semester=" + s + "and sem_year='" + y + "';";
			s1=connection.createStatement();
			rs1=s1.executeQuery(query1);
			while(rs1.next()) {
				String cid = rs1.getString("course_id");
				String att = rs1.getString("attendance");
				String[] strAtt = att.split("/");
				int[] arrAtt = new int[strAtt.length];
				arrAtt[0] = Integer.parseInt(strAtt[0]);
				arrAtt[1] = Integer.parseInt(strAtt[1]);
				String query2 ="select name from course_table where course_id= '"+ cid +"';";
				s2=connection.createStatement();
				rs2 = s2.executeQuery(query2);
				String cname = null;
				while(rs2.next()) {
					cname = rs2.getString("name");
				}
				AttendanceList.add(new Attendance(cid,cname,arrAtt[0],arrAtt[1]));
			}
			attendTable.setItems(AttendanceList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
	
	private void initTable() {
		initCols();
	}


	private void initCols() {

		col_courseId.setCellValueFactory(cellData-> cellData.getValue().getCourseId());
		col_courseName.setCellValueFactory(cellData-> cellData.getValue().getCourseName());

		col_att.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getAttended()));
		col_total.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getTotal()));
	}
}
