/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hc.wm.actives;

/**
 *
 * @author Dale
 */
public interface Actives {
    public ActiveType actype();
    public Double amount();
    public Double price();
    public OperHistory operations();
    public void actype(ActiveType x);
    public void amount(Double x);
    public void price(Double x);
    public void operations(OperHistory x);
}
