package com.zendaimoney.thirdpp.route.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUil<E> {
    
    public static final String SORT_DESC = "desc";
    public static final String SORT_ASC = "asc";

    @SuppressWarnings("unchecked")
    public void Sort(List<E> list, final String method, final String sort) {
        Collections.sort(list, new Comparator<E>() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    if (sort != null) {
                        Method m1 = ((E) a).getClass().getMethod(method);
                        Method m2 = ((E) b).getClass().getMethod(method);
                        if ("desc".equals(sort)) {//倒序  
                            ret = m2.invoke(((E) b)).toString()
                                .compareTo(m1.invoke(((E) a)).toString());
                        } else if ("asc".equals(sort)) {//正序  

                            ret = m1.invoke(((E) a)).toString()
                                .compareTo(m2.invoke(((E) b)).toString());
                        }
                    }
                } catch (Exception ne) {
                    System.out.println(ne);
                }
                return ret;
            }
        });
    }
    
    
    @SuppressWarnings("unchecked")
    public void Sort_U(List<E> list, final String field, final String sort) {
        Collections.sort(list, new Comparator<E>() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    if (sort != null) {
                        Field f1 = ((E) a).getClass().getDeclaredField(field);
                        Field f2 = ((E) b).getClass().getDeclaredField(field);
                        
                        f1.setAccessible(true); //设置些属性是可以访问的
                        f2.setAccessible(true); //设置些属性是可以访问的
                        
                        Object val1 = f1.get(a);
                        Object val2 = f2.get(b);
                        
                        if ("desc".equals(sort)) {//倒序  
                            ret = val2.toString().compareTo(val1.toString());
                        } else if ("asc".equals(sort)) {//正序  

                            ret = val1.toString().compareTo(val2.toString());
                        }
                        
                        f1.setAccessible(false); //设置些属性是不可以访问的
                        f2.setAccessible(false); //设置些属性是不可以访问的
                    }
                } catch (Exception ne) {
                    System.out.println(ne);
                }
                return ret;
            }
        });
    }
}