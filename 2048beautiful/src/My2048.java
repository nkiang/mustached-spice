import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.lang.Math;

@SuppressWarnings("serial")
public class My2048 extends JFrame implements KeyListener {

	Block[] block;
	JPanel panel;
	boolean numFlag;
	int moveFlag;
	int numTotal = 25;
	int numSqrt = 5;

	public My2048() {

		numFlag = true;
		moveFlag = 0;
		block = new Block[numTotal];
		setTitle("2048Goddess");
		setSize(500, 500);
		setLocation(500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = (JPanel) getContentPane();
		panel.setLayout(new GridLayout(5, 5, 5, 5));
		addBlock();
		for (int i = 0; i < 2; i++)
			appearBlock();
		this.addKeyListener(this);

		this.setVisible(true);
	}

	public void addBlock() {
		for (int i = 0; i < numTotal; i++) {
			block[i] = new Block();
			block[i].setHorizontalAlignment(JLabel.CENTER);
			// 不透明
			block[i].setOpaque(true);
			panel.add(block[i]);
		}
	}

	public void appearBlock() {
	while(numFlag) {
			int index = (int) (Math.random() * numTotal);
			if (block[index].getValue() == 0) {
				if (Math.random() < 0.5)
					block[index].setValue(2);
				else
					block[index].setValue(4);
				break;
			}
		}

	}

	public void judgeAppear() {
		int sum = 0;
		for (int i = 0; i < numTotal; i++) {
			if (block[i].getValue() != 0)
				sum++;
		}
		if (sum == numTotal)
			numFlag = false;

	}

	public void upBlock() {

		for (int i = numSqrt*(numSqrt-1); i < numTotal; i++) {
			int index = i;
			for (int j = i - numSqrt; j >= i - numSqrt*(numSqrt-1); j -= numSqrt) {
				int valueI = block[index].getValue(), valueJ = block[j]
						.getValue();
				if (valueJ == 0) {
					block[index].setValue(0);
					block[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						block[index].setValue(0);
						block[j].setValue(valueI + valueJ);
						if (valueI + valueJ == 4096)
							win();
						numFlag = true;
						moveFlag = 0;
					} else if (numFlag == false)
						moveFlag += 1;
				}
				index = j;
			}
		}

	}

	public void downBlock() {

		for (int i = 0; i < numSqrt; i++) {
			int index = i;
			for (int j = i + numSqrt; j <= i + numSqrt*(numSqrt-1); j += numSqrt) {
				int valueI = block[index].getValue(), valueJ = block[j]
						.getValue();
				if (valueJ == 0) {
					block[index].setValue(0);
					block[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						block[index].setValue(0);
						block[j].setValue(valueI + valueJ);
						if (valueI + valueJ == 4096)
							win();
						numFlag = true;
						moveFlag = 0;
					} else if (numFlag == false)
						moveFlag += 1;
				}
				index = j;
			}
		}

	}

	public void rightBlock() {

		for (int i = 0; i <= numSqrt*(numSqrt-1); i += numSqrt) {
			int index = i;
			for (int j = i + 1; j <= i + (numSqrt-1); j++) {
				int valueI = block[index].getValue(), valueJ = block[j]
						.getValue();
				if (valueJ == 0) {
					block[index].setValue(0);
					block[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						block[index].setValue(0);
						block[j].setValue(valueI + valueJ);
						if (valueI + valueJ == 4096)
							win();
						numFlag = true;
						moveFlag = 0;
					} else if (numFlag == false)
						moveFlag += 1;
				}
				index = j;
			}
		}

	}

	public void leftBlock() {

		for (int i = (numSqrt-1); i <= (numTotal-1); i += numSqrt) {
			int index = i;
			for (int j = i - 1; j >= i - (numSqrt-1); j--) {
				int valueI = block[index].getValue(), valueJ = block[j]
						.getValue();
				if (valueJ == 0) {
					block[index].setValue(0);
					block[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						block[index].setValue(0);
						block[j].setValue(valueI + valueJ);
						if (valueI + valueJ == 4096)
							win();
						numFlag = true;
						moveFlag = 0;
					} else if (numFlag == false)
						moveFlag += 1;
				}
				index = j;
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			upBlock();
			judgeAppear();
			appearBlock();
			over();
			break;
		case KeyEvent.VK_DOWN:
			downBlock();
			judgeAppear();
			appearBlock();
			over();
			break;
		case KeyEvent.VK_LEFT:
			leftBlock();
			judgeAppear();
			appearBlock();
			over();
			break;
		case KeyEvent.VK_RIGHT:
			rightBlock();
			judgeAppear();
			appearBlock();
			over();
			break;
		}

	}

	public void over() {
		if (!numFlag && moveFlag >= 36) {
			block[numSqrt].setText("G");
			block[numSqrt+1].setText("A");
			block[numSqrt+2].setText("M");
			block[numSqrt+3].setText("E");
			block[numSqrt*2].setText("O");
			block[numSqrt*2+1].setText("V");
			block[numSqrt*2+2].setText("E");
			block[numSqrt*2+3].setText("R");
			
			block[numSqrt*2+3].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					reStart();
				}
			});
		}
	}
    
	public void win() {
		
		block[0].setText("Y");
		block[1].setText("O");
		block[2].setText("U");
		block[numTotal-3].setText("W");
		block[numTotal-2].setText("I");
		block[numTotal-1].setText("N");
		
		block[numTotal-1].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				reStart();
			}
		});

	}
    public void reStart(){
    	numFlag=true;
		moveFlag=0;
		for(int i=0;i<numTotal;i++)
			block[i].setValue(0);
    	for (int i = 0; i < 2; i++)
			appearBlock();
    }
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public static void main(String args[]) {
		try {
			UIManager
					.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch(InstantiationException e){
			e.printStackTrace();
		} catch (IllegalAccessException e){
			e.printStackTrace();
		} catch(UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		new My2048();
	}

}
