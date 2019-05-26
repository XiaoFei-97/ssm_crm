package com.jzfblog.crm.mapper;

import java.util.List;

import com.jzfblog.crm.pojo.Customer;
import com.jzfblog.crm.pojo.QueryVo;

public interface CustomerMapper {

	// ����vo������ѯ��¼��
	int queryCountByQueryVo(QueryVo vo);

	// ��ҳ�Ľ����
	List<Customer> queryCustomerByQueryVo(QueryVo vo);

	// ����id��ѯ�����ͻ�
	Customer queryCustomerById(Integer id);

	void updateCustomer(Customer customer);

	void deleteCustomer(Customer customer);
	
	

}
