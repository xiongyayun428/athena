package com.xiongyayun.athena.core.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xiongyayun.athena.core.i18n.I18nService;
import lombok.Getter;
import lombok.Setter;

/**
 * ResponseBody
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
@JsonPropertyOrder({"rtnCode", "rtnMsg", "rtnData"})
public class ResBody implements ResponseEntity {
    private static final String SUCCESS_CODE = "000000";
    private static final String SUCCESS_MSG = "SUCCESS";

    /**
     * 返回码
     */
    @Setter
    @Getter
    private String rtnCode;

    /**
     * 返回信息
     */
    @Setter
    private String rtnMsg;

    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object rtnData;

    @JsonIgnore
    private I18nService i18nService;

    @JsonIgnore
    private Object[] args;

    public ResBody() {
        this.rtnCode = SUCCESS_CODE;
        this.rtnMsg = SUCCESS_MSG;
    }

    public ResBody(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public ResBody(String rtnCode, String rtnMsg) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
    }

    public ResBody(String rtnCode, Object[] args) {
        this.rtnCode = rtnCode;
        this.args = args;
    }

    public String getRtnMsg() {
        if (this.rtnMsg == null && i18nService != null) {
            this.rtnMsg = i18nService.get(rtnCode, args);
        }
        return this.rtnMsg;
    }

    public ResBody withI18nService(I18nService i18nService) {
        this.i18nService = i18nService;
        return this;
    }

    public ResBody withCode(String rtnCode) {
        this.rtnCode = rtnCode;
        return this;
    }

    public ResBody withMsg(String msg) {
        this.rtnMsg = msg;
        return this;
    }

    public ResBody withArgs(Object[] args) {
        this.args = args;
        return this;
    }

}
