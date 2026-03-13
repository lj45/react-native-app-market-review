package com.appmarketreview;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;

public class MarketTools {

  private static MarketTools tools;
  private static final String schemaUrl = "market://details?id=";

  public static MarketTools getTools() {
    if (null == tools) {
      tools = new MarketTools();
    }
    return tools;
  }

  /***
   * 不指定包名
   * @param config
   */
  public void startToDetail(Activity activity, ReadableMap config) {
    try {
      String deviceBrand = getDeviceBrand();//获得手机厂商
      //根据厂商获取对应市场的包名
      String brandName = deviceBrand.toUpperCase();//大写
      String marketPackageName = getBrandName(brandName);
      Log.d("MarketTest","marketPackageName:" + marketPackageName);
      Log.d("MarketTest","packageName:" + activity.getPackageName());
      if(PACKAGE_NAME.OPPO_PACKAGE_NAME.equals(marketPackageName)){
        OppoMarketManager.jumpToDetail(activity,marketPackageName);
        return;
      }
      if(PACKAGE_NAME.VIVO_PACKAGE_NAME.equals(marketPackageName)){
        VivoMarketManager.jumpToDetail(activity,marketPackageName);
        return;
      }
      String huaweiAppId = "";
      if(config != null && config.hasKey("huaweiAppId")) {
        huaweiAppId = config.getString("huaweiAppId");
      }
      if (PACKAGE_NAME.HUAWEI_PACKAGE_NAME.equals(marketPackageName)
              && huaweiAppId != null && !huaweiAppId.isEmpty()) {
        HuaWeiMarketManager.jumpToDetail(activity, marketPackageName, huaweiAppId);
        return;
      }
      if(PACKAGE_NAME.HONOR_PACKAGE_NAME.equals(marketPackageName)){
        if (isInstalled("com.hihonor.appmarket",activity)) {
          HonorMarketManager.jumpToDetail(activity,marketPackageName);
          return;
        } else if(isInstalled("com.huawei.appmarket",activity)
                && huaweiAppId != null && !huaweiAppId.isEmpty()){
          HuaWeiMarketManager.jumpToDetail(activity,marketPackageName,huaweiAppId);
          return;
        }
      }
      if(PACKAGE_NAME.XIAOMI_PACKAGE_NAME.equals(marketPackageName)){
        XiaoMiMarketManager.jumpToDetail(activity);
        return;
      }
      String packageName = activity.getPackageName();
      startMarket(activity, packageName, marketPackageName);
    } catch (ActivityNotFoundException anf) {
      Log.e("MarketTools", "要跳转的应用市场不存在!");
    } catch (Exception e) {
      Log.e("MarketTools", "其他错误：" + e.getMessage());
    }
  }

  /**
   * 指定包名
   *
   */
  public boolean startComment(Activity activity, ReadableMap config) {
    try {
      String packageName = activity.getPackageName();
      String deviceBrand = getDeviceBrand();//获得手机厂商
      //根据厂商获取对应市场的包名
      String brandName = deviceBrand.toUpperCase();//大写
      String marketPackageName = getBrandName(brandName);
      Log.d("MarketTest","marketPackageName:" + marketPackageName);
      Log.d("MarketTest","packageName:" + packageName);
      if(PACKAGE_NAME.OPPO_PACKAGE_NAME.equals(marketPackageName)){
        OppoMarketManager.jumpToComment(activity);
        return true;
      }
      if(PACKAGE_NAME.VIVO_PACKAGE_NAME.equals(marketPackageName)){
        VivoMarketManager.jumpToComment(activity);
        return true;
      }
      String huaweiAppId = "";
      if(config != null && config.hasKey("huaweiAppId")) {
        huaweiAppId = config.getString("huaweiAppId");
      }
      if(PACKAGE_NAME.HUAWEI_PACKAGE_NAME.equals(marketPackageName)
              && huaweiAppId != null && !huaweiAppId.isEmpty()){
        HuaWeiMarketManager.jumpToDetail(activity,marketPackageName,huaweiAppId);
        return true;
      }
      if(PACKAGE_NAME.HONOR_PACKAGE_NAME.equals(marketPackageName)){
        if (isInstalled("com.hihonor.appmarket",activity)) {
          HonorMarketManager.jumpToComment(activity,marketPackageName);
          return true;
        } else if(isInstalled("com.huawei.appmarket",activity)
                && huaweiAppId != null && !huaweiAppId.isEmpty()){
          HuaWeiMarketManager.jumpToDetail(activity,marketPackageName,huaweiAppId);
          return true;
        }
      }
      if(PACKAGE_NAME.XIAOMI_PACKAGE_NAME.equals(marketPackageName)){
        XiaoMiMarketManager.jumpToComment(activity);
        return true;
      }
      if (null == marketPackageName || "".equals(marketPackageName)) {
        //手机不再列表里面,去尝试寻找
        //检测百度和应用宝是否在手机上安装,如果安装，则跳转到这两个市场的其中一个
        boolean isExit1 = isCheckBaiduOrYYB(activity, PACKAGE_NAME.BAIDU_PACKAGE_NAME);
        if (isExit1) {
          startMarket(activity, packageName, PACKAGE_NAME.BAIDU_PACKAGE_NAME);
          return true;
        }
        boolean isExit2 = isCheckBaiduOrYYB(activity, PACKAGE_NAME.TENCENT_PACKAGE_NAME);
        if (isExit2) {
          startMarket(activity, packageName, PACKAGE_NAME.TENCENT_PACKAGE_NAME);
          return true;
        }
      }
      startMarket(activity, packageName, marketPackageName);
      return true;
    } catch (ActivityNotFoundException anf) {
      Log.e("MarketTools", "要跳转的应用市场不存在!");
    } catch (Exception e) {
      Log.e("MarketTools", "其他错误：" + e.getMessage());
    }
    return false;
  }

