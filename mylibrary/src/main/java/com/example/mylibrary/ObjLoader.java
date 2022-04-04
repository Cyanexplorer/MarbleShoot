package com.example.mylibrary;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by g3863 on 2017/10/18.
 */

public class ObjLoader {

    public static class ObjFile{
        public int numFaces;
        public float[] normal;
        public float[] textureCoordinates;
        public float[] positions;

        void initialArray(int numFaces){
            this.numFaces = numFaces;
            normal = new float[numFaces*3];
            textureCoordinates = new float[numFaces*2];
            positions = new float[numFaces*3];
        }

        void putPosition(Vector<Float> vertices, int desIndex, int srcIndex){

            positions[desIndex++] = vertices.get(srcIndex++);
            positions[desIndex++] = vertices.get(srcIndex++);
            positions[desIndex] = vertices.get(srcIndex);
        }

        void putTexture(Vector<Float> textures, int desIndex, int srcIndex){
            textureCoordinates[desIndex++] = textures.get(srcIndex++);
            textureCoordinates[desIndex] = textures.get(srcIndex);
        }

        void putNullTexture(Vector<Float> vertices, int desIndex, int srcIndex){
            textureCoordinates[desIndex++] = triLength(vertices.get(srcIndex),vertices.get(srcIndex+2));
            textureCoordinates[desIndex] = triLength(vertices.get(srcIndex+1),vertices.get(srcIndex+2));
        }

        float triLength(float x, float y){
            return (float) Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        }

        void putNormal(Vector<Float> normals, int desIndex, int srcIndex){
            normal[desIndex++] = normals.get(srcIndex++);
            normal[desIndex++] = normals.get(srcIndex++);
            normal[desIndex] = normals.get(srcIndex);
        }

        void putNullNormal(int desIndex){
            normal[desIndex++] = 0;
            normal[desIndex++] = 0;
            normal[desIndex] = 0;
        }

        void putNormal(float x,float y,float z,int desIndex){
            normal[desIndex++] = x;
            normal[desIndex++] = y;
            normal[desIndex] = z;
        }

    }

    public static ObjFile loadModel(Context a, String path, float scale){
        ObjFile file = new ObjFile();

        Vector<Float> vertices = new Vector<>();
        Vector<Float> normals = new Vector<>();
        Vector<Float> textures = new Vector<>();
        Vector<String> faces = new Vector<>();

        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(a.getAssets().open(path));
            reader = new BufferedReader(in);

            String line;
            Vector<String> p = new Vector<>();
            while ((line = reader.readLine())!=null){
                p.clear();
                StringTokenizer tokenizer = new StringTokenizer(line);
                if(tokenizer.hasMoreTokens()){
                    while (tokenizer.hasMoreTokens()){
                        p.add(tokenizer.nextToken());
                    }
                    String[] parts = p.toArray(new String[p.size()]);
                    switch (parts[0]){
                        case "v":
                            vertices.add(Float.valueOf(parts[1])*scale);
                            vertices.add(Float.valueOf(parts[2])*scale);
                            vertices.add(Float.valueOf(parts[3])*scale);
                            break;
                        case "vt":
                            textures.add(Float.valueOf(parts[1]));
                            textures.add(Float.valueOf(parts[2]));
                            break;
                        case "vn":
                            normals.add(Float.valueOf(parts[1]));
                            normals.add(Float.valueOf(parts[2]));
                            normals.add(Float.valueOf(parts[3]));
                            break;
                        case "f":
                            faces.add(parts[1]);
                            faces.add(parts[2]);
                            faces.add(parts[3]);
                            if(parts.length>4){
                                for(int i = 4;i<parts.length;i++){
                                    faces.add(parts[1]);
                                    faces.add(parts[i-1]);
                                    faces.add(parts[i]);
                                }
                            }

                            break;
                        default:
                            break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }
        finally {
            if (reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        file.initialArray(faces.size());

        int positionIndex = 0;
        int normalIndex = 0;
        int textureIndex = 0;

        ArrayList<Float> buffer = new ArrayList<>();
        for(String face:faces){

            String[] parts = face.split("/");
            if(parts.length == 1){
                int index = 3*(Integer.valueOf(parts[0])-1);
                file.putPosition(vertices,positionIndex,index);
                file.putNullTexture(vertices,textureIndex,index);
                file.putNullNormal(normalIndex);
                buffer.add(vertices.get(index));
                buffer.add(vertices.get(index+1));
                buffer.add(vertices.get(index+2));
                if(buffer.size()==9) {
                    float x, y, z;
                    x = (buffer.get(4) - buffer.get(1)) * (buffer.get(8) - buffer.get(2)) - (buffer.get(5) - buffer.get(2)) * (buffer.get(7) - buffer.get(2));
                    y = (buffer.get(5) - buffer.get(2)) * (buffer.get(6) - buffer.get(0)) - (buffer.get(3) - buffer.get(0)) * (buffer.get(8) - buffer.get(2));
                    z = (buffer.get(3) - buffer.get(0)) * (buffer.get(7) - buffer.get(1)) - (buffer.get(4) - buffer.get(1)) * (buffer.get(6) - buffer.get(0));
                    file.putNormal(x, y, z, normalIndex - 6);
                    file.putNormal(x, y, z, normalIndex - 3);
                    file.putNormal(x, y, z, normalIndex);
                    buffer.clear();
                }
            }
            else if(parts.length == 2){
                file.putPosition(vertices,positionIndex,3*(Integer.valueOf(parts[0])-1));
                file.putTexture(textures,textureIndex,2*(Integer.valueOf(parts[1])-1));
                file.putNullNormal(normalIndex);
            }
            else if(parts.length == 3){
                file.putPosition(vertices,positionIndex,3*(Integer.valueOf(parts[0])-1));
                file.putTexture(textures,textureIndex,2*(Integer.valueOf(parts[1])-1));
                file.putNormal(normals,normalIndex,3*(Integer.valueOf(parts[2])-1));
            }

            positionIndex+=3;
            textureIndex+=2;
            normalIndex+=3;
        }

        return file;
    }
}
