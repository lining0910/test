<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/include/page.jspf" %>
<html>
<head>
	<title>商品出库管理</title>
	<jsp:include page="/include/header.jsp"></jsp:include>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/interfaces.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/TimePickerField.js"></script>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/DateTimePicker.js"></script>  	
  	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ux/form/DateTimeField.js"></script>
</head> 
  <body>   
	<script type="text/javascript">
		
		Ext.Loader.setConfig({
			enabled:true//, disableCaching: false
		});
		
		Ext.application({
	   		name: 'Taole',
	   		appFolder: '<%=request.getContextPath()%>/app',
	   		controllers: [
	       		'Taole.goods.goodsDelivery.controller.GoodsDeliveryCtrl'	       		
	   		],
		    launch: function() {
		    	Ext.create("Ext.Viewport", {
		    		layout: 'fit',
		    		items:[
		    		{
		    			xtype: 'goodsDeliveryPanel'
		    		}
		    		]
		    	})
		    }
		});
	</script> 
  </body>
</html>