  /***
   * 指定包名，指定市场
   * @param mContext
   * @param packageName
   * @param marketPackageName
   */
  public void startMarket(Context mContext, String packageName, String marketPackageName) {
    try {
      openMarket(mContext, packageName, marketPackageName);
    } catch (ActivityNotFoundException anf) {
      Log.e("MarketTools", "要跳转的应用市场不存在!");
    } catch (Exception e) {
      Log.e("MarketTools", "其他错误：" + e.getMessage());
    }
  }

  /***
   * 打开应用市场
   * @param mContext
   * @param packageName
   * @param marketPackageName
   */
  private void openMarket(Context mContext, String packageName, String marketPackageName) {
    try {
      Uri uri = Uri.parse(schemaUrl + packageName);
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      if(marketPackageName != null && marketPackageName.length() > 0){
        intent.setPackage(marketPackageName);
      }
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      mContext.startActivity(intent);
    } catch (ActivityNotFoundException anf) {
      Log.e("MarketTools", "要跳转的应用市场不存在!");
    } catch (Exception e) {
      Log.e("MarketTools", "其他错误：" + e.getMessage());
    }
  }

  /***
   * 检测是否是应用宝或者是百度市场
   * @param mContext
   * @param packageName
   * @return
   */
  private boolean isCheckBaiduOrYYB(Context mContext, String packageName) {
    boolean installed = isInstalled(packageName, mContext);
    return installed;
  }

  /****
   * 检查APP是否安装成功
   * @param packageName
   * @param context
   * @return
   */
  private boolean isInstalled(String packageName, Context context) {
    if (packageName == null || "".equals(packageName) || packageName.length() == 0) {
      return false;
    }
    PackageInfo packageInfo;
    try {
      packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
    } catch (PackageManager.NameNotFoundException e) {
      packageInfo = null;
    }
    if (packageInfo == null) {
      return false;
    } else {
      return true;
    }
  }

  private String getBrandName(String brandName) {
    if (BRAND.HUAWEI_BRAND.equals(brandName)) {
      //华为
      return PACKAGE_NAME.HUAWEI_PACKAGE_NAME;
    } else if (BRAND.OPPO_BRAND.equals(brandName)) {
      //oppo
      return PACKAGE_NAME.OPPO_PACKAGE_NAME;
    } else if (BRAND.VIVO_BRAND.equals(brandName)) {
      //vivo
      return PACKAGE_NAME.VIVO_PACKAGE_NAME;
    } else if (BRAND.XIAOMI_BRAND.equals(brandName)) {
      //小米
      return PACKAGE_NAME.XIAOMI_PACKAGE_NAME;
    } else if (BRAND.LENOVO_BRAND.equals(brandName)) {
      //联想
      return PACKAGE_NAME.LIANXIANG_PACKAGE_NAME;
    } else if (BRAND.QH360_BRAND.equals(brandName)) {
      //360
      return PACKAGE_NAME.QH360_PACKAGE_NAME;
    } else if (BRAND.MEIZU_BRAND.equals(brandName)) {
      //魅族
      return PACKAGE_NAME.MEIZU_PACKAGE_NAME;
    } else if (BRAND.HONOR_BRAND.equals(brandName)) {
      //荣耀
      return PACKAGE_NAME.HONOR_PACKAGE_NAME;
    } else if (BRAND.XIAOLAJIAO_BRAND.equals(brandName)) {
      //小辣椒
      return PACKAGE_NAME.ZHUOYI_PACKAGE_NAME;
    } else if (BRAND.ZTE_BRAND.equals(brandName)) {
      //zte
      return PACKAGE_NAME.ZTE_PACKAGE_NAME;
    } else if (BRAND.NIUBIA_BRAND.equals(brandName)) {
      //努比亚
      return PACKAGE_NAME.NIUBIA_PACKAGE_NAME;
    } else if (BRAND.ONE_PLUS_BRAND.equals(brandName)) {
      //OnePlus
      return PACKAGE_NAME.OPPO_PACKAGE_NAME;
    } else if (BRAND.MEITU_BRAND.equals(brandName)) {
      //美图
      return PACKAGE_NAME.MEITU_PACKAGE_NAME;
    } else if (BRAND.SONY_BRAND.equals(brandName)) {
      //索尼
      return PACKAGE_NAME.GOOGLE_PACKAGE_NAME;
    } else if (BRAND.GOOGLE_BRAND.equals(brandName)) {
      //google
      return PACKAGE_NAME.GOOGLE_PACKAGE_NAME;
    }
    return "";
  }

