package jant;

import java.nio.FloatBuffer;

import com.sun.opengl.util.BufferUtil;

public class VertexBufferObject {
	public FloatBuffer vertexe;
	public float fVertexe[];

	public VertexBufferObject(DreiDeEsObject obj) {
		// System.out.println("FOO1");
		vertexe = BufferUtil.newFloatBuffer(obj.faceCount * 9 + 9);
		// System.out.println("FOO2");
		float v[] = new float[obj.faceCount * 9];
		// System.out.println("FOO3");
		int pos = 0;
		// System.out.println("FOO FacCount " + obj.faceCount);
		for (int i = 0; i < obj.faceCount; i++) {
			// System.out.println("FACE "+ i + " von " + obj.faceCount);
			// System.out.println("POS " + pos);
			for (int f = 0; f < 3; f++) {
				float x = obj.vertexe[obj.faces[i][f]][0] * 30;
				float y = obj.vertexe[obj.faces[i][f]][1] * 30;
				float z = obj.vertexe[obj.faces[i][f]][2] * 30;
				v[pos++] = x;
				v[pos++] = y;
				v[pos++] = z;
			}
			// System.out.println("FOO " + pos);
		}

		// System.out.println("FOOXi");
		vertexe.put(v);
		// System.out.println("FOOX");
		fVertexe = v;
	}
}
