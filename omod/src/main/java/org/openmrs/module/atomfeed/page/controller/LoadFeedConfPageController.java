/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.atomfeed.page.controller;

import org.openmrs.module.atomfeed.api.FeedConfigurationService;
import org.openmrs.module.atomfeed.api.utils.AtomfeedUtils;
import org.openmrs.module.uicommons.UiCommonsConstants;
import org.openmrs.module.uicommons.util.InfoErrorMessageUtil;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoadFeedConfPageController {

	private static final String SAVE_CONFIG_ERROR = "atomfeed.configuration.json.save.fail";
	private static final String SAVE_CONFIG_SUCCESS = "atomfeed.configuration.json.save.success";


	protected static final Logger LOGGER = LoggerFactory.getLogger(LoadFeedConfPageController.class);

	public String get(PageModel model) {
		return "loadFeedConf";
	}
	
	public String post(PageModel model, @SpringBean("atomfeed.feedConfigurationService") FeedConfigurationService feedConfigurationService,
					   @RequestParam("json") String json, HttpSession session, UiUtils ui) {

		try {
			feedConfigurationService.saveConfig(json);
			InfoErrorMessageUtil.flashInfoMessage(session, ui.message(SAVE_CONFIG_SUCCESS));

			return "redirect:/atomfeed/atomfeed.page";
		} catch (Exception e) {
			LOGGER.warn("Error during save:", e);

			session.setAttribute(UiCommonsConstants.SESSION_ATTRIBUTE_ERROR_MESSAGE, ui.message(SAVE_CONFIG_ERROR));
		}

		return null;
	}
	
	@ResponseBody
	@RequestMapping("/atomfeed/verifyJson")
	public SimpleObject verifyJson(@RequestParam("json") String json) {
		SimpleObject result = new SimpleObject();
		try {
			AtomfeedUtils.isValidateJson(json);
			result.put("isValid", true);
		}
		catch (Exception e) {
			LOGGER.warn("Invalid json:", e);
			result.put("isValid", false);
		}
		
		return result;
	}
}
