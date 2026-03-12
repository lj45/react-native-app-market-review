package com.appmarketreview;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.TurboReactPackage;
import java.util.HashMap;
import java.util.Map;
public class RNMarketReviewPackage extends TurboReactPackage {

  @Nullable
  @Override
  public NativeModule getModule(String name, ReactApplicationContext reactContext) {
    if (name.equals(RNMarketReviewImpl.NAME)) {
      return new RNMarketReview(reactContext);
    } else {
      return null;
    }
  }

  @Override
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return () -> {
      final Map<String, ReactModuleInfo> moduleInfos = new HashMap<>();
      moduleInfos.put(
        RNMarketReviewImpl.NAME,
        new ReactModuleInfo(
          RNMarketReviewImpl.NAME,
          RNMarketReviewImpl.NAME,
          false, // canOverrideExistingModule
          false, // needsEagerInit
          true, // hasConstants
          false, // isCxxModule
          true // isTurboModule
        ));
      return moduleInfos;
    };
  }

}
