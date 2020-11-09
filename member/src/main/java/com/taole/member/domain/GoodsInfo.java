package com.taole.member.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taole.framework.annotation.PrimaryKey;
import com.taole.framework.bean.DomainObject;
import com.taole.member.ProjectConfig;

@Entity(name = ProjectConfig.PREFIX + "goodsInfo")
@Table(name = "GOODS_INFO")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class GoodsInfo implements DomainObject, Cloneable {

	private static final long serialVersionUID = 4284563374732065005L;

	/**
	 * 商品ID
	 */
	@Id
	@PrimaryKey
	@Column(name = "GOODS_ID", unique = true, nullable = false)
	private String goodsId;

	/**
	 * 商品编码
	 */
	@Column(name = "GOODS_CODE")
	private String goodsCode;

	/**
	 * 商品名称
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 商品分类ID
	 */
	@Column(name = "CATALOG_ID")
	private String catalogId;

	/**
	 * 商品售价
	 */
	@Column(name = "SALE_MONEY")
	private Double saleMoney;
	
	/**
	 * 商品原价
	 */
	@Column(name = "ORIGINAL_COST")
	private Double originalCost;
	
	@Column(name = "MAXNUMBER")
	private Integer maxnumber;

	/**
	 * 销价单位（次/元）
	 */
	@Column(name = "UNIT")
	private String unit;

	/**
	 * 商品描述
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * 状态（启用/停用）
	 */
	@Column(name = "STATUS")
	private String status;

	/**
	 * 有效截止日期
	 */
	@Column(name = "DEADLINE")
	private Date deadline;
	
	@Column(name = "BEGIN_DATE")
	private Date beginDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	/**
	 * 线上是否可售
	 */
	@Column(name = "ONLINE_BUY")
	private String onlineBuy;
	
	/**
	 * 商品图片
	 */
	@Column(name = "IMAGE")
	private String image;

	/**
	 * 修改时间
	 */
	@Column(name = "UPDATE_TIME")
	private Date updateTime;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	/**
	 * 背面图
	 */
	@Column(name = "IMAGE_BACK")
	private String imageBack;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public Double getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getOnlineBuy() {
		return onlineBuy;
	}

	public void setOnlineBuy(String onlineBuy) {
		this.onlineBuy = onlineBuy;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImageBack() {
		return imageBack;
	}

	public void setImageBack(String imageBack) {
		this.imageBack = imageBack;
	}

	public Double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Double originalCost) {
		this.originalCost = originalCost;
	}


	public Integer getMaxnumber() {
		return maxnumber;
	}

	public void setMaxnumber(Integer maxnumber) {
		this.maxnumber = maxnumber;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
