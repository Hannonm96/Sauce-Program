// GUI.java
// Marshall Hannon
// 12/11/19
// This creates a GUI that holds records of sauces that can be added, deleted, modified, searched, and saves the data.
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import java.io.*;

public class GUI extends Application {
	// create the labels for name, quantity, and heat rating
	protected Label lblName = new Label("Sauce Name: ");
	protected Label lblQuantity = new Label("Quantity: ");
	protected Label lblRating = new Label("Heat Rating: ");
	// create the text fields for name, quantity, heat rating, and searching
	protected TextField textField1 = new TextField();
	protected TextField textField2 = new TextField();
	protected TextField textField3 = new TextField();
	protected TextField textField4 = new TextField();
	// create a table, observable array list, and import the saved class file
	protected TableView<Sauce> sauceTable = new TableView<>();
	protected ObservableList<Sauce> products = FXCollections.observableArrayList();
	protected java.io.File file = new java.io.File("sauce.txt");
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, IOException, ClassNotFoundException {
		// Set a prompt text for the text fields
		textField1.setPromptText("Sauce Name");
		textField2.setPromptText("Quantity");
		textField3.setPromptText("Heat Rating");
		textField4.setPromptText("Search");
		
		// create the pane for text
		HBox textPane = new HBox(10);
		textPane.getChildren().addAll(lblName, textField1, lblQuantity, 
				textField2, lblRating, textField3);
		textPane.setAlignment(Pos.TOP_LEFT);
		
		try {
				@SuppressWarnings("resource")
				// create a input stream for the object files
				ObjectInputStream inputObj = new ObjectInputStream(new FileInputStream(file));
				// while there is objects in the file add them to the list
				while (true) {
				    try {
				        products.add((Sauce) inputObj.readObject());
				    } catch (EOFException e) {
				    	// end when there are no more objects
				        break; 
				    }
				}
				// close the file
				inputObj.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
	
		// create the sauce name column
		TableColumn<Sauce, String> sauceName = new TableColumn<>("Sauce Name: ");
		sauceName.setMinWidth(200); // set a width
		sauceName.setCellValueFactory(new PropertyValueFactory<>("name")); // set the name attributes of the list
		sauceName.setCellFactory(TextFieldTableCell.forTableColumn()); // allow the column to be modified
		// if an edit happens and event triggers that replaces old value with a new one and refresh the table
		sauceName.setOnEditCommit(e -> {
			String newName = e.getNewValue() != null ?
					e.getNewValue() : e.getOldValue();
					((Sauce) e.getTableView().getItems()
					.get(e.getTablePosition().getRow())).setName(newName);
					sauceTable.refresh();
			});
		// create a sauce Quantity clolumn
		TableColumn<Sauce, Integer> sauceQuantity = new TableColumn<>("Quantity: ");
		sauceQuantity.setMinWidth(100); // set a width
		sauceQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity")); // set the values to the value attribute in list
		sauceQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); // allow the column to be editable
		// if an edit happens and event triggers that replaces old value with a new one and refresh the table
		sauceQuantity.setOnEditCommit(e -> {
			int newQuantity = e.getNewValue() != null ?
					e.getNewValue() : e.getOldValue();
					((Sauce) e.getTableView().getItems()
					.get(e.getTablePosition().getRow())).setQuantity(newQuantity);
					sauceTable.refresh();
			});
		// create a Heat rating column 
		TableColumn<Sauce, Integer> sauceHeatRate = new TableColumn<>("Heat Rating: ");
		sauceHeatRate.setMinWidth(100); // set a width
		sauceHeatRate.setCellValueFactory(new PropertyValueFactory<>("heatRating")); // set the values to the heat rating attribute
		sauceHeatRate.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); // allow the column to be editable
		// if an edit happens and event triggers that replaces old value with a new one and refresh the table
		sauceHeatRate.setOnEditCommit(e -> {
			int newHeat = e.getNewValue() != null ?
					e.getNewValue() : e.getOldValue();
					((Sauce) e.getTableView().getItems()
					.get(e.getTablePosition().getRow())).setHeatRating(newHeat);
					sauceTable.refresh();
			});
		// add the columns to the Table
		sauceTable.getColumns().addAll(sauceName, sauceQuantity, sauceHeatRate);
		// get data for table
		sauceTable.setItems(products);
		// set the table to be editable 
		sauceTable.setEditable(true);
		// allow multiple columns to be selected
		sauceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		// create a pane for the table
		VBox tablePane = new VBox(20);
		tablePane.getChildren().addAll(sauceTable);
		tablePane.setAlignment(Pos.CENTER);
		
		// create four buttons
		Button btAdd = new Button("Add");
		Button btDelete = new Button("Delete");
		Button btClose = new Button("Close");
		
