package com.lixl.bean;

import android.net.Uri;

public class ChatRecordItem extends Model<String> {
	private static final String TAG = ChatRecordItem.class.getSimpleName();
	public int chatId;
	private Uri personAvatar;
	private String personName;
	public String chatTimeAgo;
	private String chatContent;
	
	public String getName() {
        return personName;
    }
    public void setName(String name) {
        this.personName = name;
    }
    
	public String getContent() {
        return chatContent;
    }
    public void setConten(String content) {
        this.chatContent = content;
    }
    
    public Uri getAvatar(){
    	return personAvatar;
    }
    public void setAvatar(Uri avatar){
    	this.personAvatar = avatar;
    }
    
	public String getTimeAgo() {
        return chatContent;
    }
    public void setTimeAgo(String timeAgo) {
        this.chatTimeAgo = timeAgo;
    }
}
