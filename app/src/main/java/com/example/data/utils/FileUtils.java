package com.example.data.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZN on 2015/9/12.
 */
public class FileUtils {

    private static FileOutputStream fileOutputStream;
	private static ObjectOutputStream objectOutputStream;
	private static ObjectInputStream objectInputStream;
	private static FileInputStream fileInputStream;
	@SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
	/**
     * 递归删除文件和文件夹
     * 
     * @param file
     *            要删除的根目录
     */ 
    public static void DeleteFile(File file) { 
        if (file.exists() == false) { 
          // ChatApplication.getInstance().showMessage("没有缓存目录！"); 
            return; 
        } else { 
            if (file.isFile()) { 
                file.delete(); 
                return; 
            } 
            if (file.isDirectory()) { 
                File[] childFile = file.listFiles(); 
                if (childFile == null || childFile.length == 0) { 
                    file.delete(); 
                    return; 
                } 
                for (File f : childFile) { 
                    DeleteFile(f); 
                } 
                file.delete(); 
            } 
        } 
    } 
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
    /**
	 * 读取表情配置文件
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getEmojiFile(Context context) {
		try {
			List<String> list = new ArrayList<String>();
			InputStream in = context.getResources().getAssets().open("emoji");// �ļ�����Ϊrose.txt
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String str = null;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}

			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 序列化对象至本地;
	 * 
	 * @param context
	 * @return
	 */
	public static boolean saveObjectToSD(String filePath,Object object) {
		
		try {
			//存入数据
		
		    File file = new File(filePath);
		    if (!file.getParentFile().exists()) {
		        file.getParentFile().mkdirs();
		    }
		 
		    if (!file.exists()) {
		        file.createNewFile();
		    }
		    fileOutputStream= new FileOutputStream(file.toString());
		    objectOutputStream= new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(object);
		
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		finally{
		    if (objectOutputStream!=null) {
		        try {
		            objectOutputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		            return false;
		        }
		    }
		    if (fileOutputStream!=null) {
		        try {
		            fileOutputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		            return false;
		        }
		    }
		}
		return true;
	}
	/**
	 * 序列化对象至本地;
	 * 
	 * @param context
	 * @return
	 */
	public static Object loadObjectFromSD(String filePath) {
		 Object object=null;
		try {
			//存入数据
		//	String tempPath = Environment.getExternalStorageDirectory().getPath() +"/"+BitmapTools.APP_FILE+"/"+BitmapTools.APP_BACKUP+"/temp.jpg";//
		    File file = new File(filePath);
		 
		    if (!file.exists()) {
		        return null;
		    }
		    fileInputStream = new FileInputStream(file.toString());
		    objectInputStream = new ObjectInputStream(fileInputStream);
		    object=  objectInputStream.readObject();
		
			
		} catch (IOException e) {
			e.printStackTrace();
			object=null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			object=null;
		}finally{
			if (objectInputStream!=null) {
		        try {
		            objectInputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		            object=null;
		        }
		    }
		    if (fileInputStream!=null) {
		        try {
		            fileInputStream.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		            object=null;
		        }
		    }
		}
		return object;
	}
}
