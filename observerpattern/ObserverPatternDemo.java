package observerpattern;

import java.util.ArrayList;
import java.util.List;

interface Observer{
     void update(float temperature, float humidity, float pressure);

}

interface Publisher {
     void registerObserver(Observer o);
     void removeObserver(Observer o);
     void notifyObservers();

}

// All the observers maintain a ref to the subject or publisher interface and 
// subscribe for it.
class displayConditions implements Observer {

    private Publisher publisher;
    private float temp;
    private float pressure;
    private float humid;

    public displayConditions(Publisher publisher){
        this.publisher = publisher;
        publisher.registerObserver(this);

    }

    public void update(float temperature, float humidity, float pressure) {
        this.temp = temperature;
        this.pressure = pressure;
        this.humid = humidity;
        display();
    }
    public void display(){
        System.out.println("Current conditions: " + temp + 
        "F degrees and " + humid + "% humidity");
    }
}

//Concerete Subject or Publisher 
class WeatherStation implements Publisher {
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherStation(){
        observers = new ArrayList<>();

    }

    public void registerObserver(Observer o){
        observers.add(o);

    }

    public void removeObserver(Observer o){
        int i = observers.indexOf(o);
        if (i >=0){
            observers.remove(o);
        }
    }

    public void temperatureChange(){
        notifyObservers();
    }
    // For any change that happened in weatherStation invoke update method
    // implemented in all the observer class.
    public void notifyObservers() {
        for (Observer o: observers){
            o.update(temperature, humidity, pressure);
        }

    }

    public void weatherChange(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;

        temperatureChange();
    }


}

public class ObserverPatternDemo {
    public static void main(String[] args)
    {
        // Create  Subject
        WeatherStation w = new WeatherStation();
        // Create  Observer
        displayConditions display = new displayConditions(w);
        w.weatherChange(39.89f,100.20f,98.60f);

        // remove this observer
        w.removeObserver(display);
    }
}