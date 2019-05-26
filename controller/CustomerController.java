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
 *  客户管理
 * @author 蒋振飞
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

	// 客户来源
	@Value("${CUSTOMER_FROM_TYPE}")
	private String CUSTOMER_FROM_TYPE;
	// 客户行业
	@Value("${CUSTOMER_INDUSTRY_TYPE}")
	private String CUSTOMER_INDUSTRY_TYPE;
	// 客户级别
	@Value("${CUSTOMER_LEVEL_TYPE}")
	private String CUSTOMER_LEVEL_TYPE;

	@Autowired
	private BaseDictService baseDictService;
	
	@Autowired
	private CustomerService customerService;
	
	// 入口
	@RequestMapping("list")
	public String queryCustomerList(QueryVo vo, Model model) {
		try {
			// 解决get请求乱码问题
			if (vo.getCustName() != null && "".equals(vo.getCustName())) {
				vo.setCustName(new String(vo.getCustName().getBytes("ISO-8859-1"), "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 客户来源
		List<BaseDict> fromType = this.baseDictService.queryBaseDictByDictTypeCode(this.CUSTOMER_FROM_TYPE);
		// 所属行业
		List<BaseDict> industryType = this.baseDictService.queryBaseDictByDictTypeCode(this.CUSTOMER_INDUSTRY_TYPE);
		// 客户级别
		List<BaseDict> levelType = this.baseDictService.queryBaseDictByDictTypeCode(this.CUSTOMER_LEVEL_TYPE);
		
		// 把前端页面需要显示的内容放到模型里
		model.addAttribute("fromType", fromType);
		model.addAttribute("industryType", industryType);
		model.addAttribute("levelType", levelType);
		
		// 通过四个条件，查询分页对象
		Page<Customer> page = customerService.selectPageByQueryVo(vo);
		model.addAttribute("page", page);
		
		model.addAttribute("custName", vo.getCustName());
		model.addAttribute("custIndustry", vo.getCustIndustry());
		model.addAttribute("custSource", vo.getCustSource());
		model.addAttribute("custLevel", vo.getCustLevel());

		return "customer";
	}
	
	// 修改客户ajax回显
	@RequestMapping("edit")
	@ResponseBody
	public Customer editCustomer(Integer id) {
		
		Customer customer = this.customerService.queryCustomerById(id);
		return customer;
		
	}
	
	// 修改客户操作
	@RequestMapping("update")
	@ResponseBody
	public String updateCustomer(Customer customer) {
		
		this.customerService.updateCustomer(customer);
		return "OK";
	}
	
	// 删除客户操作
	@RequestMapping("delete")
	@ResponseBody
	public String deleteCustomer(Integer id) {
		Customer customer = this.customerService.queryCustomerById(id);
		this.customerService.deleteCustomer(customer);
		return "OK";
	}
}
