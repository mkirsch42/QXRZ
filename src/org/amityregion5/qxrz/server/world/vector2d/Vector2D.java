package org.amityregion5.qxrz.server.world.vector2d;

public class Vector2D
{
	//coordinates
	private double x;
	private double y;
	
	public Vector2D()
	{
		this(0,0);
	}
	
	public Vector2D(double X, double Y)
	{
		x=X;
		y=Y;
	}
	
	public Vector2D(double rad)
	{
		x = Math.cos(rad);
		y = Math.sin(rad);
	}
	
	public Vector2D multiply(double scalar)
	{
		return new Vector2D(x*scalar, y*scalar);
	}
	
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x+v.getX(), y+v.getY());
	}
	
	public Vector2D subtract(Vector2D v)
	{
		return new Vector2D(x-v.getX(), y-v.getY());
	}
	
	public double length()
	{
		return Math.sqrt(x*x+y*y);
	}
	
	public double angle()
	{
		return Math.atan2(y,x);
	}
	
	public Vector2D project(Vector2D b)
	{
		return new Vector2D(b.angle()) .multiply( length() * Math.cos( angle()-b.angle() ) );
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public boolean isVert()
	{
		return x==0;
	}
	
	public boolean isHor()
	{
		return y==0;
	}
	
	public String toString()
	{
		return String.format("(%3.3f , %3.3f) | (%3.3f , %3.3f)", x, y, length(), angle());
	}
}
