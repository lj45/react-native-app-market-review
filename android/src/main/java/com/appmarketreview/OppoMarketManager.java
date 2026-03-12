package com.appmarketreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class OppoMarketManager {

  private final static String PKG_MK_HEYTAP = "com.heytap.market";//Q之后的软件商店包名
  private final static String PKG_MK_OPPO = "com.oppo.market";//Q之前的软件商店包名
  private final static String COMMENT_DEEPLINK_PREFIX = "oaps://mk/developer/comment?pkg=";
  private final static int SUPPORT_MK_VERSION = 84000; // 支持评论功能的软件商店版本

  public static boolean jumpToComment(Activity context) {
    String url = COMMENT_DEEPLINK_PREFIX + context.getPackageName();
    // 优先判断heytap包
    if (MarketTools.getVersionCode(context, PKG_MK_HEYTAP) >= SUPPORT_MK_VERSION) {
      return jumpApp(context, Uri.parse(url), PKG_MK_HEYTAP);
    }
    if (MarketTools.getVersionCode(context, PKG_MK_OPPO) >= SUPPORT_MK_VERSION) {
      return jumpApp(context, Uri.parse(url), PKG_MK_OPPO);
    }
    return false;
  }



  private static boolean jumpApp(Activity context, Uri uri, String targetPkgName) {
    try {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_VIEW);
      intent.addCategory(Intent.CATEGORY_DEFAULT);
      intent.setPackage(targetPkgName);
      intent.setData(uri);
      // 建议采用startActivityForResult 方法启动商店页面，requestCode由调用方自定义且必须大于0，软件商店不关注
      context.startActivityForResult(intent, 100);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  // https://open.oppomobile.com/documentation/page/info?id=11969
  // 自更新支持应用直接拉起 OPPO 软件商店详情页，完成自动/手动更新
  // market://details？id=应用包名&v_code = xxxx
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