  /**
   * 获取手机厂商
   */
  private String getDeviceBrand() {
    return android.os.Build.BRAND;
  }

  private boolean isHonorDevice() {
    return Build.MANUFACTURER.equalsIgnoreCase("HONOR");
  }

  public static class BRAND {
    public static final String HUAWEI_BRAND = "HUAWEI";//HUAWEI_PACKAGE_NAME
    public static final String HONOR_BRAND = "HONOR";//HUAWEI_PACKAGE_NAME
    public static final String OPPO_BRAND = "OPPO";//OPPO_PACKAGE_NAME
    public static final String MEIZU_BRAND = "MEIZU";//MEIZU_PACKAGE_NAME
    public static final String VIVO_BRAND = "VIVO";//VIVO_PACKAGE_NAME
    public static final String XIAOMI_BRAND = "XIAOMI";//XIAOMI_PACKAGE_NAME
    public static final String LENOVO_BRAND = "LENOVO";//LIANXIANG_PACKAGE_NAME //Lenovo
    public static final String ZTE_BRAND = "ZTE";//ZTE_PACKAGE_NAME
    public static final String XIAOLAJIAO_BRAND = "XIAOLAJIAO";//ZHUOYI_PACKAGE_NAME
    public static final String QH360_BRAND = "360";//QH360_PACKAGE_NAME
    public static final String NIUBIA_BRAND = "NUBIA";//NIUBIA_PACKAGE_NAME
    public static final String ONE_PLUS_BRAND = "ONEPLUS";//OPPO_PACKAGE_NAME
    public static final String MEITU_BRAND = "MEITU";//MEITU_PACKAGE_NAME
    public static final String SONY_BRAND = "SONY";//GOOGLE_PACKAGE_NAME
    public static final String GOOGLE_BRAND = "GOOGLE";//GOOGLE_PACKAGE_NAME

    public static final String HTC_BRAND = "HTC";//未知应用商店包名
    public static final String ZUK_BRAND = "ZUK";//未知应用商店包名

  }

  /** Redmi*/
  /**
   * 华为，oppo,vivo,小米，360，联想，魅族，安智，百度，阿里，应用宝，goog，豌豆荚，pp助手
   **/
  public static class PACKAGE_NAME {
    public static final String OPPO_PACKAGE_NAME = "com.oppo.market";//oppo
    public static final String VIVO_PACKAGE_NAME = "com.bbk.appstore";//vivo
    public static final String HUAWEI_PACKAGE_NAME = "com.huawei.appmarket";//华为
    public static final String HONOR_PACKAGE_NAME = "com.hihonor.appmarket";
    public static final String QH360_PACKAGE_NAME = "com.qihoo.appstore";//360
    public static final String XIAOMI_PACKAGE_NAME = "com.xiaomi.market";//小米
    public static final String MEIZU_PACKAGE_NAME = "com.meizu.mstore";//，魅族
    public static final String LIANXIANG_PACKAGE_NAME = "com.lenovo.leos.appstore";//联想
    public static final String ZTE_PACKAGE_NAME = "zte.com.market";//zte
    public static final String ZHUOYI_PACKAGE_NAME = "com.zhuoyi.market";//卓易
    public static final String GOOGLE_PACKAGE_NAME = "com.android.vending";//google
    public static final String NIUBIA_PACKAGE_NAME = "com.nubia.neostore";//努比亚
    public static final String MEITU_PACKAGE_NAME = "com.android.mobile.appstore";//美图
    public static final String BAIDU_PACKAGE_NAME = "com.baidu.appsearch";//baidu
    public static final String TENCENT_PACKAGE_NAME = "com.tencent.android.qqdownloader";//应用宝
    public static final String PPZHUSHOU_PACKAGE_NAME = "com.pp.assistant";//pp助手
    public static final String ANZHI_PACKAGE_NAME = "com.goapk.market";//安智市场
    public static final String WANDOUJIA_PACKAGE_NAME = "com.wandoujia.phonenix2";//豌豆荚
//        public static final String SUONI_PACKAGE_NAME = "com.android.vending";//索尼

  }

  public static long getVersionCode(Activity context, String packageName) {
    long versionCode = -1;
    try {
      PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
      if (info != null) {
        versionCode = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? info.getLongVersionCode() : info.versionCode;
      }
    } catch (PackageManager.NameNotFoundException e) {
    }
    return versionCode;
  }

}