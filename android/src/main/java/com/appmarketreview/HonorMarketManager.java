package com.appmarketreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
// https://developer.honor.com/cn/doc/guides/101567
public class HonorMarketManager {

    public static long getVersionCode(Context context)  {
        try {
            return context.getPackageManager().getPackageInfo("com.hihonor.appmarket",0).getLongVersionCode();
        } catch (Exception e) {
            return 0;
        }
    }

    public static void jumpToComment(Context context, String marketPackageName) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if(getVersionCode(context) >= 160036301){
                intent.setData(Uri.parse("market://comment?id=" + context.getPackageName()));
            } else {
                intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
            }
            intent.setPackage(marketPackageName);
            context.startActivity(intent);
        } catch (Exception e) {
            jumpToDetail(context,marketPackageName);
        }
    }

    public static void jumpToDetail(Context context, String marketPackageName) {
        try {
            String id = context.getPackageName();
            Uri marketUri = Uri.parse("market://details?id=" + id);
            Intent intent = new Intent(Intent.ACTION_VIEW, marketUri);
            intent.setPackage(marketPackageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
