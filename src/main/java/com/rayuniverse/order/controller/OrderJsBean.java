package com.rayuniverse.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.jsrpc.JsBean;

@Controller
@JsBean("OrderJsBean")
public class OrderJsBean {

	private static Logger logger = LoggerFactory.getLogger(OrderJsBean.class);

}
