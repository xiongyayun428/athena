package com.xyy.athena.user.controller;

import com.xyy.athena.core.annotation.Logger;
import com.xyy.athena.core.exception.AthenaException;
import com.xyy.athena.core.exception.AthenaRuntimeException;
import com.xyy.athena.user.model.Menu;
import com.xyy.athena.user.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * MenuController
 *
 * @author: 熊亚运
 * @date: 2019-05-30
 */
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Logger("查询所有菜单信息")
    @GetMapping("select")
    public List<Menu> selectAll() {
        throw new AthenaRuntimeException(new NullPointerException());
//        return menuService.selectAll();
    }

    @Logger("新增菜单")
    @PostMapping("add")
    public void add(@Valid @RequestBody Menu menu) throws AthenaException {
        log.debug("add", menu);
        throw new AthenaException(new NullPointerException());
    }
}
