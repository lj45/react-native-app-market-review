package com.appmarketreview;

import android.app.Activity;
// https://developer.honor.com/cn/doc/guides/101567

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNMarketReview extends ReactContextBaseJavaModule {

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

  @ReactMethod
  public void startToDetail(Promise promise) {
    Activity activity = mReactContext.getCurrentActivity();
    if (activity == null) {
      promise.resolve(false);
      return;
    }
    delegate.startToDetail(activity);
  }

  @ReactMethod
  public void startToComment(Promise promise) {
    Activity activity = mReactContext.getCurrentActivity();
    if (activity == null) {
      promise.resolve(false);
      return;
    }
    delegate.startToComment(activity);
  }



}
