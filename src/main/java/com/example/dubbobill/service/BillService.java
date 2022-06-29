package com.example.dubbobill.service;


public interface BillService {
    int killgoods(Long id,int goodsPrice);
    int addmoney(Long id, int money);
}
