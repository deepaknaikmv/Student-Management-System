package application;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.converter.NumberStringConverter;

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
public class Admin_AttendanceController implements Initializable{

private String sid;
@FXML
private AnchorPane attendancePane;

@FXML
private ComboBox<Integer> sem;

@FXML
private TextField stud_id;

@FXML
private TextField year;

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
private Button updateButton;

@FXML
private Button getButton;

public void set(String id)
{
	sid=id;
}

public ObservableList<Attendance>  AttendanceList= FXCollections.observableArrayList();;
	Connection connection=null;
 	Statement s1=null;
 	ResultSet rs1=null;
 	Statement s2=null;
 	ResultSet rs2=null;
 	Statement s3=null;
 	int rs3;
 	
 	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<Integer> semList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8);
    	sem.setItems(semList);

    	initTable();

	}
	
	
	
	@FXML
    public void getButtonOnAction(ActionEvent event) throws IOException
    {
		attendTable.getItems().removeAll(attendTable.getItems());
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			String sid=stud_id.getText();
			String s=String.valueOf(sem.getValue());
			String y=year.getText();
			String query1="select * from marksheet_table where student_id= '" + sid + "' and semester=" + s + "and sem_year='" + y+ "';";
			s1=connection.createStatement();
			rs1=s1.executeQuery(query1);
			while(rs1.next()) {
				String cid = rs1.getString("course_id");
				String att = rs1.getString("attendance");
				int[] arrAtt= new int[2];
				if(att.equals("NA"))
				{
					arrAtt[0]=0;
					arrAtt[1]=0;
				}
				else
				{
					String[] strAtt = att.split("/");
					arrAtt = new int[strAtt.length];
					arrAtt[0] = Integer.parseInt(strAtt[0]);
					arrAtt[1] = Integer.parseInt(strAtt[1]);
				}
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
	
	@FXML
    public void updateButtonOnAction(ActionEvent event) throws IOException
    {
		String sid=stud_id.getText();
		String s=String.valueOf(sem.getValue());
		String y=year.getText();
		System.out.println(sid);
		System.out.println(s);
		System.out.println(y);
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			for(int i = 0; i< AttendanceList.size();i++) {
				Attendance att = AttendanceList.get(i);
				String strAttendance = String.valueOf(att.getAttended()) + "/" + String.valueOf(att.getTotal());
				
				String query3 ="update marksheet_table set attendance='" + strAttendance + "' where student_id='"+ sid + "' and course_id = '"+ att.getCourseId().get() + "' and semester= "+ s +
						 " and sem_year = '"+ y+ "';";
				s3=connection.createStatement();
				rs3 = s3.executeUpdate(query3);
			}
			
			
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
		editableCols();
	}


	private void editableCols() {

		col_att.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		col_att.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setAttended(e.getNewValue().intValue());
		});
		
		col_total.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		col_total.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setTotal(e.getNewValue().intValue());
		});
		
		attendTable.setEditable(true);
	}

	
}
