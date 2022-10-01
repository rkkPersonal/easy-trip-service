package com.easy.trip.architect;

import com.easy.trip.proxy.InvokerService;
import com.easy.trip.proxy.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Steven
 * @date 2022年09月12日 6:29
 */
public class TisFramework {


   static Map<String,Object> beanMap=new ConcurrentHashMap<>();

    static {
    /*    Object obj= new InvokerService(new UserServiceImpl()).newProxy();
        beanMap.put(UserServiceImpl.class.getName(),obj);*/
        Object obj2= new InvokerService(Service.class).newProxyInstance();
        beanMap.put(Service.class.getName(),obj2);
    }

    public static void main(String[] args) {
       /* UserService userService =(UserService) beanMap.get(UserServiceImpl.class.getName());
        System.out.println(userService.listUsers());*/

        Service service =(Service) beanMap.get(Service.class.getName());
        System.out.println(service.hello());

    }
}
