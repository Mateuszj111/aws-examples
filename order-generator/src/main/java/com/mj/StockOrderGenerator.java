package com.mj;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StockOrderGenerator implements OrderGenerator<StockOrder> {

    private final String trader;
    private final List<String> availableParties;

    /** The ratio of the deviation from the mean price **/
    private static final double MAX_DEVIATION = 0.2;

    /** Probability of trade being a sell **/
    private static final double PROBABILITY_SELL = 0.4; // ie 40%


    private static class StockPrice {
        String tickerSymbol;
        double price;

        StockPrice(String tickerSymbol, double price) {
            this.tickerSymbol = tickerSymbol;
            this.price = price;
        }
    }

    private static final List<StockPrice> STOCK_PRICES = new ArrayList<>();
    static {
        STOCK_PRICES.add(new StockPrice("AAPL", 119.72));
        STOCK_PRICES.add(new StockPrice("XOM", 91.56));
        STOCK_PRICES.add(new StockPrice("GOOG", 527.83));
        STOCK_PRICES.add(new StockPrice("BRK.A", 223999.88));
        STOCK_PRICES.add(new StockPrice("MSFT", 42.36));
        STOCK_PRICES.add(new StockPrice("WFC", 54.21));
        STOCK_PRICES.add(new StockPrice("JNJ", 99.78));
        STOCK_PRICES.add(new StockPrice("WMT", 85.91));
        STOCK_PRICES.add(new StockPrice("CHL", 66.96));
        STOCK_PRICES.add(new StockPrice("GE", 24.64));
        STOCK_PRICES.add(new StockPrice("NVS", 102.46));
        STOCK_PRICES.add(new StockPrice("PG", 85.05));
        STOCK_PRICES.add(new StockPrice("JPM", 57.82));
        STOCK_PRICES.add(new StockPrice("RDS.A", 66.72));
        STOCK_PRICES.add(new StockPrice("CVX", 110.43));
        STOCK_PRICES.add(new StockPrice("PFE", 33.07));
        STOCK_PRICES.add(new StockPrice("FB", 74.44));
        STOCK_PRICES.add(new StockPrice("VZ", 49.09));
        STOCK_PRICES.add(new StockPrice("PTR", 111.08));
        STOCK_PRICES.add(new StockPrice("BUD", 120.39));
        STOCK_PRICES.add(new StockPrice("ORCL", 43.40));
        STOCK_PRICES.add(new StockPrice("KO", 41.23));
        STOCK_PRICES.add(new StockPrice("T", 34.64));
        STOCK_PRICES.add(new StockPrice("DIS", 101.73));
        STOCK_PRICES.add(new StockPrice("AMZN", 370.56));
    }

    public enum TradeType {
        BUY,
        SELL
    }

    public StockOrderGenerator() {

        List<String> buyerIDs = List.of(
                "C1000", "C1001", "C1002", "C1003", "C1004", "C1005", "C1006", "C1007", "C1008", "C1009",
                "C1010", "C1011", "C1012", "C1013", "C1014", "C1015", "C1016", "C1017", "C1018", "C1019"
        );
        this.trader = buyerIDs.get(RandomUtility.nextInt(buyerIDs.size()));
        this.availableParties = buyerIDs.stream().filter(x -> !x.equals(this.trader)).collect(Collectors.toList());
    }

    public String getTrader() {
        return trader;
    }

    @Override
    public StockOrder generateSingleItem(){
        StockPrice stockPrice = STOCK_PRICES.get(RandomUtility.nextInt(STOCK_PRICES.size()));

        double deviation = (RandomUtility.nextDouble() - 0.5) * 2.0 * MAX_DEVIATION;
        double price = Math.round(stockPrice.price * (1 + deviation) * 100.0) / 100.0;

        String tradeParty = availableParties.get(RandomUtility.nextInt(availableParties.size()));

        if (RandomUtility.nextDouble() > PROBABILITY_SELL){
            return StockOrder.builder().price(price).tickerSymbol(stockPrice.tickerSymbol).buyer(trader).seller(tradeParty).tradeType(TradeType.BUY.toString()).build();
        }else{
            return StockOrder.builder().price(price).tickerSymbol(stockPrice.tickerSymbol).buyer(tradeParty).seller(trader).tradeType(TradeType.SELL.toString()).build();
        }

    }

    @Override
    public OrderBundle<StockOrder> generateOrderBundle() {
        int nStocks = RandomUtility.nextInt(STOCK_PRICES.size());
        List<StockOrder> stocks = IntStream.range(1, nStocks).mapToObj(x -> this.generateSingleItem()).collect(Collectors.toList());

        return new OrderBundle<>(stocks, this.trader);
    }
}
