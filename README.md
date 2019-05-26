# ssm_crm
基于Spring、SpringMVC、Mybatis的客户模块功能

## 一、SpringMVC的配置

#### 1.web.xml

> 配置SpringMVC和Post乱码。

```xml

<display-name>boot-crm</display-name>
<welcome-file-list>
	<welcome-file>index.jsp</welcome-file>
</welcome-file-list>

<!-- 配置过滤器，解决post的乱码问题 -->
<filter>
	<filter-name>encoding</filter-name>	<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>encoding</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>

<!-- 配置SpringMVC -->
<servlet>
	<servlet-name>boot-crm</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/springmvc.xml</param-value>
	</init-param>
	<!-- 配置springmvc什么时候启动，参数必须为整数 -->
	<!-- 如果为0或者大于0，则springMVC随着容器启动而启动 -->
	<!-- 如果小于0，则在第一次请求进来的时候启动 -->
	<load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>boot-crm</servlet-name>
	<!-- 所有的请求都进入springMVC -->
	<url-pattern>/</url-pattern>
</servlet-mapping>
```

#### 2.springmvc.xml

> 配置SpringMVC表现层：Controller扫描、注解驱动、视图解析器。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
	<!-- 配置Controller扫描 -->
	<context:component-scan base-package="com.jzfblog..crm.controller" />

	<!-- 配置注解驱动 -->
	<mvc:annotation-driven />

	<!-- 配置视图解析器 -->
	<bean	class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!-- 前缀 -->
		<property name="prefix" value="/WEB-INF/jsp/" />
		<!-- 后缀 -->
		<property name="suffix" value=".jsp" />
	</bean>
</beans>
```

## 二、Spring的配置

#### 1.web.xml

```xml
<!-- 配置spring -->
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>classpath:spring/applicationContext-*.xml</param-value>
</context-param>

<!-- 配置监听器加载spring -->
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

#### 2.jdbc.properties

```xml
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/crm?characterEncoding=utf-8
jdbc.username=root
jdbc.password=mysql
```

#### 3.applicationContext-dao.xml

> 加载properties文件，数据源，SqlSessionFactory，Mapper扫描。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
	http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

	<!-- 配置 读取properties文件 jdbc.properties -->
	<context:property-placeholder location="classpath:jdbc.properties" />

	<!-- 配置 数据源 -->
	<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
		<property name="driverClassName" value="${jdbc.driver}" />
		<property name="url" value="${jdbc.url}" />
		<property name="username" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
	</bean>

	<!-- 配置SqlSessionFactory -->
	<bean class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 设置MyBatis核心配置文件 -->
		<property name="configLocation" value="classpath:mybatis/SqlMapConfig.xml" />
		<!-- 设置数据源 -->
		<property name="dataSource" ref="dataSource" />
	</bean>

	<!-- 配置Mapper扫描 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 设置Mapper扫描包 -->
		<property name="basePackage" value="com.jzfblog.crm.mapper" />
	</bean>
</beans>
```

#### 4.applicationContext-service.xml

```xml
<!-- 配置Service扫描 -->
<context:component-scan base-package="com.jzfblog.crm.service" />
```

#### 5.applicationContext-trans.xml

```xml
<!-- 事务管理器 -->
<bean id="transactionManager"	class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<!-- 数据源 -->
	<property name="dataSource" ref="dataSource" />
</bean>

<!-- 通知 -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
	<tx:attributes>
		<!-- 传播行为 -->
		<tx:method name="save*" propagation="REQUIRED" />
		<tx:method name="insert*" propagation="REQUIRED" />
		<tx:method name="add*" propagation="REQUIRED" />
		<tx:method name="create*" propagation="REQUIRED" />
		<tx:method name="delete*" propagation="REQUIRED" />
		<tx:method name="update*" propagation="REQUIRED" />
		<tx:method name="find*" propagation="SUPPORTS" read-only="true" />
		<tx:method name="select*" propagation="SUPPORTS" read-only="true" />
		<tx:method name="get*" propagation="SUPPORTS" read-only="true" />
		<tx:method name="query*" propagation="SUPPORTS" read-only="true" />
	</tx:attributes>
</tx:advice>

<!-- 切面 -->
<aop:config>
	<aop:advisor advice-ref="txAdvice"
		pointcut="execution(* com.jzfblog.crm.service.*.*(..))" />
