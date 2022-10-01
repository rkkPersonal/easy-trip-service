package com.easy.trip.proxy;

import com.easy.trip.annotation.Trace;

/**
 * @author Steven
 * @date 2022年09月12日 5:51
 */
public class UserServiceImpl implements UserService {

    @Trace
    @Override
    public String listUsers() {
        return "list users information";
    }
}
