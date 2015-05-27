package org.amityregion5.qxrz.server.world.vector2d;

//physics class 
public class Vector2D
{
	private static final double EPSILON = 0.000001;
	
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
	
	public Vector2D add(Vector2D v) //shift coordinate
	{
		return new Vector2D(x+v.getX(), y+v.getY());
	}
	
	public Vector2D subtract(Vector2D v) //shift coordinate
	{
		return new Vector2D(x-v.getX(), y-v.getY());
	}
	
	public Vector2D opposite()
	{
		return new Vector2D(-x,-y);
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
		return String.format("(%5.5f , %3.3f) | (%3.3f , %3.3f)", x, y, length(), angle());
	}
	
	public Vector2D clone()
	{
		return new Vector2D(x,y);
	}
	
	public boolean equals(Object o)
	{
		if (o instanceof Vector2D)
		{
			if(((Vector2D)o).getX()==x&&((Vector2D)o).getY()==y)
			{
				return true;
			}
		}
		return false;
	}
	
	public Vector2D rotate(double theta)
	{
		return new Vector2D(angle()+theta).multiply(length());
	}
	
	public Vector2D rotateQuad(double q)
	{
		Vector2D r = clone();
		for(;q>0;q--)
		{
			r = new Vector2D(-r.getY(), r.getX());
		}
		for(;q<0;q++)
		{
			r = new Vector2D(r.getY(), -r.getX());
		}
		return r;
	}

	public Vector2D snap()
	{
		if(Math.abs(getX())<EPSILON)
		{
			return new Vector2D(0, getY());
		}
		if(Math.abs(getY())<EPSILON)
		{
			return new Vector2D(getX(), 0);
		}
		
		double quad = Math.round(angle()/(Math.PI/2));
		
		System.out.println(quad);
		
		return null;
	}
}