</aop:config>
```

## 三、Mybatis配置

#### 1.SqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
 
 	<!-- 别名 -->
 	<typeAliases>
 		<package name="com.jzfblog.crm.pojo"/>
 	</typeAliases>
 
</configuration>
```

## 四、log4j配置

```xml
# Global logging configuration
log4j.rootLogger=DEBUG, stdout
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

## 五、准备工作

#### 1.BaseDict.java

```java
public class BaseDict implements Serializable{
	private String dict_id;
	private String dict_type_code;
	private String dict_type_name;
	private String dict_item_name;
	private String dict_item_code;
	private Integer dict_sort;
	private String dict_enable;
	private String dict_memo;
}
```

#### 2.Customer.java

```java
public class Customer implements Serializable{

	private Long cust_id;
	private String cust_name;
	private Long cust_user_id;
	private Long cust_create_id;
	private String cust_source;
	private String cust_industry;
	private String cust_level;
	private String cust_linkman;
	private String cust_phone;
	private String cust_mobile;
	private String cust_zipcode;
	private String cust_address;
	private Date cust_createtime;
}
```
#### 3.CustomerController.java

```java
/**
 *  客户管理
 * @author 蒋振飞
 *
 */
@Controller
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	private BaseDictService baseDictService;
	
    @Autowired
    private CustomerService customerService;

}
```

#### 4.BaseDictServiceImpl

> 需要特别注意的是，必须要在类上加上@Service，否则在controller层的baseDictService会注入失败。

```java
@Service("baseDictService")
public class BaseDictServiceImpl implements BaseDictService{

	@Autowired
	private BaseDictMapper baseDictMapper;
	
}
```

#### 5.CustomerServiceImpl.java

```java
@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerMapper customerMapper;
}
```

#### 6.创建好BaseDictMapper.java和CustomerMapper.java接口


#### 7.BaseDictMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.jzfblog.crm.mapper.BaseDictMapper">

</mapper>
```

#### 8.CustomerMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.jzfblog.crm.mapper.CustomerMapper">

</mapper>
```

#### 9.SqlMapConfig.xml

```xml
<mappers>
	<mapper resource="com/jzfblog/crm/mapper/BaseDictMapper.xml" />
	<mapper resource="com/jzfblog/crm/mapper/CustomerMapper.xml" />
</mappers>
```

## 六、数据字典查询

#### 1.env.properties

> 解决硬编码问题。

```xml
CUSTOMER_FROM_TYPE=002
CUSTOMER_INDUSTRY_TYPE=001
CUSTOMER_LEVEL_TYPE=006
```

#### 2.springmvc.xml

> 在springmvc.xml中加载env.properties。

```xml
<!-- 加载controller需要的配置信息 -->
<context:property-placeholder 
location="classpath:env.properties" />
```

#### 3.前端页面

```html
<select	class="form-control" id="customerFrom" placeholder="客户来源" name="custSource">
	<option value="">--请选择--</option>
	<c:forEach items="${fromType}" var="item">
		<option value="${item.dict_id}"<c:if test="${item.dict_id == custSource}"> selected</c:if>>${item.dict_item_name }</option>
	</c:forEach>
</select>
<select	class="form-control" id="custIndustry"  name="custIndustry">
	<option value="">--请选择--</option>
	<c:forEach items="${industryType}" var="item">
		<option value="${item.dict_id}"<c:if test="${item.dict_id == custIndustry}"> selected</c:if>>${item.dict_item_name }</option>
	</c:forEach>
</select>
<select	class="form-control" id="custLevel" name="custLevel">
	<option value="">--请选择--</option>
	<c:forEach items="${levelType}" var="item">
		<option value="${item.dict_id}"<c:if test="${item.dict_id == custLevel}"> selected</c:if>>${item.dict_item_name }</option>
	</c:forEach>
</select>
```

#### 4.CustomerController.java

```java
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
public String queryCustomerList(Model model) {


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

	return "customer";
}
```

#### 5.BaseDictServiceImpl.java

```java
public List<BaseDict> queryBaseDictByDictTypeCode(String dictTypeCode) {
	
	return this.baseDictMapper.queryBaseDictByDictTypeCode(dictTypeCode);
}
```

#### 6.BaseDictMapper.java

```java
List<BaseDict> queryBaseDictByDictTypeCode(String dictTypeCode);
```

#### 7.BaseDictMapper.xml

```xml
<!-- 根据类别代码查询数据 -->
<select id="queryBaseDictByDictTypeCode" parameterType="String"
	resultType="BaseDict">
	SELECT * FROM base_dict WHERE dict_type_code =
	#{dict_type_code}
