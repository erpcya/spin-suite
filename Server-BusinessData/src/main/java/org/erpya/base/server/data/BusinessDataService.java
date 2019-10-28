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
package org.erpya.base.server.data;

import org.spin.grpc.util.BusinessDataServiceGrpc;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * AccessService class for business data
 */
public class BusinessDataService {
    private static final BusinessDataService instance = new BusinessDataService();

    /**
     * Get default instance
     * @return
     */
    public static BusinessDataService getInstance() {
        return instance;
    }

    private BusinessDataService() {
        host = BusinessDataProviderDefaultValues.HOST;
        port = BusinessDataProviderDefaultValues.PORT;
        language = "en_US";
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
    private BusinessDataServiceGrpc.BusinessDataServiceBlockingStub getServiceProvider() {
        return BusinessDataServiceGrpc.newBlockingStub(getConnectionProvider());
    }

//    /**
//     * Make login with Role, Organization and Warehouse as default values
//     */
//    public Session requestLoginDefault(String userName, String userPass, String language) {
//        if(!Util.isEmpty(language)) {
//            this.language = language;
//        }
//        LoginRequest request = LoginRequest.newBuilder()
//                .setUserName(userName)
//                .setUserPass(userPass)
//                .setLanguage(this.language)
//                .setClientVersion(clientVersion)
//                .build();
//        return getServiceProvider().runLoginDefault(request);
//    }

    /**
     * Close Service Provider
     */
    public void closeServiceProvider() throws InterruptedException {
        if(connectionChannel == null) {
            connectionChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        }
    }

//    /**
//     * Request logout
//     * @param sessionUuid
//     * @return
//     */
//    public Session requestLogout(String sessionUuid) {
//        AccessServiceGrpc.AccessServiceBlockingStub accessService = AccessServiceGrpc.newBlockingStub(getConnectionProvider());
//        LogoutRequest request = LogoutRequest.newBuilder()
//                .setSessionUuid(sessionUuid)
//                .setLanguage(this.language)
//                .setClientVersion(clientVersion)
//                .build();
//        return getServiceProvider().runLogout(request);
//    }

    /** Host for access */
    private String host;
    /** Port for access */
    private int port;
    /** Language    */
    private String language;
    /** Client version  */
    private String clientVersion;
    /** connection  */
    private ManagedChannel connectionChannel;
}
