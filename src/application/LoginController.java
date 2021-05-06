package application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;

public class LoginController {
	
	@FXML
	private TextField uid;
	
	@FXML
	private PasswordField pass;
	
	@FXML
	private Label tryLabel;

	@FXML
	private Button submitButton;
	
	@FXML
	private Button cancelButton;
	
	public void cancelButtonOnAction(ActionEvent event)
	{
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void submitButtonOnAction(ActionEvent event)
	{
		tryLabel.setText("                     ");
		if(uid.getText().isBlank()==false && pass.getText().isBlank()==false)
		{
			try {
				String s=uid.getText();
				int n=s.length();
				if(n>=3)
				{
					if(s.substring(0,3).equals("ADM") || s.substring(0,3).equals("STD"))
					{
						System.out.println(s.substring(0,3));
						Connection connection=null;
						Statement s1=null;
						Statement s2=null;
						ResultSet rs1=null;
						ResultSet rs2=null;
						connectDB obj = new connectDB();
						connection = obj.get_connection();
						System.out.println("connected");
						
						try
						{
							String query1="select * from admin_table";
							s1=connection.createStatement();
							rs1=s1.executeQuery(query1);
							while(rs1.next())
							{
								if(rs1.getString(1).equals(uid.getText()) && rs1.getString(3).equals(pass.getText()))
								{
									try
									{
										Stage stage = (Stage) submitButton.getScene().getWindow();
										FXMLLoader loader =new FXMLLoader(getClass().getResource("Admin_Main.fxml"));
										Parent root = loader.load();
										Admin_MainController c = loader.getController();
										c.showInfo(uid.getText());
										stage.close();
										Stage Admin_HomeStage = new Stage();
										Scene scene = new Scene(root,1048,897);
										scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
										Admin_HomeStage.setScene(scene);
										Admin_HomeStage.show();
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
							
							String query2="select * from student_table";
							s2=connection.createStatement();
							rs2=s2.executeQuery(query2);
							while(rs2.next())
							{
								if(rs2.getString(1).equals(uid.getText()) && rs2.getString(3).equals(pass.getText()))
								{
									try
									{
										Stage stage = (Stage) submitButton.getScene().getWindow();
										FXMLLoader loader =new FXMLLoader(getClass().getResource("Student_Main.fxml"));
										Parent root = loader.load();
										Student_MainController c = loader.getController();
										c.showInfo(uid.getText());
										stage.close();
										Stage Admin_HomeStage = new Stage();
										Scene scene = new Scene(root,1048,897);
										scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
										Admin_HomeStage.setScene(scene);
										Admin_HomeStage.show();
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
							}
							
							
							tryLabel.setText("Invalid User ID or Password. Please try again.");
							uid.setText("");
							pass.setText("");
							
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						
						tryLabel.setText("Invalid User ID or Password. Please try again.");
						uid.setText("");
						pass.setText("");
					}
				}
				else
				{
					
					tryLabel.setText("Invalid User ID or Password. Please try again.");
					uid.setText("");
					pass.setText("");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			
			tryLabel.setText("Invalid User ID or Password. Please try again.");
			uid.setText("");
			pass.setText("");
		}
	}
}
