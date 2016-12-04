package ex5.models;

import javax.media.opengl.glu.*;


import javax.media.opengl.GL;

import ex5.math.Vec;


public class Asteroid implements IRenderable {
	public boolean start = true;    
	public double placement = 600;
	private float[] cl; 
	Vec position;
	Vec velocity;
	boolean white;
	private GLU glu;

	public Asteroid(double mass, Vec position, float[] cl, boolean white){
		this.white = white;
		this.position = position;
		velocity = Vec.scale(-0.03, position);
		glu = new GLU();
		this.cl = cl;
	}
	@Override
	public void render(GL gl, boolean good){
		gl.glPushMatrix();
		gl.glTranslated(position.x, position.y, position.z);
		gl.glColor3fv(this.cl,0);
		GLUquadric SOLID = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle( SOLID, GLU.GLU_FILL);

		glu.gluQuadricNormals( SOLID, GLU.GLU_SMOOTH );
		glu.gluQuadricTexture(SOLID, true);
		if(white) {
		gl.glDisable(GL.GL_LIGHTING);
		}
		glu.gluSphere(SOLID, 0.5, 5, 5);

		gl.glPopMatrix();

	}
	public void update(long delta){
		double scale = delta/1000;
		position = Vec.add(position, Vec.scale(scale, velocity));            
	}        

	public void initializeState() {
		// TODO Auto-generated method stub

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

