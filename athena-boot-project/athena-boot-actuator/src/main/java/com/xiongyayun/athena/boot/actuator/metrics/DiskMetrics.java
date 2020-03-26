package com.xiongyayun.athena.boot.actuator.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 磁盘指标
 *
 * @author: 熊亚运
 * @date: 2019-06-17
 */
@Component
public class DiskMetrics implements MeterBinder {

    private File rootFilePath;

    public DiskMetrics() {
        this.rootFilePath = new File(".");
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        // 磁盘已用容量
        Gauge.builder("disk.space.total", rootFilePath, File::getTotalSpace)
                .description("磁盘已用容量")
                .register(registry);
        // 磁盘剩余容量
        Gauge.builder("disk.space.free", rootFilePath, File::getFreeSpace)
                .description("磁盘剩余容量")
                .register(registry);
        // 磁盘使用率
        Gauge.builder("disk.space.usage", rootFilePath, c -> {
            long totalDiskSpace = rootFilePath.getTotalSpace();
            if (totalDiskSpace == 0) {
                return 0.0;
            }

            long usedDiskSpace = totalDiskSpace - rootFilePath.getFreeSpace();
            return (double) usedDiskSpace / totalDiskSpace * 100;
        })
                .description("磁盘使用率")
                .register(registry);
    }
}
