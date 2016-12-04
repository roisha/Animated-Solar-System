package ex5.models;

import javax.media.opengl.GL;



public class Axis
{
	public static void render(GL gl, float length, boolean axis)
	{

		if(axis) {


			gl.glLineWidth(1);
			boolean flag = gl.glIsEnabled(2896);
			gl.glDisable(2896);
			gl.glBegin(1);
			gl.glColor3d(1.0D, 0.0D, 0.0D);
			gl.glVertex3d(0.0D, 0.0D, 0.0D);
			gl.glVertex3d(length, 0.0D, 0.0D);

			gl.glColor3d(0.0D, 0.0D, 1.0D);
			gl.glVertex3d(0.0D, 0.0D, 0.0D);
			gl.glVertex3d(0.0D, length, 0.0D);

			gl.glColor3d(0.0D, 1.0D, 0.0D);
			gl.glVertex3d(0.0D, 0.0D, 0.0D);
			gl.glVertex3d(0.0D, 0.0D, length);

			gl.glEnd();
			if (flag)
				gl.glEnable(2896);
		}
	}

}