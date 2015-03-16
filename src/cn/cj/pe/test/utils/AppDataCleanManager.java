package cn.cj.pe.test.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * ����ͻ��������࣬�൱�ڴ�����������ͻ�������
 * @author yetong
 *
 */
public class AppDataCleanManager {
    /** 
     * �����Ӧ���ڲ�����(/data/data/com.xxx.xxx/cache) 
     *  @param context 
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * �����Ӧ���������ݿ�(/data/data/com.xxx.xxx/databases)
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * �����Ӧ��SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /** 
     * ���/data/data/com.xxx.xxx/files�µ����� 
     * @param context 
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * ����ⲿcache�µ�����(/mnt/sdcard/android/data/com.xxx.xxx/cache) 
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * �����Ӧ�����е����� 
     * @param context 
     * @param filepath 
     */
    public static void cleanApplicationData(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
    }

    /** 
     * ɾ������ ����ֻ��ɾ��ĳ���ļ����µ��ļ�����������directory�Ǹ��ļ������������� 
     * @param directory 
     */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
