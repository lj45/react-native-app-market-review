package com.appmarketreview;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
//https://dev.mi.com/xiaomihyperos/documentation/detail?pId=1999 小米的onelink
public class XiaoMiMarketManager {
  public static void jumpToComment(Context context) {
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse("market://comments?id=" + context.getPackageName()));
      intent.setPackage("com.xiaomi.market");
      context.startActivity(intent);
    } catch (Exception e) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(
        "https://app.mi.com/details?id=" + context.getPackageName()));
      context.startActivity(intent);
    }
  }

  public static void jumpToDetail(Context context){
    // 小米自更新可以到应用详情页面
    // https://dev.mi.com/xiaomihyperos/documentation/detail?pId=2006
    try {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
      intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
      intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
      intent.setPackage("com.xiaomi.market");
      context.startActivity(intent);
    } catch (Exception e) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(
        "https://app.mi.com/details?id=" + context.getPackageName()));
      context.startActivity(intent);
    }
  }

}
