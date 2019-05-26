package com.jzfblog.crm.pojo;

public class QueryVo {

	// �ͻ�����
	private String custName;
	// �ͻ���Դ
	private String custSource;
	// �ͻ���ҵ
	private String custIndustry;
	// �ͻ�����
	private String custLevel;
	
	// ��ǰҳ
	private Integer currentPage;
	// ÿҳ��¼��
	private Integer pageSize = 10;
	// ��ʼ��¼
	private Integer startIndex = 0;
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustSource() {
		return custSource;
	}
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	public String getCustIndustry() {
		return custIndustry;
	}
	public void setCustIndustry(String custIndustry) {
		this.custIndustry = custIndustry;
	}
	public String getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.startIndex = (currentPage - 1)*pageSize;
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

}
