//package com.xiongyayun.athena.system.modules.monitor.controller;
//
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.NumberUtil;
//import cn.hutool.system.*;
//import com.xiongyayun.athena.core.constant.SymbolConstant;
//import com.xiongyayun.athena.system.modules.monitor.model.Monitor;
//import com.xiongyayun.athena.system.modules.monitor.model.SysJavaInfo;
//import com.xiongyayun.athena.system.modules.monitor.model.SysJvmMemInfo;
//import com.xiongyayun.athena.system.modules.monitor.model.SysOsInfo;
//import io.swagger.annotations.Api;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//
///**
// * SysMonitorController
// *
// * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
// * @date 2021/4/23
// */
//@RestController
//@RequestMapping("/sys/monitor")
//@Api(value = "sysMonitor", tags = {"系统用户管理模块"})
//public class SysMonitorController {
//
//	@RequestMapping("/get")
//	public Monitor get() {
//		Monitor monitor = new Monitor();
//
//		JvmInfo jvmInfo = SystemUtil.getJvmInfo();
//		JavaRuntimeInfo javaRuntimeInfo = SystemUtil.getJavaRuntimeInfo();
//		OsInfo osInfo = SystemUtil.getOsInfo();
//		HostInfo hostInfo = SystemUtil.getHostInfo();
//		RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
//
//		//系统信息
//		SysOsInfo sysOsInfo = new SysOsInfo();
//		sysOsInfo.setOsName(osInfo.getName());
//		sysOsInfo.setOsArch(osInfo.getArch());
//		sysOsInfo.setOsVersion(osInfo.getVersion());
//		sysOsInfo.setOsHostName(hostInfo.getName());
//		sysOsInfo.setOsHostAddress(hostInfo.getAddress());
//		monitor.setSysOsInfo(sysOsInfo);
//
//		//Java信息
//		SysJavaInfo sysJavaInfo = new SysJavaInfo();
//		sysJavaInfo.setJvmName(jvmInfo.getName());
//		sysJavaInfo.setJvmVersion(jvmInfo.getVersion());
//		sysJavaInfo.setJvmVendor(jvmInfo.getVendor());
//		sysJavaInfo.setJavaName(javaRuntimeInfo.getName());
//		sysJavaInfo.setJavaVersion(javaRuntimeInfo.getVersion());
//		monitor.setSysJavaInfo(sysJavaInfo);
//
//		//jvm内存信息
//		SysJvmMemInfo sysJvmMemInfo = new SysJvmMemInfo();
//		sysJvmMemInfo.setJvmMaxMemory(FileUtil.readableFileSize(runtimeInfo.getMaxMemory()));
//		sysJvmMemInfo.setJvmUsableMemory(FileUtil.readableFileSize(runtimeInfo.getUsableMemory()));
//		sysJvmMemInfo.setJvmTotalMemory(FileUtil.readableFileSize(runtimeInfo.getTotalMemory()));
//		sysJvmMemInfo.setJvmFreeMemory(FileUtil.readableFileSize(runtimeInfo.getFreeMemory()));
//		BigDecimal usedMemory = NumberUtil.sub(new BigDecimal(runtimeInfo.getTotalMemory()), new BigDecimal(runtimeInfo.getFreeMemory()));
//		sysJvmMemInfo.setJvmUsedMemory(FileUtil.readableFileSize(usedMemory.longValue()));
//		BigDecimal rate = NumberUtil.div(usedMemory, runtimeInfo.getTotalMemory());
//		String usedRate = new DecimalFormat("#.00").format(NumberUtil.mul(rate, 100)) + SymbolConstant.PERCENT;
//		sysJvmMemInfo.setJvmMemoryUsedRate(usedRate);
//		monitor.setSysJvmMemInfo(sysJvmMemInfo);
//		return monitor;
//	}
//}
