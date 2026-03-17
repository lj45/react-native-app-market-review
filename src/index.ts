import { NativeModules, Platform } from 'react-native';

export interface MarketReviewConfig {
  // 华为应用市场：AppId（核心配置）
  huaweiAppId?: string;
}


export function initConfig(config: MarketReviewConfig){
  if (Platform.OS === 'android') {
    return NativeModules?.AppMarketReview.initConfig(config);
  }
}

export function startToDetail(){
  if (Platform.OS === 'android'){
    return  NativeModules?.AppMarketReview.startToDetail();
  }
}

export function startToComment(){
  if (Platform.OS === 'android'){
    if (!NativeModules.AppMarketReview) {
      console.error('原生模块 AppMarketReview 未找到！');
      return;
    }
    return NativeModules?.AppMarketReview.startToComment();
  }
}
