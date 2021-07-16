package com.dts.intechweb.controllers;

import com.dts.intechweb.model.ChatMessage;
import com.dts.intechweb.services.ChatMessageService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {
    private final ChatMessageService chatMessageService;




    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @SendTo("/topic/public")
    @MessageMapping("${chat.app.send-message-mapping}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessageService.saveMessage(chatMessage);
        return chatMessage;
    }


    @SendTo("/topic/public")
    @MessageMapping("${chat.app.add-user-mapping}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage) {
        chatMessageService.saveMessage(chatMessage);
        return chatMessage;
    }

    @RequestMapping({"/","/index","/index.html","/chat"})
    public String index(Model model,
                        @Value("${chat.app.ws-endpoint-path}") String wsEndpointPath,
                        @Value("${chat.app.topic.public}") String topicPublic,
                        @Value("${chat.app.application-destination-prefix}") String applicationDestinationPrefix,
                        @Value("${chat.app.add-user-mapping}") String addUserMapping,
                        @Value("${chat.app.send-message-mapping}") String sendMessageMapping) {
        model.addAttribute("wsEndpointPath", wsEndpointPath.toCharArray());
        model.addAttribute("topicPublic", topicPublic);
        model.addAttribute("addUserMapping", applicationDestinationPrefix + addUserMapping);
        model.addAttribute("sendMessageMapping", applicationDestinationPrefix + sendMessageMapping);
        return "chat";
    }
}
