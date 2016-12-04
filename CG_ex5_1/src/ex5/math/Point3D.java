package ex5.math;

import java.util.Scanner;

public class Point3D {

	// TODO comment this code
	
	public double x;
	public double y;
	public double z;

	/**
	 * create a 3D point from information from XML file
	 * 
	 * @param value
	 *            String representation of a 3D point
	 */
	public Point3D(String value) {
		Scanner scanner = new Scanner(value);
		this.x = scanner.nextDouble();
		this.y = scanner.nextDouble();
		this.z = scanner.nextDouble();
		scanner.close();
	}

	/**
	 * create a 3D point from given values
	 * 
	 * @param x
	 *            x-value
	 * @param y
	 *            y-value
	 * @param z
	 *            z-value
	 */
	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * default point, create at origin
	 */
	public Point3D() {
		this.x = 0.0D;
		this.y = 0.0D;
		this.z = 0.0D;
	}

	/**
	 * duplicate a point from a given point
	 * 
	 * @param p
	 *            point to duplicate
	 */
	public Point3D(Point3D p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	/**
	 * add the value of the given vector to the point
	 * 
	 * @param v
	 *            the vector whose values are added
	 */
	public void add(Vec v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
	}

	public void mac(double s, Vec a) {
		this.x += s * a.x;
		this.y += s * a.y;
		this.z += s * a.z;
	}

	/**
	 * create a vector using the given points on the
	 * 
	 * @return vector created
	 */
	public Vec ToVec() {
		Vec v = new Vec(x, y, z);
		return v;
	}

	/**
	 * create a vector using two given points
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * @return vector
	 */
	public static Vec sub(Point3D a, Point3D b) {
		return new Vec(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public Vec sub(Point3D p) {
		return new Vec(this.x - p.x, this.y - p.y, this.z - p.z);
	}

	public static Point3D add(Vec a, Point3D b) {
		Point3D p = new Point3D();
		p.x = (a.x + b.x);
		p.y = (a.y + b.y);
		p.z = (a.z + b.z);

		return p;
	}

	public static double dotProd(Vec a, Point3D b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public static Point3D scale(double s, Vec a) {
		return new Point3D(a.x * s, a.y * s, a.z * s);
	}

	public static Vec sub(Vec a, Point3D b) {
		return new Vec(a.x - b.x, a.y - b.y, a.z - b.z);
	}

}
