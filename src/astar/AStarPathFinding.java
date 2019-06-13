package astar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AStarPathFinding {
	
	//FRAME
	JFrame frame;
	//GENERAL VARIABLES
	private int cells = 20;
	private int delay = 30;
	private double dense = 0.5;
	private double density = (cells*cells)*.5;
	private int startx = -1;
	private int starty = -1;
	private int finishx = -1;
	private int finishy = -1;
	private int tool = 0;
	private int checks = 0;
	private int length = 0;
	private int WIDTH = 950;
	private final int HEIGHT = 650;
	private final int MSIZE = 600;
	private int CSIZE = MSIZE/cells;
	//UTIL ARRAYS
	private String[] tools = {"Start","Finish","Wall", "Eraser"};
	//BOOLEANS
	private boolean solving = false;
	//UTIL
	NodeA[][] map;
	Algorithm Alg = new Algorithm();
	Random r = new Random();
	//SLIDERS
	JSlider size = new JSlider(1,5,2);
	JSlider speed = new JSlider(0,500,delay);
	JSlider obstacles = new JSlider(1,100,50);
	//LABELS
	JLabel toolL = new JLabel("Toolbox");
	JLabel sizeL = new JLabel("Size:");
	JLabel cellsL = new JLabel(cells+"x"+cells);
	JLabel delayL = new JLabel("Delay:");
	JLabel msL = new JLabel(delay+"ms");
	JLabel obstacleL = new JLabel("Dens:");
	JLabel densityL = new JLabel(obstacles.getValue()+"%");
	JLabel checkL = new JLabel("Checks: "+checks);
	JLabel lengthL = new JLabel("Path Length: "+length);
	//BUTTONS
	JButton searchB = new JButton("Start Search");
	JButton resetB = new JButton("Reset");
	JButton genMapB = new JButton("Generate Map");
	JButton clearMapB = new JButton("Clear Map");
	JButton backB = new JButton("Close");
	//DROP DOWN
	JComboBox toolBx = new JComboBox(tools);
	//PANELS
	JPanel toolP = new JPanel();
	//CANVAS
	Map canvas;
	//BORDER
	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

	public AStarPathFinding() {	
		clearMap();
		initialize();
	}
	
//	public static void main(String[] args){
//		new PathFinding();
//	}
	
	
	public void generateMap() {	//tao map ngau nhien
		clearMap();	
		for(int i = 0; i < density; i++) {
			NodeA current;
			do {
				int x = r.nextInt(cells);
				int y = r.nextInt(cells);
				current = map[x][y];	// chon random node
			} while(current.getType()==2);	// check dieu kien khac wall
			current.setType(2);	//set kieu
		}
	}
	
	public void clearMap() {	
		finishx = -1;	//reset start va finish
		finishy = -1;
		startx = -1;
		starty = -1;
		map = new NodeA[cells][cells];	//tao node moi
		for(int x = 0; x < cells; x++) {
			for(int y = 0; y < cells; y++) {
				map[x][y] = new NodeA(3,x,y);	// set note
			}
		}
		reset();	
	}
	
	public void resetMap() {	
		for(int x = 0; x < cells; x++) {
			for(int y = 0; y < cells; y++) {
				NodeA current = map[x][y];
				if(current.getType() == 4 || current.getType() == 5)	
					map[x][y] = new NodeA(3,x,y);	//set empty node
			}
		}
		if(startx > -1 && starty > -1) {	//reset start finish
			map[startx][starty] = new NodeA(0,startx,starty);
			map[startx][starty].setHops(0);
		}
		if(finishx > -1 && finishy > -1)
			map[finishx][finishy] = new NodeA(1,finishx,finishy);
		reset();	
	}

	private void initialize() {	//INITIALIZE THE GUI ELEMENTS
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(WIDTH,HEIGHT);
		frame.setTitle("Demo a*algorithim");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		int space = 25;
		int buff = 45;
		
		toolP.setLayout(null);
		toolP.setBackground(new Color(144, 2, 226));
		toolP.setBounds(10,10,310,600);
		
		Border bline = BorderFactory.createLineBorder(new Color(95, 244, 244),3);
		searchB.setBounds(90,space, 120, 25);
		searchB.setBackground(new Color(144, 2, 226));
		searchB.setBorder(bline);
		searchB.setForeground(Color.WHITE);
		toolP.add(searchB);
		space+=buff;
		
		resetB.setBounds(90,space,120,25);
		resetB.setBackground(new Color(144, 2, 226));
		resetB.setBorder(bline);
		resetB.setForeground(Color.WHITE);
		toolP.add(resetB);
		space+=buff;
		
		genMapB.setBounds(90,space, 120, 25);
		genMapB.setBackground(new Color(144, 2, 226));
		genMapB.setBorder(bline);
		genMapB.setForeground(Color.WHITE);
		toolP.add(genMapB);
		space+=buff;
		
		clearMapB.setBounds(90,space, 120, 25);
		clearMapB.setBackground(new Color(144, 2, 226));
		clearMapB.setBorder(bline);
		clearMapB.setForeground(Color.WHITE);
		toolP.add(clearMapB);
		space+=40;
	
		toolL.setBounds(90,space,120,25);
		toolL.setForeground(Color.WHITE);
		toolL.setFont(new Font(Font.DIALOG,Font.BOLD, 14));
		toolP.add(toolL);
		space+=25;
		
		toolBx.setBounds(90,space,120,25);
		toolBx.setBackground(new Color(111, 247, 211));
		toolP.add(toolBx);
		space+=buff;
		
		sizeL.setBounds(60,space,40,25);
		sizeL.setForeground(Color.WHITE);
		sizeL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(sizeL);
		size.setMajorTickSpacing(10);
		size.setBounds(95,space,100,25);
		size.setBackground(new Color(144, 2, 226));
		toolP.add(size);
		cellsL.setBounds(210,space,40,25);
		cellsL.setForeground(Color.WHITE);
		cellsL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(cellsL);
		space+=buff;
		
		delayL.setBounds(60,space,50,25);
		delayL.setForeground(Color.WHITE);
		delayL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(delayL);
		speed.setMajorTickSpacing(5);
		speed.setBounds(95,space,100,25);
		speed.setBackground(new Color(144, 2, 226));
		toolP.add(speed);
		msL.setBounds(210,space,40,25);
		msL.setForeground(Color.WHITE);
		msL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(msL);
		space+=buff;
		
		obstacleL.setBounds(60,space,100,25);
		obstacleL.setForeground(Color.WHITE);
		obstacleL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(obstacleL);
		obstacles.setMajorTickSpacing(5);
		obstacles.setBounds(95,space,100,25);
		obstacles.setBackground(new Color(144, 2, 226));
		toolP.add(obstacles);
		densityL.setBounds(210,space,100,25);
		densityL.setForeground(Color.WHITE);
		densityL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(densityL);
		space+=buff;
		
		checkL.setBounds(60,space,100,25);
		checkL.setForeground(Color.WHITE);
		checkL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(checkL);
		space+=buff;
		
		lengthL.setBounds(60,space,100,25);
		lengthL.setForeground(Color.WHITE);
		lengthL.setFont(new Font(Font.DIALOG,Font.BOLD, 12));
		toolP.add(lengthL);
		space+=buff;
		
		backB.setBounds(90, space, 120, 25);
		backB.setBackground(new Color(144, 2, 226));
		backB.setBorder(bline);
		backB.setForeground(Color.WHITE);
		toolP.add(backB);
		
		frame.getContentPane().add(toolP);
		
		canvas = new Map();
		canvas.setBounds(330, 10, MSIZE+1, MSIZE+1);
		frame.getContentPane().add(canvas);
		
		searchB.addActionListener(new ActionListener() {		//action listener
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
				if((startx > -1 && starty > -1) && (finishx > -1 && finishy > -1))
					solving = true;
			}
		});
		resetB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetMap();
				Update();
			}
		});
		genMapB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateMap();
				Update();
			}
		});
		clearMapB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMap();
				Update();
			}
		});
		toolBx.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				tool = toolBx.getSelectedIndex();
			}
		});
		size.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cells = size.getValue()*10;
				clearMap();
				reset();
				Update();
			}
		});
		speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				delay = speed.getValue();
				Update();
			}
		});
		obstacles.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				dense = ((double)obstacles.getValue()/100);
				Update();
			}
		});
		backB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
