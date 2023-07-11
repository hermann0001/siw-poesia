package it.uniroma3.siw.poesia.siwpoesia0.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            HttpStatus statusCode = HttpStatus.valueOf(Integer.parseInt(status.toString()));
            model.addAttribute("status", statusCode);

            if(statusCode.is4xxClientError()) {
                return "/errors/error404";
            }
            else if(statusCode.is5xxServerError()) {
                return "/errors/error500";
            }
        }
        return "/errors/error404";
    }
}
