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
 * 客户管理
 * @author 蒋振飞
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public Page<Customer> selectPageByQueryVo(QueryVo vo) {
		// 通过四个条件 查询分页对象
		Page<Customer> page = new Page<Customer>();
		// 判断vo查询对象是否为空
		if(null != vo) {
			// 判断当前页是否为空
			if(null != vo.getCurrentPage()) {
				page.setPage(vo.getCurrentPage());
				vo.setStartIndex((vo.getCurrentPage() - 1)*vo.getPageSize());
			}
		}
		// 根据vo查询总记录数
		int total = customerMapper.queryCountByQueryVo(vo);
		// 根据vo查询结果集
		List<Customer> rows = customerMapper.queryCustomerByQueryVo(vo);
		// 设置page条件
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
