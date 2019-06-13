package sequential;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SequentialController implements Initializable {
	
	@FXML BorderPane borderPane;
	@FXML Pane pane;
	@FXML TextField text_field;
	private SequentialArray ojArr;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ojArr = new SequentialArray();
		for(CircleD c: ojArr.getArr()) {
			pane.getChildren().addAll(c,c.text);
		}
	}
	
	@FXML public void insertOnAction(ActionEvent e) {
		try {
			int check = 0;
			for(CircleD c: ojArr.getArr()) {
				if(c.getKey()==Integer.parseInt(text_field.getText().trim())) {
					check=1;
				}
			}
			if(check==1) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting value. This input value already exists in the array.",
						ButtonType.OK);
				alert.showAndWait()
						.filter(response -> response == ButtonType.OK)
						.ifPresent(response -> alert.close());
			}else {
			ojArr.insert(Integer.parseInt(text_field.getText().trim()));
			pane.getChildren().clear();
			for(CircleD c: ojArr.getArr())
			pane.getChildren().addAll(c,c.text);
			}
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting value. The input field can only accept numbers.",
					ButtonType.OK);
			alert.showAndWait()
					.filter(response -> response == ButtonType.OK)
					.ifPresent(response -> alert.close());
		}
	}
	
	@FXML public void searchOnAction(ActionEvent e) {
		try {
			ojArr.search(Integer.parseInt(text_field.getText().trim()));
//			if(ojArr.searchKeyId(Integer.parseInt(text_field.getText().trim()))==-1){
//				Alert alert = new Alert(Alert.AlertType.INFORMATION, "This value does not exist in the array.",ButtonType.OK);
//				alert.showAndWait()
//						.filter(response -> response == ButtonType.OK)
//						.ifPresent(response -> {alert.close();});
//			}
			
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(Alert.AlertType.ERROR,
					"Error searching for value. The input field can only accept numbers.", 	ButtonType.OK);

			alert.showAndWait()
					.filter(response -> response == ButtonType.OK)
					.ifPresent(response -> alert.close());

		}
	}
	
	@FXML public void deleteOnAction(ActionEvent e) {
		try {
			ojArr.delete(Integer.parseInt(text_field.getText().trim()));
			pane.getChildren().clear();
			for(CircleD c: ojArr.getArr())
			pane.getChildren().addAll(c,c.text);
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error deleting value. The input field can only accept numbers.",
					ButtonType.OK);
			alert.showAndWait()
					.filter(response -> response == ButtonType.OK)
					.ifPresent(response -> alert.close());
		}
	}
	
	@FXML public void clearOnAction(ActionEvent e) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to empty the array?", ButtonType.OK);
		alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> {ojArr.makeEmpty();pane.getChildren().clear();
				});
	}
	
	@FXML public void backOnAction(ActionEvent e) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/application/FXMLMain.fxml"));
		Scene newScene = new Scene(parent);
		Node node = (Node) e.getSource();
		Stage window = (Stage) node.getScene().getWindow();
		window.setScene(newScene);
		window.show();
	}

	
	

}