</select>
```

## 七、条件查询客户

#### 1.QueryVo.java

```java
public class QueryVo {

	// 客户名称
	private String custName;
	// 客户来源
	private String custSource;
	// 客户行业
	private String custIndustry;
	// 客户级别
	private String custLevel;
	
	// 当前页
	private Integer currentPage;
	// 每页记录数
	private Integer pageSize = 10;
	// 起始记录
	private Integer startIndex = 0;
}
```

#### 2.Page.java

```java
public class Page<T> {
    
	private int total;
	private int page;
	private int size;
    private List<T> rows;
}
```

#### 3.前端页面

```html
<c:forEach items="${page.rows}" var="row">
	<tr>
		<td>${row.cust_id}</td>
		<td>${row.cust_name}</td>
		<td>${row.cust_source}</td>
		<td>${row.cust_industry}</td>
		<td>${row.cust_level}</td>
		<td>${row.cust_phone}</td>
		<td>${row.cust_mobile}</td>
		<td>
			<a href="#" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#customerEditDialog" onclick="editCustomer(${row.cust_id})">修改</a>
			<a href="#" class="btn btn-danger btn-xs" onclick="deleteCustomer(${row.cust_id})">删除</a>
		</td>
	</tr>
</c:forEach>
```

#### 4.CustomerController.java

```java
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
```

#### 5.CustomerServiceImpl.java

```java
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
```

#### 6.CustomerMapper.java

```java
// 根据vo条件查询记录数
int queryCountByQueryVo (QueryVo vo);

// 分页的结果集
 List<Customer> queryCustomerByQueryVo(QueryVo vo);
```

#### 6.CustomerMapper.xml

```xml
<!-- 根据vo条件查询客户总条数 -->
<select id="queryCountByQueryVo" parameterType="QueryVo"
	resultType="Integer">
	SELECT count(*) FROM customer
	<where>
		<if test="custName != null and custName != ''">cust_name like "%"#{custName}"%"</if>
		<if test="custSource != null and custSource != ''">and cust_source = #{custSource}</if>
		<if test="custIndustry != null and custIndustry != ''">and cust_industry = #{custIndustry}</if>
		<if test="custLevel != null and custLevel != ''">and cust_level = #{custLevel}</if>
	</where>
</select>

<!-- 根据vo条件查询客户的结果集 -->
<select id="queryCustomerByQueryVo" parameterType="QueryVo"
	resultType="Customer">
	SELECT
	a.cust_id,
	a.cust_name,
	a.cust_user_id,
	a.cust_create_id,
	b.dict_item_name cust_source,
	c.dict_item_name cust_industry,
	d.dict_item_name cust_level,
	a.cust_linkman,
	a.cust_phone,
	a.cust_mobile,
	a.cust_zipcode,
	a.cust_address,
	a.cust_createtime
  FROM
	customer a
	LEFT JOIN base_dict b ON a.cust_source = b.dict_id
	LEFT JOIN base_dict c ON a.cust_industry = c.dict_id
	LEFT JOIN base_dict d ON a.cust_level = d.dict_id
	<where>
		<if test="custName != null and custName != ''">a.cust_name like "%"#{custName}"%"</if>
		<if test="custSource != null and custSource != ''">and a.cust_source = #{custSource}</if>
		<if test="custIndustry != null and custIndustry != ''">and a.cust_industry = #{custIndustry}</if>
		<if test="custLevel != null and custLevel != ''">and a.cust_level = #{custLevel}</if>
	</where>
	limit #{startIndex}, #{pageSize}
</select>
```

## 八、修改客户

#### 1.前端页面

```html
<input type="text" class="form-control" id="edit_customerName" placeholder="客户名称" name="cust_name">

<select	class="form-control" id="edit_customerFrom" placeholder="客户来源" name="cust_source">
	<option value="">--请选择--</option>
	<c:forEach items="${fromType}" var="item">
		<option value="${item.dict_id}"<c:if test="${item.dict_id == custSource}"> selected</c:if>>${item.dict_item_name }</option>
	</c:forEach>
</select>

