package com.appmarketreview;

import android.app.Activity;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

// https://developer.honor.com/cn/doc/guides/101567
public class RNMarketReviewImpl {

  public static final String NAME = "AppMarketReview";

  public RNMarketReviewImpl(ReactApplicationContext reactContext){

  }

  public void startToDetail(Activity activity, ReadableMap config){
    MarketTools.getTools().startToDetail(activity,config);
  }

  public void startToComment(Activity activity, ReadableMap config){
    MarketTools.getTools().startComment(activity,config);
  }

}
