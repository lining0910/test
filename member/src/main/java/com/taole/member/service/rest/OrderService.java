package com.taole.member.service.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.member.Constants.OrderState;
import com.taole.member.Constants.OrderType;
import com.taole.member.ProjectConfig;
import com.taole.member.domain.Order;
import com.taole.member.manager.MemberCardManager;
import com.taole.member.manager.OrderManager;
import com.taole.member.manager.ShopCardManager;
import com.taole.member.manager.ShopGoodsManager;

@Controller
@Api(tags = { "订单管理服务" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "Order")
public class OrderService {
	
	private Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private ShopCardManager shopCardManager;
	
	@Autowired
	private ShopGoodsManager shopGoodsManager;
	
	@Autowired
	private MemberCardManager memberCardManager;
	
	/**
	 * 支付成功回调
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/collection/orderCallback", method = RequestMethod.GET, produces = "application/json;")
	public @ResponseBody Object orderCallback(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
		
		try {
			
			logger.info("order call back orderid ============= " + orderId);
			
			Order order = orderManager.getOrder(orderId);
			if(order != null && Integer.valueOf(OrderState.WAIT_PAY) == order.getState()){
				if(OrderType.CARD.equals(order.getType())){//办卡订单支付成功
					shopCardManager.buyCardSuccess(orderId);
				}else if(OrderType.GOODS.equals(order.getType()) || OrderType.TICKET.equals(order.getType())){
					shopGoodsManager.buySuccess(orderId);
				}else if(OrderType.RECHARGE.equals(order.getType())){
					memberCardManager.rechargeSuccess(orderId);
				}
				order.setState(Integer.valueOf(OrderState.PAY_SUCCESS));
				orderManager.updateOrder(order);
			}
			
			return new ServiceResult(ReturnResult.SUCCESS, "回调成功");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopCard" + ".orderCallback:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "回调失败: " + e.getMessage());
		}
	}
}
