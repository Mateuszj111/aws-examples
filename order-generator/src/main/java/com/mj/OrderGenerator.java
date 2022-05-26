package com.mj;

import java.util.List;

public interface OrderGenerator<T> {
    public T generateSingleItem();
    public OrderBundle<T> generateOrderBundle();
}