		// create pane for buttons
		HBox buttonPane = new HBox(5);
		buttonPane.getChildren().addAll(btAdd, btDelete, textField4, btClose);
		buttonPane.setAlignment(Pos.TOP_CENTER);

		// create the border pane and add the nodes
		BorderPane pane = new BorderPane();
		pane.setTop(textPane);
		pane.setCenter(tablePane);
		pane.setBottom(buttonPane);
		
		// Create a Scene and place it in the stage
		Scene scene = new Scene(pane, 800, 400);
		primaryStage.setTitle("Sauce Program"); // Set the stage title
		primaryStage.setOnCloseRequest(e -> { // if the X button is hit call closeWindow method
			try {
				closeWindow();
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		primaryStage.setScene(scene); // Place a scene in the stage
		primaryStage.show(); // Display the stage
	
		// Add button action
		btAdd.setOnAction(e -> {
			try {
				// set the table to the list and call addButton method
				sauceTable.setItems(products);
				addButton();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		// delete button action
		btDelete.setOnAction(e -> {
			// set the table to the list and call deleteButton method
			sauceTable.setItems(products);
			deleteButton();
		});
		// close button action 
		btClose.setOnAction(e -> {
			try {
				// call the closeWindow method then close window
				closeWindow();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			primaryStage.close();
		});
		// when search field is typed in call searchField method
		textField4.setOnKeyTyped(e -> {
			searchField();
		});
	}	
		
		/** This method adds and integer to the Observable array list 
		 * @throws IOException 
		 * @throws FileNotFoundException */
		private void addButton() throws FileNotFoundException, IOException {
			// check to see if there is valid data in the textFields
			if (isEmpty(textField1) && isInt(textField2) && isInt(textField3)) {
				// create a new object with the data from the text fields
				Sauce userSauce = new Sauce(textField1.getText().substring(0,1).toUpperCase() + 
					textField1.getText().substring(1).toLowerCase(), 
					Integer.parseInt(textField2.getText()), 
					Integer.parseInt(textField3.getText()));
				products.add(userSauce); // add the object to the list
				// clear the text fields
				textField1.clear();
				textField2.clear();
				textField3.clear();
			} else {
				AlertBox.display("Error!", "The data you entered is incorrect, please try again.");
			}
		}
		
		/** This method deletes selected rows from the table*/
		private void deleteButton() {
			// create two lists one duplicate and one with selected items
			ObservableList<Sauce> sauceList = FXCollections.observableArrayList();
			ObservableList<Sauce> selectedList = FXCollections.observableArrayList();
			sauceList = sauceTable.getItems();
			selectedList = sauceTable.getSelectionModel().getSelectedItems();
			// delete each of the selected rows in the table
			selectedList.forEach(sauceList::remove);
		}
		
		/** This method searches for data in the table and returns the filtered data*/
		public void searchField() {
			// declare a filtered list with the Observable array list
			FilteredList<Sauce> filteredData = new FilteredList<>(products, e -> true);
			
			// add a listener to the search textfield 
			textField4.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate(sauce -> {
					// if text field is empty keep the list the same
					if (newValue == null || newValue.isEmpty()) {
						return true;
					} 
					String lowerCaseFilter = newValue.toLowerCase();
					// check the attributes of the sauce and filter the list
					if (sauce.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(sauce.getQuantity()).indexOf(lowerCaseFilter) != -1) {
						return true;
					} else if (String.valueOf(sauce.getHeatRating()).indexOf(lowerCaseFilter) != -1) {
						return true;
					} else {
						return false;
					}
				});
			});
			// add the filtered data into a sorted list and add it to the table 
			SortedList<Sauce> sortedList = new SortedList<>(filteredData);
			sortedList.comparatorProperty().bind(sauceTable.comparatorProperty()); // bind the sorted list to table
			sauceTable.setItems(sortedList);
		}
		
		/** This method saves the objects in the list to a file */
		private void closeWindow() throws FileNotFoundException, IOException {
			// create an output stream fro the file
			ObjectOutputStream objFile = new ObjectOutputStream(new FileOutputStream(file));
			// loop through the list and write the object to a file
			for (int i = 0; i < products.size(); i++) {
				try {
					objFile.writeObject(products.get(i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// close the object
			objFile.close();
		}
		
		/** This method check text fields for data*/
		 public boolean isEmpty(TextField data) {
			 if (data.getText().trim().length() == 0) { // check the text field
				 return false;
		        } else {
		        	return true;
		        } 
		    }
		 
		 /** Check to see if TextField is an integer */ 
		public boolean isInt(TextField data) {
			try {
			   int num = Integer.parseInt(data.getText()); // check the text field
			   return true;
			} catch(NumberFormatException e) { // catch the exception
			  return false;
			   
			}
		}

	/** Main method to start the program*/	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args); // launch javafx
	}

	

}
