package com.xyy.athena.core.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xyy.athena.core.i18n.I18nService;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

/**
 * ResponseBody
 *
 * @author: 熊亚运
 * @date: 2019-05-21
 */
@Data
@JsonPropertyOrder({"rtnCode", "rtnMsg", "rtnData"})
public class ResBody implements ResponseEntity {
    @JsonIgnore
    protected Log log = LogFactory.getLog(this.getClass());
    private static final String SUCCESS_CODE = "000000";
    private static final String SUCCESS_MSG = "SUCCESS";


    /**
     * 返回码
     */
    private String rtnCode;

    /**
     * 返回信息
     */
    private String rtnMsg;

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

    public ResBody(I18nService i18nService, String rtnCode, Throwable e) {
        this.rtnCode = rtnCode;
        if (i18nService != null) {
            this.rtnMsg = i18nService.get(rtnCode);
        }
        if (e != null) {
            log.error(this.rtnMsg, e);
        }
    }

    public ResBody(String rtnCode, String rtnMsg) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
    }

    public ResBody(I18nService i18nService, String rtnCode, Object[] args, Throwable e) {
        this.rtnCode = rtnCode;
        if (i18nService != null) {
            this.rtnMsg = i18nService.get(rtnCode, args);
        }
        if (e != null) {
            log.error(this.rtnMsg, e);
        }
    }

    public ResBody(I18nService i18nService, String rtnCode, Object[] args, String defaultMsg) {
        this.rtnCode = rtnCode;
        if (i18nService != null) {
            this.rtnMsg = i18nService.get(rtnCode, args, defaultMsg);
        }
    }

    public ResBody withCode(String rtnCode) {
        this.rtnCode = rtnCode;
        return this;
    }

    public ResBody withCode(Object[] args) {
        this.args = args;
        return this;
    }

    public ResBody withMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
        return this;
    }

    public ResBody withMsg(Object rtnData) {
        this.rtnData = rtnData;
        return this;
    }

    public ResBody withStatus(HttpStatus status) {
//        this.rtnData = status;
        return this;
    }

}
