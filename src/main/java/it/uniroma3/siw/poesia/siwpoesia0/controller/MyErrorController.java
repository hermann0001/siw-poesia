package it.uniroma3.siw.poesia.siwpoesia0.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            HttpStatus statusCode = HttpStatus.valueOf(Integer.parseInt(status.toString()));

            if(statusCode.is4xxClientError()) {
                return "error";
            }
            else if(statusCode.is5xxServerError()) {
                return "error";
            }
        }
        return "error";
    }
}
