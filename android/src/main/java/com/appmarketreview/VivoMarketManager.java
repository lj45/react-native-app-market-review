package com.appmarketreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class VivoMarketManager {

  private final static int SUPPORT_MK_VERSION = 5020;
  private final static String PKG_MK = "com.bbk.appstore";
  private final static String COMMENT_DEEPLINK_PREFIX = "market://details?id=";

  // https://dev.vivo.com.cn/documentCenter/doc/257
  public static boolean jumpToComment(Activity context) {
    try {
      if (MarketTools.getVersionCode(context, PKG_MK) >= SUPPORT_MK_VERSION) {
        String url = COMMENT_DEEPLINK_PREFIX + context.getPackageName() + "&th_name=need_comment";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage(PKG_MK);
        intent.setData(Uri.parse(url));
        // 建议采用startActivityForResult 方法启动商店页面，requestCode由调用方自定义且必须大于0，软件商店不关注
        context.startActivityForResult(intent, 100);
        return true;
      } else {
        Uri uri = Uri.parse(COMMENT_DEEPLINK_PREFIX + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(COMMENT_DEEPLINK_PREFIX);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }

  // https://dev.vivo.com.cn/documentCenter/doc/240
  // vivo有自更新能力，可以直接下载后默认安装
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
