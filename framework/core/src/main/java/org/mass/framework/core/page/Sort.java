package org.mass.framework.core.page;

/**
 * Created by Allen on 2015/4/27.
 */
public class Sort {

    private String field;

    private Order order;

    public enum Order {
        ASC,DESC
    }

    public Sort() {}

    public Sort(String field, Order order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Order getOrder() {
        return order;
    }

    public void setDirection(Order direction) {
        this.order = order;
    }

//    public org.hibernate.criterion.Order getHibernateOrder() {
//        if (this.order != null && this.order.equals(Order.DESC)) {
//            return org.hibernate.criterion.Order.desc(field);
//        } else {
//            return org.hibernate.criterion.Order.asc(field);
//        }
//    }
//
//    public static List<Sort> parse(Map<String,String> map) {
//        List<Sort> sorts = Lists.newArrayList();
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            String field = entry.getKey();
//            String order = entry.getValue();
//            sorts.add(new Sort(field,Order.valueOf(order.toUpperCase())));
//        }
//        return sorts;
//    }
}
