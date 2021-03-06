package com.xxm.review.domain;


import com.xxm.review.utils.GlUtils;

import java.nio.FloatBuffer;

public class Model {

    //三角面个数
    private int faceCount;
    //顶点坐标数组
    private float[] verts;
    //每个顶点对应的法向量的数组
    private float[] vnorms;
    //每个三角面的属性信息
    private short[] remarks;

    //顶点数组转换而来的Buffer
    private FloatBuffer vertBuffer;
    //每个顶点对应的法向量转换而来的Buffer
    private FloatBuffer vnormBuffer;
    //以下分别保存x、y、z方向上的最大值 、最小值
    float maxX;
    float minX;
    float maxY;
    float minY;
    float maxZ;
    float minZ;

    //返回的模型中心点
    public Point getCentrePoint() {
        float cx = minX + (maxX - minX) / 2;
        float cy = minY + (maxY - minY) / 2;
        float cz = minZ + (maxZ - minZ) / 2;
        return new Point(cx, cy, cz);
    }

    //包裹模型的最大半径
    public float getR() {
        float dx = maxX - minX;
        float dy = maxY - minY;
        float dz = maxZ - minZ;
        float max = dx;
        if (dy > max) {
            max = dy;
        }
        if (dz > max) {
            max = dz;
        }
        return max;
    }


    public int getFaceCount() {
        return faceCount;
    }

    public void setFaceCount(int faceCount) {
        this.faceCount = faceCount;
    }

    public float[] getVerts() {
        return verts;
    }

    //设置顶点数据的同时，设置对应的Buffer
    public void setVerts(float[] verts) {
        this.verts = verts;
        vertBuffer = GlUtils.floatToBuffer(verts);
    }

    public float[] getVnorms() {
        return vnorms;
    }

    //设置顶点数组法向量的同时，设置对应的Buffer
    public void setVnorms(float[] vnorms) {
        this.vnorms = vnorms;
        vnormBuffer = GlUtils.floatToBuffer(vnorms);
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }

    public FloatBuffer getVertBuffer() {
        return vertBuffer;
    }

    public void setVertBuffer(FloatBuffer vertBuffer) {
        this.vertBuffer = vertBuffer;
    }

    public FloatBuffer getVnormBuffer() {
        return vnormBuffer;
    }

    public void setVnormBuffer(FloatBuffer vnormBuffer) {
        this.vnormBuffer = vnormBuffer;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getMinX() {
        return minX;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMinY() {
        return minY;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public float getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(float maxZ) {
        this.maxZ = maxZ;
    }

    public float getMinZ() {
        return minZ;
    }

    public void setMinZ(float minZ) {
        this.minZ = minZ;
    }
}
