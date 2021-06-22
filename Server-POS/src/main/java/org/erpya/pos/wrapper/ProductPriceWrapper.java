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
package org.erpya.pos.wrapper;

import org.erpya.base.util.Env;
import org.erpya.pos.util.ServerValueUtil;
import org.spin.grpc.util.ProductPrice;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

/**
 * POS Wrapper
 */
public class ProductPriceWrapper {
    private String uuid;
    private String name;
    private String description;
    private String taxIndicator;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private String currencyCode;
    private BigDecimal price;
    private BigDecimal priceWithTax;
    private String displayCurrencyCode;
    private BigDecimal displayPrice;
    private BigDecimal displayPriceWithTax;

    /**
     * Default Create from
     * @param productPrice
     * @return
     */
    public static ProductPriceWrapper createFrom(ProductPrice productPrice) {
        ProductPriceWrapper wrapper = new ProductPriceWrapper();
        wrapper.uuid = productPrice.getProduct().getUuid();
        wrapper.name = productPrice.getProduct().getName();
        wrapper.description = productPrice.getProduct().getDescription();
        wrapper.taxIndicator = productPrice.getTaxRate().getName();
        wrapper.currencyCode = productPrice.getCurrency().getIsoCode();
        wrapper.displayCurrencyCode = productPrice.getDisplayCurrency().getIsoCode();
        //  Convert Decimal
        wrapper.price = Optional.ofNullable(ServerValueUtil.getBigDecimalFromDecimal(productPrice.getPriceStandard())).orElse(Env.ZERO);
        wrapper.displayPrice = Optional.ofNullable(ServerValueUtil.getBigDecimalFromDecimal(productPrice.getDisplayPriceStandard())).orElse(Env.ZERO);
        wrapper.taxRate = Optional.ofNullable(ServerValueUtil.getBigDecimalFromDecimal(productPrice.getTaxRate().getRate())).orElse(Env.ZERO);
        wrapper.taxAmount = getTaxAmount(wrapper.price, wrapper.taxRate);
        wrapper.priceWithTax = wrapper.price.add(getTaxAmount(wrapper.price, wrapper.taxRate));
        wrapper.displayPriceWithTax = wrapper.displayPrice.add(getTaxAmount(wrapper.displayPrice, wrapper.taxRate));
        return wrapper;
    }

    /**
     * Get Currency Rate based on display currency and price list currency
     * @return
     */
    public BigDecimal getCurrencyRate() {
        BigDecimal displayAmount = Optional.ofNullable(displayPrice).orElse(Env.ZERO);
        BigDecimal priceListAmount = Optional.ofNullable(price).orElse(Env.ZERO);
        if(displayAmount.compareTo(Env.ZERO) == 0 || priceListAmount.compareTo(Env.ZERO) == 0) {
            return Env.ZERO;
        }
        //  Calculate
        return displayAmount.max(priceListAmount).divide(displayAmount.min(priceListAmount), MathContext.DECIMAL128);
    }

    /**
     * Get Tax Amount
     * @param basePrice
     * @param taxAmount
     * @return
     */
    private static BigDecimal getTaxAmount(BigDecimal basePrice, BigDecimal taxAmount) {
        return (Optional.ofNullable(basePrice).orElse(Env.ZERO).multiply(Optional.ofNullable(taxAmount).orElse(Env.ZERO))).divide(Env.ONEHUNDRED);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTaxIndicator() {
        return taxIndicator;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getPriceWithTax() {
        return priceWithTax;
    }

    public String getDisplayCurrencyCode() {
        return displayCurrencyCode;
    }

    public BigDecimal getDisplayPrice() {
        return displayPrice;
    }

    public BigDecimal getDisplayPriceWithTax() {
        return displayPriceWithTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    @Override
    public String toString() {
        return "ProductPriceWrapper{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taxIndicator='" + taxIndicator + '\'' +
                ", taxRate=" + taxRate +
                ", taxAmount=" + taxAmount +
                ", currencyCode='" + currencyCode + '\'' +
                ", price=" + price +
                ", priceWithTax=" + priceWithTax +
                ", displayCurrencyCode='" + displayCurrencyCode + '\'' +
                ", displayPrice=" + displayPrice +
                ", displayPriceWithTax=" + displayPriceWithTax +
                '}';
    }
}