<select	class="form-control" id="edit_custIndustry"  name="cust_industry">
	<option value="">--请选择--</option>
	<c:forEach items="${industryType}" var="item">
		<option value="${item.dict_id}"<c:if test="${item.dict_id == custIndustry}"> selected</c:if>>${item.dict_item_name }</option>
	</c:forEach>
</select>

<select	class="form-control" id="edit_custLevel" name="cust_level">
	<option value="">--请选择--</option>
	<c:forEach items="${levelType}" var="item">
		<option value="${item.dict_id}"<c:if test="${item.dict_id == custLevel}"> selected</c:if>>${item.dict_item_name }</option>
	</c:forEach>
</select>

<input type="text" class="form-control" id="edit_linkMan" placeholder="联系人" name="cust_linkman">
<input type="text" class="form-control" id="edit_phone" placeholder="固定电话" name="cust_phone">
<input type="text" class="form-control" id="edit_mobile" placeholder="移动电话" name="cust_mobile">
<input type="text" class="form-control" id="edit_zipcode" placeholder="邮政编码" name="cust_zipcode">
<input type="text" class="form-control" id="edit_address" placeholder="联系地址" name="cust_address">
```

```javascript
<!-- 加载各个Mapper.xml -->
function editCustomer(id) {
	$.ajax({
		type:"get",
		url:"<%=basePath%>customer/edit.action",
		data:{"id":id},
		success:function(data) {
			$("#edit_cust_id").val(data.cust_id);
			$("#edit_customerName").val(data.cust_name);
			$("#edit_customerFrom").val(data.cust_source)
			$("#edit_custIndustry").val(data.cust_industry)
			$("#edit_custLevel").val(data.cust_level)
			$("#edit_linkMan").val(data.cust_linkman);
			$("#edit_phone").val(data.cust_phone);
			$("#edit_mobile").val(data.cust_mobile);
			$("#edit_zipcode").val(data.cust_zipcode);
			$("#edit_address").val(data.cust_address);
			
		}
	});
	}
	function updateCustomer() {
	$.post("<%=basePath%>customer/update.action",$("#edit_customer_form").serialize(),function(data){
		alert("客户信息更新成功！");
		window.location.reload();
	});
}
```

#### 2.CustomerController.java

```java
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
```

#### 3.CustomerServiceImpl.java

```java
@Override
public Customer queryCustomerById(Integer id) {
	
	Customer customer = this.customerMapper.queryCustomerById(id);
	return customer;
}

@Override
public void updateCustomer(Customer customer) {

	this.customerMapper.updateCustomer(customer);
}
```

#### 4.CustomerMapper.java

```java
// 根据id查询单个客户
Customer queryCustomerById(Integer id);
// 修改客户
void updateCustomer(Customer customer);
```

#### 5.CustomerMapper.xml

```xml
<!-- 根据id条查询单个客户 -->
<select id="queryCustomerById" parameterType="Integer"
	resultType="Customer">
	SELECT * FROM customer WHERE cust_id = #{id}
</select>

<!-- 修改用户 -->
<update id="updateCustomer" parameterType="Customer">
	UPDATE customer 
	<set>
		<if test="cust_name != null and cust_name != ''">
			cust_name = #{cust_name}
		</if>
	</set>
	<where>
		cust_id = #{cust_id}
	</where>
</update>
```

## 九、删除客户

#### 1.前端js

```javascript
function deleteCustomer(id) {
	if(confirm('确实要删除该客户吗?')) {
		$.post("<%=basePath%>customer/delete.action",{"id":id},function(data){
			alert("客户删除更新成功！");
			window.location.reload();
		});
	}
}
```

#### 2.CustomerController.java

```java
// 删除客户操作
@RequestMapping("delete")
@ResponseBody
public String deleteCustomer(Integer id) {
	Customer customer = this.customerService.queryCustomerById(id);
	this.customerService.deleteCustomer(customer);
	return "OK";
}
```

#### 3.CustomerServiceImpl.java

```java
@Override
public void deleteCustomer(Customer customer) {

	this.customerMapper.deleteCustomer(customer);
}
```

#### 4.CustomerMapper.java

```java
void deleteCustomer(Customer customer);
```

#### 5.CustomerMapper.xml

```xml
<!-- 删除用户 -->
<delete id="deleteCustomer" parameterType="Customer">
	Delete FROM customer
	<where>cust_id = #{cust_id}</where>
</delete>
```
