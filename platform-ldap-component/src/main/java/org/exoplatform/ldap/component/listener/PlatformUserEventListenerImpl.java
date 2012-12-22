package org.exoplatform.ldap.component.listener;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.organization.User;
import org.exoplatform.services.organization.UserEventListener;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.exception.MessageException;

/**
 * 
 * @author <a href="tungdt@exoplatform.com">Do Thanh Tung </a>
 */
public class PlatformUserEventListenerImpl extends UserEventListener {

  @Override
  public void preSave(User user, boolean isNew) throws Exception {
    RequestLifeCycle.begin(PortalContainer.getInstance());
    boolean readOnlyMode = System.getProperties().getProperty("ldap.read.only.mode").equals("true");
    if (readOnlyMode && isNew) {
      throw new MessageException(new ApplicationMessage("PlatformUserEventListener.msg.add-user-read-only-mode-error", null, 
          ApplicationMessage.WARNING));
    }
    if (readOnlyMode && !isNew) {
      throw new MessageException(new ApplicationMessage("PlatformUserEventListener.msg.update-user-read-only-mode-error", null,
          ApplicationMessage.WARNING));
    }
    RequestLifeCycle.end();
  }

  @Override
  public void preDelete(final User user) throws Exception {
    RequestLifeCycle.begin(PortalContainer.getInstance());
    boolean readOnlyMode = System.getProperties().getProperty("ldap.read.only.mode").equals("true");
    if (readOnlyMode) {
      throw new MessageException(new ApplicationMessage("PlatformUserEventListener.msg.delete-user-read-only-mode-error", null,
          ApplicationMessage.WARNING));
    }        
    RequestLifeCycle.end();
  }
}

