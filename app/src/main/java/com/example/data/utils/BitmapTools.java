package com.example.data.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.administrator.gradesystem.R;
import com.example.view.utils.CommonAdapter;
import com.example.view.utils.ViewHolder;

public class BitmapTools {
	
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��		
			baos.reset();//����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			options -= 10;//ÿ�ζ�����10
			if (options<=0) {
				break;
			}
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ
		if (image != bitmap ) {
			image.recycle();
		}
		return bitmap;
	}
	
	/**
	 * 根据路径读取一张本地图片到内存
	 * @param filePath 
	 * @param W  压缩宽度
	 * @param H  压缩高度
	 * @return
	 */
	public static Bitmap readImgByPath(String filePath ,int W,int H) {
		if(filePath == null){
			Log.e("=====>>>>>> readImgByPath ", "readImgByPath filePath is null !!");
			return null;
		}
		Log.i("=====>>>>>> readImgByPath filePath", filePath);
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		BitmapFactory.Options opts =  new  BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath ,opts);
		int imgw = opts.outWidth;
		int imgh = opts.outHeight;
		int insize = 1;
		if (imgw> W || imgh >H) {//图片像素有一个如果有一个大于我们的设定，则压缩我们的图片；
			insize = Math.max(imgw/W , imgh/H);
//			insize =(int)Math.pow( 2, Math.round( Math.log((double)insize)/Math.log((double)2))) ;
		}
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = insize;
		Bitmap bm = BitmapFactory.decodeFile(filePath ,opts);
		return compressImage(bm);
	}
