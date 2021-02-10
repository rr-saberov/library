package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    Logger logger = Logger.getLogger(ErrorController.class);

    @GetMapping("/404")
    public String notFoundError() {
        return "errors/404";
    }
//
//    @GetMapping("/500")
//    public String serverError() {
//        logger.info("before 500 error");
//        return "errors/500";
//    }
}
