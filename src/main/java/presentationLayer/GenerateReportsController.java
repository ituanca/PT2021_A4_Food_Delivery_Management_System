package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GenerateReportsController implements Initializable, Window {
    public Button btnTimeInterval;
    public ComboBox cbStartHour;
    public ComboBox cbEndHour;
    public Button btnGenerateReport;

    public ScrollPane scrollPaneTimeIntervalOfTheOrdersReport;
    public ListView listViewTimeIntervalOfTheOrdersReport;
    public AnchorPane anchorPaneTimeIntervalOfTheOrdersReport;

    public Button btnProductsOrdered;
    public AnchorPane anchorPaneProductsOrderedReport;
    public Button btnGenerateReportProductsOrdered;
    public ScrollPane scrollPaneProductsOrderedReport;
    public ListView listViewProductsOrderedReport;
    public TextField tfNumberOfTimes;

    public Button btnClients;
    public AnchorPane anchorPaneClientsReport;
    public Button btnGenerateReportClients;
    public ScrollPane scrollPaneClientsReport;
    public ListView listViewClientsReport;
    public TextField tfNumberOfTimesClientsOrdered;
    public TextField tfPriceOfOrder;

    public AnchorPane anchorPaneProductsOrderedInADayReport;
    public Button btnGenerateReportProductsOrderedInADay;
    public ScrollPane scrollPaneProductsOrderedInADayReport;
    public ListView listViewProductsOrderedInADayReport;
    public Button btnProductsOrderedInADay;
    public DatePicker datePicker;

    public Button btnGoBack;

    DeliveryService deliveryService = new DeliveryService();

    @Override
    public void create(Stage window, Scene scene) {
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbStartHour.getItems().addAll(generateArrayOfHours());
        cbEndHour.getItems().addAll(generateArrayOfHours());
    }

    public void openTimeIntervalOfTheOrders(ActionEvent actionEvent) {
        anchorPaneTimeIntervalOfTheOrdersReport.setVisible(true);
        anchorPaneProductsOrderedReport.setVisible(false);
        anchorPaneClientsReport.setVisible(false);
        anchorPaneProductsOrderedInADayReport.setVisible(false);
    }

    public void generateReportTimeInterval(ActionEvent actionEvent) {
        scrollPaneTimeIntervalOfTheOrdersReport.setVisible(true);
        listViewTimeIntervalOfTheOrdersReport.getItems().clear();
        List<String> report = deliveryService.generateReportOfOrders(getStartHour(), getEndHour());
        if(report.isEmpty()){
            listViewTimeIntervalOfTheOrdersReport.getItems().addAll("There are no orders in the specified interval of time");
        }else{
            listViewTimeIntervalOfTheOrdersReport.getItems().addAll(report);
        }
    }

    private ArrayList<String> generateArrayOfHours(){
        ArrayList<String> hours = new ArrayList<String>();
        for(int i = 0; i < 24; i++){
            hours.add(String.valueOf(i));
        }
        return hours;
    }

    private String getStartHour() { return (String) cbStartHour.getValue(); }

    private String getEndHour() { return (String) cbEndHour.getValue(); }

    public void openProductsOrdered(ActionEvent actionEvent) {
        anchorPaneTimeIntervalOfTheOrdersReport.setVisible(false);
        anchorPaneProductsOrderedReport.setVisible(true);
        anchorPaneClientsReport.setVisible(false);
        anchorPaneProductsOrderedInADayReport.setVisible(false);
    }

    public void generateReportProductsOrdered(ActionEvent actionEvent) {
        if(validateProductsOrderedReport()){
            scrollPaneProductsOrderedReport.setVisible(true);
            listViewProductsOrderedReport.getItems().clear();
            List<String> report = deliveryService.generateReportOfProducts(getNumberOfTimes());
            if(report.isEmpty()){
                listViewProductsOrderedReport.getItems().addAll("There are no products ordered more than the specified number of times");
            }else{
                listViewProductsOrderedReport.getItems().addAll(report);
            }
        }
    }

    private boolean validateProductsOrderedReport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(getNumberOfTimes() == -1){
            alert.setContentText("Please fill in the text field");
            alert.show();
            return false;
        }
        if(getNumberOfTimes() < 0 ){
            alert.setContentText("Please fill in the field with valid data");
            alert.show();
            return false;
        }
        return true;
    }

    private Integer getNumberOfTimes() { return Integer.parseInt(tfNumberOfTimes.getText()); }

    public void openClientsReport(ActionEvent actionEvent) {
        anchorPaneTimeIntervalOfTheOrdersReport.setVisible(false);
        anchorPaneProductsOrderedReport.setVisible(false);
        anchorPaneClientsReport.setVisible(true);
        anchorPaneProductsOrderedInADayReport.setVisible(false);
    }

    public void generateReportClients(ActionEvent actionEvent) {
        if(validateClientsReport()){
            scrollPaneClientsReport.setVisible(true);
            listViewClientsReport.getItems().clear();
            List<String> report = deliveryService.generateReportOfClients(getNumberOfTimesClientsOrdered(), getPriceOfOrder());
            if(report.isEmpty()){
                listViewClientsReport.getItems().addAll("There are no clients meeting the specified criteria");
            }else{
                listViewClientsReport.getItems().addAll(report);
            }
        }
    }

    private boolean validateClientsReport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(getNumberOfTimesClientsOrdered() == -1 || getPriceOfOrder() == -1 ){
            alert.setContentText("Please fill in both text fields");
            alert.show();
            return false;
        }
        if(getNumberOfTimesClientsOrdered() < 0 || getPriceOfOrder() < 0){
            alert.setContentText("Pleased fill in the fields with valid data");
            alert.show();
            return false;
        }
        return true;
    }

    private Integer getNumberOfTimesClientsOrdered() {
        try{
            return Integer.parseInt(tfNumberOfTimesClientsOrdered.getText());
        } catch(NumberFormatException nfe){
            return -1;
        }
    }

    private Integer getPriceOfOrder() {
        try{
            return Integer.parseInt(tfPriceOfOrder.getText());
        } catch(NumberFormatException nfe){
            return -1;
        }
    }

    public void openProductsOrderedInADay(ActionEvent actionEvent) {
        anchorPaneTimeIntervalOfTheOrdersReport.setVisible(false);
        anchorPaneProductsOrderedReport.setVisible(false);
        anchorPaneClientsReport.setVisible(false);
        anchorPaneProductsOrderedInADayReport.setVisible(true);
    }

    public void generateReportProductsOrderedInADay(ActionEvent actionEvent) {
        if(validateProductsOrderedInADayReport()){
            scrollPaneProductsOrderedInADayReport.setVisible(true);
            listViewProductsOrderedInADayReport.getItems().clear();
            List<String> report = deliveryService.generateReportOfProductsOrderedInADay(getDay());
            if(report.isEmpty()){
                listViewProductsOrderedInADayReport.getItems().addAll("There are no orders registered in the specified date");
            }else{
                listViewProductsOrderedInADayReport.getItems().addAll(report);
            }
        }
    }

    private boolean validateProductsOrderedInADayReport(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(datePicker.getValue() == null){
            alert.setContentText("Please select a day");
            alert.show();
            return false;
        }
        if(datePicker.getValue().isAfter(LocalDate.now())){
            alert.setContentText("Please select a valid date");
            alert.show();
            return false;
        }
        return true;
    }

    private LocalDate getDay() { return datePicker.getValue(); }

    public void goBack(ActionEvent actionEvent) throws IOException { goToAdministratorOptionsWindow(); }

    private void goToAdministratorOptionsWindow() throws IOException { Controller.openNextWindow("administrator", new AdministratorController()); }

}
