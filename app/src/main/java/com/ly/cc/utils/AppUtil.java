package com.ly.cc.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;

import java.util.List;

/**
 * 跟App相关的辅助类
 */
public class AppUtil {

    /**
     * 转化version为长整型，便于比较
     *
     * @param version
     * @return
     */
    public static long convertVersionToLong(String version) {
        String[] split = version.split("\\.");
        long result = 0;

        if (split.length >= 3) {
            for (int i = 0; i < 3; i++) {
                switch (i) {
                    case 0:
                        result += Integer.parseInt(split[i]) * 100;
                        break;
                    case 1:
                        result += Integer.parseInt(split[i]) * 10;
                        break;
                    case 2:
                        result += Integer.parseInt(split[i]);
                        break;
                    default:
                        break;
                }
            }
        }

        return result;
    }

    /**
     * 获取API版本
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getAPIVersion() {
        int APIVersion;
        try {
            APIVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            APIVersion = 0;
        }
        return APIVersion;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<PackageInfo> getAllInstallApp(Context ctx) {
        final PackageManager packageManager = ctx.getPackageManager();
        return packageManager.getInstalledPackages(0); // 获取所有已安装程序的包信息
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本地版本
     *
     * @param context
     * @return
     */
    public static long getVersionNameLong(Context context) {
        return convertVersionToLong(getVersionName(context));
    }

    /**
     * 检查是否安装了指定应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        List<PackageInfo> pinfo = getAllInstallApp(context);
        int packageInfoSize = pinfo.size();
        for (int i = 0; i < packageInfoSize; i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * 判断系统是否大于等于4.0
     *
     * @return
     */
    public static boolean isHighThanOrEqual4point0() {
        if (AppUtil.getAPIVersion() >= 14) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 虚拟键是否显示
     *
     * @param context
     * @return
     */
    public static boolean isVirtualKeyShow(Activity context) {
        if (AppUtil.isHighThanOrEqual4point0() == true) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (resourceId > 0) {
                return resources.getBoolean(resourceId);
            }
        }
        return false;
    }

    private AppUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void runApp(Context ctx, String packageName) {
        PackageInfo pi;
        try {
            pi = ctx.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = ctx.getPackageManager();
            List<ResolveInfo> apps = pManager.queryIntentActivities(resolveIntent, 0);

            if (apps != null && apps.size() > 0) {
                ResolveInfo ri = apps.iterator().next();
                if (ri != null) {
                    packageName = ri.activityInfo.packageName;
                    String className = ri.activityInfo.name;

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName cn = new ComponentName(packageName, className);

                    intent.setComponent(cn);
                    ctx.startActivity(intent);
                }
            } else {
                T.showShort(ctx, "打不开邮件客户端");
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRunningFront(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            ComponentName topConponent = tasksInfo.get(0).topActivity;
            if (packageName.equals(topConponent.getPackageName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return info.packageName;
    }
}
