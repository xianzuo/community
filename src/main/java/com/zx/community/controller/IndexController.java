package com.zx.community.controller;

import com.zx.community.dto.PaginationDTO;
import com.zx.community.dto.QuestionDTO;
import com.zx.community.exception.CustomizeErrorCode;
import com.zx.community.exception.CustomizeException;
import com.zx.community.mapper.UserMapper;
import com.zx.community.model.Question;
import com.zx.community.model.User;
import com.zx.community.service.QuestionService;
import com.zx.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @GetMapping(value = {"/","/index"})
    public String index(HttpServletResponse response,
                        HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "8") Integer size){

        PaginationDTO pagination=questionService.list(page,size);
        if(pagination==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
