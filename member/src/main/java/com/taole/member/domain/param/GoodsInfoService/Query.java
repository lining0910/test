package com.taole.member.domain.param.GoodsInfoService;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Query {
	@ApiModel(value = "查询商品信息列表请求参数")
	public static class GoodsInfoServiceQueryReq {

		@ApiModelProperty(value = "商品名称", example = "国贸店")
		private String name;

		@ApiModelProperty(value = "商品类型", example = "MPL")
		private String catalogId;
		
		@ApiModelProperty(value = "商品id", example = "42324242")
		private String goodsId;
		
		
		
		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	@ApiModel(value = "查询商品信息列表返回参数")
	public static class GoodsInfoServiceQueryResp {
		@ApiModelProperty(value = "商品Id")
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称", example = "可乐")
		private String name;

		@ApiModelProperty(value = "商品编码", example = "43254")
		private String goodsCode;

		@ApiModelProperty(value = "商品分类ID", example = "3213")
		private String catalogId;

		@ApiModelProperty(value = "商品售价", example = "600")
		private String saleMoney;

		@ApiModelProperty(value = "销价单位", example = "次")
		private String unit;

		@ApiModelProperty(value = "商品描述")
		private String description;

		@ApiModelProperty(value = "状态(启用/停用)")
		private String status;
		
		@ApiModelProperty(value = "有效截止日期")
		private Date deadline;
		
		@ApiModelProperty(value = "商品图片")
		private String image;
		
		@ApiModelProperty(value = "线上是否可售 1可售 0不可售")
		private String onlineBuy;
		
		@ApiModelProperty(value = "创建时间")
		private String createTime;
		
		@ApiModelProperty(value = "修改时间")
		private String updateTime;
		
		@ApiModelProperty(value = "首页配置类别ID")
		private List<String> homeTypes;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getGoodsCode() {
			return goodsCode;
		}

		public void setGoodsCode(String goodsCode) {
			this.goodsCode = goodsCode;
		}

		public String getCatalogId() {
			return catalogId;
		}

		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}

		public String getSaleMoney() {
			return saleMoney;
		}

		public void setSaleMoney(String saleMoney) {
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

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public List<String> getHomeTypes() {
			return homeTypes;
		}

		public void setHomeTypes(List<String> homeTypes) {
			this.homeTypes = homeTypes;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}
	}
}
