package com.peng.utils.tips;

import com.peng.utils.HttpHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by PengJK on 2018/3/2.
 */
@ApiModel(value = "响应",description = "响应通用对象")
public abstract class Tip<T> {

    @ApiModelProperty(value = "响应状态码",readOnly = true)
    private String code;
    @ApiModelProperty(value = "响应主体",readOnly = true)
    private T body;
    @ApiModelProperty(value = "响应信息",readOnly = true)
    private String message;
    @ApiModelProperty(value = "请求标识",readOnly = true)
    private String requestId = HttpHolder.getRequestId();

    public Tip(T body) {
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
