package com.mj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomUtility.class)
public class StockOrderGeneratorTest {


    @Test
    public void Should_generate_single_order_type_buy() {
        mockStatic(RandomUtility.class);

        when(RandomUtility.nextInt(Mockito.anyInt())).thenReturn(1);
        when(RandomUtility.nextDouble()).thenReturn(0.9);
        StockOrder orderToTest = new StockOrderGenerator().generateSingleItem();

        assertEquals("C1001", orderToTest.getBuyer());

        //skipped C1001
        assertEquals("C1002", orderToTest.getSeller());
        assertEquals("XOM", orderToTest.getTickerSymbol() );
        assertEquals("BUY", orderToTest.getTradeType());
    }

    @Test
    public void Should_generate_single_order_type_sell() {
        mockStatic(RandomUtility.class);
        when(RandomUtility.nextInt(Mockito.anyInt())).thenReturn(1);
        when(RandomUtility.nextDouble()).thenReturn(0.1);

        StockOrder orderToTest = new StockOrderGenerator().generateSingleItem();
        System.out.println(orderToTest);
        assertEquals("C1002", orderToTest.getBuyer());

        //skipped C1001
        assertEquals("C1001", orderToTest.getSeller());
        assertEquals("XOM", orderToTest.getTickerSymbol());
        assertEquals("SELL", orderToTest.getTradeType());
    }

    @Test
    public void Should_generate_one_bundle_of_two_orders_with_different_ids() {
        mockStatic(RandomUtility.class);
        when(RandomUtility.nextInt(Mockito.anyInt())).thenReturn(3);

        OrderBundle<StockOrder> bundleToTest = new StockOrderGenerator().generateOrderBundle();

        assertEquals(2, bundleToTest.getOrderItems().size());
        assertEquals(2, bundleToTest.getOrderItems().stream().map(StockOrder::getOrderId).distinct().count());
    }
}