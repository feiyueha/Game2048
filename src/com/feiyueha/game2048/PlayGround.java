package com.feiyueha.game2048;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.res.*;

public class PlayGround extends SurfaceView implements SurfaceHolder.Callback,OnTouchListener
{
	private static final int ACTION_CLICK=0;//点击常量
	private static final int ACTION_LEFT=1;//左滑常量
	private static final int ACTION_UP=2;//上滑常量
	private static final int ACTION_RIGHT=3;//右滑常量
	private static final int ACTION_DOWN=4;//下滑常量
	private static int Width=0;//宽度
	private static int Offsetw=0;//宽度偏移值
	private static int Offseth=0;//高度偏移值
	private static int scoreh=0;//积分板高度
	private static float x1=0,y1=0,x2=0,y2=0;//触碰的坐标
	private static int score=0;//游戏得分
	public static int textsize=100;
	private Number map[][]=new Number[4][4];//游戏数据
	private static boolean gameStatus=false;
	@Override
	public void surfaceCreated(SurfaceHolder p1)
	{
		drawMap();
		setOnTouchListener(this);
		// TODO: Implement this method
	}

	@Override
	public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4)
	{
		if (p3 < p4)
		{
			Width = (int)(p3 / 4.5);
			Offsetw = (int)(p3 / 4.5 * 0.25);
			Offseth = 2 * Width;
			scoreh = Offseth - Offsetw;
			drawMap();
		}
		else
		{

			Canvas c= getHolder().lockCanvas();
			c.drawColor(Color.LTGRAY);
			Paint p=new Paint();
			p.setColor(Color.RED);
			p.setTextSize(100);
			c.drawText("请竖屏进行游戏!",0,p4 / 2,p);
			getHolder().unlockCanvasAndPost(c);
		}
		// TODO: Implement this method
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder p1)
	{
		// TODO: Implement this method
	}

	public PlayGround(Context context)
	{
		super(context);
		getHolder().addCallback(this);
		initgame();
	}
	public void initgame()
	{
		for (int i=0;i < 4;i++)
		{
			for (int j=0;j < 4;j++)
			{
				map[j][i] = new Number(j,i,0);
			}
		}
		score = 0;
		putRandom();
		putRandom();
		gameStatus = true;
	}

	public void drawMap()//绘制游戏界面
	{
		Canvas c=getHolder().lockCanvas();
		c.drawColor(Color.LTGRAY);
		Paint p=new Paint();
		for (int i=0;i < 4;i++)
		{
			for (int j=0;j < 4;j++)
			{
				Number n=map[j][i];
				String mBgColor;
				switch (n.getSize())
				{
					case 0:
						mBgColor = "#CCC0B3";
						break;
					case 2:
						mBgColor = "#EEE4DA";
						break;
					case 4:
						mBgColor = "#EDE0C8";
						break;
					case 8:
						mBgColor = "#F2B179";
						break;
					case 16:
						mBgColor = "#F49563";
						break;
					case 32:
						mBgColor = "#F57940";
						break;
					case 64:
						mBgColor = "#F55D37";
						break;
					case 128:
						mBgColor = "#EEE863";
						break;
					case 256:
						mBgColor = "#EDB040";
						break;
					case 512:
						mBgColor = "#ECB040";
						break;
					case 1024:
						mBgColor = "#EB9437";
						break;
					case 2048:
						mBgColor = "#EA7821";
						break;
					default:
						mBgColor = "#EA7821";
						break;
				}
				p.setColor(Color.parseColor(mBgColor));
				p.setStrokeJoin(Paint.Join.ROUND);
				c.drawRoundRect(Offsetw + Width * n.getX(),
								Offseth + Width * n.getY(),
								Offsetw + Width * (n.getX() + 1),
								Offseth + Width * (n.getY() + 1),
								(int)(0.5 * Offsetw),(int)(0.5 * Offsetw),p);
			}

		}
		//画计分板
		Paint scorep=new Paint();
		RectF rectScore=new RectF(Offsetw,Offsetw,Offsetw + Width * 4,scoreh);
		scorep.setColor(0xFFEEE4DA);
		c.drawRoundRect(rectScore,(int)(0.5 * Offsetw),(int)(0.5 * Offsetw),scorep);
		scorep.setTextSize(textsize);
		scorep.setColor(Color.WHITE);
		Rect minScoreRect=new Rect();
		String text="当前得分:" + score;
		scorep.getTextBounds(text,0,text.length(),minScoreRect);
		double Xs = (rectScore.width() - minScoreRect.width()) / 2;
		double Ys = (rectScore.height() + minScoreRect.height()) / 2;
		c.drawText(text,(int)(Offsetw + Xs),(int)(Offsetw + Ys),scorep);
		//绘制方块上的数字
		for (int i=0;i < 4;i++)
		{
			for (int j=0;j < 4;j++)
			{
				Number n=map[j][i];
				Integer num=n.getSize();
				if ((int)num != 0)
				{
					Paint pt = new Paint();
					pt.setTextSize(textsize);
					pt.setColor(Color.WHITE);
					String nums=num.toString();
					Rect minRect=new Rect();
					pt.getTextBounds(nums,0,nums.length(),minRect);
					double x = (Width - minRect.width()) / 2;
					double y = (Width + minRect.height()) / 2;
					c.drawText(nums,(int)(Offsetw + Width * n.getX() + x),(int)(Offseth + Width * n.getY() + y),pt);
				}
			}
		}
		if (isGameOver())//判断游戏是否结束
		{
			gameStatus = false;
			Paint failp=new Paint();
			failp.setColor(0xAFFFDAB9);
			c.drawRoundRect(Offsetw,Offseth,Offsetw + Width * 4,Offseth + Width * 4,(int)(0.5 * Offsetw),(int)(0.5 * Offsetw),failp);
			Paint ptf = new Paint();
			ptf.setTextSize(textsize);
			ptf.setColor(Color.WHITE);
			Rect minRect=new Rect();
			String failtext = "GameOver";
			ptf.getTextBounds(failtext,0,failtext.length(),minRect);
			double x = (Width*4 - minRect.width()) / 2;
			double y = (Width*4 + minRect.height()) / 2;
			c.drawText(failtext,(int)(Offsetw+ x),(int)(Offseth+ y),ptf);
			Toast.makeText(getContext(),"游戏结束!",Toast.LENGTH_LONG).show();
		}
		getHolder().unlockCanvasAndPost(c);
	}

	private void putRandom()
	{
		while (!isfull())
		{
			int x=(int)(Math.random() * 1000 % 4);
			int y=(int)(Math.random() * 1000 % 4);
			if (map[x][y].getSize() == 0)
			{
				if ((int)(Math.random() * 1000 % 2) == 0)
				{//放置数字2
					map[x][y].setSize(2);
				}
				else
				{//放置数字4
					map[x][y].setSize(4);
				}
				break;
			}
		}
	}
	private boolean isfull()
	{
		for (int i=0;i < 4;i++)
		{
			for (int j=0;j < 4;j++)
			{
				if (map[j][i].size == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	@Override
	public boolean onTouch(View p1, MotionEvent p2)
	{
		if (p2.getAction() == MotionEvent.ACTION_DOWN)
		{
			x1 = p2.getX();
			y1 = p2.getY();
		}
		else if (p2.getAction() == MotionEvent.ACTION_UP)
		{
			x2 = p2.getX();
			y2 = p2.getY();
			move(getDirecton());
		}
		// TODO: Implement this method
		return true;
	}
	private void move(int a)
	{
		if (gameStatus == true)
		{
			switch (a)
			{
				case ACTION_LEFT://左滑
					for (int i=0;i < 4;i++)
					{
						int[] one=new int[4];
						for (int j=0;j < 4;j++)
						{
							one[j] = map[j][i].getSize();
						}
						one = mix(one);
						for (int j=0;j < 4;j++)
						{
							map[j][i].setSize(one[j]);
						}
					}
					putRandom();
					drawMap();
					break;
				case ACTION_UP://上滑
					for (int i=0;i < 4;i++)
					{
						int[] one=new int[4];
						for (int j=0;j < 4;j++)
						{
							one[j] = map[i][j].getSize();
						}
						one = mix(one);
						for (int j=0;j < 4;j++)
						{
							map[i][j].setSize(one[j]);
						}
					}
					putRandom();
					drawMap();
					break;
				case ACTION_RIGHT://右滑
					for (int i=0;i < 4;i++)
					{
						int[] one=new int[4];
						int index=3;
						for (int j=0;j < 4;j++)
						{
							one[index] = map[j][i].getSize();
							index--;
						}
						one = mix(one);
						index = 3;
						for (int j=0;j < 4;j++)
						{
							map[j][i].setSize(one[index]);
							index--;
						}
					}
					putRandom();
					drawMap();
					break;
				case ACTION_DOWN://下滑
					for (int i=0;i < 4;i++)
					{
						int[] one=new int[4];
						int index=3;
						for (int j=0;j < 4;j++)
						{
							one[index] = map[i][j].getSize();
							index--;
						}
						one = mix(one);
						index = 3;
						for (int j=0;j < 4;j++)
						{
							map[i][j].setSize(one[index]);
							index--;
						}
					}
					putRandom();
					drawMap();
					break;
				case ACTION_CLICK:
					break;
			}
		}
	}
	private int getDirecton()
	{
		if ((x1 - x2) > 0.5 * Width && (x1 - x2) > Math.abs(y1 - y2))
		{//向左滑动
			Toast.makeText(getContext(),"左滑!",Toast.LENGTH_SHORT).show();
			return ACTION_LEFT;
		}
		else if ((y1 - y2) > 0.5 * Width && (y1 - y2) > Math.abs(x1 - x2))
		{//向上滑动
			Toast.makeText(getContext(),"上滑!",Toast.LENGTH_SHORT).show();
			return ACTION_UP;
		}
		else if ((x2 - x1) > 0.5 * Width && (x2 - x1) > Math.abs(y1 - y2))
		{//向右滑动
			Toast.makeText(getContext(),"右滑!",Toast.LENGTH_SHORT).show();
			return ACTION_RIGHT;
		}
		else if ((y2 - y1) > 0.5 * Width && (y2 - y1) > Math.abs(x1 - x2))
		{//向下滑动
			Toast.makeText(getContext(),"下滑!",Toast.LENGTH_SHORT).show();
			return ACTION_DOWN;
		}
		else
		{
			Toast.makeText(getContext(),"点击!",Toast.LENGTH_SHORT).show();
			return ACTION_CLICK;
		}
	}
	private int[] mix(int[] args)
	{
		int[] mixed=order(args);
		for (int i=0;i < 3;i++)
		{
			if (mixed[i] == mixed[i + 1])
			{
				mixed[i + 1] = 0;
				mixed[i] = mixed[i] * 2;
				score += mixed[i];
				mixed = order(mixed);
			}
		}
		return mixed;
	}
	private int[] order(int[] args)
	{
		int[] order=new int[4];
		int index=0;
		for (int i=0;i < 4;i++)
		{
			if (args[i] != 0)
			{
				order[index] = args[i];
				index++;
			}
		}
		return order;
	}
	private boolean isGameOver()
	{//判断游戏是否结束
		if (isfull() == false)//先判断是否满格
		{
			return false;
		}
		else
		{
			for (int i=0;i < 3;i++)
			{
				for (int j=0;j < 3;j++)
				{
					if (map[j][i].getSize() == map[j + 1][i].getSize())//判断左右
					{
						return false;
					}
					if (map[j][i].getSize() == map[j][i + 1].getSize())//判断上下
					{
						return false;
					}
				}
				if (map[3][i].getSize() == map[3][i + 1].getSize())
				{
					return false;
				}
			}
			for (int i=0;i < 3;i++)
			{
				if (map[i][3].getSize() == map[i + 1][3].getSize())
				{
					return false;
				}
			}
			return true;
		}
	}
}
