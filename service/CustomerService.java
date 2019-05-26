package com.jzfblog.crm.service;

import com.jzfblog.common.utils.Page;
import com.jzfblog.crm.pojo.Customer;
import com.jzfblog.crm.pojo.QueryVo;

public interface CustomerService {

	Page<Customer> selectPageByQueryVo(QueryVo vo);

	Customer queryCustomerById(Integer id);

	void updateCustomer(Customer customer);

	void deleteCustomer(Customer customer);

}
