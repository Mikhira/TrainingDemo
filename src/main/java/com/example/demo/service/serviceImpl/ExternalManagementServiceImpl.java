package com.example.demo.service.serviceImpl;

import com.example.demo.service.ExternalManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalManagementServiceImpl implements ExternalManagementService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ExternalManagementServiceImpl.class.getSimpleName());

    @Value("${rest-endpoints.get.test-info}")
    private String urlToCall;

    @Override
    public void processExternalCallOfApiAndLogResult() {
        final RestTemplate restTemplate = new RestTemplate();
        final Object responseEntity = restTemplate.getForObject(urlToCall, Object.class);
        LOGGER.info(responseEntity.toString());
    }

}
