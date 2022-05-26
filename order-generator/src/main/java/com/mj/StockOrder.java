package com.mj;


import java.util.UUID;

public class StockOrder {
    private String orderId;
    private String tickerSymbol;
    private double price;
    private String tradeType;
    private String buyer;
    private String seller;

    public static final class Builder {
        private String tickerSymbol;
        private double price;
        private String tradeType;
        private String buyer;
        private String seller;

        public Builder tickerSymbol(String tickerSymbol) {
            this.tickerSymbol = tickerSymbol;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder tradeType(String tradeType) {
            this.tradeType = tradeType;
            return this;
        }

        public Builder buyer(String buyer){
            this.buyer = buyer;
            return this;
        }

        public Builder seller(String seller){
            this.seller = seller;
            return this;
        }

        public StockOrder build() {
            StockOrder order = new StockOrder();
            order.orderId = UUID.randomUUID().toString();
            order.tickerSymbol = this.tickerSymbol;
            order.price = this.price;
            order.tradeType = this.tradeType;
            order.buyer = this.buyer;
            order.seller = this.seller;
            return order;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private StockOrder(){

    }

    public String getOrderId() {
        return orderId;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getSeller() {
        return seller;
    }

    public String getTradeType() {
        return tradeType;
    }

    @Override
    public String toString() {
        return String.format("StockOrder{orderId='%s', tickerSymbol='%s', tradeType='%s', price=%.2f, buyer='%s', seller='%s'}",
                orderId, tickerSymbol, tradeType, price, buyer, seller);
    }
}
