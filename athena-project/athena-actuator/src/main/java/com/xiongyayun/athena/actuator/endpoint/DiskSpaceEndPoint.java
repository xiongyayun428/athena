package com.xiongyayun.athena.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义一个端点 id = diskspace 获取磁盘容量信息
 * @EndPoint中的id不能使用驼峰法，需要以-分割
 *
 * @author: 熊亚运
 * @date: 2019-06-17
 */
@Configuration
@Endpoint(id = "diskspace")
public class DiskSpaceEndPoint {
    /**
     * GET
     * 获取自定义端点需要监测的数据 -- 磁盘容量信息
     *
     * @return Map<String, String>
     */
    @ReadOperation
    public Map<String, Object> diskSpaceInfo() {
        Map<String, Object> result = new HashMap<>(3);
        // 获取磁盘容量信息
        File[] rootFiles = File.listRoots();
        if (rootFiles != null && rootFiles.length != 0) {
            long total = 0;
            long free = 0;
            for (File file : rootFiles) {
                // 总量
                total += file.getTotalSpace();
                // 未用
                free += file.getUsableSpace();
            }
            // 已用
            long user = total - free;
            // 利用率
            double userRate = total == 0 ? 0 : ((double) user / total);
            result.put("total", String.valueOf(total));
            result.put("free", String.valueOf(free));
            result.put("usage", String.valueOf(userRate * 100));
        }
        return result;
    }
}
