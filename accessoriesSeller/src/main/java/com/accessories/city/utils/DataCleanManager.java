package com.accessories.city.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * ��Ӧ���������������
 *
 * @Description ��Ҫ�����������/�⻺�棬������ݿ⣬����Զ���Ŀ¼
 * @author ccs7727@163.com
 * @date 2015��10��17�� ����4:55:38
 *
 */
public class DataCleanManager {

	/**
	 * ���������ô˷�������
	 *
	 * @param context
	 * @param filepath
	 */
	public static void cleanApplicationData(List<String> filepath) {
		if (filepath == null) {
			return;
		}
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * ����Զ���Ŀ¼��ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ��
	 *
	 * @param filePath
	 */
	public static void cleanCustomCache(String filePath) {
		// deleteFilesByDirectory(new File(filePath));
		deleteFolderFile(filePath, true);
	}

	/**
	 * ɾ���ļ��������Ŀ¼�����Ŀ¼����һɾ��
	 *
	 * @param directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				if (item.isDirectory()) {
					for (File item2 : item.listFiles()) {
						item2.delete();
					}
				} else {
					item.delete();
				}
			}
		}
	}

	/**
	 * ��ȡ�ļ���С
	 *
	 * @param file
	 * @return long����
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			if (null != file && file.exists()) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					// ������滹���ļ�
					if (fileList[i].isDirectory()) {
						size = size + getFolderSize(fileList[i]);
					} else {
						size = size + fileList[i].length();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * ɾ��ָ��Ŀ¼���ļ���Ŀ¼
	 *
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// ������滹���ļ�
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// ������ļ���ɾ��
						file.delete();
					} else {// Ŀ¼
						if (file.listFiles().length == 0) {// Ŀ¼��û���ļ�����Ŀ¼��ɾ��
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ʽ����λ
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	public static String getCacheSize(File file) throws Exception {
		return getFormatSize(getFolderSize(file));
	}

	/**
	 * * �����������Ӧ�����ݿ� * *
	 * 
	 * @param context
	 * @param dbName
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

}