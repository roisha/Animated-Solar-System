

package ex5.models;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import ex5.math.Vec;

public class SolarSystem
implements IRenderable {

	private boolean isLight = true;
	private boolean isAxis = true;
	private static final float[] black = { 0.0F, 0.0F, 0.0F };
	Axis axis;

	//EARTH
	private static final float[] EarthColor =  { 0.3f, 0.5f, 1f};

	private  float EARTH_ORBIT_DUR = 365.0F;
	float CurrentEarthRotation = 0.0F;
	float EarthDaysTranspired = 0.0F;
	float EarthDayIncrement = 0.01F; 


	//Moon
	private static final float[] MoonColor = {0.658824F, 0.658824F, 0.658824F };

	//Mercury
	private static final float[] MercuryColor = { 0.8f, 0.498039f, 0.196078f };



	//Venus
	private static final float[] VenusColor = { 0.647059F,0.164706F , 0.164706F};

	//Mars
	private static final float[] MarsColor = { 1.0F, 0.5F, 0F };


	//Jupiter  
	private static final float[] JupiterColor = {0.623529F ,0.623529F ,0.372549F};


	//Saturn  
	private static final float[] SaturnRingColor = { 0.647059F,0.164706F , 0.164706F};
	private static final float[] SaturnColor = {0.65F, 0.49F, 0.24F};


	//Uranus  
	private static final float[] UranusColor = {0.419608F, 0.137255F, 0.556863F};




	//Neptune
	private static final float[] NeptuneColor = {0.439216F, 0.858824F, 0.858824F};

	//Pluto
	private static final float[] PlutoColor = {0.6F, 0.8F, 0.196078F };


	public void render(GL gl, boolean Axis) {


		this.isAxis = Axis;

		gl.glScaled(0.1D, 0.1D, 0.1D);

		gl.glLineWidth(1.0F);

		gl.glMaterialf(1028, 5633, 3.0F);

		float[] firstlightpos = { 0.0F, 8.0F, 0.0F, 1.0F };
		float[] firstlightcolor = { 1.0F, 1.0F, 1.0F, 1.0F };
		gl.glLightfv(16384, 4609, firstlightcolor, 0);
		gl.glLightfv(16384, 4610, firstlightcolor, 0);
		gl.glLightfv(16384, 4611, firstlightpos, 0);
		gl.glEnable(16384);

		float[] secondlightpos = { 0.0F, -8.0F, 0.0F, 1.0F };
		float[] secondlightcolor = { 1.0F, 0.0F, 0.0F, 1.0F };
		gl.glLightfv(16385, 4609, secondlightcolor, 0);
		gl.glLightfv(16385, 4610, secondlightcolor, 0);
		gl.glLightfv(16385, 4611, secondlightpos, 0);
		gl.glEnable(16385);

		float[] thirdlightpos  = { 0.0F, 0.0F, 0.0F, 0.0F };
		float[] thirdlightcolor = { 1.0F, 0.6F, 0.0F, 4.0F };
		gl.glLightfv(16388, 4609, thirdlightcolor, 0);
		gl.glLightfv(16388, 4610, thirdlightcolor, 0);
		gl.glLightfv(16388, 4611, thirdlightpos, 0);
		gl.glEnable(16388);



		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();

		if (this.isLight) {
			boolean light = gl.glIsEnabled(2896);
			gl.glDisable(2896);

			gl.glPushMatrix();
			gl.glTranslated(firstlightpos[0], firstlightpos[1], firstlightpos[2]);
			gl.glColor4fv(firstlightcolor, 0);
			glu.gluSphere(quad, 0.1D, 20, 20);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslated(secondlightpos[0], secondlightpos[1], secondlightpos[2]);
			gl.glColor4fv(secondlightcolor, 0);
			glu.gluSphere(quad, 0.1D, 20, 20);
			gl.glPopMatrix();


			if (light) {
				gl.glEnable(2896);
			}
		}
		gl.glDisable(2896);
		gl.glPushMatrix();
		gl.glTranslated(thirdlightpos[0], thirdlightpos[1], thirdlightpos[2]);
		gl.glColor4fv(thirdlightcolor, 0);
		glu.gluSphere(quad, 2.1F, 20, 20);
		gl.glPopMatrix();
		gl.glEnable(2896);


		Planets(gl, glu, quad);

		//IRenderable spaceship = new IRenderable();
		Spaceship spaceship = new Spaceship();
		spaceship.render(gl, true);
		Random r = new Random();
		int k1 = -1;
		int k2 = 1;
		int k3 = -1;
		float [] cl =  {0.2F,0.2F,0.2F,0.1F};
		float [] cl1 = {0.2F,0.1F,0.0F,0.1F};
		int grey = 1;
		boolean white = false;
		int fourAsteroids = 0;
		for (int i = 0; i < 20; i++) {
			Vec x = new Vec(k1*r.nextDouble()*20,k2*r.nextDouble()*20,k3*r.nextDouble()*20);
			if(grey == 1) {
				Asteroid asteroid = new Asteroid(8, x, cl, white );
				asteroid.render(gl, true);
				grey = 0;
			}
			else {
				Asteroid asteroid1 = new Asteroid(8, x, cl1, white);
				asteroid1.render(gl, true);
				grey = 1;
			}

			if(white == true) {
				white = false;
			}

			k1= k2 *-1;
			k2= k3 *-1;
			k3= k1 *-1;
			fourAsteroids++;
			if(fourAsteroids == 4) {
				white = true;
			}

		}

		glu.gluDeleteQuadric(quad);

	}

	private void Planets(GL gl, GLU glu, GLUquadric quad) {

		Planet(gl, glu, quad, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, EarthColor, 0.0);

		Planet(gl, glu, quad, 4.0F, 7.0F, 
				88.0F, 2.4F,  0.1F, 0.2F, MercuryColor ,0.0);

		Planet(gl, glu, quad, 2.0F, 3.39F, 225.0F, 
				4.0F, -243.0F, 0.38F, VenusColor, 0.5);

		Planet(gl, glu, quad, 24.0F, 1.85F, 686.0F, 6.8F, 
				1.0F, 0.3F, MarsColor, 2);

		Planet(gl, glu, quad, 3.1F, 1.3F, 4333.0F, 9.0F, 
				0.4F, 0.7F, JupiterColor, -3.0);

		Planet(gl, glu, quad, 26.7F, 2.49F, 10759.0F, 11.6F, 
				0.4F, 0.6F, SaturnColor , 1);

		Planet(gl, glu, quad, 97.9F, 0.77F, 30685.0F, 13.6F, 
				-0.7F, 0.46F, UranusColor,  0.6);

		Planet(gl, glu, quad, 28.8F, 1.77F, 60190.0F, 16.6F, 
				0.7F, 0.5F, NeptuneColor,  1.2);

		Planet(gl, glu, quad,57.5F, 17.2F, 90800.0F, 18.0F, 
				-6.0F, 0.2F, PlutoColor,  0.5 * Math.PI);
	}

	private void Orbit(GL gl, float inclination, float radius, float[] clr) {

		gl.glPushMatrix();
		gl.glRotated(inclination, 0.0D, 0.0D, 1.0D);
		boolean lightFlag = gl.glIsEnabled(2896);
		gl.glDisable(2896);
		gl.glColor3f(150, 300, 300);
		gl.glBegin(2);
		double iterations = (radius + 1.0F) * 6.0F * 100.0F;
		for (int i = 0; i < (int)iterations; i++)
			gl.glVertex3d(radius * Math.cos(6.283185307179586D * i / iterations), 0.0D, radius * Math.sin(6.283185307179586D * i / iterations));
		gl.glEnd();
		if (lightFlag)
			gl.glEnable(2896);
		gl.glPopMatrix();
	}

	private void Planet(GL gl, GLU glu, GLUquadric quad, float axisIclination, float orbitInclination, float orbitDuration, 
			float orbitRadius, float rotationDuration, float radius, float[] clr, double alpha)
	{

		if(clr.equals(EarthColor)){

			float[] cl = { 1.0F, 0.0F, 1.0F, 1.0F };
			Orbit(gl, 0.0F, 5.15F, cl);
			gl.glColor3fv(EarthColor, 0);
			gl.glMaterialfv(1028, 4609, EarthColor, 0);
			gl.glMaterialfv(1028, 4610, black, 0);
			gl.glMaterialfv(1028, 5632, black, 0);

			gl.glPushMatrix();

			gl.glRotated(0.0D, 0.0D, 0.0D, 1.0D);
			gl.glRotated(360.0D * (this.EarthDaysTranspired / EARTH_ORBIT_DUR), 0.0D, 1.0D, 0.0D);
			gl.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
			gl.glTranslated(5.15D, 0.0D, 0.0D);
			gl.glRotated(360.0D * this.CurrentEarthRotation, 0.0D, 1.0D, 0.0D);
			gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
			gl.glRotated(23.450000762939453D, 0.0D, 0.0D, 1.0D);

			glu.gluSphere(quad, 0.4F, 48, 48);
			Axis.render(gl, 0.27F,isAxis);

			gl.glColor3fv(MoonColor, 0);
			gl.glMaterialfv(1028, 4609, MoonColor, 0);
			gl.glRotated(360.0D * EarthDaysTranspired / 30.0F, 0.0D, 1.0D, 0.0D);
			gl.glTranslated(0.5500000029802322D, 0.0D, 0.0D);
			glu.gluSphere(quad, 0.1, 48, 48);

			Axis.render(gl, 0.6F,isAxis);

			gl.glPopMatrix();
		}
		else {
			Orbit(gl, orbitInclination, orbitRadius, clr);
			gl.glPushMatrix();
			gl.glColor3fv(clr, 0);
			gl.glMaterialfv(1028, 4609, clr, 0);
			gl.glMaterialfv(1028, 4610, black, 0);
			gl.glMaterialfv(1028, 5632, black, 0);
			gl.glPushMatrix();
			gl.glRotated(orbitInclination, 0.0D, 0.0D, 1.0D);
			gl.glRotated(360.0D * (this.EarthDaysTranspired / orbitDuration), 0.0D, 1.0D, 0.0D);


			double x = (double)orbitRadius * Math.sin(alpha);
			double z = (double)orbitRadius * Math.cos(alpha);
			gl.glTranslated(x, 0.0D, z);

			gl.glRotated(360.0D * (this.CurrentEarthRotation / rotationDuration), 0.0D, 1.0D, 0.0D);
			gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
			gl.glRotated(axisIclination, 0.0D, 0.0D, 1.0D);
			glu.gluSphere(quad, radius, 48, 48);
			Axis.render(gl, radius * 1.5F,isAxis);
			gl.glPopMatrix();
			gl.glPopMatrix();

			if (clr.equals(SaturnColor)) {
				gl.glEnable(2896);
				gl.glPushMatrix();

				gl.glColor3fv(SaturnRingColor, 0);
				gl.glMaterialfv(1032, 4609, black, 0);
				gl.glMaterialfv(1028, 4610, SaturnRingColor, 0);
				gl.glMaterialfv(1028, 5632, black, 0);

				gl.glPushMatrix();
				gl.glRotated(2.49D, 0.0D, 0.0D, 1.0D);
				gl.glRotated(360.0D * (this.EarthDaysTranspired / 10759.0F), 0.0D, 1.0D, 0.0D);
				gl.glTranslated(x, 0.0D, z);
				gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);

				gl.glRotated(26.700000762939453D, 0.0D, 0.0D, 1.0D);
				glu.gluDisk(quad, 0.75D, 1.0D, 48, 48);
				gl.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
				glu.gluDisk(quad, 0.75D, 1.0D, 48, 48);

				gl.glPopMatrix();
				gl.glPopMatrix();
			}
		}
	}

	public String toString()
	{
		return "SolarSystem";
	}

	public void control(int type, Object params)
	{
		switch (type)
		{
		case 0:
			this.isLight = (!this.isLight);
			break;
		default:
			System.out.println("Control type not supported: " + toString() + ", " + type);
		}
	}


	public boolean isAnimated()
	{
		return false;
	}

	public void init(GL gl)
	{
	}

	public void setCamera(GL gl)
	{
	}
}