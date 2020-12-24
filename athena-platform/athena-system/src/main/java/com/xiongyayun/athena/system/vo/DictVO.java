package com.xiongyayun.athena.system.vo;

import com.xiongyayun.athena.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DictVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictVO extends BaseVO {
	private String id;
	private String dictName;
	private String dictCode;
	private String description;
	private Boolean enabled;
	private Boolean allowUpdate;
	private Boolean allowDelete;
}
