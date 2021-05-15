package presentationLayer;

import businessLayer.DeliveryService;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController {
    private static Stage nextWindow;
    public TextField tfTitle;
    public TextField tfRating;
    public TextField tfCalories;
    public TextField tfProtein;
    public TextField tfFat;
    public TextField tfSodium;
    public TextField tfPrice;
    public Button btnAddProduct;

    DeliveryService deliveryService = new DeliveryService();

    public static void create(Stage window, Scene scene){
        window.setScene(scene);
        window.show();
        nextWindow = window;
    }

    private boolean validate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(getTitle().isEmpty() || tfRating.getText().equals("") || tfCalories.getText().equals("") || tfProtein.getText().equals("") || tfFat.getText().equals("") ||
                tfSodium.getText().equals("") || tfPrice.getText().equals("")){
            alert.setContentText("Please fill in all the fields");
            alert.show();
            return false;
        }
        return true;
    }

    public void addProduct(ActionEvent actionEvent) {
        if(validate()){
            deliveryService.addProduct(getTitle(), getRating(), getCalories(), getProtein(), getFat(), getSodium(), getPrice());
        }
    }

    private String getTitle() { return tfTitle.getText(); }

    private Double getRating() {
        try {
            return Double.parseDouble(tfRating.getText());
        }catch(NumberFormatException nfe){
            return (double) -1;
        }
    }

    private Integer getCalories() { return Integer.parseInt(tfCalories.getText()); }

    private Integer getProtein() { return Integer.parseInt(tfProtein.getText()); }

    private Integer getFat() { return Integer.parseInt(tfFat.getText()); }

    private Integer getSodium() { return Integer.parseInt(tfSodium.getText()); }

    private Integer getPrice() { return Integer.parseInt(tfPrice.getText()); }

}
