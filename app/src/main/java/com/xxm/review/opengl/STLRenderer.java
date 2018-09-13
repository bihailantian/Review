package com.xxm.review.opengl;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.xxm.review.domain.Model;
import com.xxm.review.domain.Point;
import com.xxm.review.opengl.reader.STLReader;
import com.xxm.review.utils.GlUtils;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class STLRenderer implements GLSurfaceView.Renderer {

    private Model model;
    private Point mCenterPoint;
    private Point eye = new Point(0, 0, -3);
    private Point up = new Point(0, 1, 0);
    private Point center = new Point(0, 0, 0);
    private float mScalef = 1;
    private float mDegree = 0;


    public STLRenderer(Context context) {

        try {
            model = new STLReader().parserBinInAsserts(context, "bear.stl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void rotate(float degree) {
        mDegree = degree;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度缓存
        gl.glClearDepthf(1.0f); // 设置深度缓存值
        gl.glDepthFunc(GL10.GL_LEQUAL); // 设置深度缓存比较函数
        gl.glShadeModel(GL10.GL_SMOOTH);// 设置阴影模式GL_SMOOTH

        //打开灯光
        openLight(gl);

        enableMaterial(gl);

        float r = model.getR();
        //r是半径，不是直径，因此用0.5/r可以算出放缩比例
        mScalef = 0.5f / r;
        mCenterPoint = model.getCentrePoint();
    }


    float[] materialAmb = {0.4f, 0.4f, 1.0f, 1.0f,};
    float[] materialDiff = {0.0f, 0.0f, 1.0f, 1.0f,};
    float[] materialSpec = {1.0f, 0.5f, 0.0f, 1.0f,};

    /**
     * 设置材料属性
     */
    private void enableMaterial(GL10 gl) {

        /*
        face : 在OpenGL ES中只能使用GL_FRONT_AND_BACK，表示修改物体的前面和后面的材质光线属性。
        pname: 参数类型，这些参数用在光照方程。可以取如下值：
            GL_AMBIENT
            GL_DIFFUSE
            GL_SPECULAR
            GL_EMISSION
            GL_SHININESS。
        param：指定反射的颜色。
         */

        //材料对环境光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, GlUtils.floatToBuffer(materialAmb));
        //散射光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, GlUtils.floatToBuffer(materialDiff));
        //镜面光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, GlUtils.floatToBuffer(materialSpec));
    }


    float[] ambient = {0.9f, 0.9f, 0.9f, 1.0f};
    float[] diffuse = {0.5f, 0.5f, 0.5f, 1.0f};
    float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};
    float[] lightPosition = {0.5f, 0.5f, 0.5f, 0.0f};

    /**
     * 打开灯光
     */
    private void openLight(GL10 gl) {
        /*
        light: 指光源的序号，OpenGL ES可以设置从0到7共八个光源。
        pname: 光源参数名称，可以有如下：
            GL_SPOT_EXPONENT
            GL_SPOT_CUTOFF
            GL_CONSTANT_ATTENUATION
            GL_LINEAR_ATTENUATION
            GL_QUADRATIC_ATTENUATION
            GL_AMBIENT(用于设置环境光颜色)
            GL_DIFFUSE(用于设置漫反射光颜色)
            GL_SPECULAR(用于设置镜面反射光颜色)
            GL_SPOT_DIRECTION
            GL_POSITION（用于设置光源位置）
        params: 参数的值（数组或是Buffer类型），数组里面含有4个值分别表示R,G,B,A。
         */

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, GlUtils.floatToBuffer(ambient));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, GlUtils.floatToBuffer(diffuse));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, GlUtils.floatToBuffer(specular));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, GlUtils.floatToBuffer(lightPosition));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(width, height)指定了视口的大小
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION); // 设置投影矩阵
        gl.glLoadIdentity(); // 设置矩阵为单位矩阵，相当于重置矩阵
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);// 设置透视范围

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();


        //眼睛对着原点看
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);

        //为了能有立体感觉，通过改变mDegree值，让模型不断旋转
        gl.glRotatef(mDegree, 0, 1, 0);

        //将模型放缩到View刚好装下
        gl.glScalef(mScalef, mScalef, mScalef);
        //把模型移动到原点
        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y, -mCenterPoint.z);


        //===================begin==============================//

        //允许给每个顶点设置法向量
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        // 允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 允许设置颜色

        //设置法向量数据源
        gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
        // 设置三角形顶点数据源
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());

        // 绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFaceCount() * 3);

        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //取消法向量设置
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        //=====================end============================//
    }
}
