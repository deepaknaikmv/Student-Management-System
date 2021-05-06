package application;

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
public class Admin_MarksController implements Initializable{
	
	private String sid;

	 @FXML
	    private AnchorPane marksPane;

	    @FXML
	    private TextField stud_id;

	    @FXML
	    private ComboBox<Integer> sem;

	    @FXML
	    private TextField year;

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

	    
	public void set(String id)
	{
		sid=id;
	}
	
    public ObservableList<Marksheet>  MarkList= FXCollections.observableArrayList();;
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
		markTable.getItems().removeAll(markTable.getItems());
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
				int p1 = rs1.getInt("p1");
				int p2 =rs1.getInt("p2");
				int end = rs1.getInt("end_sem");
				String query2 ="select name from course_table where course_id= '"+ cid +"';";
				s2=connection.createStatement();
				rs2 = s2.executeQuery(query2);
				String cname = null;
				while(rs2.next()) {
					cname = rs2.getString("name");
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
			for(int i = 0; i< MarkList.size();i++) {
				Marksheet mark = MarkList.get(i);
				
				System.out.println(String.valueOf(mark.getP1()));
				System.out.println(mark.getCourseId().get());
				String g ="NA";
				if(mark.getEnd() != -1) {
					
					double avg = mark.getP1()+mark.getP2() + mark.getEnd();
					if(avg>=90)
			        {
			            g = "A";
			        }
					else if(avg>=80 && avg<90)
			        {
			           g = "B";
			        } 
			        else if(avg>=70 && avg<80)
			        {
			           g = "C";
			        } 
			        else if(avg>=60 && avg<70)
			        {
			            g = "D";
			        }
			        else if(avg>=50 && avg<60)
			        {
			            g = "E";
			        }
			        else
			        {
			            g ="F";
			        }
				}
				String query3 ="update marksheet_table set p1=" + String.valueOf(mark.getP1()) + ", p2= " + String.valueOf(mark.getP2()) + ", end_sem =" + 
						 String.valueOf(mark.getEnd()) + ", grade='"+g+"' where student_id='"+ sid + "' and course_id = '"+ mark.getCourseId().get() + "' and semester= "+ s +
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

		col_P1.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getP1()));
		col_P2.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getP2()));
		col_End.setCellValueFactory(cellData-> new SimpleIntegerProperty(cellData.getValue().getEnd()));
		editableCols();
	}


	private void editableCols() {

		col_P1.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		col_P1.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setP1(e.getNewValue().intValue());
		});
		
		col_P2.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		col_P2.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setP2(e.getNewValue().intValue());
		});
		
		col_End.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		col_End.setOnEditCommit(e->{
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setEnd(e.getNewValue().intValue());
		});
		
		markTable.setEditable(true);
	}
	
	
}
