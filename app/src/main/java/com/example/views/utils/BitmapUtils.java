package com.example.views.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {  
      
      
      
    /** 
     * @param将图片内容解析成字节数组
     * @param inStream 
     * @return byte[] 
     * @throws Exception 
     */  
    public static byte[] readStream(InputStream inStream) throws Exception {  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        while ((len = inStream.read(buffer)) != -1) {  
            outStream.write(buffer, 0, len);  
        }  
        byte[] data = outStream.toByteArray();  
        outStream.close();  
        inStream.close();  
        return data;  
  
    }  
    public static void saveImageToGallery(Context context,String filePath, Bitmap bmp) {
    	// 首先保存图片     
//    	File appDir = new File(dirpath); 
//    	if (!appDir.exists()) {
//    		appDir.mkdir();     
//    	}     
//    	
   	File file = new File(filePath);
   	String fileName=file.getName();
    	try {
    		FileOutputStream fos = new FileOutputStream(file);
    		bmp.compress(CompressFormat.JPEG, 100, fos);
    		fos.flush();
    		fos.close();
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    				}         
    	// 其次把文件插入到系统图库     
    	try {
    		MediaStore.Images.Media.insertImage(context.getContentResolver(),file.getAbsolutePath(), fileName, null);
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    			}     // 最后通知图库更新
    	context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
    	
    }
    /** 
     * @param将字节数组转换为ImageView可调用的Bitmap对象
     * @param bytes 
     * @param opts 
     * @return Bitmap 
     */  
    public static Bitmap getPicFromBytes(byte[] bytes,  
            BitmapFactory.Options opts) {  
        if (bytes != null)  
            if (opts != null)  
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,  
                        opts);  
            else  
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  
        return null;  
    }  
    /** 
     * @param图片缩放
     * @param bitmap 对象 
     * @param w 要缩放的宽度 
     * @param h 要缩放的高度 
     * @return newBmp 新 Bitmap对象 
     */  
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){  
        int width = bitmap.getWidth();  
        int height = bitmap.getHeight();  
        Matrix matrix = new Matrix();  
        float scaleWidth = ((float) w / width);  
        float scaleHeight = ((float) h / height);  
        matrix.postScale(scaleWidth, scaleHeight);  
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,  
                matrix, true);  
        return newBmp;  
    }  
      
    /**
     * 将图片文件转为字节流
     */
    public static byte[] File2Bytes(String filePath){
    	
    		File inFile = new File(filePath);
    		if(!inFile.exists()){
    			return null;
    		}
    		byte[] filea =new byte[(int) inFile.length()];
    		FileInputStream fileInputStream=null;
			try {
				fileInputStream = new FileInputStream(inFile);
			
				fileInputStream.read(filea);
				fileInputStream.close();//关闭输入流
    		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return filea;
    }
    /** 
     * 把Bitmap转Byte 
     */  
    public static byte[] Bitmap2Bytes(Bitmap bm){  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();  
    }  
    /** 
     * 把字节数组保存为一个文件 
     */  
    public static File getFileFromBytes(byte[] b, String outputFile) {  
        BufferedOutputStream stream = null;  
        File file = null;  
        try {  
            file = new File(outputFile);  
            FileOutputStream fstream = new FileOutputStream(file);  
            stream = new BufferedOutputStream(fstream);  
            stream.write(b);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (stream != null) {  
                try {  
                    stream.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
        return file;  
    }  
          
}  