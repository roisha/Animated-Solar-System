package ex5.models;

import javax.media.opengl.GL;

public class Cube
  implements IRenderable
{
  private boolean isLightSpheres;
  
  public void render(GL gl, boolean noUse)
  {
    drawRGBCube(gl);
  } 
  



  private void drawRGBCube(GL gl)
  {
    double r = 0.5D;
    
    boolean lightingFlag = gl.glIsEnabled(2896);
    gl.glDisable(2896);
    
    gl.glBegin(7);
    gl.glColor3d(0D, 0D, 1D);
    gl.glVertex3d(-r, -r, r);
    gl.glColor3d(1D, 0D, 1D);
    gl.glVertex3d(r, -r, r);
    gl.glColor3d(1D, 1D, 1D);
    gl.glVertex3d(r, r, r);
    gl.glColor3d(0D, 1D, 1D);
    gl.glVertex3d(-r, r, r);
    
    gl.glColor3d(0D, 0D, 0D);
    gl.glVertex3d(-r, -r, -r);
    gl.glColor3d(0D, 0D, 1D);
    gl.glVertex3d(-r, -r, r);
    gl.glColor3d(0D, 1D, 1D);
    gl.glVertex3d(-r, r, r);
    gl.glColor3d(0D, 1D, 0D);
    gl.glVertex3d(-r, r, -r);
    
    gl.glColor3d(1D, 0D, 1D);
    gl.glVertex3d(r, -r, r);
    gl.glColor3d(1D, 0D, 0D);
    gl.glVertex3d(r, -r, -r);
    gl.glColor3d(1D, 1D, 0D);
    gl.glVertex3d(r, r, -r);
    gl.glColor3d(1D, 1D, 1D);
    gl.glVertex3d(r, r, r);
    
    gl.glColor3d(1D, 1D, 0D);
    gl.glVertex3d(r, r, -r);
    gl.glColor3d(1D, 0D, 0D);
    gl.glVertex3d(r, -r, -r);
    gl.glColor3d(0D, 0D, 0D);
    gl.glVertex3d(-r, -r, -r);
    gl.glColor3d(0D, 1D, 0D);
    gl.glVertex3d(-r, r, -r);
    
    gl.glColor3d(0D, 1D, 1D);
    gl.glVertex3d(-r, r, r);
    gl.glColor3d(1D, 1D, 1D);
    gl.glVertex3d(r, r, r);
    gl.glColor3d(1D, 1D, 0D);
    gl.glVertex3d(r, r, -r);
    gl.glColor3d(0D, 1D, 0D);
    gl.glVertex3d(-r, r, -r);
    
    gl.glColor3d(0D, 0D, 0D);
    gl.glVertex3d(-r, -r, -r);
    gl.glColor3d(1D, 0D, 0D);
    gl.glVertex3d(r, -r, -r);
    gl.glColor3d(1D, 0D, 1D);
    gl.glVertex3d(r, -r, r);
    gl.glColor3d(0D, 0D, 1D);
    gl.glVertex3d(-r, -r, r);
    
    gl.glEnd();
    
    if (lightingFlag) {
      gl.glEnable(GL.GL_LIGHTING);
    } 
  } 
  
  public String toString() {
    return "Cube";
  } 
  


  public void control(int type, Object params)
  {
    switch (type)
    {
    case 0: 
      this.isLightSpheres = (!this.isLightSpheres);
      break;
    
    default: 
      System.out.println("Control type not supported: " + toString() + ", " + type);
    } 
  } 
  
  public boolean isAnimated()
  {
    return false;
  } 
  
  public void init(GL gl) {}
  
  public void setCamera(GL gl) {}
} 