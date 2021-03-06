package com.taole.member.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taole.dataprivilege.manager.UserDataPrivilegeManager;
import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.dao.util.Range;
import com.taole.framework.dao.util.Sorter;
import com.taole.framework.rest.RestService;
import com.taole.framework.rest.RestUrlRuler;
import com.taole.framework.rest.ReturnResult;
import com.taole.framework.service.ServiceResult;
import com.taole.framework.util.SerializableJSONTransformer;
import com.taole.member.Constants.ShopSetStatus;
import com.taole.member.Constants.ShopStatus;
import com.taole.member.ProjectConfig;
import com.taole.member.condition.ShopInfoCondition;
import com.taole.member.condition.ShopStoreSetCondition;
import com.taole.member.domain.GoodsInfo;
import com.taole.member.domain.ShopInfo;
import com.taole.member.domain.ShopStoreSet;
import com.taole.member.domain.param.ShopInfoService.Create;
import com.taole.member.domain.param.ShopInfoService.Delete;
import com.taole.member.domain.param.ShopInfoService.GetShopCode;
import com.taole.member.domain.param.ShopInfoService.Query;
import com.taole.member.domain.param.ShopInfoService.QueryForShopSet;
import com.taole.member.domain.param.ShopInfoService.QueryForUser;
import com.taole.member.domain.param.ShopInfoService.Update;
import com.taole.member.manager.GoodsInfoManager;
import com.taole.member.manager.ShopInfoManager;
import com.taole.member.manager.ShopStoreSetManager;
import com.taole.toolkit.dict.manager.DictionaryManager;
import com.taole.toolkit.util.biz.ConvertUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "店面后台管理" })
@RequestMapping(value = RestUrlRuler.REST_ROOT + "/" + ProjectConfig.PREFIX + "ShopInfo")
@Controller
@RestService(name = ProjectConfig.PREFIX + "ShopInfo")
public class ShopInfoService {
	private Logger logger = LoggerFactory.getLogger(ShopInfoService.class);

	private final static String RETURN_CODE_URL = ProjectConfig.RETURN_CODE_PATH + "ShopInfo_";

	@Autowired
	private ShopInfoManager shopInfoManager;
	
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@Autowired
	private ShopStoreSetManager shopStoreSetManager;
	
