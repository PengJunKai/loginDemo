package com.peng.utils.tips;

import com.peng.utils.ExceptionKit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by PengJK on 2018/3/2.
 */
@ApiModel(value = "失败响应", description = "失败响应通用对象")
public class ErrorTip<T> extends Tip<T>  {

    @ApiModelProperty(value = "堆栈信息",readOnly = true)
    private String exMsg;

    private final Integer ERROR_CODE = 200;

    public ErrorTip() {
        super(null);
        super.setCode(ERROR_CODE);
        super.setMessage("操作失败");
    }

    public ErrorTip(String message) {
        super(null);
        super.setCode(ERROR_CODE);
        super.setMessage(message);
    }

    public ErrorTip(Integer code, String message, Exception e) {
        super(null);
        super.setCode(code);
        super.setMessage(message);
        setExMsg(ExceptionKit.toString(e));
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }
}
