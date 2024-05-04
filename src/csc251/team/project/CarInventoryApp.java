import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CarInventoryApp extends Application {

    private CarLot carLot = new CarLot(); // Creates an instance of CarLot to manage the car inventory.

    @Override
    public void start(Stage primaryStage) {
        ListView<Car> listView = new ListView<>(); // ListView to display cars in the inventory.
        listView.getItems().addAll(carLot.getInventory()); // Adds all cars from the CarLot to the ListView.

        // TextFields for inputting new car data.
        TextField idInput = new TextField("Enter ID");
        TextField mileageInput = new TextField("Enter Mileage");
        TextField mpgInput = new TextField("Enter MPG");
        TextField costInput = new TextField("Enter Cost");
        TextField priceInput = new TextField("Enter Sales Price");

        Button addButton = new Button("Add Car"); // Button to add a new car.
        addButton.setOnAction(e -> { // Action when the Add button is clicked.
            try {
                Car newCar = new Car(idInput.getText(), Integer.parseInt(mileageInput.getText()), Integer.parseInt(mpgInput.getText()),
                                     Double.parseDouble(costInput.getText()), Double.parseDouble(priceInput.getText()));
                carLot.addCar(newCar); // Adds the new car to the CarLot.
                listView.getItems().add(newCar); // Also adds the new car to the ListView for display.
            } catch (NumberFormatException ex) {
                System.out.println("Please enter valid numbers for mileage, mpg, cost, and price"); // Handles invalid number inputs.
            }
        });

        Button sellButton = new Button("Sell Car"); // Button to sell a selected car.
        sellButton.setOnAction(e -> {
            Car selected = listView.getSelectionModel().getSelectedItem(); // Gets the selected car from the ListView.
            if (selected != null && !selected.isSold()) { // Checks if a car is selected and it is not already sold.
                try {
                    carLot.sellCar(selected.getId(), selected.getSalesPrice()); // Attempts to sell the car.
                    listView.refresh(); // Refreshes the ListView to show updated car details.
                } catch (IllegalArgumentException ex) {
                    System.out.println("Error: " + ex.getMessage()); // Handles errors in selling the car.
                }
            }
        });

        HBox inputBox = new HBox(10, idInput, mileageInput, mpgInput, costInput, priceInput, addButton, sellButton);
        // Horizontal box to arrange inputs and buttons.

        VBox root = new VBox(10, listView, inputBox); // Vertical box to arrange all GUI elements.
        Scene scene = new Scene(root, 800, 600); // Creates the scene with a specified size.
        primaryStage.setTitle("Car Inventory System"); // Sets the title of the primary stage.
        primaryStage.setScene(scene); // Sets the scene on the primary stage.
        primaryStage.show(); // Displays the primary stage with all its contents.
    }

    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application.
    }
}
