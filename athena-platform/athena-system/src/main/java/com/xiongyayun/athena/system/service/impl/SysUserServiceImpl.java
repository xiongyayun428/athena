package com.xiongyayun.athena.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiongyayun.athena.system.mapper.SysUserMapper;
import com.xiongyayun.athena.system.model.SysUser;
import com.xiongyayun.athena.system.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author Yayun.Xiong
 * @date 2019-05-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
