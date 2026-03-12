"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.startToComment = exports.startToDetail = void 0;
const react_native_1 = require("react-native");
const { AppMarketReview } = react_native_1.NativeModules;
function startToDetail() {
    return AppMarketReview.startToDetail();
}
exports.startToDetail = startToDetail;
function startToComment() {
    return AppMarketReview.startToComment();
}
exports.startToComment = startToComment;