	@Autowired
	private GoodsInfoManager goodsInfoManager;
	/**
	 * 新增店面信息
	 * w
	 * @param request
	 * @param createReq
	 * @return
	 */
	@RequestMapping(value = "/collection/create", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "新增店面信息", httpMethod = "POST", response = Create.ShopInfoServiceCreateResp.class, notes = "新增店面信息")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "create.html") })
	@ReturnCodeInfo(value = "create")
	public @ResponseBody Object create(HttpServletRequest request,
			@RequestBody Create.ShopInfoServiceCreateReq createReq) {
		try {
			ShopInfo shopInfo = new ShopInfo();
			ConvertUtil.BeanToBean(createReq, shopInfo);
			shopInfo.setStatus(ShopStatus.CLOSE);
			
			/*int code = shopInfoManager.getShopCode();
			int newCode= code + 1;
			shopInfo.setCode(newCode);*/
			
			String shopId = shopInfoManager.createShopInfo(shopInfo);
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(shopInfo);
			jsonObject.put("shopId", shopId);
			return new ServiceResult(ReturnResult.SUCCESS, "新增店面信息成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".create:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "新增店面信息失败: " + e.getMessage());
		}
	}
	
	/**
	 * 删除店面基本信息
	 * 
	 * @param response
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/collection/delete", method = RequestMethod.DELETE, produces = "application/json;")
	@ApiOperation(value = "删除店面基本信息", httpMethod = "DELETE", response =Delete.ShopInfoServiceDeleteResp .class, notes = "删除店面基本信息")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "delete.html") })
	@ReturnCodeInfo(value = "delete")
	public @ResponseBody Object delete(HttpServletResponse response,
			@ApiParam(value = "店面ID，多个以“,”分隔", name = "ids", required = true) @RequestParam String ids) {
		try {
			if (StringUtils.isEmpty(ids)) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店面Id不能为空");
			}
			ShopStoreSetCondition shopStoreSetCondition = new ShopStoreSetCondition();
			shopStoreSetCondition.setShopId(ids);
			ShopStoreSet shopStoreSet = shopStoreSetManager.findByCondition(shopStoreSetCondition);

			if(shopStoreSet != null) {
				GoodsInfo goodsInfo = goodsInfoManager.getGoodsInfo(shopStoreSet.getObjectId());
				ShopInfo shopInfo  = shopInfoManager.getShopInfo(shopStoreSet.getShopId());
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店面已设置"+goodsInfo.getName()+"和"+shopInfo.getName()+"无法删除！");
			}
			
			String[] shopIds = ids.split(",");
			for (String shopId : shopIds) {
				ShopInfo shopInfo = shopInfoManager.getShopInfo(shopId);
				
				
				if(shopInfo.getStatus().equals("1")){
					return new ServiceResult(ReturnResult.VALIDATION_ERROR, "无法删除，店面已开业！");
				}
				if (shopInfo != null ) {
					shopInfoManager.deleteShopInfo(shopInfo);
				}
			}
			return new ServiceResult(ReturnResult.SUCCESS, "删除店面信息成功");
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".delete:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "删除店面信息失败: " + e.getMessage());
		}
	}
	
	/**
	 * 修改店面信息
	 * 
	 * @param request
	 * @param categoryIds
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST, produces = "application/json;")
	@ApiOperation(value = "修改店面信息", httpMethod = "POST", response = Update.ShopInfoServiceUpdateResp.class, notes = "修改店面信息")
	@ApiResponses(value = {@ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "updateCategory.html") })
	@ReturnCodeInfo(value = "update")
	public @ResponseBody Object update(HttpServletRequest request,
			@RequestBody Update.ShopInfoServiceUpdateReq updateReq,
			@ApiParam(required = true, name = "id", value = "店面id") @PathVariable String id) {
		try {
			//CardInfo cardInfo = cardInfoManager.getCardInfo(id);
			ShopInfo shopInfo = shopInfoManager.getShopInfo(id);
			if(shopInfo == null)
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店面信息不存在！");
			
			if (StringUtils.isEmpty(id)) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "店面id不能为空！");
			}
			ConvertUtil.BeanToBean(updateReq, shopInfo);
//			BeanUpdater.updateObject(shopInfo, updateReq);
			shopInfoManager.updateShopInfo(shopInfo);
			JSONObject jsonObject = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(shopInfo);
			return new ServiceResult(ReturnResult.SUCCESS, "修改店面信息成功",jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".update:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "修改店面信息失败: " + e.getMessage());
		}
	}
	
	/**
	 * 查询店面信息列表
	 * 
	 * @param request
	 * @param response
	 * @param start
	 * @param limit
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping(value = "/collection/query", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询店面信息列表", httpMethod = "GET", response = Query.ShopInfoServiceQueryResp.class, notes = "查询店面信息列表")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	@ReturnCodeInfo(value = "query")
	public @ResponseBody Object query(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute Query.ShopInfoServiceQueryReq queryCondition) {
		try {
			Range range = new Range(start, limit);
			ShopInfoCondition condition = new ShopInfoCondition();
			if(null != queryCondition.getName()){
				condition.setName(queryCondition.getName());
			}
			if(StringUtils.isNotEmpty(queryCondition.getStatus())){
				condition.setStatus(queryCondition.getStatus());
			}
			if(null != queryCondition.getProvince()){
				condition.setProvince(queryCondition.getProvince());
			}
			if(null != queryCondition.getCity()){
				condition.setCity(queryCondition.getCity());
			}
			
			JSONArray jsonArray = (JSONArray) shopInfoManager.searchToJson(condition, range, new Sorter().desc("sort"));

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			jsonObject.put("total", shopInfoManager.count(condition));
			return new ServiceResult(ReturnResult.SUCCESS, "查询店面信息列表成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ContentService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询店面信息列表失败: " + e.getMessage());
		}
	}
	
	/**
	 * 查询店面信息列表（数据权限）
	 * 
	 * @param request
	 * @param response
	 * @param start
	 * @param limit
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping(value = "/collection/queryByUser", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "根据登录用户查询店面信息列表", httpMethod = "GET", response = QueryForUser.ShopInfoServiceQueryForUserResp.class, notes = "根据登录用户查询店面信息列表")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	@ReturnCodeInfo(value = "queryByUser")
	public @ResponseBody Object queryByUser(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(value = "分页参数，开始行数从0开始", required = true, defaultValue = "0") @RequestParam int start,
			@ApiParam(value = "分页参数，每页显示条数", required = true, defaultValue = "25") @RequestParam int limit,
			@ModelAttribute QueryForUser.ShopInfoServiceQueryForUserReq queryReq) {
		try {
			
			JSONObject userDataObj =
					UserDataPrivilegeManager.getUserAllDataPrivilege("shop", request, queryReq.getUserId());
			
//			ShopInfoCondition condition = new ShopInfoCondition();
//			if(null != userDataObj){
//				condition.setIds(userDataObj.);
//			}
			
			JSONArray jsonArray = (JSONArray) shopInfoManager.searchToJsonForMember(userDataObj);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("items", jsonArray);
			return new ServiceResult(ReturnResult.SUCCESS, "根据登录用户查询店面信息列表成功", jsonObject);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ContentService" + ".query:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "根据登录用户查询店面信息列表失败: " + e.getMessage());
		}
	}
	
	/**
	 * 查询店面设置信息列表
	 * 
	 * @param request
	 * @param response
	 * @param start
	 * @param limit
	 * @param queryCondition
	 * @return
	 */
	@RequestMapping(value = "/collection/queryForShopSet", method = RequestMethod.GET, produces = "application/json;")
	@ApiOperation(value = "查询店面设置信息列表", httpMethod = "GET", response = QueryForShopSet.ShopInfoServiceQueryResp.class, notes = "查询店面设置信息列表")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "query.html") })
	@ReturnCodeInfo(value = "queryForShopSet")
	@ResponseBody
	public Object queryForShopSet(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute QueryForShopSet.ShopInfoServiceQueryReq queryCondition) {
		try {
		
			ShopInfoCondition condition = new ShopInfoCondition();
			List<ShopInfo> list = shopInfoManager.list(condition);
			JSONArray resultAry = new JSONArray();
			for (ShopInfo shopInfo : list) {
				JSONObject obj = new JSONObject();
				obj.put("shopId", shopInfo.getShopId());
				obj.put("name", shopInfo.getName());//名称
				
				ShopStoreSetCondition shopStoreSetCondition = new ShopStoreSetCondition();
				shopStoreSetCondition.setShopId(shopInfo.getShopId());
				shopStoreSetCondition.setObjectId(queryCondition.getObjectId());
				ShopStoreSet shopStoreSet = shopStoreSetManager.findByCondition(shopStoreSetCondition);
				obj.put("setStatus", ShopSetStatus.UNSET);
				if(null != shopStoreSet){
					obj.put("setStatus", ShopSetStatus.SET); //1-已设置；0-未设置
				}
				resultAry.add(obj);
			}
			return new ServiceResult(ReturnResult.SUCCESS, "查询成功", resultAry);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "ShopInfoService" + ".queryForShopSet:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "查询失败: " + e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "/{id}/retrieve", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "获取店面信息详情", httpMethod = "GET", response = Query.ShopInfoServiceQueryResp.class, notes = "获取店面信息详情")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "retrieve.html") })
	@ReturnCodeInfo(value = "Retrieve")
	public Object retrieve(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "id", value = "卡id") @PathVariable String id) {

		try {
			ShopInfo shopInfo = shopInfoManager.getShopInfo(id);
			if (null == shopInfo) {
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "没有店面信息！");
			}
			JSONObject object = (JSONObject) SerializableJSONTransformer.transformPojo2Jso(shopInfo);
			
			return new ServiceResult(ReturnResult.SUCCESS, "获取店面信息成功", object);
		} catch (Exception e) {
			logger.error(ProjectConfig.PREFIX + "CardInfoService" + ".retrieve:{}", e);
			return new ServiceResult(ReturnResult.FAILURE, "获取店面信息失败", e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}/getShopCode", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ApiOperation(value = "获取店面编码", httpMethod = "GET", response = GetShopCode.ShopInfoServiceGetShopCodeResp.class, notes = "获取店面信息详情")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "点击链接查看具体返回码：" + RETURN_CODE_URL + "retrieve.html") })
	@ReturnCodeInfo(value = "getShopCode")
	public Object getShopCode(HttpServletRequest request, HttpServletResponse response,
			@ApiParam(required = true, name = "id", value = "店面id") @PathVariable String id) {
				
		
		
		return id;

	}
}
