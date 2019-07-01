package com.xiongyayun.athena.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.security.PermitAll;

/**
 * 中国地理信息
 *
 * @author: Yayun.Xiong
 * @date: 2019-06-27
 */
public class ChinaRegionController {
//	/**
//	 * 省
//	 */
//	@Autowired
//	private ProvinceService provinceService;
//
//	/**
//	 * 市
//	 */
//	@Autowired
//	private CityService cityService;
//
//	/**
//	 * 区
//	 */
//	@Autowired
//	private CountryService countryService;
//
//	@PermitAll
//	@GetMapping("/provinces")
//	public ResultMsg<List<CascaderOptionDto>> getProvinces(){
//		return ResultMsg.success(provinceService.getCascaderOptionDtos());
//	}
//
//	@PermitAll
//	@GetMapping("/provinces/{provinceId}/cities")
//	public ResultMsg<List<CascaderOptionDto>> getCities(@PathVariable String provinceId){
//		return ResultMsg.success(cityService.getCascaderOptionDtos(provinceId));
//	}
//
//	@PermitAll
//	@GetMapping("/provinces/{provinceId}/cities/{cityId}/countries")
//	public List<CascaderOptionDto> getCountries(@PathVariable String provinceId, @PathVariable String cityId){
//		return countryService.getCascaderOptionDtos(cityId);
//	}
}
