package ex5;

import java.awt.Point;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.FPSAnimator;

import ex5.math.Vec;
import ex5.models.IRenderable;
import ex5.models.SolarSystem;

/**
 * An OpenGL model viewer 
 *
 */
public class Viewer implements GLEventListener {

	private double zoom = 0.0; //How much to zoom in? >0 mean magnify, <0 means shrink
	private Point mouseFrom; //From where to where was the mouse dragged between the last redraws?
	private Point mouseTo;
	private boolean isWireframe = false; //Should we display wireframe or not?
	public boolean isAxis = true; //Should we display axes or not?
	private boolean isStars = false; //Should we display axes or not?
	private IRenderable model; //Model to display
	private FPSAnimator ani; //This object is responsible to redraw the model with a constant FPS
	private GLAutoDrawable m_drawable = null; //We store the drawable OpenGL object to refresh the scene
	private boolean isModelCamera = false; //Whether the camera is relative to the model, rather than the world (ex6)
	private boolean isModelInitialized = false; //Whether model.init() was called.
	private double[] rotationMatrix = { 1D, 0D, 0D, 0D, 0D, 1D, 0D, 0D, 0D, 0D,
			1D, 0D, 0D, 0D, 0D, 1D };
	private int canvasWidth;
	private int canvasHeight;

	@Override
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glClear(16640);
		gl.glMatrixMode(5888);
		if (!isModelInitialized) {
			model.init(gl);
			isModelInitialized = true;
		}

		setupCamera(gl);
		if (isAxis)
			renderAxis(gl);
		if(isStars)
			renderStarts(gl);

		if (this.isWireframe) {
			gl.glPolygonMode(1032, 6913);
		} else {
			gl.glPolygonMode(1032, 6914);
		}

