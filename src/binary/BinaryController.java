package binary;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BinaryController implements Initializable {
	
	@FXML BorderPane borderPane;
	@FXML Pane pane;
	@FXML TextField text_field;
	private BinaryArray ojArr;
	int l=0;
	int mid;
	int r;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ojArr = new BinaryArray();
		r= ojArr.getArr().size()-1;
		for(CircleD c: ojArr.getArr()) {
			pane.getChildren().addAll(c,c.text);
		}
	}
	
	@FXML public void insertOnAction(ActionEvent e) {
		try {
			if(ojArr.getArrInt().contains(Integer.parseInt(text_field.getText().trim()))) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting value. This input value already exists in the array.",
						ButtonType.OK);
				alert.showAndWait()
						.filter(response -> response == ButtonType.OK)
						.ifPresent(response -> alert.close());
			}else {
			ojArr.insert(Integer.parseInt(text_field.getText().trim()));
			pane.getChildren().clear();
			r=ojArr.getArr().size()-1;
			System.out.println("rrr:" + r);
			l=0;
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
	

	@FXML public void nextOnEvent(ActionEvent e) {
		try {
			System.out.println("l:" + l + " r: " + r + " mid: " + mid);
		if (r >= l) { 
            int mid = l + (r - l) / 2; 
            
            if (ojArr.getArr().get(mid).getKey() == Integer.parseInt(text_field.getText().trim())) {
            	ojArr.resetColor();
                ojArr.getArr().get(mid).setHighLight(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "This value has been found in index: "+ mid,ButtonType.OK);
        		alert.showAndWait()
        				.filter(response -> response == ButtonType.OK)
        				.ifPresent(response -> { ojArr.resetColor();r=ojArr.getArr().size()-1;l=0;alert.close();});
            }
            else if(ojArr.getArr().get(mid).getKey() > Integer.parseInt(text_field.getText().trim())) {
            	ojArr.resetColor();
            	r = mid -1;
            	for(int i=l;i<=mid-1;i++) {
    	    		CircleD c = ojArr.getArr().get(i);
    	        	c.setFill(Color.YELLOW);
    	        	c.setStroke(Color.RED);
            	}
            }else {
            	ojArr.resetColor();
            	l = mid +1;
            	for(int i=mid+1;i<=r;i++) {
     	    		CircleD c = ojArr.getArr().get(i);
     	        	c.setFill(Color.YELLOW);
     	        	c.setStroke(Color.RED);
     	         }
            }
        }else {
        	Alert alert = new Alert(Alert.AlertType.INFORMATION, "This value does not exist in the array.",ButtonType.OK);
		alert.showAndWait()
				.filter(response -> response == ButtonType.OK)
				.ifPresent(response -> { r=ojArr.getArr().size()-1;l=0;alert.close();});
        }
		}catch (NumberFormatException nfe) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Error inserting value. The input field can only accept numbers.",
					ButtonType.OK);
			alert.showAndWait()
					.filter(response -> response == ButtonType.OK)
					.ifPresent(response -> alert.close());
		}
	}
	
	
	@FXML public void deleteOnAction(ActionEvent e) {
		try {
			ojArr.delete(Integer.parseInt(text_field.getText().trim()));
			pane.getChildren().clear();
			r=ojArr.getArr().size()-1;
			l=0;
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
