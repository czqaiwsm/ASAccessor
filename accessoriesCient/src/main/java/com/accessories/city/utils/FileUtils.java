package com.accessories.city.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class FileUtils {

	private static class FileUtilsHolder {
		private static final FileUtils INSTANCE = new FileUtils();
	}

	private FileUtils() {
	}

	public static final FileUtils getInstance() {
		return FileUtilsHolder.INSTANCE;
	}

	/**
	 * 获取SD卡对应目录
	 * 
	 * @return
	 */
	public String getNormalSDCardPath() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)){
			return Environment.getExternalStorageDirectory().getPath();
		}
		return "";
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static File createDir(String path) {
		File file = new File(path);
		if (!file.exists()) { // 不存在
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) { // 父目录不存在
				createDir(parentFile.getAbsolutePath());
			}

			file.mkdir();
		}
		return file;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 */
	public static File createFile(String path) {
		File file = null;

		try {
			file = new File(path);
			if (!file.exists()) { // 不存在
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) { // 父目录不存在
					createDir(parentFile.getAbsolutePath());
				}
				file.createNewFile();
			}
		} catch (Exception e) {
			Log.i("test", "error = " + e.toString());
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * 删除单个文件
	 *
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 *
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public boolean DeleteFolder(String sPath) {

		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return false;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 *
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		boolean flag = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		return dirFile.delete();
	}

	public double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小,以“兆”为单位
				double size = (double) file.length() / 1024 / 1024;
				return size;
			}
		} else {
			System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}

	public File saveBitmapToFile(Bitmap bitmap) {
		File file = createFile(getNormalSDCardPath()
				 + "crop.jpg");// 将要保存图片的路径
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// add by dongbing
	public void showAllFiles(File dir, ArrayList<String> str) throws Exception {

		if (!dir.exists()) {
			return;
		}
		File[] fs = dir.listFiles();

		if (fs == null) {
			return;
		}
		for (int i = 0; i < fs.length; i++) {

			str.add(fs[i].getName());
		}
	}
}
