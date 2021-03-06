package com.taole.member.domain.param.UserApi;

import io.swagger.annotations.ApiModelProperty;

public class UserBillList {

	public static class UserApiUserBillListReq {
		@ApiModelProperty(value = "用户ID", required = true)
		private String userId;
		
		@ApiModelProperty(value = "消费类型")
		private String actionTypeId;
		
		@ApiModelProperty(value = "查询年月 2019-05")
		private String time;
		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getActionTypeId() {
			return actionTypeId;
		}

		public void setActionTypeId(String actionTypeId) {
			this.actionTypeId = actionTypeId;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}
	
	public class UserApiUserBillListRsp {
		
		@ApiModelProperty(value = "用户流水ID")
		private String userBilId;
		
		@ApiModelProperty(value = "消费店铺名称")
		private String shopName;
		
		@ApiModelProperty(value = "消费金额")
		private Double money;
		
		@ApiModelProperty(value = "消费时间")
		private String time;
		
		@ApiModelProperty(value = "消费类型")
		private String actionTypeName;
		
		@ApiModelProperty(value = "总支出")
		private Double payCount;

		public String getUserBilId() {
			return userBilId;
		}

		public void setUserBilId(String userBilId) {
			this.userBilId = userBilId;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getActionTypeName() {
			return actionTypeName;
		}

		public void setActionTypeName(String actionTypeName) {
			this.actionTypeName = actionTypeName;
		}

		public Double getPayCount() {
			return payCount;
		}

		public void setPayCount(Double payCount) {
			this.payCount = payCount;
		}
	}
}
