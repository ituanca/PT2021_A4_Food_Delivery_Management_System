package businessLayer;

import javafx.scene.control.Alert;
import presentationLayer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Object ordersList){
        for(Observer observer : this.observers){
            observer.update(ordersList);
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Our employees were notified about your order");
        alert.show();
    }

}
