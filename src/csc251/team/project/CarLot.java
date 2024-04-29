package csc251.team.project;

import java.util.ArrayList;
import java.util.Collections;

public class CarLot {
	private ArrayList<Car> inventory; //change datatype to ArrayList
	private int numberOfCars = 0;
	private int capacity = 0;
	
	public CarLot() { 
		this(100); 
	}
	
	public CarLot(int capacity) {
		this.capacity = capacity;
		this.inventory = new ArrayList<>(); // initialize as ArrayList
	}
	
	public void addCar(String id, int mileage, int mpg, double cost, double salesPrice) {
		if (numberOfCars < capacity) {
			this.inventory.add(new Car(id, mileage, mpg, cost, salesPrice)); // add to ArrayList
			numberOfCars++;
		}
	}
	
	public ArrayList<Car> getInventory() {
		ArrayList<Car> allCars = new ArrayList<Car>(); // specified type argument explicitly
	    for (Car car : this.inventory) {
	        allCars.add(car);
	    }
	    return allCars;
	}
	
	public Car findCarByIdentifier(String identifier) {
		for (Car aCar : this.inventory) {
            if (aCar.getId().equals(identifier)) {
                return aCar;
			}
		}
		return null;
	}
	
	public void sellCar(String identifier, double priceSold) throws IllegalArgumentException {
		Car aCar = this.findCarByIdentifier(identifier);
		if (aCar != null) {
			aCar.sellCar(priceSold);
		} else {
			throw new IllegalArgumentException("No car with identifier " + identifier);
		}
	}
	
	public ArrayList<Car> getCarsInOrderOfEntry() {
        return new ArrayList<>(this.inventory); // return a copy of the ArrayList
    }
	
	public ArrayList<Car> getCarsSortedByMPG() {
		ArrayList<Car> allCars = new ArrayList<>(this.inventory); // copy inventory to ArrayList
        Collections.sort(allCars, (Car c1, Car c2) -> c2.compareMPG(c1));
        return allCars;
	}
	
	public Car getCarWithBestMPG() {
		Car rtn = null;
        int bestMpg = -1;
        for (Car aCar : this.inventory) {
            if (aCar.getMpg() > bestMpg) {
                bestMpg = aCar.getMpg();
                rtn = aCar;
			}
		}
		return rtn;
	}
	
	public Car getCarWithHighestMileage() {
		Car rtn = null;
        int highestMileage = -1;
        for (Car aCar : this.inventory) {
            if (aCar.getMileage() > highestMileage) {
                highestMileage = aCar.getMileage();
                rtn = aCar;
			}
		}
		return rtn;
	}
	
	public double getAverageMpg() {
		double totalMpg = 0D;
        for (Car aCar : this.inventory) {
            totalMpg += aCar.getMpg();
        }
        return totalMpg / this.numberOfCars;
	}
	
	public double getTotalProfit() {
		double profit = 0D;
        for (Car aCar : this.inventory) {
            profit += (aCar.isSold() ? aCar.getProfit() : 0);
		}
		return profit;
	}
	
}
