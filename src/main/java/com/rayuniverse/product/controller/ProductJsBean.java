package com.rayuniverse.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.rayuniverse.framework.jsrpc.JsBean;

@Controller
@JsBean("ProductJsBean")
public class ProductJsBean {

	private static Logger logger = LoggerFactory.getLogger(ProductJsBean.class);

}
