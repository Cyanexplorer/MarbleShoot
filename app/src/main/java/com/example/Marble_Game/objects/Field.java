package com.example.Marble_Game.objects;

import com.example.Marble_Game.Builder.ObjectBuilder;
import com.example.Marble_Game.data.VertexArray;
import com.example.Marble_Game.programs.PointLightShaderProgram;
import com.example.Marble_Game.util.Geometry;

import java.util.List;

import static com.example.Marble_Game.util.Constants.F_FRAG;
import static com.example.Marble_Game.util.Constants.NORMAL_COMPONENT_COUNT;
import static com.example.Marble_Game.util.Constants.POSITION_COMPONENT_COUNT;
import static com.example.Marble_Game.util.Constants.STRIDE;
import static com.example.Marble_Game.util.Constants.S_FRAG;
import static com.example.Marble_Game.util.Constants.TEXTURE_COMPONENT_COUNT;

/**
 * Created by User on 2016/12/26.
 */

public class Field {

    public final float radius;
    public final float height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Field(float radius, float height, int numPoints) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createField(
                new Geometry.Point(0f, 0f, 0f), radius, height, numPoints);

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(PointLightShaderProgram lightprogram) {
        //vertice to glsl
        vertexArray.setVertexAttribPointer(0,
                lightprogram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, STRIDE);
        //texture to glsl
        vertexArray.setVertexAttribPointer(F_FRAG,
                lightprogram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COMPONENT_COUNT,STRIDE);
        //vector to glsl
        vertexArray.setVertexAttribPointer(S_FRAG,
                lightprogram.getNormalAttributeLocation(),NORMAL_COMPONENT_COUNT,
                STRIDE);
    }

    //store operation
    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
