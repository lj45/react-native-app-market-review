import { NativeModules } from 'react-native';

const {AppMarketReview} = NativeModules ;

export interface MarketReviewConfig {
  // 华为应用市场：AppId（核心配置）
  huaweiAppId?: string;
}


export function initConfig(config: MarketReviewConfig){
  return AppMarketReview.initConfig(config);
}

export function startToDetail(){
  return AppMarketReview.startToDetail();
}

export function startToComment(){
  if (!NativeModules.AppMarketReview) {
    console.error('原生模块 AppMarketReview 未找到！');
    return;
  }
  return AppMarketReview.startToComment();
}
