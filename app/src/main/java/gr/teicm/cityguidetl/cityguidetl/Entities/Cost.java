package gr.teicm.cityguidetl.cityguidetl.Entities;

import java.util.List;

public class Cost {

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public List<Point> getPointsTaken() {
		return pointsTaken;
	}

	public void setPointsTaken(List<Point> pointsTaken) {
		this.pointsTaken = pointsTaken;
	}

	public double cost;

	public List<Point> pointsTaken;

	public Cost() {

	}

	public Cost(double cost) {

	}
}
