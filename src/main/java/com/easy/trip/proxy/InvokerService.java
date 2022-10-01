package com.easy.trip.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Steven
 * @date 2022年09月12日 5:43
 */
public class InvokerService implements InvocationHandler {

    private Object target;
    private Class<?> classInterfaces;

    public InvokerService(Object target) {
        this.target = target;
    }

    public InvokerService(Class<?> classInterfaces) {
        this.classInterfaces = classInterfaces;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke " + method.getName());
/*
        Method declaredMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        if (declaredMethod.isAnnotationPresent(Trace.class)) {
            System.out.println("Trace is working ......");
        }
        Object invoke = method.invoke(this.target, args);*/
        System.out.println("After invoke " + method.getName());
        return null;
    }

    public Object newProxy() {
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target
                .getClass().getInterfaces(), this::invoke);
    }

    public Object newProxyInstance() {
        return Proxy.newProxyInstance(classInterfaces.getClassLoader(), classInterfaces.getInterfaces(), this::invoke);
    }

}
