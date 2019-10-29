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
package org.erpya.base.access;

import org.spin.grpc.util.EnrollUserRequest;
import org.spin.grpc.util.EnrollmentServiceGrpc;
import org.spin.grpc.util.ResetPasswordRequest;
import org.spin.grpc.util.ResetPasswordResponse;
import org.spin.grpc.util.ResetPasswordTokenRequest;
import org.spin.grpc.util.User;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * AccessService class for login and enrollment
 */
public class EnrollmentService {
    private static final EnrollmentService instance = new EnrollmentService();

    /**
     * Get default instance
     * @return
     */
    public static EnrollmentService getInstance() {
        return instance;
    }

    private EnrollmentService() {
        host = AccessProviderDefaultValues.ENROLLMENT_HOST;
        port = AccessProviderDefaultValues.ENROLLMENT_PORT;
        clientVersion = BuildConfig.VERSION_CODE + " - " + BuildConfig.VERSION_NAME;
    }

    /**
     * Get connection provider for gRPC
     * @return
     */
    private ManagedChannel getConnectionProvider() {
        if(connectionChannel == null) {
            connectionChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        }
        return connectionChannel;
    }

    /**
     * Get Service Provider
     * @return
     */
    private EnrollmentServiceGrpc.EnrollmentServiceBlockingStub getServiceProvider() {
        return EnrollmentServiceGrpc.newBlockingStub(getConnectionProvider());
    }

    /**
     * Enroll a new user
     * @param name
     * @param userName
     * @param email
     * @return
     */
    public User enrollUser(String name, String userName, String email) {
        EnrollUserRequest request = EnrollUserRequest.newBuilder()
                .setName(name)
                .setUserName(userName)
                .setEMail(email)
                .build();
        return getServiceProvider().enrollUser(request);
    }

    /**
     * Close Service Provider
     */
    public void closeServiceProvider() throws InterruptedException {
        if(connectionChannel != null) {
            connectionChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            connectionChannel = null;
        }
    }

    /**
     * Reset password
     * @param userName
     * @param email
     * @return
     */
    public ResetPasswordResponse requestResetPassword(String userName, String email) {
        ResetPasswordRequest request = ResetPasswordRequest.newBuilder()
                .setUserName(userName)
                .setEMail(email)
                .build();
        return getServiceProvider().resetPassword(request);
    }

    /**
     * Reset password from token
     * @param token
     * @param password
     * @return
     */
    public ResetPasswordResponse resetPasswordFromToken(String token, String password) {
        ResetPasswordTokenRequest request = ResetPasswordTokenRequest.newBuilder()
                .setToken(token)
                .setPassword(password)
                .build();
        return getServiceProvider().resetPasswordFromToken(request);
    }

    /** Host for enroll */
    private String host;
    /** Port for enroll */
    private int port;
    /** Client version  */
    private String clientVersion;
    /** connection  */
    private ManagedChannel connectionChannel;
}
