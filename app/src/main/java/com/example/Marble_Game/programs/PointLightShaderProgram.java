/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/
package com.example.Marble_Game.programs;

import android.content.Context;

import com.example.Marble_Game.R;
import com.example.Marble_Game.util.MatrixState;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform3fv;
import static android.opengl.GLES20.glUniformMatrix4fv;

public class PointLightShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int uTextureUnitLocation;
    private final int uMVPMatrixHandle;
    private final int uMMatrixHandle;
    private final int maCameraHandle;
    private final int muRHandle;
    private final int maLightLocationHandle;
    private final int maNormalHandle;

    // Attribute locations
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public PointLightShaderProgram(Context context) {
        super(context, R.raw.light_vertex_shader,
            R.raw.light_fragment_shader);

        // Retrieve uniform locations for the shader program.
        uMVPMatrixHandle = glGetUniformLocation(program, U_MVPMATRIX);
        uMMatrixHandle =glGetUniformLocation(program,U_MVMATRIX);
        maCameraHandle=glGetUniformLocation(program,U_CAMERA);
        maLightLocationHandle=glGetUniformLocation(program,U_LIGHTLOCATION);
        muRHandle=glGetUniformLocation(program,U_R);

        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);
        maNormalHandle=glGetAttribLocation(program,A_NORMAL);
        // Retrieve attribute locations for the shader program.
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(int textureId) {
        // Pass the matrix into the shader program.
        glUniformMatrix4fv(uMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        glUniformMatrix4fv(uMMatrixHandle,1,false,MatrixState.getMMatrix(),0);
        glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
        //將光源位置傳入著色器程序

        glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
        // Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0);

        // Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId);


        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        glUniform1i(uTextureUnitLocation, 0);

    }
    
    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }
    public int getTextureCoordinatesAttributeLocation() {
        return aTextureCoordinatesLocation;
    }
    public int getNormalAttributeLocation() {
        return maNormalHandle;
    }
}