package com.rayuniverse.home;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.comm.ResultObject;
import com.rayuniverse.framework.json.JsonUtil;
import com.rayuniverse.order.service.OrderService;
import com.rayuniverse.user.domain.UserInfo;
import com.rayuniverse.user.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping("/")
	public String index(ModelMap map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// �����û�����
		// �û���Ϣ
		UserInfo ui = null;
		String uid = "";
		String userid = PlatformContext.getUmUserId();	// �û��������û�ID
		
		ResultObject ro = userService.getUserByUserId(userid);
		if(ro != null && ro.isState())
		{
			ui = (UserInfo) ro.getValue();
			
			uid = ui.getId();
			resultMap.put("_user_id", ui.getId());	// Ψһ��ʾID
			resultMap.put("_user_account", ui.getUserId());	// �û���
			resultMap.put("_user_name", ui.getName());		// �û�����
			resultMap.put("_user_info", ui);
		}
		else
		{
			System.out.println("�û���Ϣ��ѯʧ��[userid:"+userid+"]");
		}
		
//		// ��ѯ�û������Դ
//		ResultObject ro2 = userService.queryResourceListByUserId(uid);
//		if(ro2 != null && ro2.isState())
//		{
//			List rs = (List) ro2.getValue();
//			
//			if(rs != null && rs.size() > 0)
//			{
//				String khMenu = ",W1,W2,W3,W4,R1,R2,R3,S1,C1,C2,";
//				String wxMenu = ",WX1,WX2,WX3,WX4,";
//				String xtMenu = ",M1,M2,";
//				boolean hasKH = false;	// �ͻ�����
//				boolean hasWX = false;	// ΢��ƽ̨
//				boolean hasXT = false;	// ϵͳ����
//				Map<String, String> rMap = new HashMap<String, String>(); 
//				
//				for (Object rm : rs)
//				{
//					String rid = String.valueOf(((Map)rm).get("resource_id"));
//					if(khMenu.indexOf(","+rid+",") != -1)
//					{
//						hasKH = true;
//					}
//					else if(wxMenu.indexOf(","+rid+",") != -1)
//					{
//						hasWX = true;
//					}
//					else if(xtMenu.indexOf(","+rid+",") != -1)
//					{
//						hasXT = true;
//					}
//					else
//					{
//						System.out.println("��ԴID["+rid+"]δ�ڳ�����ָ���������˵�����Ҫ����HomeController��index������ָ��");
//					}
//					resultMap.put("_rid_"+rid, true);
//				}
//				
//				resultMap.put("_main_menu_kh", hasKH);
//				resultMap.put("_main_menu_wx", hasWX);
//				resultMap.put("_main_menu_xt", hasXT);
//				resultMap.put("_resource_map", rMap);
//				resultMap.put("_resource_list", rs);
//			}
//		}
//		else
//		{
//			System.out.println("�û������Դ��ѯʧ��[userid:"+userid+"]");
//		}
		
		map.put("_user_info_json", JsonUtil.toJson(resultMap, false));
		Map orderCountMap = orderService.getOrderCount();
		map.put("order_count", JsonUtil.toJson(orderCountMap, false));
		return "home/index";
	}
	@RequestMapping("/recharge_list")
	public String rechargeList(ModelMap map) {
		Map sumConsumeMap = orderService.querySumConsume();
		Map sumRechargeMap = orderService.querySumRecharge();
		map.put("sum_consum", sumConsumeMap.get("sumConsume"));
		map.put("sum_recharge", sumRechargeMap.get("sumRecharge"));
		return "financial/recharge_list";
	}
	
	@RequestMapping("/order_list")
	public String orderList(ModelMap map) {
		return "order/order_list";
	}
	
	@RequestMapping("/home")
	public String home(ModelMap map) {
		return "home/home";
	}
	
	@RequestMapping("/product_add")
	public String productAdd(ModelMap map) {
		return "product/product_add";
	}
	@RequestMapping("/product_list")
	public String productList(ModelMap map) {
		return "product/product_list";
	}
	
	@RequestMapping("/classification_add")
	public String classificationAdd(ModelMap map) {
		return "classification/classification_add";
	}
	@RequestMapping("/classification_list")
	public String classificationList(ModelMap map) {
		return "classification/classification_list";
	}
	
	@RequestMapping("/advertisement_list")
	public String advertisementList(ModelMap map) {
		return "ad/advertisement_list";
	}
	
	@RequestMapping("/card_list")
	public String cardList(ModelMap map) {
		return "card/card_list";
	}
	
	@RequestMapping("/card_add")
	public String cardAdd(ModelMap map) {
		
		return "card/card_add";
	}
	
	@RequestMapping("/evaluate_list")
	public String evaluateList(ModelMap map) {
		return "evaluate/evaluate_list";
	}
	
	@RequestMapping("/advertisement_add")
	public String advertisementAdd(ModelMap map) {
		return "ad/advertisement_add";
	}
	
	@RequestMapping("/system_set")
	public String systemSet(ModelMap map) {
		Map sumPostMap = orderService.queryFreepost();
		map.put("setId",sumPostMap.get("set_id"));
		map.put("freepost", sumPostMap.get("set_object"));
		return "sysset/system_set";
	}
	
	@RequestMapping("/password_set")
	public String passwordSet(ModelMap map) {
		return "sysset/password_set";
	}
}
