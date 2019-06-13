package binary;

import javafx.animation.FillTransition;
import javafx.geometry.Point2D;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class CircleD extends Circle  {
	private Integer key;
	Text text;
	Point2D point;
	public CircleD(Integer key) {
		super();
		this.key = key;
	}
	public CircleD (Integer searchKey,double x, double y) {
		this.key=searchKey;
		this.setRadius(26); 
		this.setFill(Color.web("#35fff1"));
        this.setEffect(new Lighting());
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(3);
        this.setPoint(new Point2D(x,y));
        this.setCenterX(x);
        this.setCenterY(y);
        this.text = new Text (String.valueOf(searchKey));
        this.text.setX(x-10);
        this.text.setY(y+5);
        this.text.setFont(Font.font("Arial", 16));
        this.text.setStroke(Color.BLACK);
        //create a layout for circle with text inside
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	
	
	public Point2D getPoint() {
		return point;
	}
	public void setPoint(Point2D point) {
		this.point = point;
	}
	public void setHighLight(Boolean x) {
		if(x == true) {
			this.setFill(Color.GREEN);
			this.setStroke(Color.RED);
			FillTransition fill = new FillTransition();  
		    fill.setAutoReverse(true);  
		    fill.setCycleCount(5);  
		    fill.setDuration(Duration.millis(500));  
		    fill.setToValue(Color.YELLOW);  
		    fill.setShape(this);
		    fill.play(); 
		}
	}
	
}