		model.render(gl, isAxis);
	}

	private void setupCamera(GL gl) {
		if (!isModelCamera) { 

			gl.glLoadIdentity();
			if ((this.mouseFrom != null) && (this.mouseTo != null)) {
				Vec from = mouseToVec(this.mouseFrom);
				Vec to = mouseToVec(this.mouseTo);
				Vec axis = Vec.crossProd(from, to);
				System.out.println("from: " + from + "to: " + to);
				if (axis.length() > 0D) {
					axis.normalize();
					double angle = 57.29577951308232D * Math.acos(Vec.dotProd(
							from, to));
					gl.glRotated(angle, axis.x, axis.y, axis.z);
					System.out.println("rotated");
				}
			}
			gl.glMultMatrixd(this.rotationMatrix, 0);
			gl.glGetDoublev(2982, this.rotationMatrix, 0);

			gl.glLoadIdentity();
			gl.glTranslated(0D, 0D, -1.2D);
			gl.glTranslated(0D, 0D, -this.zoom);
			gl.glMultMatrixd(this.rotationMatrix, 0);

			mouseFrom = null;
			mouseTo = null;
		} else { 
			gl.glLoadIdentity();
			model.setCamera(gl);
		}
	}


	private Vec mouseToVec(Point pt) {
		double x = 2D * pt.x / this.canvasWidth - 1D;
		double y = 1D - 2D * pt.y / this.canvasHeight;
		double z2 = 2.0D - x * x - y * y;
		if (z2 < 0D)
			z2 = 0D;
		double z = Math.sqrt(z2);
		Vec ret = new Vec(x, y, z);
		ret.normalize();
		return ret;
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		drawable.setGL(new javax.media.opengl.DebugGL(gl));		
		gl.glCullFace(GL.GL_BACK);    // Set Culling Face To Back Face
		gl.glEnable(GL.GL_CULL_FACE); // Enable back face culling
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glLightModelf(GL.GL_LIGHT_MODEL_TWO_SIDE, 1F);
		gl.glEnable(GL.GL_LIGHTING);

		model.init(gl);

		// Initialize display callback timer
		ani = new FPSAnimator(30, true);
		ani.add(drawable);

		m_drawable = drawable;

		if (model.isAnimated()) //Start animation (for ex6)
			startAnimation();
		else
			stopAnimation();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();

		this.canvasWidth = width;
		this.canvasHeight = height;

		gl.glMatrixMode(5889);
		gl.glLoadIdentity();

		gl.glFrustum(-0.1D, 0.1D, -0.1D * height / width,
				0.1D * height / width, 0.1D, 1000.0D);
	}

	/**
	 * Stores the mouse coordinates for trackball rotation.
	 * 
	 * @param from
	 *            2D canvas point of drag beginning
	 * @param to
	 *            2D canvas point of drag ending
	 */
	public void storeTrackball(Point from, Point to) {

		if (!isModelCamera) {
			if (null == mouseFrom)
				mouseFrom = from;
			mouseTo = to;
			m_drawable.repaint();
			System.out.println("dragged");
		}
	}

	/**
	 * Zoom in or out of object. s<0 - zoom out. s>0 zoom in.
	 * 
	 * @param s
	 *            Scalar
	 */
	public void zoom(double s) {
		if (!isModelCamera) {
			zoom += s*0.1D;
			m_drawable.repaint();
		}
	}

	/**
	 * Toggle rendering method. Either wireframes (lines) or fully shaded
	 */
	public void toggleRenderMode() {
		isWireframe = !isWireframe;
		m_drawable.repaint();
	}

	/**
	 * Toggle whether little spheres are shown at the location of the light sources.
	 */
	public void toggleLightSpheres() {
		model.control(IRenderable.TOGGLE_LIGHT_SPHERES, null);
		m_drawable.repaint();
	}

	public void AddStars() {
		isStars = !isStars;
		m_drawable.repaint();

	}

	/**
	 * Toggle axis
	 */
	public void toggleAxes() {
		isAxis = !isAxis;
		m_drawable.repaint();
	}

	public void toggleModelCamera() {
		isModelCamera =! isModelCamera;
		m_drawable.repaint();
	}

	/**
	 * Start redrawing the scene with 60 FPS
	 */
	public void startAnimation() {
		if (!ani.isAnimating())
			ani.start();
	}

	/**
	 * Stop redrawing the scene with 60 FPS
	 */
	public void stopAnimation() {
		if (ani.isAnimating())
			ani.stop();
	}

	private void renderAxis(GL gl) {
		gl.glLineWidth(2);
		boolean flag = gl.glIsEnabled(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glBegin(GL.GL_LINES);
		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(10, 0, 0);

		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 10, 0);

		gl.glColor3d(0, 0, 255);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, 20);

		gl.glEnd();
		if(flag)
			gl.glEnable(GL.GL_LIGHTING);
	}

	private void renderStarts(GL gl) {

		GLU glu = new GLU();
		GLUquadric quad = glu.gluNewQuadric();
		int i = 0;
		Random r = new Random();
		int k1 = -1;
		int k2 = 1;
		int k3 = -1;
		while (i < 10000) {
			float[] clr = { 1.0F, 1.0F, 1.0F, 1.0F };

			gl.glPushMatrix();
			gl.glPushMatrix();
			gl.glRotated(10.0D, 0.0D, 0.0D, 1.0D);
			gl.glRotated(360.0D * (k1*r.nextDouble() * (k2 * 100 * r.nextDouble()) / k1 * r.nextDouble() * (k3 *100 * r.nextDouble())), 0.0D, 1.0D, 0.0D);
			gl.glTranslated(k1 * r.nextDouble() * 100, 0.0D, 0.0D);
			gl.glRotated(360.0D * (k3 * r.nextDouble() * (k2 * 100 * r.nextDouble()) /k3 *  r.nextDouble() * (k3 * 100 * r.nextDouble())), 0.0D, 1.0D, 0.0D);
			gl.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
			gl.glRotated(k1 * r.nextDouble() * 100, 0.0D, 0.0D, 1.0D);
			gl.glColor3fv(clr, 0);
			gl.glMaterialfv(1028, 4609, clr, 0);
			glu.gluSphere(quad, 0.01F, 48, 48);
			gl.glPopMatrix();
			gl.glPopMatrix();
			i++;
			k1= k2 *-1;
			k2= k3 *-1;
			k3= k1 *-1;

		}
	}


	public void setModel(IRenderable model) {
		this.model = model;
	}

}
