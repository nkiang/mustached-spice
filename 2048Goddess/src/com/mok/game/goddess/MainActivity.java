package com.mok.game.goddess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	final int RIGHT = 0;
	final int LEFT = 1;
	final int TOP = 2;
	final int BOTTOM = 3;
	
	private GestureDetector gestureDetector;
	LinearLayout main_linear;
	GridLayout main_grid;
	Button main_start;
	TextView main_score;
	TextView main_highest_score;
	
	Block[] blocks;
	Block block;

	boolean numFlag;	//is fill the container full
//	int moveFlag;
	int numTotal = 25;
	int numSqrt = 5;
	int endStore = 8192;
	int blockWidth;
	int currentScore = 0;
	boolean isGameOver = false;
	boolean leftFlag = true, rightFlag = true, upFlag = true, downFlag = true;

	//** Called when the activity is first created. **//
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViews();
		setContent();
		gestureDetector = new GestureDetector(MainActivity.this, onGestureListener);
	}
	
	private void findViews(){
		main_linear = (LinearLayout)findViewById(R.id.main_linear);
		main_grid = (GridLayout)findViewById(R.id.main_grid);
		main_start = (Button)findViewById(R.id.main_btn_start);
		main_score = (TextView)findViewById(R.id.main_txt_score);
		main_highest_score = (TextView) findViewById(R.id.main_txt_highest_score);
	}
	private void setContent(){
		//init data
		numFlag = true;
//		moveFlag = 0;
		leftFlag = true; rightFlag = true; upFlag = true; downFlag = true;
		//container layout
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		params.width = GridLayout.LayoutParams.MATCH_PARENT;
//		params.height = params.width;
		main_grid.setColumnCount(numSqrt);
		main_grid.setRowCount(numSqrt);
		main_grid.setLayoutParams(params);
		//
		WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
		int widthScreen = wm.getDefaultDisplay().getWidth();
		blockWidth = Math.round(widthScreen / numSqrt);
		//
		blocks = new Block[numTotal];
		addBlock();
		for (int i = 0; i < 2; i++)
			appearBlock();
		//
		setBtnClick();
		
		File storeFile = new File(getFilesDir() + "/2048");
		try{
			if(!storeFile.exists())
				storeFile.createNewFile();
			else{
				FileInputStream inputStream = new FileInputStream(storeFile);
	            byte[] b = new byte[inputStream.available()];
	            inputStream.read(b);
	            main_highest_score.setText(new String(b));
			}
		}catch(Exception e){
			Log.e("MainActivity", e.getMessage());
		}
		if(main_highest_score.getText().equals(""))
			main_highest_score.setText("0");
	}
	
	public void addBlock() {
		for (int i = 0; i < numTotal; i++) {
			blocks[i] = new Block(this);
			
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(blockWidth, blockWidth);
			blocks[i].setLayoutParams(params);
			// 透明度
			//blocks[i].setAlpha(1);
			//指定该组件所在的行
			GridLayout.Spec rowSpec = GridLayout.spec(i / 5 );
			//指定该组件所在的列
		    GridLayout.Spec columnSpec = GridLayout.spec( i % 5);
		    GridLayout.LayoutParams paramsGrid = new GridLayout.LayoutParams(rowSpec,columnSpec);
		    paramsGrid.setGravity(Gravity.FILL);
			main_grid.addView(blocks[i], paramsGrid);
		}
	}
	//random appear a block
	public void appearBlock() {
		while(numFlag) {
			int index = (int) (Math.random() * numTotal);
			if (blocks[index].getValue() == 0) {
				if (Math.random() < 0.5)
					blocks[index].setValue(2);
				else
					blocks[index].setValue(4);
				break;
			}
		}
	}
	// judge the game if is continue
	public void judgeAppear() {
		int sum = 0;
		for (int i = 0; i < numTotal; i++) {
			if (blocks[i].getValue() != 0)
				sum++;
		}
		if (sum == numTotal)
			numFlag = false;
	}
	
	public void upBlock() {
		if(isGameOver)
			return;

		for (int i = numSqrt*(numSqrt-1); i < numTotal; i++) {
			int index = i;
			for (int j = i - numSqrt; j >= i - numSqrt*(numSqrt-1); j -= numSqrt) {
				int valueI = blocks[index].getValue(), valueJ = blocks[j].getValue();
				if (valueJ == 0) {
					blocks[index].setValue(0);
					blocks[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						blocks[index].setValue(0);
						blocks[j].setValue(valueI + valueJ);
						if (valueI + valueJ == endStore)
							win();
						numFlag = true;
//						moveFlag = 0;
						setMoveFlag(0);
						setScore(valueI+valueJ);
					} else if (numFlag == false)
//						moveFlag += 1;
						setMoveFlag(3);
				}
				index = j;
			}
		}

	}

	public void downBlock() {
		if(isGameOver)
			return;

		for (int i = 0; i < numSqrt; i++) {
			int index = i;
			for (int j = i + numSqrt; j <= i + numSqrt*(numSqrt-1); j += numSqrt) {
				int valueI = blocks[index].getValue(), valueJ = blocks[j].getValue();
				if (valueJ == 0) {
					blocks[index].setValue(0);
					blocks[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						blocks[index].setValue(0);
						blocks[j].setValue(valueI + valueJ);
						if (valueI + valueJ == endStore)
							win();
						numFlag = true;
//						moveFlag = 0;
						setMoveFlag(0);
						setScore(valueI+valueJ);
					} else if (numFlag == false)
//						moveFlag += 1;
						setMoveFlag(4);
				}
				index = j;
			}
		}

	}

	public void rightBlock() {
		if(isGameOver)
			return;

		for (int i = 0; i <= numSqrt*(numSqrt-1); i += numSqrt) {
			int index = i;
			for (int j = i + 1; j <= i + (numSqrt-1); j++) {
				int valueI = blocks[index].getValue(), valueJ = blocks[j].getValue();
				if (valueJ == 0) {
					blocks[index].setValue(0);
					blocks[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						blocks[index].setValue(0);
						blocks[j].setValue(valueI + valueJ);
						if (valueI + valueJ == endStore)
							win();
						numFlag = true;
//						moveFlag = 0;
						setMoveFlag(0);
						setScore(valueI+valueJ);
					} else if (numFlag == false)
//						moveFlag += 1;
						setMoveFlag(2);
				}
				index = j;
			}
		}

	}

	public void leftBlock() {
		if(isGameOver)
			return;

		for (int i = (numSqrt-1); i <= (numTotal-1); i += numSqrt) {
			int index = i;
			for (int j = i - 1; j >= i - (numSqrt-1); j--) {
				int valueI = blocks[index].getValue(), valueJ = blocks[j].getValue();
				if (valueJ == 0) {
					blocks[index].setValue(0);
					blocks[j].setValue(valueI);
				} else {
					if (valueI == valueJ) {
						blocks[index].setValue(0);
						blocks[j].setValue(valueI + valueJ);
						if (valueI + valueJ == endStore)
							win();
						numFlag = true;
//						moveFlag = 0;
						setMoveFlag(0);
						setScore(valueI+valueJ);
					} else if (numFlag == false)
//						moveFlag += 1;
						setMoveFlag(1);
				}
				index = j;
			}
		}
	}
	
	public void setMoveFlag(int num){
		switch(num){
		case 0:
			leftFlag = true; rightFlag = true; upFlag = true; downFlag = true;
			break;
		case 1:
			leftFlag = false;
			break;
		case 2:
			rightFlag = false;
			break;
		case 3:
			upFlag = false;
			break;
		case 4:
			downFlag = false;
			break;
		}
	}
	
	public void over() {
//		if (!numFlag && moveFlag >= 36) {
		if (!numFlag && !upFlag && !downFlag && !leftFlag && !rightFlag ) {
			blocks[numSqrt+2].setValue(0);
			blocks[numSqrt*2+2].setValue(0);

			blocks[numSqrt+2].setText("GAME");
			blocks[numSqrt*2+2].setText("OVER");

			main_start.setText(R.string.start);
			setHighScore();
			isGameOver = true;
		}
	}
    
	public void win() {
		
		blocks[numSqrt+2].setValue(0);
		blocks[numSqrt*2+2].setValue(0);

		blocks[numSqrt+2].setText("YOU");
		blocks[numSqrt*2+2].setText("WIN");
		
		main_start.setText(R.string.start);
		setHighScore();
	}
    public void reStart(){
    	isGameOver = false;
    	numFlag=true;
//		moveFlag=0;
    	setMoveFlag(0);
		for(int i=0;i<numTotal;i++)
			blocks[i].setValue(0);
    	for (int i = 0; i < 2; i++)
			appearBlock();
    	setScore(0);
    }

	private void setScore(int value){
		if(value == 0)
			currentScore = 0;
		else
			currentScore += value;
		main_score.setText(String.valueOf(currentScore));
	}
	private void setHighScore(){
		String _highSocre = (String) main_highest_score.getText();
		_highSocre = _highSocre.trim();
		String _currScore = (String) main_score.getText();
		if(_highSocre.equals("0") || Integer.parseInt(_highSocre) < Integer.parseInt(_currScore) ){
			main_highest_score.setText(_currScore);
			
			String fileName = getFilesDir() + "/2048";
			String message = _currScore;
			FileOutputStream fout = null;
			OutputStreamWriter osw = null;
			try{
				File file = new File(fileName);
				if(file.exists())
					file.delete();
				file.createNewFile();
		        byte [] bytes = message.getBytes(); 
//		        FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
//		        fout.write(bytes); 
//		        fout.close(); 
		        fout = new FileOutputStream(file, true); //true表示追加到已存在的文件
		        osw = new OutputStreamWriter(fout);
                osw.write(getChars(bytes));
                osw.flush();
		    } 
		    catch(Exception e){ 
		        e.printStackTrace(); 
		    } 
			finally{
				 try {
					osw.close();
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private char[] getChars (byte[] bytes) {
	      Charset cs = Charset.forName ("UTF-8");
	      ByteBuffer bb = ByteBuffer.allocate (bytes.length);
	      bb.put (bytes);
	                 bb.flip ();
	       CharBuffer cb = cs.decode (bb);
	  
	   return cb.array();
	}
    
    private void setBtnClick(){
    	main_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isGameOver)
					reStart();
				else
					dialog();
			}
		});
    }
    
    protected void dialog() {
    	AlertDialog.Builder builder = new Builder(MainActivity.this);
    	builder.setMessage("确认要重新玩吗？");
    	builder.setTitle("提示");
    	builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				reStart();
				dialog.dismiss();
			}
		});
	    builder.create().show();
    }

	private GestureDetector.OnGestureListener onGestureListener = 
		new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = e2.getX() - e1.getX();
			float y = e2.getY() - e1.getY();

			System.out.println("x=" + x);
			System.out.println("y=" + y);
			if(Math.abs(x) > Math.abs(y)){
				if (x > 0)
					doResult(RIGHT);
				else if (x < 0)
					doResult(LEFT);
			}else {
				if(y > 0)
					doResult(BOTTOM);
				else if(y < 0)
					doResult(TOP);
			}
			return true;
		}
	};

	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public void doResult(int action) {

		switch (action) {
		case RIGHT:
			System.out.println("go right");
			rightBlock();
			judgeAppear();
			appearBlock();
			over();
			break;

		case LEFT:
			System.out.println("go left");
			leftBlock();
			judgeAppear();
			appearBlock();
			over();
			break;

		case BOTTOM:
			System.out.println("go bottom");
			downBlock();
			judgeAppear();
			appearBlock();
			over();
			break;
			
		case TOP:
			System.out.println("go top");
			upBlock();
			judgeAppear();
			appearBlock();
			over();
			break;
			
		}
	}
}