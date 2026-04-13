package com.group.InternMap.ResponseError;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.web.servlet.error.ErrorController;

@Controller
public class Error implements ErrorController {

    ErrorAttributes errorAttributes;
    ErrorResponse errorResponse;

//    @Autowired
//    public Error(ErrorResponse errorResponse) {
//        this.errorResponse = errorResponse;
//    }

    @GetMapping("/error")
    public String error(ErrorResponse errorResponse, Model model) {
        model.addAttribute("error" , errorResponse.getStatusCode());
        return "error";
    }
}
