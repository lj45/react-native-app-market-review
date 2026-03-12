package com.appmarketreview

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
// https://developer.honor.com/cn/doc/guides/101567

import com.appmarketreview.NativeRNShareSpec;
public class RNMarketReview extends  NativeRNShareSpec{

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
