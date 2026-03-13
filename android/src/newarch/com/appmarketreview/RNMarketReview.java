package com.appmarketreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
// https://developer.honor.com/cn/doc/guides/101567

public class RNMarketReview extends  NativeAppMarketReviewSpec{

  private ReactApplicationContext mReactContext;
  private final RNMarketReviewImpl delegate;

  public RNMarketReview(ReactApplicationContext reactContext) {
    super(reactContext);
    delegate = new RNMarketReviewImpl(reactContext);
    this.mReactContext = reactContext;
  }

  @NonNull
  @Override
  public String getName() {
    return RNMarketReviewImpl.NAME;
  }



  @Override
  public void startToDetail(ReadableMap options, Promise promise) {
    Activity activity = mReactContext.getCurrentActivity();
    if (activity == null) {
      promise.resolve(false);
      return
    }
    delegate.startToDetail(promise);
  }

  @Override
  public void startToComment(ReadableMap options, Promise promise) {
    Activity activity = mReactContext.getCurrentActivity();
    if (activity == null) {
      promise.resolve(false);
      return
    }
    delegate.startToComment(promise);
  }



}
