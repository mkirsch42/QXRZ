package org.amityregion5.qxrz.server.world.vector2d;

public class Vector2D
{

	private double x;
	private double y;
	
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
	
	Vector2D multiply(double scalar)
	{
		return new Vector2D(x*scalar, y*scalar);
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
}
