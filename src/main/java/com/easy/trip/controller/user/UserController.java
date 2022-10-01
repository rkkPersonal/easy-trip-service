package com.easy.trip.controller.user;

import com.easy.trip.common.dto.ResultDTO;
import com.easy.trip.server.task.MessagePublish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Steven
 * @date 2022年10月02日 2:33
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private MessagePublish messagePublish;

    @GetMapping("/search/{userId}")
    public ResultDTO<String> searchUserInfo(@PathVariable("userId") Long userId, @RequestParam("message") String message) {
        log.info("userId:{},message:{}", userId, message);
        messagePublish.pushMsgToOne(userId.toString(), message);
        return ResultDTO.ok();
    }
}
