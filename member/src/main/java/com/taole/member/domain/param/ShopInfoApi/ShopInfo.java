package com.taole.member.domain.param.ShopInfoApi;

import io.swagger.annotations.ApiModelProperty;

public class ShopInfo {

	public class ShopInfoApiShopInfoRsp {
		
		@ApiModelProperty(value = "店铺ID")
		private String id;
		
		@ApiModelProperty(value = "店铺名称")
		private String name;
		
		@ApiModelProperty(value = "图片")
		private String image;
		
		@ApiModelProperty(value = "电话")
		private String tel;
		
		@ApiModelProperty(value = "地址")
		private String addres;
		
		@ApiModelProperty(value = "营业开始时间")
		private String openTime;
		
		@ApiModelProperty(value = "营业开始时间")
		private String closeTime;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getAddres() {
			return addres;
		}

		public void setAddres(String addres) {
			this.addres = addres;
		}

		public String getOpenTime() {
			return openTime;
		}

		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}

		public String getCloseTime() {
			return closeTime;
		}

		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}
	}
}
