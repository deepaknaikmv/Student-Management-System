package application;
import java.io.*;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;

public class Student_MarksController implements Initializable{
	  
	private String sid;

	@FXML
	private AnchorPane marksPane;
	 
	@FXML
	private ComboBox<Integer> sem;
	    
	@FXML
	private Label sid_L;
	
	@FXML
	private Label status;
	    
	@FXML
	private Button getButton;

	@FXML
	private TableView<Marksheet> markTable;

	@FXML
	private TableColumn<Marksheet,String> col_courseId;

	@FXML
	private TableColumn<Marksheet,String> col_courseName;

	@FXML
	private TableColumn<Marksheet,Number> col_P1;

	@FXML
	private TableColumn<Marksheet,Number> col_P2;

	@FXML
	private TableColumn<Marksheet,Number> col_End;

	@FXML
	private Button updateButton;
    
	void set(String ID) {
	      this.sid = ID;
	      sid_L.setText(sid);
	}
	
	public ObservableList<Marksheet>  MarkList= FXCollections.observableArrayList();;
	Connection connection=null;
  	Statement s1=null;
 	Statement ss=null;
  	ResultSet rs=null;
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
    void getButtonOnAction(ActionEvent event) {
    	
    	markTable.getItems().removeAll(markTable.getItems());;
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		try
		{
			String s=String.valueOf(sem.getValue());
			String y=null;
			String year_query = "SELECT sem_year FROM student_sem_year WHERE student_id=\'"+sid+"\' AND semester="+s+";"; 
			ss=connection.createStatement();
			rs=ss.executeQuery(year_query);
			if (rs.next()) {
				status.setText("");
		        y = rs.getString("sem_year"); 
		    }
			else {
				status.setText("In-valid year");
			}
		
			String query1="select * from marksheet_table where student_id= '" + sid + "' and semester="+s+"and sem_year='"+y+"';";
			s1=connection.createStatement();
			rs1=s1.executeQuery(query1);
			while(rs1.next()) {
				String cid = rs1.getString("course_id");
				int p1 = rs1.getInt("p1");
				int p2 =rs1.getInt("p2");
				int end = rs1.getInt("end_sem");
				System.out.println(cid  + String.valueOf(p1) +String.valueOf(p1) + String.valueOf(end));
				String query2 ="select name from course_table where course_id= '"+ cid +"';";
				s2=connection.createStatement();
				rs2 = s2.executeQuery(query2);
				String cname = null;
				while(rs2.next()) {
					cname = rs2.getString("name");
					System.out.println(cname);
				}
				MarkList.add(new Marksheet(cid,cname,p1,p2,end));
			}
			markTable.setItems(MarkList);
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

		col_P1.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getP1()));
		col_P2.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getP2()));
		col_End.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getEnd()));
	}
}
    
