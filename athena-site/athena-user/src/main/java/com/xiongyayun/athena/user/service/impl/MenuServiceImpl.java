package com.xiongyayun.athena.user.service.impl;

import com.xiongyayun.athena.user.mapper.MenuMapper;
import com.xiongyayun.athena.user.model.Menu;
import com.xiongyayun.athena.user.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MenuServiceImpl
 *
 * @author: 熊亚运
 * @date: 2019-05-30
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }
}
