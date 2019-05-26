package com.jzfblog.crm.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jzfblog.common.utils.Page;
import com.jzfblog.crm.pojo.BaseDict;
import com.jzfblog.crm.pojo.Customer;
import com.jzfblog.crm.pojo.QueryVo;
import com.jzfblog.crm.service.BaseDictService;
import com.jzfblog.crm.service.CustomerService;
import com.sun.org.apache.xpath.internal.operations.And;

/**
 *  �ͻ�����
 * @author �����
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

	// �ͻ���Դ
	@Value("${CUSTOMER_FROM_TYPE}")
	private String CUSTOMER_FROM_TYPE;
	// �ͻ���ҵ
	@Value("${CUSTOMER_INDUSTRY_TYPE}")
	private String CUSTOMER_INDUSTRY_TYPE;
	// �ͻ�����
	@Value("${CUSTOMER_LEVEL_TYPE}")
	private String CUSTOMER_LEVEL_TYPE;

	@Autowired
	private BaseDictService baseDictService;
	
	@Autowired
	private CustomerService customerService;
	
	// ���
	@RequestMapping("list")
	public String queryCustomerList(QueryVo vo, Model model) {
		try {
			// ���get������������
			if (vo.getCustName() != null && "".equals(vo.getCustName())) {
				vo.setCustName(new String(vo.getCustName().getBytes("ISO-8859-1"), "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// �ͻ���Դ
		List<BaseDict> fromType = this.baseDictService.queryBaseDictByDictTypeCode(this.CUSTOMER_FROM_TYPE);
		// ������ҵ
		List<BaseDict> industryType = this.baseDictService.queryBaseDictByDictTypeCode(this.CUSTOMER_INDUSTRY_TYPE);
		// �ͻ�����
		List<BaseDict> levelType = this.baseDictService.queryBaseDictByDictTypeCode(this.CUSTOMER_LEVEL_TYPE);
		
		// ��ǰ��ҳ����Ҫ��ʾ�����ݷŵ�ģ����
		model.addAttribute("fromType", fromType);
		model.addAttribute("industryType", industryType);
		model.addAttribute("levelType", levelType);
		
		// ͨ���ĸ���������ѯ��ҳ����
		Page<Customer> page = customerService.selectPageByQueryVo(vo);
		model.addAttribute("page", page);
		
		model.addAttribute("custName", vo.getCustName());
		model.addAttribute("custIndustry", vo.getCustIndustry());
		model.addAttribute("custSource", vo.getCustSource());
		model.addAttribute("custLevel", vo.getCustLevel());

		return "customer";
	}
	
	// �޸Ŀͻ�ajax����
	@RequestMapping("edit")
	@ResponseBody
	public Customer editCustomer(Integer id) {
		
		Customer customer = this.customerService.queryCustomerById(id);
		return customer;
		
	}
	
	// �޸Ŀͻ�����
	@RequestMapping("update")
	@ResponseBody
	public String updateCustomer(Customer customer) {
		
		this.customerService.updateCustomer(customer);
		return "OK";
	}
	
	// ɾ���ͻ�����
	@RequestMapping("delete")
	@ResponseBody
	public String deleteCustomer(Integer id) {
		Customer customer = this.customerService.queryCustomerById(id);
		this.customerService.deleteCustomer(customer);
		return "OK";
	}
}
