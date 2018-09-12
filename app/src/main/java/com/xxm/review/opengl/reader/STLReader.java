package com.xxm.review.opengl.reader;


import android.content.Context;

import com.xxm.review.domain.Model;
import com.xxm.review.utils.GlUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class STLReader {

    private StlLoadListener stlLoadListener;

    //从SDCard中解析二进制的Stl文件
    public Model parserBinStlInSDCard(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        return parserBinStl(fis);
    }

    //从Asserts文件中解析二进制的Stl文件
    public Model parserBinInAsserts(Context context, String fileName) throws IOException {
        InputStream is = context.getAssets().open(fileName);
        return parserBinStl(is);
    }


    //解析二进制的Stl文件
    private Model parserBinStl(InputStream in) throws IOException {
        if (stlLoadListener != null) {
            stlLoadListener.onStart();
        }
        Model model = new Model();
        //前面80字节是文件头，用于存储文件名
        in.skip(80);

        //紧接着用4个字节的整数来描述三角面片个数
        byte[] bytes = new byte[4];
        in.read(bytes);
        int facetCount = GlUtils.byte4ToInt(bytes, 0);
        model.setFaceCount(facetCount);
        if (facetCount == 0) {
            in.close();
            return model;
        }

        //每个三角面片占用固定的50字节
        // TODO: 数组越界，或 OOM
        byte[] facetBytes = new byte[10 * facetCount];
        //将所有的三角面片读取到字节数组
        in.read(facetBytes);
        //数据读取完毕后，关闭输入流
        in.close();

        parserModel(model, facetBytes);
        if (stlLoadListener != null) {
            stlLoadListener.onFinished();
        }

        return model;
    }

    /**
     * 解析模型数据，包括顶点数据，法向量数据、所占空间范围等
     *
     * @param model      模型实体类对象
     * @param facetBytes 所有三角面片的占用的字节数组
     */
    private void parserModel(Model model, byte[] facetBytes) {
        int facetCount = model.getFaceCount();
        /*
         *  每个三角面片占用固定的50个字节,50字节当中：
         *  三角片的法向量：（1个向量相当于一个点）*（3维/点）*（4字节浮点数/维）=12字节
         *  三角片的三个点坐标：（3个点）*（3维/点）*（4字节浮点数/维）=36字节
         *  最后2个字节用来描述三角面片的属性信息
         */

        //保存所有顶点坐标信息，一个三角形三个顶点，一个顶点3个坐标轴
        float[] verts = new float[facetCount * 3 * 3];
        // 保存所有三角面对应的法向量位置，
        // 一个三角面对应一个法向量，一个法向量有3个点
        // 而绘制模型时，是针对需要每个顶点对应的法向量，因此存储长度需要*3
        // 又同一个三角面的三个顶点的法向量是相同的，
        // 因此后面写入法向量数据的时候，只需连续写入3个相同的法向量即可
        float[] vnorms = new float[facetCount * 3 * 3];
        //保存所有的三角形面的属性信息
        short[] remarks = new short[facetCount];


        int stlOffset = 0;
        try {
            for (int i = 0; i < facetCount; i++) {
                if (stlLoadListener != null) {
                    stlLoadListener.onLoading(i, facetCount);
                }

                for (int j = 0; j < 4; j++) {
                    float x = GlUtils.byte4ToFloat(facetBytes, stlOffset);
                    float y = GlUtils.byte4ToFloat(facetBytes, stlOffset + 4);
                    float z = GlUtils.byte4ToFloat(facetBytes, stlOffset + 8);
                    stlOffset += 12;

                    if (j == 0) {  //法向量
                        vnorms[i * 9] = x;
                        vnorms[i * 9 + 1] = y;
                        vnorms[i * 9 + 2] = z;
                        vnorms[i * 9 + 3] = x;
                        vnorms[i * 9 + 4] = y;
                        vnorms[i * 9 + 5] = z;
                        vnorms[i * 9 + 6] = x;
                        vnorms[i * 9 + 7] = y;
                        vnorms[i * 9 + 8] = z;
                    } else { //三个顶点
                        verts[i * 9 + (j - 1) * 3] = x;
                        verts[i * 9 + (j - 1) * 3 + 1] = y;
                        verts[i * 9 + (j - 1) * 3 + 2] = z;

                        //记录模型中三个坐标轴方向的最大最小值
                        if (i == 0 && j == 1) {
                            model.setMaxX(x);
                            model.setMinX(x);

                            model.setMaxY(y);
                            model.setMinY(y);

                            model.setMaxZ(z);
                            model.setMinZ(z);
                        } else {
                            model.setMaxX(Math.max(model.getMaxX(), x));
                            model.setMinX(Math.min(model.getMinX(), x));

                            model.setMaxY(Math.max(model.getMaxY(), y));
                            model.setMinY(Math.min(model.getMinY(), y));

                            model.setMaxZ(Math.max(model.getMaxZ(), z));
                            model.setMinZ(Math.min(model.getMinZ(), z));
                        }
                    }
                }


                short r = GlUtils.byte2ToShort(facetBytes, stlOffset);
                stlOffset = stlOffset + 2;
                remarks[i] = r;


            }
        } catch (Exception e) {
            if (stlLoadListener != null) {
                stlLoadListener.onFailure(e);
            } else {
                e.printStackTrace();
            }
        }

        //将读取到的数据设置到Model对象中
        model.setVerts(verts);
        model.setVnorms(vnorms);
        model.setRemarks(remarks);


    }


    public static interface StlLoadListener {

        void onStart();

        void onLoading(int cur, int total);

        void onFinished();

        void onFailure(Exception e);

    }


}
