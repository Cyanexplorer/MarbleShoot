
precision mediump float;

varying vec3 vPosition;
varying vec4 vAmbient;
varying vec4 vDiffuse;
varying vec4 vSpecular;
varying vec4 vShadowCoord;

uniform sampler2D uTextureUnit;
varying vec2 vTextureCoordinates;


void main() {

    vec4 temperature=vec4(1.2,1.2,1.0,0.0);
    vec4 finalColor=texture2D(uTextureUnit, vTextureCoordinates)*temperature;//vec4(color,0);

    gl_FragColor=(finalColor*vAmbient+finalColor*vDiffuse+finalColor*vSpecular);
}
