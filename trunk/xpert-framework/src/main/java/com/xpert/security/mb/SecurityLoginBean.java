package com.xpert.security.mb;

import com.xpert.faces.utils.FacesUtils;
import com.xpert.security.model.User;
import com.xpert.security.session.AbstractUserSession;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ayslan
 */
public abstract class SecurityLoginBean {

    private String userLogin;
    private String userPassword;

    public abstract User getUser(String login, String password);

    public abstract AbstractUserSession getUserSession();

    /**
     * Define something when login is successful like add a message "Welcome
     * user"
     *
     * @return
     */
    public void onSucess() {
    }

    /**
     * Define something when login is unsuccessful
     *
     * @return
     */
    public void onError() {
    }

    /**
     * Redirect to this page when login is successful
     *
     * @return
     */
    public abstract String getRedirectPageWhenSucess();

    /**
     * Message when user was not found.
     *
     * @return
     */
    public String getUserNotFoundMessage() {
        return "User not found";
    }

    /**
     * Message when user is not active not found.
     *
     * @return
     */
    public String getInactiveUserMessage() {
        return "Inactive User";
    }

    /**
     * Message when there is no role.
     *
     * @return
     */
    public String getNoRolesFoundMessage() {
        return "No roles found for this user";
    }

    public boolean validate() {
        boolean valid = true;
        if (userLogin == null || userLogin.trim().isEmpty()) {
            addErrorMessage("User is required");
            valid = false;
        }
        if (userPassword == null || userPassword.trim().isEmpty()) {
            addErrorMessage("Password is required");
            valid = false;
        }
        return valid;
    }

    public void login() {

        //clear user session
        if (getUserSession() != null) {
            getUserSession().setUser(null);
        }

        if (validate()) {
            User user = getUser(userLogin, userPassword);

            if (user == null) {
                addErrorMessage(getUserNotFoundMessage());
                onError();
                return;
            }
            if (!user.isActive()) {
                addErrorMessage(getInactiveUserMessage());
                onError();
                return;
            }

            //set user in session
            if (getUserSession() != null) {
                getUserSession().setUser(user);
                getUserSession().createSession();
                //when no roles found, login is not sucessful
                if (getUserSession().getRoles() == null || getUserSession().getRoles().isEmpty()) {
                    addErrorMessage(getNoRolesFoundMessage());
                    getUserSession().setUser(null);
                    return;
                }
            }
            onSucess();
            FacesUtils.redirect(getRedirectPageWhenSucess());
        }

    }

    private void addErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
