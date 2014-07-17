package com.lixl.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lixl.R;
import com.lixl.android.widget._BaseAdapter;
import com.lixl.bean.ChatRecordItem;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

public class TestListView extends Activity {
	private ListView lv;
	private ArrayList<ChatRecordItem> items;
    private List<Map<String, Object>> data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_chat);
        lv = (ListView)findViewById(R.id.test_listview_chat);
        //获取将要绑定的数据设置到data中
        data = getData();
        items = getItems();
        _BaseAdapter adapter = new _BaseAdapter(this, items, data);
        lv.setAdapter(adapter);
    }
    
    private ArrayList<ChatRecordItem> getItems(){
    	ArrayList<ChatRecordItem> items = new ArrayList<ChatRecordItem>();
    	ChatRecordItem item = new ChatRecordItem();
    	item.setName("小黑");
    	item.setConten("打啊！你放大啊");
    	item.setTimeAgo("昨天");
    	item.setAvatar(Uri.parse("http://www.baidu.com/img/baidu_sylogo1.gif"));
    	
    	items.add(item);
    	
    	return items;
    }
    private List<Map<String, Object>> getData()
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for(int i=0;i<10;i++)
        {
            map = new HashMap<String, Object>();
            map.put("img", R.drawable.ic_launcher);
            map.put("name", "跆拳道");
            map.put("content", "快乐源于生活...");
            list.add(map);
        }
        return list;
    }
}
