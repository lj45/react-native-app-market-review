import { NativeModules } from 'react-native';

const {AppMarketReview} = NativeModules ;

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
