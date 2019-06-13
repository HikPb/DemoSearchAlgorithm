package binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public final class BinaryArray {
	private Integer[] NUMBER_ARR = { 26,14,38,12,22,55,11,15,49,16,34,73,98,62,88};
	private List<Integer> arrInt = new ArrayList<Integer>();
	private List<CircleD> arr = new ArrayList<CircleD>();
	
	public BinaryArray() {
		createArrInt();
		createArray();
	}
	
	public void createArrInt(){
		for(Integer h : NUMBER_ARR) {
			arrInt.add(h);
		}
	}
	

	public void createArray() {
		double x=40;
		Collections.sort(arrInt);
		for (Integer number : arrInt) {
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
	public int binarySearch(List<CircleD> arr, int l, int r, Integer key) 
    { 
        if (r >= l) { 
            int mid = l + (r - l) / 2; 
            
            if (arr.get(mid).getKey() == key) {
                return mid;
            }
            else if(arr.get(mid).getKey() > key) {
        	    binarySearch(arr, l, mid - 1, key); 
            }else {
            	binarySearch(arr, mid + 1, r, key); 
            }
        }
		return -1; 
    } 

	public void insert(Integer searchKey) {
		resetColor();
		arrInt.add(searchKey);
		arr.clear();
		createArray();
		
	}

	public void delete(Integer searchKey) {
		for(int i= arr.size()-1;i>=0;i--) {
			CircleD c = arr.get(i);
			if(c.getKey()==searchKey) {
				arr.remove(c);
				arrInt.remove(searchKey);
			}
		}
		updatePoint();
		resetColor();
	}

	public void makeEmpty() {
		arr.clear();
		arrInt.clear();
		
	}
	
	public List<CircleD> getArr() {
		return arr;
	}
	public void setArr(List<CircleD> arr) {
		this.arr = arr;
	}

	public List<Integer> getArrInt() {
		return arrInt;
	}

	public void setArrInt(List<Integer> arrInt) {
		this.arrInt = arrInt;
	}
	
	
}
	

