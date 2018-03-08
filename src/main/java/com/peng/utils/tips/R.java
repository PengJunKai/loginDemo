package com.peng.utils.tips;

/**
 * Created by PengJK on 2018/3/2.
 */
public class R {

    /******************success********************/

    public static Tip success() {
        return new SuccessTip();
    }

    public static Tip success(Object body) {
        return new SuccessTip(body);
    }

    public static Tip Success(String message){ return new SuccessTip(message); }

    public static Tip success(Object body,String message) {
        return new SuccessTip(body,message);
    }

    /******************error********************/
    public static Tip error() {
        return new ErrorTip();
    }

    public static Tip error(String message) {
        return new ErrorTip(message);
    }

    public static Tip error(String errorCode,String message) {
        return new ErrorTip(errorCode,message);
    }
}
