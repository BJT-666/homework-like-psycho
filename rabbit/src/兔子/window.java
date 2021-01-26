package 兔子;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class window implements ActionListener {
	JFrame frame = new JFrame();
	ImageIcon name = new ImageIcon("name.png");
	JButton namebutton = new JButton(name);
	ImageIcon reset = new ImageIcon("reset.png");
	JButton rebutton = new JButton(reset);
	ImageIcon tree = new ImageIcon("tree.jpg");
	ImageIcon rabbit = new ImageIcon("rabbit.jpg");
	ImageIcon flame = new ImageIcon("flame.jpg");
	ImageIcon win = new ImageIcon("win.jpg");
	ImageIcon lose = new ImageIcon("lose.jpg");
	JLabel level = new JLabel("关卡："+1);
	
	//数据结构
	int ROW = 10;
	int COL = 10;
	int[][] data = new int[ROW][COL];
	JButton[][] buttons = new JButton[ROW][COL];
	int count = 40;//will be change while level up

		
	
	
	
	public window() {
		frame.setSize(540, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		setHeader();
		
		setBottom();
		
		setButtons();
		
		//setStart();
		
		frame.setVisible(true);
	}
	
	private void setHeader() {
		namebutton.setBackground(Color.PINK);
		namebutton.setBorderPainted(false);
		namebutton.setOpaque(true);
		//namebutton.setEnabled(false);
		frame.add(namebutton, BorderLayout.NORTH);
	}
	
	private void setBottom() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		level.setOpaque(true);
		level.setBackground(Color.pink);
		level.setBorder(BorderFactory.createLineBorder(Color.pink));
		
		GridBagConstraints c1 = new GridBagConstraints(0,0,1,1,3.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(level, c1);
		
		rebutton.setBackground(Color.ORANGE);
		rebutton.setBorderPainted(false);
		GridBagConstraints c2 = new GridBagConstraints(1,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(rebutton, c2);
		rebutton.addActionListener(this);
		
		frame.add(panel, BorderLayout.SOUTH);
		
	}
	
	private void setButtons() {
		Container con = new Container();
		con.setLayout(new GridLayout(ROW,COL));
		//rabbit
		Random rand = new Random();
		int r1 = rand.nextInt(2)+4;
		int c1 = rand.nextInt(2)+4;
		data[r1][c1] = -1;
		//fire
		for(int k = 0;k<count;)
		{
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			if((data[r][c]!=-1)&&(data[r][c]!=1))
			{
				data[r][c] = 1;
				k++;
			}
		}
		//tree	
		for(int i = 0;i<ROW;i++)
		{
			for(int j = 0;j<COL;j++)
			{
				if(data[i][j]==-1)
				{
					JButton rab = new JButton(rabbit);
					rab.addActionListener(this);
					con.add(rab);
					buttons[i][j] = rab;
				}
				else if(data[i][j]==1)
				{
					JButton fire = new JButton(flame);
					fire.addActionListener(this);
					con.add(fire);
					buttons[i][j] = fire;
				}
				else
				{
					JButton green = new JButton(tree);
					green.addActionListener(this);
					con.add(green);
					buttons[i][j] = green;
				}
			}
		}
		frame.add(con, BorderLayout.CENTER);
	}
	
	private int judge(int arr[][],int a,int b) {
		int sum = 0;
		if((a-1<0)||(a+1>9)||(b-1<0)||(b+1>9)) sum = -1;
		else sum = arr[a-1][b]+arr[a+1][b]+arr[a][b-1]+arr[a][b+1]+arr[a-1][b-1]+arr[a+1][b-1]+arr[a-1][b+1]+arr[a+1][b+1];
		return sum;
	}
	
	public void win() {
		JOptionPane.showMessageDialog(frame, "YOU win!\nNew challenge is waiting for you~", "Congratulations",0, lose);
	}
	
	public void lose() {
		JOptionPane.showMessageDialog(frame, "YOU LOSE!\nPity!Please start again", "Game Over",0, win);
	}
	
	public void loop() {
		for(int i=1;i<ROW-1;i++)
			for(int j=1;j<COL-1;j++)
			{
				if(data[i][j]==-1)
				{
					//fire around
					int round = 0;
					int[] dir = {data[i-1][j],data[i+1][j],data[i][j-1],data[i][j+1],data[i-1][j-1],data[i+1][j-1],data[i-1][j+1],data[i+1][j+1]};
					for(int k=0;k<8;k++)
						if(dir[k]==1) round++;
					//mount of fire
					if(round==8) {win(); return;}//??game over
					else
					{
						//set trees
						data[i][j] = 0;
						buttons[i][j].setIcon(tree);
						//judge
						int[] m = new int[8];
						if(data[i-1][j]==0) m[0]=judge(data,i-1,j);
						else m[0] = 10;
						if(data[i+1][j]==0) m[1] = judge(data,i+1,j);
						else m[1] = 10;
						if(data[i][j-1]==0) m[2]=judge(data,i,j-1);
						else m[2] = 10;
						if(data[i][j+1]==0) m[3]=judge(data,i,j+1);
						else m[3] = 10;
						
						if(data[i-1][j-1]==0) m[4]=judge(data,i-1,j-1);
						else m[4] = 10;
						if(data[i+1][j-1]==0) m[5] = judge(data,i+1,j-1);
						else m[5] = 10;
						if(data[i-1][j+1]==0) m[6]=judge(data,i-1,j+1);
						else m[6] = 10;
						if(data[i+1][j+1]==0) m[7]=judge(data,i+1,j+1);
						else m[7] = 10;
						//
						int min = 8;
						int t;
						int[] arr = new int[8];
						for(t=0;t<8;t++)
						{
							if(m[t]<=min) min = m[t];
						}
						for(t=0;t<8;t++)										
							if(m[t]==min) arr[t] = t;										
						int mark;
						Random rand = new Random();
						//while(true)
						{										
							while(true)
							{
								int x = rand.nextInt(8);
								if(arr[x]!=0)
								{
									mark = x;
									break;
								}
							}
							if((mark==0)&&(data[i-1][j]==0))
							{
									buttons[i-1][j].setIcon(rabbit);
									data[i-1][j] = -1;
									if(i-1==0) {lose();return;}
									return;
							}
							else if((mark==1)&&(data[i+1][j]==0))
							{
									buttons[i+1][j].setIcon(rabbit);
									data[i+1][j] = -1;
									if(i+1==9) {lose();return;}
									return;
							}
							else if((mark==2)&&(data[i][j-1]==0))
							{
									buttons[i][j-1].setIcon(rabbit);
									data[i][j-1] = -1;
									if(j-1==0) {lose();return;}
									return;											
							}
							else if((mark==3)&&(data[i][j+1]==0))
							{
									buttons[i][j+1].setIcon(rabbit);
									data[i][j+1] = -1;
									if(j+1==9) {lose();return;}
									return;
							}
							else if((mark==4)&&(data[i-1][j-1]==0))
							{
									buttons[i-1][j-1].setIcon(rabbit);
									data[i-1][j-1] = -1;
									if((i-1==0)||(j-1==0)) {lose();return;}
									return;
							}
							else if((mark==5)&&(data[i+1][j-1]==0))
							{
									buttons[i-1][j+1].setIcon(rabbit);
									data[i-1][j+1] = -1;
									if((i-1==0)||(j+1==9)) {lose();return;}
									return;												
							}
							else if((mark==6)&&(data[i-1][j+1]==0))
							{
									buttons[i-1][j+1].setIcon(rabbit);
									data[i-1][j+1] = -1;
									if((i-1==0)||(j+1==9)) {lose();return;}
									return;											
							}
							else if((mark==7)&&(data[i+1][j+1]==0))
							{
									buttons[i+1][j+1].setIcon(rabbit);
									data[i+1][j+1] = -1;
									if((i+1==9)||(j+1==9)) {lose();return;}
									return;
							}
					
						}
					}
		
				}

			}
	}
	
	private void restart() {
		for(int i=0;i<ROW;i++)
			for(int j=0;j<COL;j++)
				data[i][j] = 0;
		Random rand = new Random();
		int r1 = rand.nextInt(2)+4;
		int c1 = rand.nextInt(2)+4;
		data[r1][c1] = -1;
		//fire
		for(int k = 0;k<count;)
		{
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			if((data[r][c]!=-1)&&(data[r][c]!=1))
			{
				data[r][c] = 1;
				k++;
			}
		}
		//tree
		for(int i = 0;i<ROW;i++)
		{
			for(int j = 0;j<COL;j++)
			{
				if(data[i][j]==-1)
				{
					//JButton rab = new JButton(rabbit);
					//buttons[i][j].addActionListener(this);
					buttons[i][j].setIcon(rabbit); 
				}
				else if(data[i][j]==1)
				{
					//buttons[i][j].addActionListener(this);
					buttons[i][j].setIcon(flame);
				}
				else
				{
					//buttons[i][j].addActionListener(this);
					buttons[i][j].setIcon(tree);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		new window();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			JButton click = (JButton)e.getSource();
			//click reaction
			for(int r=0;r<ROW;r++)
			{
				for(int c=0;c<COL;c++)
				{
					if((click.equals(buttons[r][c]))&&(data[r][c]==0))
					{
						click.setIcon(flame);
						data[r][c] = 1;
						loop();
						return;
					}

					}	
				}
			if(click.equals(rebutton))
			{
				restart();
				return;
			}
	}
}

