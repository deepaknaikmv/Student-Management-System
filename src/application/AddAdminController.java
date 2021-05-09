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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
public class AddAdminController implements Initializable{

    @FXML
    private AnchorPane marksPane;

    @FXML
    private TextField addid;

    @FXML
    private TextField addname;

    @FXML
    private TextField addpassword;

    @FXML
    private TextField addemail;

    @FXML
    private TextField addpno;

    @FXML
    private TextField adddob;

    @FXML
    private ComboBox<String> addgender;

    @FXML
    private ComboBox<String> addtype;

    @FXML
    private Button addButton;
    
    @FXML
    private Button cancelButton;

    @FXML
    private Label slabel;
    
    Connection connection=null;
  	Statement s1=null,s2=null;
  	ResultSet rs1=null,rs2=null;
    
    @Override
	public void initialize(URL url, ResourceBundle rb)
	{
    	addgender.setPromptText("Select gender");
    	addtype.setPromptText("Select type");
    	 List<String> list = new ArrayList<String>();
         list.add("M");
         list.add("F");
         List<String> list2 = new ArrayList<String>();
         list2.add("Y");
         list2.add("N");
         ObservableList<String> obList = FXCollections.observableList(list);
         addgender.getItems().clear();
         addgender.setItems(obList);
         ObservableList<String> obList2 = FXCollections.observableList(list2);
         addtype.getItems().clear();
         addtype.setItems(obList2);
	}
    
    @FXML
	private void addButtonOnAction(ActionEvent event) throws IOException, SQLException
	{
    	connectDB obj = new connectDB();
		connection = obj.get_connection();
		int x=0;
		try
		{
			if(addid.getText().isBlank()==false && addname.getText().isBlank()==false && addpassword.getText().isBlank()==false && addemail.getText().isBlank()==false && addemail.getText().isBlank()==false && adddob.getText().isBlank()==false && addgender.getValue().isBlank()==false && addtype.getValue().isBlank()==false)
	    	{
				String query1="select admin_id from admin_table";
	    		s2=connection.createStatement();
				rs2=s2.executeQuery(query1);
				while(rs2.next())
				{
					if(rs2.getString(1).equals(addid.getText()))
					{
						x=1;
					}
				}
				if(x==0)
				{
		    		String query="insert into admin_table values ('"+addid.getText()+"','"+addname.getText()+"','"+addpassword.getText()+"','"+addemail.getText()+"','"+String.valueOf(addgender.getValue())+"','"+addpno.getText()+"','"+adddob.getText()+"','"+String.valueOf(addtype.getValue())+"');";
		    		s1=connection.createStatement();
					s1.executeQuery(query);
					Stage stage = (Stage) addButton.getScene().getWindow();
					stage.close();
				}
				else
				{
					slabel.setText("Admin ID already exits");
				}
	    	}
	    	else
	    	{
	    		slabel.setText("Missing fields");
	    	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    
    @FXML
  	private void cancelButtonOnAction(ActionEvent event) throws IOException, SQLException
  	{
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
  	}
}
