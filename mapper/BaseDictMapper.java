package com.jzfblog.crm.mapper;

import java.util.List;

import com.jzfblog.crm.pojo.BaseDict;

public interface BaseDictMapper {

	List<BaseDict> queryBaseDictByDictTypeCode(String dictTypeCode);

}