//	public static Bitmap readImgByBitmap(Bitmap image ,int W,int H) {
//		if(image == null){
//			Log.e("=====>>>>>> readImgByPath ", "readImgByPath filePath is null !!");
//			return null;
//		}
//	
//		
//		BitmapFactory.Options opts =  new  BitmapFactory.Options();
//		opts.inJustDecodeBounds = true;
//		 BitmapFactory.de(image, 0, image.length,  
//                 opts); 
//		int imgw = opts.outWidth;
//		int imgh = opts.outHeight;
//		int insize = 1;
//		if (imgw> W || imgh >H) {//图片像素有一个如果有一个大于我们的设定，则压缩我们的图片；
//			insize = Math.max(imgw/W , imgh/H);
////			insize =(int)Math.pow( 2, Math.round( Math.log((double)insize)/Math.log((double)2))) ;
//		}
//		opts.inJustDecodeBounds = false;
//		opts.inSampleSize = insize;
//		Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length,  
//                opts); 
//		return compressImage(bm);
//	}
		public static Bitmap readImgByByte(byte[] b) {
			if (b.length != 0) {
				return BitmapFactory.decodeByteArray(b, 0, b.length);
			} else {
				return null;
			}
		}
	public static Bitmap readImgByByte(byte[] image ,int W,int H) {
		if(image == null||image.length==0){
			Log.e("=====>>>>>> readImgByPath ", "readImgByPath filePath is null !!");
			return null;
		}

		BitmapFactory.Options opts =  new  BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		 BitmapFactory.decodeByteArray(image, 0, image.length,  
                 opts); 
		int imgw = opts.outWidth;
		int imgh = opts.outHeight;
		int insize = 1;
		if (imgw> W || imgh >H) {//图片像素有一个如果有一个大于我们的设定，则压缩我们的图片；
			insize = Math.max(imgw/W , imgh/H);
//			insize =(int)Math.pow( 2, Math.round( Math.log((double)insize)/Math.log((double)2))) ;
		}
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = insize;
		Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length,  
                opts); 
		return compressImage(bm);
	}
	/**
	 * 把内存的一张图片保存到本地
	 * @param bm
	 * @param filePath
	 */
	public static void saveImgByPath(Bitmap bm, String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			 
			file.delete();
		}
		if(!file.getParentFile().exists()){
			checkMarkDir(file.getParentFile().getPath());
		}
		FileOutputStream fout;
		try {
			if( file.createNewFile()){
				fout = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.JPEG , 90, fout);
				fout.flush();
				fout.close();
			}
		} catch (FileNotFoundException e) {
			fout = null;
			e.printStackTrace();
		} catch (IOException e) {
			fout = null;
			e.printStackTrace();
		}
		fout = null;
	}
	public static String APP_FILE = "GradeSystem";
	public static String APP_IMG_FILE = "GradeSystemImage";
	public static String APP_TEMPFILE = "Temp";
	public static String APP_Cache="Cache";
	public static String HeadImageDir=Environment.getExternalStorageDirectory().getPath() +
			"/"+BitmapTools.APP_FILE+"/"+"HeadImages/" ;
	public static String ImageDir = Environment.getExternalStorageDirectory().getPath() +
			"/"+BitmapTools.APP_FILE+"/"+BitmapTools.APP_IMG_FILE+"/" ;//图片路径
	/**
	 *  检查路径是否已经创建，没创建就创建
	 * @param path
	 */
	public static void checkMarkDir(String path) {
		File file = new File(path);
		if( !file.exists() ){
			file.mkdirs();
		}
		
	}
	/**
	 * 根据名字来读某个文件夹下的一张图片到Bitmap
	 * @param fileName
	 * @return
	 */
	public static Bitmap readImgByName(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		
		return BitmapFactory.decodeFile(filePath);
	}
	public static Bitmap makeRoundCorner(Bitmap bitmap)
	{
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int left = 0, top = 0, right = width, bottom = height;
		float roundPx = height/2;
		if (width > height) {
			left = (width - height)/2;
			top = 0;
			right = left + height;
			bottom = height;
		} else if (height > width) {
			left = 0;
			top = (height - width)/2;
			right = width;
			bottom = top + width;
			roundPx = width/2;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		int color = 0xff424242;
		Paint paint = new Paint();
		Rect rect = new Rect(left, top, right, bottom);
		RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	/**
	 * 获取图片旋转的角度
	 * @param path   本地图片路径
	 * @return ������ת�Ƕ�
	 */
	public static int getBitmapDegree(String path) {
	    int degree = 0;
	    try {
	        ExifInterface exifInterface = new ExifInterface(path);
	        
	        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            degree = 90;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            degree = 180;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            degree = 270;
	            break;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return degree;
	}

	/***
	 * 將頭像轉為灰色
	 * @param bitmap
	 * @return
     */
	public static final Bitmap getGreyBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap
				.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
				colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}
	/***
	 * 將頭像轉為灰色
	 * @param bitmap
	 * @return
	 */
	public static final Bitmap getGreyBitmap(byte[] bytes) {
		if(bytes==null||bytes.length==0){
			return null;
		}
		Bitmap bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap
				.createBitmap(width, height, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
				colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}
	/**
	 * 旋转一张图片 一个角度
	 * @param bm
	 * @param degree
	 * @return
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
	    Bitmap returnBm = null;
	    Matrix matrix = new Matrix();
	    matrix.postRotate(degree);
	    try {
	        returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	    } catch (OutOfMemoryError e) {
	    	e.printStackTrace();
	    }
	    if (returnBm == null) {
	        returnBm = bm;
	    }
	    if (bm != returnBm) {
	        bm.recycle();
	    }
	    return returnBm;
	}
	
	/**
	 * 下拉菜单
	 * @param context
	 * @param anchor	下拉菜单停靠的view
	 * @param items		 
	 * @param mlistener 
	 */
	public static void ShowPopWindownMenu(Context context,final View anchor ,List<String> items ,final OnItemClickListener mlistener,final OnItemLongClickListener longClickListener) {
		if (items == null || items.size()<=0) {
			return;
		}
		final PopupWindow popw = new PopupWindow(context);
		ListView listview = new ListView(context);
		listview.setBackgroundDrawable( new BitmapDrawable());
		listview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		listview.setVerticalScrollBarEnabled(true);
		listview.setFadingEdgeLength(20);
		listview.setFastScrollEnabled(false);
		listview.setScrollBarFadeDuration(0);
		listview.setOverScrollMode(View.OVER_SCROLL_NEVER);
		BaseAdapter baseAdapter = new CommonAdapter<String>(context, items, R.layout.md_popmenu_item) {
			@Override
			public void convert(ViewHolder helper, String item) {
				View layoutView =  helper.getConvertView();
				if (layoutView != null) {
					LayoutParams lp = layoutView.getLayoutParams();
					if (lp == null) {
						lp = new LayoutParams(LayoutParams.MATCH_PARENT, anchor.getHeight());
					}
					lp.height = anchor.getHeight();
					layoutView.setLayoutParams(lp);
				}
				TextView popmenu_item_txt =  helper.getView(R.id.popmenu_item_txt);
				popmenu_item_txt.setText(item);
				
				
			}
		};
		listview.setAdapter(baseAdapter);
		popw.setContentView(listview);
		popw.setWidth(anchor.getWidth());
		int lenght = items.size();
		if (lenght > 4) {
			lenght = 4;
		}
		popw.setHeight(anchor.getHeight()*lenght);
		popw.setBackgroundDrawable(new BitmapDrawable());
		popw.setOutsideTouchable(true);
		popw.setFocusable(true);
		popw.showAsDropDown( anchor ,2,2);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				 if (mlistener != null) {
					 mlistener.onItemClick(arg0, arg1, arg2, arg3);
				}
				 popw.dismiss();
				
			}
		});
		// 添加长按点击,得到点中的index，即参数arg2
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						if(longClickListener!=null){
							longClickListener.onItemLongClick(arg0, arg1, arg2, arg3);
						}
						 popw.dismiss();
						return true;
					}
				});
	}
	
	
	public interface IColorPicker {
		public void onPickCallBack(int a, int r, int g, int b);
	}
	/**
	 * 拾色器
	 * @param context
	 * @param def  默认颜色值
	 */
	private static Dialog dialog_ColorPicker_instance = null;
	public static void ShowColorPickerDialog(Activity context ,int defv ,final IColorPicker cp ) {
		if (dialog_ColorPicker_instance != null ) {
			dialog_ColorPicker_instance.dismiss();
			dialog_ColorPicker_instance = null;
		}
		
		LayoutInflater lf=LayoutInflater.from(context);
		View view= lf.inflate(R.layout.md_color_picker__dialog, null);
		final ImageView colorScreen = (ImageView)view.findViewById(R.id.dialog_color_show);
		
		final SeekBar sb_a = (SeekBar)view.findViewById(R.id.colorpicker_sb_alpha);
		final TextView colorValueA = (TextView)view.findViewById(R.id.txt_value_a);
		
		final SeekBar sb_r = (SeekBar)view.findViewById(R.id.colorpicker_sb_r);
		final TextView colorValueR = (TextView)view.findViewById(R.id.txt_value_r);
		
		final SeekBar sb_g = (SeekBar)view.findViewById(R.id.colorpicker_sb_g);
		final TextView colorValueG = (TextView)view.findViewById(R.id.txt_value_g);
		
		final SeekBar sb_b = (SeekBar)view.findViewById(R.id.colorpicker_sb_b);
		final TextView colorValueB = (TextView)view.findViewById(R.id.txt_value_b);
		
		OnSeekBarChangeListener mlistener = new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				switch (arg0.getId()) {
				case R.id.colorpicker_sb_alpha:
					colorScreen.setBackgroundColor(Color.argb(sb_a.getProgress(), sb_r.getProgress(), sb_g.getProgress(), sb_b.getProgress()));
					colorValueA.setText(String.valueOf(arg1));
					break;
				case R.id.colorpicker_sb_r:
					colorScreen.setBackgroundColor(Color.argb(sb_a.getProgress(), sb_r.getProgress(), sb_g.getProgress(), sb_b.getProgress()));
					colorValueR.setText(String.valueOf(arg1));			
					break;
				case R.id.colorpicker_sb_g:
					colorScreen.setBackgroundColor(Color.argb(sb_a.getProgress(), sb_r.getProgress(), sb_g.getProgress(), sb_b.getProgress()));
					colorValueG.setText(String.valueOf(arg1));
					break;
				case R.id.colorpicker_sb_b:
					colorScreen.setBackgroundColor(Color.argb(sb_a.getProgress(), sb_r.getProgress(), sb_g.getProgress(), sb_b.getProgress()));
					colorValueB.setText(String.valueOf(arg1));
					break;
				 
				default:
					break;
				}
				
			}
		};
		sb_a.setOnSeekBarChangeListener(mlistener);
		sb_r.setOnSeekBarChangeListener(mlistener);
		sb_g.setOnSeekBarChangeListener(mlistener);
		sb_b.setOnSeekBarChangeListener(mlistener);
		sb_a.setProgress(Color.alpha(defv));
		sb_r.setProgress(Color.red(defv));
		sb_g.setProgress(Color.green(defv));
		sb_b.setProgress(Color.blue(defv));
		
		final Dialog dig=new Dialog(context,R.style.showNewMessageDialog );
    	dig.setContentView(view);
 		Window dialogWindow = dig.getWindow();
 		
         WindowManager.LayoutParams lp = dialogWindow.getAttributes();
         dialogWindow.setGravity(Gravity.CENTER | Gravity.CENTER);
         dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
         DisplayMetrics mDisplayMetrics = new DisplayMetrics();
         context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
         lp.height = (int) ( mDisplayMetrics.heightPixels* 0.45); // �߶�����Ϊ��Ļ��0.6
         lp.width = (int) (mDisplayMetrics.widthPixels * 0.6); // �������Ϊ��Ļ��0.9
         lp.dimAmount=0.6f; 
         dialogWindow.setAttributes(lp);
         dig.show();
         dig.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface arg0) {
				if (cp != null) {
					cp.onPickCallBack(sb_a.getProgress(),sb_r.getProgress(),sb_g.getProgress(), sb_b.getProgress());
				}
			}
		});
		dig.setCanceledOnTouchOutside(true);
		dialog_ColorPicker_instance = dig;
         
	}
	//***********************************start
	 
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    
    //***********************************end
    
    //共用的一个loading 
    private static  ProgressDialog m_Loading;
    public static void ShowLoading(Context context) {
		if (m_Loading != null && m_Loading.isShowing()) {
			m_Loading.dismiss();
		}
		m_Loading = ProgressDialog.show(context, null, "Loading...");
	}
    public static void HideLoading() {
    	if (m_Loading != null && m_Loading.isShowing()) {
			m_Loading.dismiss();
		}
	}
}
