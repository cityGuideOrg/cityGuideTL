package gr.teicm.cityguidetl.cityguidetl.Entities;

import java.util.List;

public class BestRoute {
	public Point getStartingPoint() {
		return startingPoint;
	}

	public void setStartingPoint(Point startingPoint) {
		this.startingPoint = startingPoint;
	}


	public Point startingPoint;

	public List<Point> getPlacesNeedsToBeVisited() {
		return placesNeedsToBeVisited;
	}

	public void setPlacesNeedsToBeVisited(List<Point> placesNeedsToBeVisited) {
		this.placesNeedsToBeVisited = placesNeedsToBeVisited;
	}

	public List<Point> placesNeedsToBeVisited;

}
