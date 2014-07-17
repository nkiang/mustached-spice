package com.lixl.test;

import java.lang.reflect.Field;

import com.lixl.R;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.widget.TextView;

public class TestTextView extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textview);
		findViews();
		setValues();
	}
	TextView text_html;
	TextView text_link;
	TextView text_img;
	
	private void findViews(){
		text_html = (TextView) findViewById(R.id.text_html);
		text_link = (TextView) findViewById(R.id.text_link);
		text_img = (TextView) findViewById(R.id.text_img);
	}
	private void setValues(){
		String html = "<font color='red'>I love Android.</font><br>";
		html += "<font color='#0000FF' ><big><i>I love Android.</i></big></font><p>";
		html += String.format("<font color='@%s' ><tt><b><big><u>I love Android.</u></big><b></tt></font><p>", android.R.color.white);
		html += "<big><a href='http://localhost:8080'>我的网站</a></big>";
		CharSequence charSequence = Html.fromHtml(html);
		text_html.setText(charSequence);
		text_html.setMovementMethod(LinkMovementMethod.getInstance());
		
		String text = "我的URL：http://www.baidu.com\n";
		text += "我的Email:abc@gmail.com\n";
		text += "我的电话:+86 010-11112222";
		text_link.setText(text);
		text_link.setMovementMethod(LinkMovementMethod.getInstance());
		
		String html1 = String.format("图像1<img src='%s'/>图像2<img src='%s'/>图像3<img src='%s'/>", "emoji_80", "emoji_81", "emoji_82"); 
		text_img.setTextColor(Color.BLACK);
		text_img.setBackgroundColor(Color.WHITE);
		text_img.setTextSize(20);
		//
		CharSequence charSequence1 = Html.fromHtml(html1, new ImageGetter() {
			
			@Override
			public Drawable getDrawable(String source) {
				// TODO Auto-generated method stub
				Drawable drawable = getResources().getDrawable(getResourceId(source));
				//第三个图像按50%等比例压缩显示(24 * 24)
				if(source.equals("emoji_82"))
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
				else
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				return drawable;
			}
		}, null);
		text_img.setText(charSequence1);
		text_img.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	//利用反射技术从R.drawable类中通过图像资源文件名获得了相应的图像资源ID
	public int getResourceId(String name){
		try{
			//根据资源ID的变量名(也就是图像资源的文件名)获得Field对象昂
			Field field = R.drawable.class.getField(name);
			//获得并返回资源ID字段(静态变量)的值
			return Integer.parseInt(field.get(null).toString());
		}catch(Exception e){
			
		}
		return 0;
	}
}
