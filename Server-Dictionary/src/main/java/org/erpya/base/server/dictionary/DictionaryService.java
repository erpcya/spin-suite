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
package org.erpya.base.server.dictionary;

import org.erpya.base.dictionary.WindowInfo;
import org.erpya.base.server.BuildConfig;
import org.erpya.base.util.Env;
import org.spin.grpc.util.ApplicationRequest;
import org.spin.grpc.util.DictionaryGrpc;
import org.spin.grpc.util.EntityRequest;
import org.spin.grpc.util.Tab;
import org.spin.grpc.util.Window;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * AccessService class for business data
 */
public class DictionaryService {
    private static final DictionaryService instance = new DictionaryService();

    /**
     * Get default instance
     * @return
     */
    public static DictionaryService getInstance() {
        return instance;
    }

    private DictionaryService() {
        host = DictionaryProviderDefaultValues.HOST;
        port = DictionaryProviderDefaultValues.PORT;
        language = "en_US";
        clientVersion = "";
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
    private DictionaryGrpc.DictionaryBlockingStub getServiceProvider() {
        return DictionaryGrpc.newBlockingStub(getConnectionProvider());
    }

    /**
     * GEt a Window information with defined window
     * a520de12-fb40-11e8-a479-7a0060f0aa01
     * @param uuid
     * @param withTabs
     * @return
     */
    public WindowInfo getWindow(String uuid, boolean withTabs) {
        ApplicationRequest applicationRequest = ApplicationRequest.newBuilder()
                .setLanguage(language)
                .build();
        EntityRequest request = EntityRequest.newBuilder()
                .setUuid(uuid)
                .setApplicationRequest(applicationRequest)
                .build();
        WindowInfo windowInfo = null;
        try {
            Window response;
            if(withTabs) {
                response = getServiceProvider().getWindow(request);
                for(Tab tab : response.getTabsList()) {

                }
            } else {
                response = getServiceProvider().getWindow(request);
            }
            windowInfo = convertWindow(response);
        } catch (StatusRuntimeException e) {
            return null;
        }
        return windowInfo;
    }

    /**
     * Convert Object from gRPC to base Window
     * @param window
     * @return
     */
    private WindowInfo convertWindow(Window window) {
        WindowInfo windowInfo = new WindowInfo(Env.getContext());
        if(window == null) {
            return windowInfo;
        }
        windowInfo.reloadFromUuid(window.getUuid());
        windowInfo.setValue("Id", window.getId());
        windowInfo.setValue("Name", window.getName());
        windowInfo.setValue("Description", window.getDescription());
        windowInfo.setValue("Help", window.getHelp());
        windowInfo.setValue("IsActive", window.getIsActive());
        windowInfo.setValue("IsSOTrx", window.getIsSalesTransaction());
        windowInfo.setValue("WindowType", window.getWindowType());
        return windowInfo;
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
