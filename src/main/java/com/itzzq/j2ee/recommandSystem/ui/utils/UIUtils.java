package com.itzzq.j2ee.recommandSystem.ui.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 * Helper util to assist in user interface
 * 
 * @author itzzq
 */
public class UIUtils implements Serializable {
	private static final long serialVersionUID = 7872083365595569634L;

	private int viewLoadCount = 0;

	public void greetOnViewLoad(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (viewLoadCount < 1 && !context.isPostback()) {
			String userName = (String) event.getComponent().getAttributes()
					.get("userName");

			FacesMessage message = new FacesMessage(String.format(
					"欢迎使用推荐系统， %s", userName));
			context.addMessage("growlMessages", message);

			viewLoadCount++;
		}
	}
}