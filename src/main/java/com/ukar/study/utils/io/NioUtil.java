package com.ukar.study.utils.io;

import com.alibaba.fastjson.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class NioUtil {
    /**
     * 使用nio复制文件
     * @param source 源文件地址
     * @param target 目标文件地址
     */
    public static void copyFile(String source, String target) throws Exception {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try{
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            FileChannel inChannel = inputStream.getChannel();
            FileChannel outChannel = outputStream.getChannel();

            //获得容器buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true){
                int read = inChannel.read(buffer);
                if(read == -1){
                    break;
                }
                //重设一下buffer的 limit = position , position = 0
                buffer.flip();
                outChannel.write(buffer);
                //写完要重置buffer，重设position = 0,limit = capacity
                buffer.clear();
            }
        }finally {
            IOUtils.close(inputStream);
            IOUtils.close(outputStream);
        }

    }

    /**
     * 按行读取文件
     * @param encode 编码
     */
    public static void readByLine(String source, String encode){
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(source);
            FileChannel inCh = inputStream.getChannel();
            //定义读取缓存
            ByteBuffer rbuf = ByteBuffer.allocate(1024);

            //用来缓存上次读取剩下的部分
            byte[] tmp = new byte[10];
            //换行符
            int LF = "\n".getBytes()[0];
            while(inCh.read(rbuf) != -1){
                int position = rbuf.position();
                byte[] rbyte = new byte[position];
                rbuf.flip();
                rbuf.get(rbyte);
                //判断是否有换行符
                int startnum = 0;
                for(int i = 0; i < rbyte.length; i++){
                    if(rbyte[i] == LF){
                        //若存在换行符
                        byte[] tmp1 = new byte[tmp.length + i - startnum + 1];
                        System.arraycopy(tmp, 0, tmp1, 0, tmp.length);
                        System.arraycopy(rbyte, startnum, tmp1, tmp.length, i - startnum + 1);
                        startnum = i + 1;
                        tmp = new byte[0];
                        //这里是读取的数据
                        String line = new String(tmp1, encode);
                        System.out.println(line);
                    }
                }
                if(startnum < rbyte.length){
                    //说明rbyte最后还剩不完整的一行
                    byte[] temp2 = new byte[tmp.length + rbyte.length - startnum];
                    System.arraycopy(tmp, 0, temp2, 0, tmp.length);
                    System.arraycopy(rbyte, startnum, temp2, tmp.length, rbyte.length - startnum);
                    tmp = temp2;
                }
                rbuf.clear();
            }
            //兼容最后一行没有换行的情况
            if(tmp.length > 0){
                String line = new String(tmp, encode);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            IOUtils.close(inputStream);
        }
    }

    public static void main(String[] args) throws Exception {
        String source = "/Users/youjia/Desktop/file/pa/刷merId完成版.txt";
        String target = "/Users/youjia/Desktop/file/pa/刷merId完成版1.txt";
        NioUtil.readByLine(source, "GBK");
    }

}
