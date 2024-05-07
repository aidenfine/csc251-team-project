package csc251.team.project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CarInventoryApp extends Application {

    private CarLot carLot = new CarLot();
    private TableView<Car> table = new TableView<>();
    private TextField idInput, mileageInput, mpgInput, costInput, salesPriceInput, priceSoldInput;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Inventory Manager");

        TableColumn<Car, String> idColumn = new TableColumn<>("ID");
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Car, Integer> mileageColumn = new TableColumn<>("Mileage");
        mileageColumn.setMinWidth(100);
        mileageColumn.setCellValueFactory(new PropertyValueFactory<>("mileage"));

        TableColumn<Car, Integer> mpgColumn = new TableColumn<>("MPG");
        mpgColumn.setMinWidth(50);
        mpgColumn.setCellValueFactory(new PropertyValueFactory<>("mpg"));

        TableColumn<Car, Double> costColumn = new TableColumn<>("Cost");
        costColumn.setMinWidth(100);
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<Car, Double> salesPriceColumn = new TableColumn<>("Sales Price");
        salesPriceColumn.setMinWidth(100);
        salesPriceColumn.setCellValueFactory(new PropertyValueFactory<>("salesPrice"));

        TableColumn<Car, Boolean> soldColumn = new TableColumn<>("Sold");
        soldColumn.setMinWidth(50);
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));

        table.getColumns().addAll(idColumn, mileageColumn, mpgColumn, costColumn, salesPriceColumn, soldColumn);
        table.setItems(getCars());

        idInput = new TextField();
        idInput.setPromptText("ID");
        idInput.setMinWidth(100);

        mileageInput = new TextField();
        mileageInput.setPromptText("Mileage");
        mileageInput.setMinWidth(100);

        mpgInput = new TextField();
        mpgInput.setPromptText("MPG");
        mpgInput.setMinWidth(50);

        costInput = new TextField();
        costInput.setPromptText("Cost");
        costInput.setMinWidth(100);

        salesPriceInput = new TextField();
        salesPriceInput.setPromptText("Sales Price");
        salesPriceInput.setMinWidth(100);

        priceSoldInput = new TextField();
        priceSoldInput.setPromptText("Price Sold");
        priceSoldInput.setMinWidth(100);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());

        Button sellButton = new Button("Sell");
        sellButton.setOnAction(e -> sellButtonClicked());

        Button profitButton = new Button("Total Profit");
        profitButton.setOnAction(e -> profitButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, mileageInput, mpgInput, costInput, salesPriceInput, priceSoldInput, addButton, sellButton, profitButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Car> getCars() {
        return FXCollections.observableArrayList(carLot.getInventory());
    }

    private void addButtonClicked() {
        try {
            Car car = new Car(idInput.getText(), Integer.parseInt(mileageInput.getText()), Integer.parseInt(mpgInput.getText()),
                    Double.parseDouble(costInput.getText()), Double.parseDouble(salesPriceInput.getText()));
            carLot.addCar(car.getId(), car.getMileage(), car.getMpg(), car.getCost(), car.getSalesPrice());
            table.getItems().add(car);
            clearInputs();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please ensure all fields are filled correctly with numbers for mileage, mpg, cost, and sales price.");
        }
    }

    private void sellButtonClicked() {
        try {
            ObservableList<Car> selectedCars = table.getSelectionModel().getSelectedItems();
            double priceSold = Double.parseDouble(priceSoldInput.getText());
            selectedCars.forEach(car -> {
                car.sellCar(priceSold);
                carLot.sellCar(car.getId(), car.getPriceSold());
            });
            table.refresh();
            priceSoldInput.clear();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid price for selling the car.");
        }
    }

    private void profitButtonClicked() {
        double totalProfit = carLot.getTotalProfit();
        showAlert("Total Profit", "The total profit from sold cars is: $" + totalProfit);
    }

    private void clearInputs() {
        idInput.clear();
        mileageInput.clear();
        mpgInput.clear();
        costInput.clear();
        salesPriceInput.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
