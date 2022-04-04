/*
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
*/
package com.example.Marble_Game.Builder;

import android.content.Context;

import com.example.Marble_Game.util.Geometry;
import com.example.Marble_Game.util.Geometry.Circle;
import com.example.Marble_Game.util.Geometry.Cylinder;
import com.example.Marble_Game.util.Geometry.Point;
import com.example.mylibrary.ObjLoader;

import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.glDrawArrays;
import static com.example.Marble_Game.util.Constants.FLOATS_PER_VERTEX;

public class ObjectBuilder {

    public interface DrawCommand {
        void draw();
    }



    public static class GeneratedData {
        public final float[] vertexData;
        public final List<DrawCommand> drawList;

        GeneratedData(float[] vertexData, List<DrawCommand> drawList) {
            this.vertexData = vertexData;
            this.drawList = drawList;
        }
    }

    public static GeneratedData createWall (Point center,float radius,float height, int numPints){
        int size=sizeOfOpenCylinderInVertices(numPints);
        ObjectBuilder builder=new ObjectBuilder(size);
        Cylinder cylinder=new Cylinder(center.translateY(height),radius,300f);
        builder.appendOpenCylinder(cylinder,numPints);
        return builder.build();
    }

    public static GeneratedData createField(
            Point center, float radius,float height, int numPoints){
        int size = sizeOfCircleInVertices(numPoints);
        ObjectBuilder builder = new ObjectBuilder(size);
        float baseHeight = height * 0.25f;

        Circle CF = new Circle(center.translateY(-baseHeight/2f), radius);

        builder.appendCircle(CF, numPoints);
        return builder.build();
    }

    public static GeneratedData createMarble(
            Point center, float radius, float height, int numPoints){
        int size = sizeOfSphereInVeryices(numPoints);
        ObjectBuilder builder = new ObjectBuilder(size);
        //float baseHeight = height * 0.25f;

        Geometry.Sphere sph=new Geometry.Sphere(center.translateY(height),
                radius);
        builder.appendSphere(sph,numPoints);
        return builder.build();
    }

    public static GeneratedData createPlygen(Context context,String path,float height){

        ObjLoader.ObjFile file = ObjLoader.loadModel(context,path,0.1f);
        ObjectBuilder builder = new ObjectBuilder(file.numFaces);
        builder.appendPlygen(file,file.numFaces,height);
        return builder.build();
    }



    public static GeneratedData createTriangle(
            Point center, float edge, float height){
        int size = sizeOfTriangleVectices();
        ObjectBuilder builder = new ObjectBuilder(size);

        Geometry.Triangle tri=new Geometry.Triangle(center.translateY(height),
                edge);
        builder.appendTriangle(tri,3);
        return builder.build();
    }

    private static int sizeOfCircleInVertices(int numPoints) {return 1 + (numPoints + 1);}
    private static int sizeOfOpenCylinderInVertices(int numPoints) {
        return (numPoints + 1) * 2;
    }
    private static int sizeOfTriangleVectices(){return 3;}
    private static int sizeOfSphereInVeryices(int numPoints){
        int t=0;
        for (float pai = -90.0f; pai < 90.0f; pai += numPoints) {
            for (float theta = 0.0f; theta <= 360.0f+numPoints; theta += numPoints) {
                t+=2;
            }
        }
        return t;
    }

    private final float[] vertexData;
    private final List<DrawCommand> drawList = new ArrayList<>();
    private int offset = 0;

    private ObjectBuilder(int sizeInVertices) {
        vertexData = new float[sizeInVertices * FLOATS_PER_VERTEX];
    }

