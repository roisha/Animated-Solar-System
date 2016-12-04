package ex5.models;


import java.util.ArrayList;

import javax.media.opengl.GL;

import ex5.math.Vec;

public class Body {
	public ArrayList<int[]> faces;
	public ArrayList<Vec> vertices;
	public ArrayList<Vec> normals;

	public Body(ArrayList<int[]> faces, ArrayList<Vec> vertices) {


		this.faces = faces;
		this.vertices = vertices;

		calculateNormals();
	}

	public void subdivide(int levels) {
		for (int i = 0; i < levels; i++) {
			refineTopology();
			refineGeometry();
		}
		calculateNormals();
	}
	
	public void calculateNormals() {
		this.normals = new ArrayList<Vec>(vertices.size());

		for (int i = 0; i < this.vertices.size(); i++) {
			this.normals.add(new Vec());
		}
		
		for (int[] face : this.faces) {
			Vec v1 = this.vertices.get(face[0]);
			Vec v2 = this.vertices.get(face[1]);
			Vec v3 = this.vertices.get(face[2]);
			
			Vec normal = Vec.crossProd(Vec.sub(v2, v1), Vec.sub(v3, v1));
			normal.normalize();

			for (int vertexIndex = 0; vertexIndex < face.length ; vertexIndex++) {
			normals.get(vertexIndex).add(normal);
			}
		}

	}
		
	private void refineTopology() {
		ArrayList<int[]> newFaces = new ArrayList<int[]>();

		for (int[] face : this.faces) {
			int newVertices[] = new int[face.length];

			Vec center = new Vec();
			for (int vertexIndex : face) {
				center.add(this.vertices.get(vertexIndex));
			}

			center.scale(1.0 / face.length);

			this.vertices.add(center);
			int centerIndex = this.vertices.size() - 1;

			for (int i = 0 ; i < face.length; i++) {
				Vec vector = new Vec(this.vertices.get(face[i]));
				Vec nextVector = this.vertices.get(face[(i + 1) % face.length]).clone();

				vector.add(nextVector);
				vector.scale(0.5);

				if (this.vertices.indexOf(vector) == -1) {
					this.vertices.add(vector);
					newVertices[i] = this.vertices.size() - 1;
				} else {
					newVertices[i] = this.vertices.indexOf(vector);
				}

			}

			for (int i = 0 ; i < face.length; i++) {
				int[] newFace = {
						face[i],
						newVertices[i],
						centerIndex,
						newVertices[(i + newVertices.length - 1) % newVertices.length]};

				newFaces.add(newFace);
			}
		}

		this.faces = newFaces;
	}

	private void refineGeometry() {
		ArrayList<Vec> newVertex = new ArrayList<Vec>(this.vertices.size());
		int[] vertexCount = new int[this.vertices.size()];

		for (int i = 0; i < this.vertices.size(); i++) {
			newVertex.add(new Vec());
			vertexCount[i] = 0;
		}
		
		for(int[] face : this.faces) {
			Vec center = new Vec();

			for (int vertexIndex : face) {
				center.add(vertices.get(vertexIndex));
			}

			center.scale(1.0 / face.length);
			
			for(int vertexIndex : face) {
				newVertex.get(vertexIndex).add(center);
				vertexCount[vertexIndex]++;
			}
		}

		for (int i = 0; i < this.vertices.size(); i++) {
			newVertex.get(i).scale(1.0 / vertexCount[i]);
		}
		
		this.vertices = newVertex;
	}


	public void draw(GL gl) {
		gl.glBegin(GL.GL_QUADS);
		for (int[] face : this.faces) {
			for (int vertexIndex : face) {
				Vec normal = this.normals.get(vertexIndex);
				Vec v = this.vertices.get(vertexIndex);
				gl.glNormal3d(normal.x, normal.y, normal.z);
				gl.glVertex2d(v.x, v.y);
			
				gl.glVertex3d(v.x, v.y, v.z);
			}
		}
		gl.glEnd();
	}
}
