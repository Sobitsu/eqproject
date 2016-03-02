package org.hc.wm.actives;

import java.util.List;

/**
 *
 * @author Dale
 */
public interface Actives {
    public ActiveType actype();
    public Double amount();
    public Double price();
    public List<OperHistory> operations();
    public void actype(ActiveType x);
    public void amount(Double x);
    public void price(Double x);
    public void operations(List<OperHistory> x);
}