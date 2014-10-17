package jant;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class DreiDeEsFile {
	LinkedList<DreiDeEsObject> objects = new LinkedList<DreiDeEsObject>();
	private DataInputStream file;

	public ChunkInfo readChunkInfo() throws IOException {
		ChunkInfo info = new ChunkInfo();
		info.ID = readShort();
		info.Size = readInt();
		return info;
	}

	int readShort() throws IOException {
		int a = file.read();
		int b = file.read();
		return a + (b << 8);
	}

	long readInt() throws IOException {
		int a = file.readUnsignedByte();
		int b = file.readUnsignedByte();
		int c = file.readUnsignedByte();
		int d = file.readUnsignedByte();
		return a + (b << 8) + (c << 16) + (d << 24);
	}

	float readFloat() throws IOException {
		int f = (file.readUnsignedByte() << 0) + (file.readUnsignedByte() << 8)
				+ (file.readUnsignedByte() << 16)
				+ (file.readUnsignedByte() << 24);
		return Float.intBitsToFloat(f);
	}

	String readStr() throws IOException {
		String temp = "";
		char c;
		while ((c = (char) file.read()) != '\0') {
			temp += c;
		}
		return temp;
	}

	public DreiDeEsFile(String fileName) {
		try {
			file = new DataInputStream(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO THERE ARE SOME FALSE FORMATET FILES.... with no 4d4d chunk
		try {
			if (readChunkInfo().ID != 0x4D4D) {
				throw new Exception("No 3DS File");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ChunkInfo info;
		DreiDeEsObject obj = null;
		try {
			while (true) {

				info = readChunkInfo();
				switch (info.ID) {
				case 0x3D3D:
					// System.out.println("0x3D3D");
					break;
				case 0x3D3E:
					// System.out.println("0x3E3D");
					file.skip(info.Size - 6);
					break;

				case 0x4000:
					obj = new DreiDeEsObject();
					obj.name = readStr();
					// System.out.println("Name: " + obj.name);
					objects.add(obj);
					break;

				case 0x4100:
					// System.out.println("0x4100");
					break;

				case 0x4110:
					// System.out.println("POINT");
					int vertexCount = readShort();
					// System.out.println("Count: " + vertexCount);
					objects.getLast().vertexCount = vertexCount;
					objects.getLast().vertexe = new float[vertexCount][];
					for (int i = 0; i < vertexCount; i++) {
						objects.getLast().vertexe[i] = new float[3];
						float x = readFloat();
						float y = readFloat();
						float z = readFloat();
						objects.getLast().vertexe[i][0] = x;
						objects.getLast().vertexe[i][1] = y;
						objects.getLast().vertexe[i][2] = z;
						// System.out.println("Vertex: " + i + " X:" + x + " Y:"
						// + y + " Z:" + z);
					}
					break;
				case 0x4120:
					// System.out.println("FACE");
					int faceCount = readShort();
					objects.getLast().faceCount = faceCount;
					objects.getLast().faces = new int[faceCount][];
					// System.out.println("Count: " + faceCount);
					for (int i = 0; i < faceCount; i++) {
						objects.getLast().faces[i] = new int[3];
						int a = readShort();
						int b = readShort();
						int c = readShort();
						readShort(); // Overread Flag
						objects.getLast().faces[i][0] = a;
						objects.getLast().faces[i][1] = b;
						objects.getLast().faces[i][2] = c;
						// System.out.println("Found: A:" + a + " B:" + b +
						// " C:" + c + " F:" + f);
					}
					break;
				default:
					/*
					System.out.println("Unknown Chunk: "
							+ Integer.toHexString(0xFFFF & info.ID));
					System.out.println("Size: " + info.Size);
					// TODO - make correkt
					*/
					if (info.Size < 0) {
						return;
					}
					// System.out.println("SKIP");
					file.skip(info.Size - 6);
				}
			}
		} catch (java.io.EOFException eo) {
			eo.printStackTrace();
			// System.out.println("End of 3DS file reached.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateVertexBuffers();
	}

	void generateVertexBuffers() {
		/*
		for(DreiDeEsObject obj : objects) {
			obj.vertexBufferObject = new VertexBufferObject(obj);
		}
		*/
	}

	public void scale(float f) {
		for (DreiDeEsObject obj : objects) {
			obj.scale(f);
		}
	}
}
