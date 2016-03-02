/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hc.wm.actives.stock;

import java.util.List;
import org.hc.wm.actives.ActiveType;
import org.hc.wm.actives.Actives;
import org.hc.wm.actives.OperHistory;

/**
 *
 * @author Dale
 */
public class Stock implements Actives {
    private ActiveType actype;
    private Double amount;
    private Double price;
    private List<OperHistory> operations;
    
    public Stock() {
    }
    
    @Override
    public ActiveType actype() {
        return actype;
    }

    @Override
    public Double amount() {
        return amount;
    }

    @Override
    public Double price() {
        return price;
    }

    @Override
    public List<OperHistory> operations() {
        return operations;
    }

    @Override
    public void actype(ActiveType x) {
        actype = x;
    }

    @Override
    public void amount(Double x) {
        amount = x;
    }

    @Override
    public void price(Double x) {
        price = x;
    }

    @Override
    public void operations(List<OperHistory> x) {
        operations = x;
    }
}
