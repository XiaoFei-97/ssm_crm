package com.jzfblog.crm.mapper;

import java.util.List;

import com.jzfblog.crm.pojo.Customer;
import com.jzfblog.crm.pojo.QueryVo;

public interface CustomerMapper {

	// 根据vo条件查询记录数
	int queryCountByQueryVo(QueryVo vo);

	// 分页的结果集
	List<Customer> queryCustomerByQueryVo(QueryVo vo);

	// 根据id查询单个客户
	Customer queryCustomerById(Integer id);

	void updateCustomer(Customer customer);

	void deleteCustomer(Customer customer);
	
	

}
