package com.feiyueha.game2048;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import java.io.*;

public class SettingActivity extends Activity implements SeekBar.OnSeekBarChangeListener
{
	private SeekBar seekbar;
	@Override
	public void onProgressChanged(SeekBar p1, int p2, boolean p3)
	{
		switch(p1.getId()){
			case R.id.Seekbar://更改设置
				PlayGround.textsize=Integer.valueOf(p1.getProgress());
				TextView tv=(TextView)findViewById(R.id.progress);
				tv.setText(String.valueOf(p1.getProgress()));
				tv.setTextSize(Float.valueOf(p1.getProgress()));
				Properties pro=new Properties();
				try
				{
					OutputStream os=openFileOutput("Setting.properties",this.MODE_PRIVATE);
					pro.setProperty("Progress",String.valueOf(p1.getProgress()));
					pro.store(os,null);
				}
				catch (Exception e)
				{
					Toast.makeText(this, "设置游戏配置失败!", Toast.LENGTH_SHORT).show();
					System.out.println(e);
				}
				break;
			default:
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onStopTrackingTouch(SeekBar p1)
	{
		// TODO: Implement this method
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		seekbar=(SeekBar)findViewById(R.id.Seekbar);
		seekbar.setOnSeekBarChangeListener(this);
		Properties pro=new Properties();
		try//读取设置
		{
			pro.load(openFileInput("Setting.properties"));
			String progress=pro.getProperty("Progress");
			System.out.println(progress);
			int textsize = Integer.parseInt(progress);
			seekbar.setProgress(textsize);
			TextView tv=(TextView)findViewById(R.id.progress);
			tv.setTextSize(textsize);
			tv.setText(progress);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "读取游戏配置失败!", Toast.LENGTH_SHORT).show();
		}
	}
}