    private void appendSphere(Geometry.Sphere sph, int numPoints){
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfSphereInVeryices(numPoints);
        float co, si;
        float r1, r2, h1, h2, theta;

        for (float pai = -90.0f; pai < 90.0f; pai += numPoints) {
            r1 = (float) Math.cos(pai * Math.PI / 180.0);
            r2 = (float) Math.cos((pai + numPoints) * Math.PI / 180.0);
            h1 = (float) Math.sin(pai * Math.PI / 180.0);
            h2 = (float) Math.sin((pai + numPoints) * Math.PI / 180.0);
            for (theta = 0.0f; theta <= 360.0f+numPoints; theta += numPoints) {
                co = (float) Math.cos(theta * Math.PI / 180.0);
                si = -(float) Math.sin(theta * Math.PI / 180.0);
                //first point
                vertexData[offset++] = sph.radius * (r2 * co)+sph.center.x;
                vertexData[offset++] = sph.radius * (h2)+sph.center.y;
                vertexData[offset++] = sph.radius * (r2 * si)+sph.center.z;
                //first point texture
                vertexData[offset++] = 0.5f+(float)Math.atan2(h2,r2 * co)/2/(float)Math.PI;
                vertexData[offset++] = (float) (0.5f-Math.asin(r2*si)/Math.PI);

                vertexData[offset++] = sph.radius * (r2 * co)+sph.center.x;
                vertexData[offset++] = sph.radius * (h2)+sph.center.y;
                vertexData[offset++] = sph.radius * (r2 * si)+sph.center.z;
                //next point
                vertexData[offset++] = sph.radius * (r1 * co)+sph.center.x;
                vertexData[offset++] = sph.radius * (h1)+sph.center.y;
                vertexData[offset++] = sph.radius * (r1 * si)+sph.center.z;
                //next point texture
                vertexData[offset++] = 0.5f+(float)Math.atan2(h1,r1 * co)/2/(float)Math.PI;
                vertexData[offset++] = (float) (0.5f-Math.asin(r1*si)/Math.PI);

                vertexData[offset++] = sph.radius * (r1 * co)+sph.center.x;
                vertexData[offset++] = sph.radius * (h1)+sph.center.y;
                vertexData[offset++] = sph.radius * (r1 * si)+sph.center.z;
            }
        }
        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private void appendTriangle(Geometry.Triangle triangle, int numPoints){
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = numPoints;

        // Center point of fan
        for (int i = 0; i < numPoints; i++) {
            float angleInRadians = (((float) i / (float) numPoints)-(1f/4f)) * ((float) Math.PI * 2f);
            vertexData[offset++] = (float) (triangle.center.x + triangle.edgelength * Math.cos(angleInRadians));
            vertexData[offset++] = (float) (triangle.center.y + triangle.edgelength * Math.sin(-angleInRadians));
            vertexData[offset++] = triangle.center.z;

            vertexData[offset++] = (float) (0.5f+(Math.cos(angleInRadians))/2);
            vertexData[offset++] = (float) (0.5f-(Math.sin(angleInRadians))/2);

            vertexData[offset++] = (float) (triangle.center.x + triangle.edgelength * Math.cos(angleInRadians));
            vertexData[offset++] = (float) (triangle.center.y + triangle.edgelength * Math.sin(-angleInRadians));
            vertexData[offset++] = triangle.center.z;
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices);
            }
        });
    }

    private void appendPlygen(ObjLoader.ObjFile file, int numPoints,float height){
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = numPoints;
        for(int i = 0;i<numPoints;i++){
            vertexData[offset++]=file.positions[i*3];
            vertexData[offset++]=file.positions[i*3+1]+height;
            vertexData[offset++]=file.positions[i*3+2];

            vertexData[offset++]=file.textureCoordinates[i*2];
            vertexData[offset++]=file.textureCoordinates[i*2+1];

            vertexData[offset++]=file.normal[i*3];
            vertexData[offset++]=file.normal[i*3+1];
            vertexData[offset++]=file.normal[i*3+2];
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLES, startVertex, numVertices);
            }
        });
    }

    private void appendCircle(Circle circle, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfCircleInVertices(numPoints);

        // Center point of fan
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;
        vertexData[offset++] = 0.5f;
        vertexData[offset++] = 0.5f;
        vertexData[offset++] = circle.center.x;
        vertexData[offset++] = circle.center.y;
        vertexData[offset++] = circle.center.z;

        // Fan around center point. <= is used because we want to generate
        // the point at the starting angle twice to complete the fan.
        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = ((float) i / (float) numPoints) * ((float) Math.PI * 2f);
            float xPosition = (float) (circle.center.x + circle.radius * Math.cos(angleInRadians));
            float zPosition = (float) (circle.center.z + circle.radius * Math.sin(-angleInRadians));
            //first point
            vertexData[offset++] = xPosition;
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = zPosition;
            //first point texture
            vertexData[offset++] = (float) (circle.radius * Math.cos(angleInRadians))/20;
            vertexData[offset++] = (float) (circle.radius * Math.sin(-angleInRadians))/20;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = circle.center.y;
            vertexData[offset++] = zPosition;
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices);
            }
        });
    }

    private void appendOpenCylinder(Cylinder cylinder, int numPoints) {
        final int startVertex = offset / FLOATS_PER_VERTEX;
        final int numVertices = sizeOfOpenCylinderInVertices(numPoints);

        final float yStart = 0f;
        final float yEnd = cylinder.center.y + (cylinder.height);

        // Generate strip around center point. <= is used because we want to
        // generate the points at the starting angle twice, to complete the
        // strip.
        for (int i = 0; i <= numPoints; i++) {
            float angleInRadians = ((float) i / (float) numPoints) * ((float) Math.PI * 2f);
            float xPosition = (float) (cylinder.center.x + cylinder.radius * Math.cos(angleInRadians));
            float zPosition = (float) (cylinder.center.z + cylinder.radius * Math.sin(-angleInRadians));

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;
            vertexData[offset++] = (float) Math.abs(2*i-numPoints)/numPoints;
            vertexData[offset++] = 0.8f;
            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;

            vertexData[offset++] = xPosition;
            vertexData[offset++] = yEnd;
            vertexData[offset++] = zPosition;
            vertexData[offset++] = (float) Math.abs(2*i-numPoints)/numPoints;
            vertexData[offset++] = 0f;
            vertexData[offset++] = xPosition;
            vertexData[offset++] = yStart;
            vertexData[offset++] = zPosition;
        }

        drawList.add(new DrawCommand() {
            @Override
            public void draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex,
                    numVertices);
            }
        });        
    }

    private GeneratedData build() {
        return new GeneratedData(vertexData, drawList);
    }
}
