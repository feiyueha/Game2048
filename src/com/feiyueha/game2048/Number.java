package com.feiyueha.game2048;

public class Number
{
	int x;
	int y;
	int size;

	public Number(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Number(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getX()
	{
		return x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getY()
	{
		return y;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getSize()
	{
		return size;
	}
	
}
