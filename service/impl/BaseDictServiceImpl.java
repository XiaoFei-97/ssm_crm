package com.jzfblog.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzfblog.crm.mapper.BaseDictMapper;
import com.jzfblog.crm.pojo.BaseDict;
import com.jzfblog.crm.service.BaseDictService;

@Service
public class BaseDictServiceImpl implements BaseDictService{

	@Autowired
	private BaseDictMapper baseDictMapper;
	
	@Override
	public List<BaseDict> queryBaseDictByDictTypeCode(String dictTypeCode) {
		
		return this.baseDictMapper.queryBaseDictByDictTypeCode(dictTypeCode);
	}

}