//		backB.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrame frame = new JFrame();
//		        JFXPanel jfxPanel = new JFXPanel();
//		        Platform.runLater(() -> {
//		            Parent root;
//					try {
//						root = FXMLLoader.load(getClass().getResource("/application/FXMLMain.fxml"));
//						Scene scene = new Scene(root);
//				        jfxPanel.setScene(scene);
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//		     
//		        });
//		        frame.add(jfxPanel);
//		        frame.setSize(900, 600);
//		        frame.setVisible(true);
//		    }     
//			});
//		
		startSearch();	
	}
	
	
	
	public void startSearch() {	
		if(solving) {
			Alg.AStar();
		}
		pause();	
	}
	
	public void pause() {	//pause
		int i = 0;
		while(!solving) {
			i++;
			if(i > 500)
				i = 0;
			try {
				Thread.sleep(1);
			} catch(Exception e) {}
		}
		startSearch();	
	}
	
	public void Update() {	//Update cac gia tri
		density = (cells*cells)*dense;
		CSIZE = MSIZE/cells;
		canvas.repaint();
		cellsL.setText(cells+"x"+cells);
		msL.setText(delay+"ms");
		lengthL.setText("Path Length: "+length);
		densityL.setText(obstacles.getValue()+"%");
		checkL.setText("Checks: "+checks);
	}
	
	public void reset() {	//Reset 
		solving = false;
		length = 0;
		checks = 0;
	}
	
	public void delay() {	//delay
		try {
			Thread.sleep(delay);
		} catch(Exception e) {}
	}
	
	class Map extends JPanel implements MouseListener, MouseMotionListener{	
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Map() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		public void paintComponent(Graphics g) {	//In
			super.paintComponent(g);
			for(int x = 0; x < cells; x++) {	//In cac node vao grid
				for(int y = 0; y < cells; y++) {
					switch(map[x][y].getType()) {
						case 0:
							g.setColor(Color.GREEN);
							break;
						case 1:
							g.setColor(Color.RED);
							break;
						case 2:
							g.setColor(Color.BLACK);
							break;
						case 3:
							g.setColor(Color.WHITE);
							break;
						case 4:
							g.setColor(Color.CYAN);
							break;
						case 5:
							g.setColor(Color.YELLOW);
							break;
					}
					g.fillRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					g.setColor(Color.BLACK);
					g.drawRect(x*CSIZE,y*CSIZE,CSIZE,CSIZE);
					//DEBUG STUFF
					/*
					if(curAlg == 1)
						g.drawString(map[x][y].getHops()+"/"+map[x][y].getEuclidDist(), (x*CSIZE)+(CSIZE/2)-10, (y*CSIZE)+(CSIZE/2));
					else 
						g.drawString(""+map[x][y].getHops(), (x*CSIZE)+(CSIZE/2), (y*CSIZE)+(CSIZE/2));
					*/
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				int x = e.getX()/CSIZE;	
				int y = e.getY()/CSIZE;
				NodeA current = map[x][y];
				if((tool == 2 || tool == 3) && (current.getType() != 0 && current.getType() != 1))
					current.setType(tool);
				Update();
			} catch(Exception z) {}
		}

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			resetMap();	
			try {
				int x = e.getX()/CSIZE;	//Lay gia tri toa do x,y cua chuot
				int y = e.getY()/CSIZE;
				NodeA current = map[x][y];
				switch(tool ) {
					case 0: {	//START NODE
						if(current.getType()!=2) {	//check dk co phai wall
							if(startx > -1 && starty > -1) {	//set start neu ton tai
								map[startx][starty].setType(3);
								map[startx][starty].setHops(-1);
							}
							current.setHops(0);
							startx = x;	//set toa do start
							starty = y;
							current.setType(0);	//set kieu
						}
						break;
					}
					case 1: {//node finish
						if(current.getType()!=2) {
							if(finishx > -1 && finishy > -1)	
								map[finishx][finishy].setType(3);
							finishx = x;	//toa do finish
							finishy = y;
							current.setType(1);	//set kieu
						}
						break;
					}
					default:
						if(current.getType() != 0 && current.getType() != 1)
							current.setType(tool);
						break;
				}
				Update();
			} catch(Exception z) {}
		}

		@Override
		public void mouseReleased(MouseEvent e) {}
	}
	
	class Algorithm {
		public void AStar() {
			ArrayList<NodeA> priority = new ArrayList<NodeA>();
			priority.add(map[startx][starty]);
			while(solving) {
				if(priority.size() <= 0) {
					solving = false;
					break;
				}
				int hops = priority.get(0).getHops()+1;
				ArrayList<NodeA> explored = exploreNeighbors(priority.get(0),hops);
				if(explored.size() > 0) {
					priority.remove(0);
					priority.addAll(explored);
					Update();
					delay();
				} else {
					priority.remove(0);
				}
				sortQue(priority);	
			}
		}
		
		public ArrayList<NodeA> sortQue(ArrayList<NodeA> sort) {	//sap xep node lan can theo ham uoc huong khoang cach
			int c = 0;
			while(c < sort.size()) {
				int sm = c;
				for(int i = c+1; i < sort.size(); i++) {
					if(sort.get(i).getEuclidDist()+sort.get(i).getHops() < sort.get(sm).getEuclidDist()+sort.get(sm).getHops())
						sm = i;
				}
				if(c != sm) {
					NodeA temp = sort.get(c);
					sort.set(c, sort.get(sm));
					sort.set(sm, temp);
				}	
				c++;
			}
			return sort;
		}
		
		public ArrayList<NodeA> exploreNeighbors(NodeA current, int hops) {	//duyet neighbor
			ArrayList<NodeA> explored = new ArrayList<NodeA>();	//danh sach duyet
			for(int a = -1; a <= 1; a++) {
				for(int b = -1; b <= 1; b++) {
					int xbound = current.getX()+a;
					int ybound = current.getY()+b;
					if((xbound > -1 && xbound < cells) && (ybound > -1 && ybound < cells)) {	//check dieu kien de node nam trong grid
						NodeA neighbor = map[xbound][ybound];
						if((neighbor.getHops()==-1 || neighbor.getHops() > hops) && neighbor.getType()!=2) {	//check node khac wall va chua duoc duyet
							explore(neighbor, current.getX(), current.getY(), hops);	//duyet node
							explored.add(neighbor);	//them vao list
						}
					}
				}
			}
			return explored;
		}
		
		public void explore(NodeA current, int lastx, int lasty, int hops) {	//kiem tra node
			if(current.getType()!=0 && current.getType() != 1)	//check node != start va finish
				current.setType(4);	//set kieu
			current.setLastNodeA(lastx, lasty);	//set node truoc
			current.setHops(hops);	//set hops cho node
			checks++;
			if(current.getType() == 1) {	//lay duong di khi gap finish
				backtrack(current.getLastX(), current.getLastY(),hops);
			}
		}
		
		public void backtrack(int lx, int ly, int hops) {	
			length = hops;
			while(hops > 1) {	
				NodeA current = map[lx][ly];
				current.setType(5);
				lx = current.getLastX();
				ly = current.getLastY();
				hops--;
			}
			solving = false;
		}
	}
	
	class NodeA {
		
		// 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath
		private int cellType = 0;
		private int hops;
		private int x;
		private int y;
		private int lastX;
		private int lastY;
		private double dToEnd = 0;
	
		public NodeA(int type, int x, int y) {	
			cellType = type;
			this.x = x;
			this.y = y;
			hops = -1;
		}
		
		public double getEuclidDist() {		//tinh toan khoang cach
			int xdif = Math.abs(x-finishx);
			int ydif = Math.abs(y-finishy);
			dToEnd = Math.sqrt((xdif*xdif)+(ydif*ydif));
			return dToEnd;
		}
		
		public int getX() {return x;}		
		public int getY() {return y;}
		public int getLastX() {return lastX;}
		public int getLastY() {return lastY;}
		public int getType() {return cellType;}
		public int getHops() {return hops;}
		
		public void setType(int type) {cellType = type;}		
		public void setLastNodeA(int x, int y) {lastX = x; lastY = y;}
		public void setHops(int hops) {this.hops = hops;}
	}
}
