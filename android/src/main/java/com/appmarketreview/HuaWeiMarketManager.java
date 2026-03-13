package com.appmarketreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

//https://developer.huawei.com/consumer/cn/forum/topic/0201448086867860655
//https://developer.huawei.com/consumer/cn/doc/app/comment-management-0000002246992933
public class HuaWeiMarketManager {

  public static void jumpToDetail(Context context, String marketPackageName, String appId) {
    try {
      // 可以到应用的详情页面
      Intent intent = new Intent("com.huawei.appmarket.appmarket.intent.action.AppDetail.withid");
      intent.setPackage("com.huawei.appmarket");
      intent.putExtra("appId", appId);
      context.startActivity(intent);
    } catch (Exception e) {
      jumpToHuaweiMarketDetailPage(context,appId,marketPackageName);
    }
  }

  private static void jumpToHuaweiMarketDetailPage(Context context, String appId, String marketPackageName) {
    try {
      Uri marketUri = Uri.parse("market://details?id=" + appId);
      Intent intent = new Intent(Intent.ACTION_VIEW, marketUri);
      intent.setPackage(marketPackageName);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}