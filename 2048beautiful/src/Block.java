import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.*;
import java.lang.Math;

@SuppressWarnings("serial")
public class Block extends JLabel {
	private int value;

	public Block() {
		value = 0;
		setFont(new Font("font", Font.PLAIN, 24));
		setBackground(Color.gray);

	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		//String text = String.valueOf(value);
		int indexStar = 0;
		for(int i = 1; i < 20; i++){
			if((int)Math.pow(2, i) == value){
				indexStar = i;
				break;      
			}
		}
		String text = starNames[indexStar];
		if (value != 0)
			setText(text);
		else
			setText("");
		setColor();
	}

	/*
	 * ImageIcon img = new ImageIcon("back.jpg"); 
  JLabel imgLabel = new JLabel(img); 
  getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE)); 
  imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight()); 
  ((JPanel)getContentPane()).setOpaque(false);
	 * */
	
	public void setColor() {
		ImageIcon img = new ImageIcon("res/drawable/hx.jgp");
		if (this.value == 0){
			img = new ImageIcon("res/drawable/hx.jgp");
			setBackground(Color.gray);
		}
		else if (this.value == 2){
			//img = new ImageIcon("res/drawable/gyy.jpg");
			setBackground(new Color(238, 228, 218));
		}
		else if (this.value == 4){
			img = new ImageIcon("res/drawable/zt.jpg");
			setBackground(new Color(238, 224, 198));
		}
		else if (this.value == 8){
			img = new ImageIcon("res/drawable/lxl.jpg");
			setBackground(new Color(243, 177, 116));
		}
		else if (this.value == 16){
			img = new ImageIcon("res/drawable/jqq.jpg");
			setBackground(new Color(243, 177, 116));
		}
		else if (this.value == 32){
			img = new ImageIcon("res/drawable/lyf.jpg");
			setBackground(new Color(248, 149, 90));
		}
		else if (this.value == 64)
			setBackground(new Color(249, 94, 50));
		else if (this.value == 128)
			setBackground(new Color(239, 207, 108));
		else if (this.value == 256)
			setBackground(new Color(239, 207, 99));
		else if (this.value == 512)
			setBackground(new Color(239, 203, 82));
		else if (this.value == 1024)
			setBackground(new Color(239, 199, 57));
		else if (this.value == 2048)
			setBackground(new Color(239, 195, 41));
		else if (this.value == 4096)
			setBackground(new Color(255, 60, 57));
		this.setIcon(img);
		this.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
	}
	
	String[] starNames = {"º«Ñ©", "¸ßÔ²Ô²", "ÕÅÍ¥", "ÁÖÎõÀÙ", "½¯ÇÚÇÚ", "ÁõÒà·Æ", "ÖÜ»ÛÃô", "°×°ÙºÏ", "»ÆÊ¥ÒÀ", "ÑîÃÝ", "Íõ×æÏÍ", "ÁÖÐÄÈç", "ÁÖÖ¾Áá", "ÑîÓ±"};

}


