package com.peng.utils.tips;

import io.swagger.annotations.ApiModel;

/**
 * Created by PengJK on 2018/3/2.
 */
@ApiModel(value = "成功响应",description = "成功响应通用对象")
public class SuccessTip<T> extends Tip{

    private final String SUCCESS_CODE = "200";

    public SuccessTip(){
        super(null);
        super.setCode(SUCCESS_CODE);
        super.setMessage("操作成功");
    }

    public SuccessTip(T body){
        super(body);
        super.setCode(SUCCESS_CODE);
        super.setMessage("操作成功");
    }

    public SuccessTip(String message) {
        super(null);
        super.setCode(SUCCESS_CODE);
        super.setMessage(message);
    }

    public SuccessTip(T body,String message) {
        super(body);
        super.setCode(SUCCESS_CODE);
        super.setMessage(message);
    }
}
