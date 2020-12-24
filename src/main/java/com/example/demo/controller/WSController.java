package com.example.demo.controller;

import com.example.demo.config.WebSocketServer;
import com.example.demo.model.ResponseMessage;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @ClassName WSController
 * @Description
 * @PackageName com.example.demo.controller.WSController
 * @Author 杨登柳
 * @Date 2020/12/24  9:51
 **/
@Controller
public class WSController {

    @GetMapping("index")
    @ResponseBody
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }

    @GetMapping("page")
    public ModelAndView page(){
        return new ModelAndView("websocket");
    }

    @RequestMapping("/push/{toUserId}")
    @ResponseBody
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    @GetMapping("/test")
    public String test() {
        return "websocket.html";
    }
}