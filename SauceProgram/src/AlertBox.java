// AlertBox.java
// Marshall Hannon
// 12/11/19
// This program creates a alert screen for javafx applications
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class AlertBox {
	/** This methos takes a title and message and creates an alert box*/
	public static void display(String title, String message) {
		Stage window = new Stage();
		// set the window to be priority
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setWidth(400);
		
		// create a label with the message
		Label label = new Label();
		label.setText(message);
		
		// create a button to close the window
		Button btClose = new Button("Close");
		btClose.setOnAction(e -> window.close());
		
		// create a pane for the label and button
		VBox vBox = new VBox(10);
		vBox.getChildren().addAll(label, btClose);
		vBox.setAlignment(Pos.CENTER);
		
		// add the pane to the scene and show it
		Scene scene = new Scene(vBox);
		window.setScene(scene);
		window.showAndWait(); // blocks other events
		
	}
}
