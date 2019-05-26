package com.jzfblog.crm.service;

import java.util.List;

import com.jzfblog.crm.pojo.BaseDict;

public interface BaseDictService {

	List<BaseDict> queryBaseDictByDictTypeCode(String dictTypeCode);

	
}
