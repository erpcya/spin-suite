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
package org.erpya.pos;

import org.erpya.pos.wrapper.PointOfSalesWrapper;
import org.erpya.pos.wrapper.ProductPriceWrapper;
import org.spin.grpc.util.ClientRequest;
import org.spin.grpc.util.GetProductPriceRequest;
import org.spin.grpc.util.ListPointOfSalesRequest;
import org.spin.grpc.util.StoreGrpc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * AccessService class for login and enrollment
 */
public class PointOfSalesService {
    private static final PointOfSalesService instance = new PointOfSalesService();

    /**
     * Get default instance
     * @return
     */
    public static PointOfSalesService getInstance() {
        return instance;
    }

    /**
     * Set Backend parameters
     * @param host
     * @param port
     * @return
     */
    public PointOfSalesService withConnectionValues(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    private PointOfSalesService() {
        host = PointOfSalesProviderDefaultValues.HOST;
        port = PointOfSalesProviderDefaultValues.PORT;
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
    private StoreGrpc.StoreBlockingStub getServiceProvider() {
        return StoreGrpc.newBlockingStub(getConnectionProvider());
    }

    /**
     * Load default Point of sales, this method is load after run
     */
    public List<PointOfSalesWrapper> getPointOfSalesList(String sessionUuid, String userUuid) {
        ListPointOfSalesRequest request = ListPointOfSalesRequest.newBuilder()
                .setUserUuid(userUuid)
                .setClientRequest(ClientRequest.newBuilder()
                        .setSessionUuid(sessionUuid)
                        .setLanguage(language)
                        .build())
                .build();
        return getServiceProvider()
                .listPointOfSales(request)
                .getSellingPointsList()
                .stream()
                .map(pointOfSales -> PointOfSalesWrapper.createFrom(pointOfSales))
                .collect(Collectors.toList());
    }

    /**
     * Load default Point of sales, this method is load after run
     */
    public ProductPriceWrapper getProductPrice(String sessionUuid, String posUuid, String upc) {
        GetProductPriceRequest request = GetProductPriceRequest.newBuilder()
                .setPosUuid(posUuid)
                .setUpc(upc)
                .setClientRequest(ClientRequest.newBuilder()
                        .setSessionUuid(sessionUuid)
                        .setLanguage(language)
                        .build())
                .build();
        return ProductPriceWrapper.createFrom(getServiceProvider()
                .getProductPrice(request));
    }

    public String getProductPrice() {
        return null;
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
