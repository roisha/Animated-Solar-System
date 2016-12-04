package ex5.models;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import ex5.math.Vec;

/**
 * A simple axes dummy 
 *
 */
public class Spaceship implements IRenderable {

	private Body body;
	private GLU glu;
	private GLUquadric gluQuad;
	private ArrayList<float[]> lightPositions = null;

	private static final int NUM_LIGHTS = 2;
	private static final float[] WHITE_COLOR = new float[] {1f, 1f, 1f, 1f};
	private static final float[] BLACK_COLOR = new float[] {0f, 0f, 0f, 1f};
	

	private static final float[] LIGHT_DIFFUSE_COLOR = new float[] {20f, 20f, 20f, 1f};
	private static final float[] LIGHT_SPECULAR_COLOR = new float[] {5f, 5f, 5f, 1f};
	private static final float LIGHT_ATTENUATION = 1.0f;

	public static final int SPACESHIP_RADIUS = 100;

	private Vec center = new Vec();
	private double radius = 1;


	public Spaceship() {
		ArrayList<int[]> faces = new ArrayList<int[]>();
		ArrayList<Vec> vertices = new ArrayList<Vec>();

		faces.add(new int[]{1, 3, 0});
		faces.add(new int[]{2, 1, 0});
		faces.add(new int[]{3, 2, 0});
		faces.add(new int[]{2, 3, 1});

		vertices.add(new Vec(-4.0 ,4.0, -10.0));
		vertices.add(new Vec(-4.0, 2.1, 0.0));
		vertices.add(new Vec(-2.0, 2.0, 0.0));
		vertices.add(new Vec(-6.0, 2.0, 0.0));
		vertices.add(new Vec(-6.0, 1.0, 0.0));
		body = new Body(faces, vertices);

		// Lights
		this.lightPositions = new ArrayList<float[]>(NUM_LIGHTS);
		lightPositions.add(new float[] {5, 2, 0, 1});
		lightPositions.add(new float[] {5, 2, 0, 1});

		this.body.subdivide(3);
	}

	public Vec center() {
		return center;
	}

	public void setCenter(Vec center) {
		this.center = center;
	}

	public double radius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void render(GL gl, boolean noUse) {
		this.glu = new GLU();
		gluQuad = glu.gluNewQuadric();
		glu.gluQuadricOrientation(gluQuad, GLU.GLU_OUTSIDE);

		createLights(gl);
		drawSpaceship(gl);
		
		glu.gluDeleteQuadric(gluQuad);
	}

	private void createLights(GL gl) {
		gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, WHITE_COLOR, 0);
		
		gl.glPushMatrix();
		gl.glLoadIdentity();

		for (int i = 0; i < lightPositions.size(); i++) {
			int lightIndex = GL.GL_LIGHT0 + i;

			gl.glLightfv(lightIndex, GL.GL_POSITION, lightPositions.get(i), 0);
			gl.glLightfv(lightIndex, GL.GL_DIFFUSE, LIGHT_DIFFUSE_COLOR , 0);
			gl.glLightfv(lightIndex, GL.GL_SPECULAR, LIGHT_SPECULAR_COLOR , 0);
			gl.glLightf(lightIndex, GL.GL_QUADRATIC_ATTENUATION, LIGHT_ATTENUATION);
			gl.glEnable(lightIndex);

		}

		gl.glPopMatrix();
		gl.glEnable(GL.GL_LIGHTING);

	}

	private void drawSpaceship(GL gl) {
		gl.glPushMatrix();

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, WHITE_COLOR , 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, WHITE_COLOR, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, BLACK_COLOR, 0);

		gl.glTranslated(-1.8, 0.2, 0);
		gl.glRotated(-90, 0, 1, 0);
		gl.glRotated(0, 0, 0, 1);
		body.draw(gl);

		gl.glPopMatrix();

	}

	@Override
	public void init(GL gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control(int type, Object params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAnimated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCamera(GL gl) {
		// TODO Auto-generated method stub
		
	}
}