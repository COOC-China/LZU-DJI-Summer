package com.company;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_face;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_highgui.imshow;
import static org.bytedeco.javacpp.opencv_highgui.waitKey;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

public class Main
{
    private List<String> imageList=new ArrayList<String>();
    private Map<Integer,String> labelMap=new HashMap<>();
    private opencv_face.BasicFaceRecognizer faceRecognizer;
    public Main()
    {

        //构造函数
        readFile("C:\\Users\\YAN\\IdeaProjects\\face_idf\\data");
        /*
        for(String s:imageList)
        {
            System.out.println(s);
            String[] splits=s.split("/");
            System.out.println(splits[splits.length-2]);
        }
        */
        faceTrain();
        facePredict("4.pgm");

    }
    public void faceTrain()
    {
        int size=imageList.size();
        opencv_core.MatVector images=new opencv_core.MatVector(size);
        Mat matlabel=new Mat(imageList.size(),1,CV_32SC1);
        IntBuffer intBuffer=matlabel.getIntBuffer();
        int count=0;
        for(String imagepath:imageList)
        {
            opencv_core.Mat mat=imread(imagepath,CV_LOAD_IMAGE_GRAYSCALE);//读入每一张图片
            //归一化
            opencv_core.Mat normalMat=new opencv_core.Mat();
            normalize(mat,normalMat,0,255,NORM_MINMAX,CV_8UC1,noArray());
            String [] splits=imagepath.split("/");
            String label=splits[splits.length-2];
            int lab=Integer.parseInt(label.charAt(1)+"");//加“”，函数只参数为字符串
            intBuffer.put(count,lab);
            images.put(count, normalMat);//列与标签的对应关系
            labelMap.put(lab,label);//label与label标号的对应关系
            count++;
        }
        faceRecognizer = createEigenFaceRecognizer(30,5000);
        faceRecognizer.train(images,matlabel);
    }
    public void facePredict(String path)
    {
        Mat mat=imread(path,CV_LOAD_IMAGE_GRAYSCALE);
        Mat matNormal=new Mat();
        normalize(mat,matNormal,0,255,NORM_MINMAX,CV_8UC1,noArray());
        int result=faceRecognizer.predict(matNormal);
        String trueResult=labelMap.get(result);
        System.out.println(trueResult);

    }
    public void readFile(String path)
    {
     File file=new File(path);
        if(!file.isDirectory())
        {
            if(path.endsWith(".pgm")||path.endsWith(".jpg"))
            {
              imageList.add(path);
            }
        }
        else
        {
            String[] pa=file.list();//目录下所有文件都列出来
            for(String p:pa)
            {
                //遍历一下数组
                String newPath=path+"/"+p;//找其下一层
                readFile(newPath);

            }
        }
    }
    public static void main(String[] args)
    {
	    // write your code here
        /*
        opencv_core.Mat mat=imread("1.jpg",CV_LOAD_IMAGE_GRAYSCALE);
        imwrite("2.jpg",mat);
        imshow("test",mat);
        waitKey(0);
        */
        new Main();


    }
}
