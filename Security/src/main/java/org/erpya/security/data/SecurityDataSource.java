/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.erpya.security.data;
import org.erpya.base.access.AccessService;
import org.erpya.base.access.EnrollmentService;
import org.erpya.security.data.model.RegisteredUser;
import org.erpya.security.data.model.RoleInfo;
import org.erpya.security.data.model.SessionInfo;
import org.erpya.security.data.model.UserInfo;
import org.spin.grpc.util.ResetPasswordResponse;
import org.spin.grpc.util.Session;
import org.spin.grpc.util.User;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class SecurityDataSource {

    /**
     * Login user
     * @param username
     * @param password
     * @return
     */
    public Result<SessionInfo> login(String username, String password) {
        try {
            Session session = AccessService.getInstance().requestLoginDefault(username, password, null);
            if(session != null) {
                SessionInfo sessionInfo = SessionInfo.getInstance()
                        .setSessionUuid(session.getUuid())
                        .setSessionName(session.getName())
                        .setSessionId(session.getId());
                org.spin.grpc.util.UserInfo user = session.getUserInfo();
                if(user != null) {
                    sessionInfo.setUserInfo(new UserInfo(username, session.getUserInfo().getName(), null, session.getUserInfo().getDescription(), session.getUserInfo().getComments()));
                }
                org.spin.grpc.util.Role role = session.getRole();
                if(role != null) {
                    sessionInfo.setRoleInfo(new RoleInfo(role.getId(), role.getUuid(), role.getName(), role.getDescription(), role.getClientName(), role.getClientId()));
                }
                AccessService.getInstance().closeServiceProvider();
                return new Result.Success<>(sessionInfo.setIsLogged(true));
            }
            throw new Exception("User / Password");
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    /**
     * Enroll new user
     * @param name
     * @param username
     * @param email
     * @return
     */
    public Result<RegisteredUser> enroll(String name, String username, String email) {
        try {
            User user = EnrollmentService.getInstance().enrollUser(name, username, email);
            if(user != null) {
                RegisteredUser fakeUser =
                        new RegisteredUser(user.getName(), user.getLastName(), user.getUserName(), user.getEMail(), user.getToken());
                EnrollmentService.getInstance().closeServiceProvider();
                return new Result.Success<>(fakeUser);
            }
            throw new Exception("User / EMail");
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    /**
     * Request reset password
     * @param userName
     * @param email
     */
    public void requestResetPassword(String userName, String email) {
        //  TODO: Implement it
    }

    /**
     * Reset password
     * @param password
     * @param token
     */
    public Result<RegisteredUser> resetPassword(String userName, String password, String token) {
        try {
            ResetPasswordResponse response = EnrollmentService.getInstance().resetPasswordFromToken(token, password);
            if(response != null) {
                Result<SessionInfo> result = login(userName, password);
                if(!(result instanceof Result.Success)) {
                    throw new Exception(result.toString());
                }
                RegisteredUser fakeUser =
                        new RegisteredUser(SessionInfo.getInstance().getUserInfo().getDisplayName(),
                                null, SessionInfo.getInstance().getUserInfo().getUserName(),
                                SessionInfo.getInstance().getUserInfo().getEMail(),
                                SessionInfo.getInstance().getSessionUuid());
                EnrollmentService.getInstance().closeServiceProvider();
                return new Result.Success<>(fakeUser);
            }
            throw new Exception("User / EMail");
        } catch (Exception e) {
            return new Result.Error(e);
        }
    }

    /**
     * Logout session
     * @param token
     */
    public Result<RegisteredUser> logout(String token) {
        //  TODO: Implement it
        return null;
    }
}
