package com.taole.member.domain.param.ShopGoodsApi;

import io.swagger.annotations.ApiModelProperty;

public class QueryTicket {

	public static class ShopGoodsApiQueryTicketReq{
		
		@ApiModelProperty(value = "店铺ID")
		private String shopId;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}
	}
	
	public class ShopGoodsApiQueryTicketRsp {
		
		@ApiModelProperty(value = "商品ID")
		private String goodsId;
		
		@ApiModelProperty(value = "商品名称")
		private String goodsName;
		
		@ApiModelProperty(value = "商品价格")
		private Double money;
		
		@ApiModelProperty(value = "商品图片")
		private String image;
		
		@ApiModelProperty(value = "商品描述")
		private String description;
		
		@ApiModelProperty(value = "背面图")
		private String imageBack;
		
		@ApiModelProperty(value = "商品原价")
		private String originalCost;
		
		@ApiModelProperty(value = "商品库存")
		private String balance;
		@ApiModelProperty(value = "限购数量")
		private Integer maxnumner;
		
		
		public String getOriginalCost() {
			return originalCost;
		}

		public void setOriginalCost(String originalCost) {
			this.originalCost = originalCost;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public Integer getMaxnumner() {
			return maxnumner;
		}

		public void setMaxnumner(Integer maxnumner) {
			this.maxnumner = maxnumner;
		}

		public String getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(String goodsId) {
			this.goodsId = goodsId;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getImageBack() {
			return imageBack;
		}

		public void setImageBack(String imageBack) {
			this.imageBack = imageBack;
		}
	}
}
