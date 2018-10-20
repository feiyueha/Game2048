package com.feiyueha.game2048;

import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity
{
	private PlayGround playground;
	public static AssetManager assetmanager;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		playground = new PlayGround(this);
		readSetting();
		setContentView(playground);
    }
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.init://重置游戏
				Toast.makeText(this, "你重置了游戏!", Toast.LENGTH_SHORT).show();
				playground.initgame();
				playground.drawMap();
				break;
			case R.id.setting://打开设置
				Intent intent=new Intent(this, SettingActivity.class);
				startActivity(intent);
				break;
			default:
		}
		return true;
	}
	private void readSetting()//读取设置
	{
		Properties pro=new Properties();
		try
		{
			pro.load(openFileInput("Setting.properties"));
			String str=pro.getProperty("Progress");
			playground.textsize = Integer.parseInt(str);
		}
		catch (Exception e)
		{
			Properties proper=new Properties();
			try
			{
				OutputStream os=openFileOutput("Setting.properties",this.MODE_PRIVATE);
				proper.setProperty("Progress","100");
				proper.store(os,null);
			}
			catch (Exception err)
			{
				Toast.makeText(this, "创建游戏配置失败!", Toast.LENGTH_SHORT).show();
				System.out.println(e.toString());
			}
		}
	}
}
