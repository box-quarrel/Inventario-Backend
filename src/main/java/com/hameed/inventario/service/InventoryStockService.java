package com.hameed.inventario.service;


public interface InventoryStockService {

    public void increaseStock(Long productId, int quantity);

    public void decreaseStock(Long productId, int quantity);

    public boolean checkStock(Long productId, int quantity);
}
