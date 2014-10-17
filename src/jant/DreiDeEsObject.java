package jant;

public class DreiDeEsObject {
	public String name;
	public int faceCount;
	public long vertexCount;
	public int faces[][];
	public float vertexe[][];

	// public VertexBufferObject vertexBufferObject;

	public void scale(float f) {
		for (int i = 0; i < vertexCount; i++) {
			vertexe[i][0] *= f;
			vertexe[i][1] *= f;
			vertexe[i][2] *= f;
		}
	}
}
