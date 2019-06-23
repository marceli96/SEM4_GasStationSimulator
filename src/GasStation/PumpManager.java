package GasStation;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class PumpManager {
    private LinkedList<Car> listOfCars;

    public Semaphore car, pump, mutex, write;

    public PumpManager(){
        listOfCars = new LinkedList<>();
        car = new Semaphore(0);
        pump = new Semaphore(0);
        mutex = new Semaphore(1);
        write = new Semaphore(1);
    }

    public void waitInQueue(Car car){
        listOfCars.add(car);
    }

    public Car takeFirstCarFromQueue(){
        return listOfCars.poll();
    }

    public String listString(){
        String list = "";
        for (Car tmp: listOfCars) {
            list = list.concat(String.valueOf(tmp.number));
            list = list.concat(" ");
        }
        return list;
    }
}
