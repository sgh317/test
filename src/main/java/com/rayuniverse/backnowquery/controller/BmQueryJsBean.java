package com.rayuniverse.backnowquery.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.rayuniverse.backnowquery.service.BmQueryReloadable;
import com.rayuniverse.framework.PlatformContext;
import com.rayuniverse.framework.jsrpc.JsBean;

@Controller
@JsBean("BmQueryJsBean")
public class BmQueryJsBean {

	private static Logger logger = LoggerFactory.getLogger(BmQueryJsBean.class);
	@Autowired
	BmQueryReloadable bmQueryReloadable;

	public Map BmQuery() {

		return (Map) this.bmQueryReloadable.getResource();
	}

}
