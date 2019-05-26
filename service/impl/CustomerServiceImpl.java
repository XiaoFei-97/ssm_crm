package com.jzfblog.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzfblog.common.utils.Page;
import com.jzfblog.crm.mapper.CustomerMapper;
import com.jzfblog.crm.pojo.Customer;
import com.jzfblog.crm.pojo.QueryVo;
import com.jzfblog.crm.service.CustomerService;

/**
 * �ͻ�����
 * @author �����
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public Page<Customer> selectPageByQueryVo(QueryVo vo) {
		// ͨ���ĸ����� ��ѯ��ҳ����
		Page<Customer> page = new Page<Customer>();
		// �ж�vo��ѯ�����Ƿ�Ϊ��
		if(null != vo) {
			// �жϵ�ǰҳ�Ƿ�Ϊ��
			if(null != vo.getCurrentPage()) {
				page.setPage(vo.getCurrentPage());
				vo.setStartIndex((vo.getCurrentPage() - 1)*vo.getPageSize());
			}
		}
		// ����vo��ѯ�ܼ�¼��
		int total = customerMapper.queryCountByQueryVo(vo);
		// ����vo��ѯ�����
		List<Customer> rows = customerMapper.queryCustomerByQueryVo(vo);
		// ����page����
		page.setSize(5);
		page.setTotal(total);
		page.setRows(rows);
		System.out.println(page);
		return page;
	}

	@Override
	public Customer queryCustomerById(Integer id) {
		
		Customer customer = this.customerMapper.queryCustomerById(id);
		return customer;
	}

	@Override
	public void updateCustomer(Customer customer) {

		this.customerMapper.updateCustomer(customer);
	}

	@Override
	public void deleteCustomer(Customer customer) {

		this.customerMapper.deleteCustomer(customer);
	}
	
	

	
	
}
