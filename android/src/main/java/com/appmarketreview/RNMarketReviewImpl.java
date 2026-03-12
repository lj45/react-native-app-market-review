package com.appmarketreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ReactApplicationContext;

// https://developer.honor.com/cn/doc/guides/101567
public class RNMarketReviewImpl {

  public static final String NAME = "AppMarketReview";

  public RNMarketReviewImpl(ReactApplicationContext reactContext){

  }

  public void startToDetail(Activity activity){
    MarketTools.getTools().startToDetail(activity,activity);
  }

  public void startToComment(Activity activity){
    MarketTools.getTools().startMarket(activity,activity);
  }

}
