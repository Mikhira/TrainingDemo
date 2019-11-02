package com.example.demo.controller;

import com.example.demo.service.ExternalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class ExternalController {

    @Autowired
    private ExternalManagementService externalManagementService;

    @GetMapping("call/api/to/get/json")
    private void callExternalAPI(){
       externalManagementService.processExternalCallOfApiAndLogResult();
    }

}
