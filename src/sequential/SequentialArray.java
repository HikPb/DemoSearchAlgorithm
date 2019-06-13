package sequential;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public final class SequentialArray {

	private static final Integer[] NUMBERS_ARRAY = { 26,14,38,12,22,55,10,15,24,69,34,73,99,62,88};

	private List<CircleD> arr = new ArrayList<CircleD>();
	private CircleD insertCircle;        // Insert circle 
	
	


	public SequentialArray() {
		createArray();
	}
	

	public void createArray() {
		double x=40;
		for (Integer number : NUMBERS_ARRAY) {
			CircleD circle = new CircleD(number,x,250);
			x=x+55;
			arr.add(circle);
		}
	}
	
	public void updatePoint() {
		for(CircleD c: arr) {
			c.setCenterX(arr.indexOf(c)*55+40);
	        c.setCenterY(250);
	        c.text.setX(arr.indexOf(c)*55+30);
	        c.text.setY(255);
	        c.point= new Point2D(arr.indexOf(c)*55+40,250);
		}
	}
	 
	public void resetColor() {
		for(CircleD c: arr) {
		c.setFill(Color.web("#35fff1"));
        c.setEffect(new Lighting());
        c.setStroke(Color.WHITE);
		}
	}

	public Integer searchKeyId(Integer k) {
		for(CircleD c: arr) {
			if(c.getKey()== k) {
				return arr.indexOf(c);
			}
		}
		return -1;
	}
	public void search(Integer searchKey) {
		Timeline timeline = new Timeline();
		int i =searchKeyId(searchKey);
		if(i==-1) {
			timeline.setCycleCount(arr.size());
		}else {
			timeline.setCycleCount(i+1);
		}
		resetColor();
		final Iterator<CircleD> iter = arr.iterator();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), ev->{
			CircleD c = iter.next();
			if(c!=null) {
				if(c.getKey()==searchKey) {
					c.setFill(Color.YELLOW);
					c.setStroke(Color.RED);
				}else {
					c.setFill(Color.web("#d6c8c7"));
				}
			}
		}));
        timeline.play();
	}	
	
	public void insert(Integer searchKey) {
		resetColor();
		if(arr.size()>0) {
			double x = arr.get(arr.size()-1).getPoint().getX();
			insertCircle = new CircleD(searchKey, x+55, 250);
			arr.add(insertCircle);
		}else {
			insertCircle = new CircleD(searchKey,40,250);
			arr.add(insertCircle);
		}
		
	}

	public void delete(Integer searchKey) {
		for(int i= arr.size()-1;i>=0;i--) {
			CircleD c = arr.get(i);
			if(c.getKey()==searchKey) {
				arr.remove(c);
			}
		}
		updatePoint();
		resetColor();
	}

	public void makeEmpty() {
		arr.clear();
	}
	
	public List<CircleD> getArr() {
		return arr;
	}
	public void setArr(List<CircleD> arr) {
		this.arr = arr;
	}
	
}
	

