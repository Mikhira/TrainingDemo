package com.example.demo.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FileInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FileInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        doInterception((StandardMultipartHttpServletRequest) request);
        return true;
    }

    private void doInterception(StandardMultipartHttpServletRequest request) throws IOException {
        final MultipartFile interceptedFile = readFileFromRequest(request);
        final String fileName = interceptedFile.getOriginalFilename();
        final String contentType = interceptedFile.getContentType();
        final Long size = interceptedFile.getSize() / 1000;
        final byte[] content = interceptedFile.getBytes();
        LOGGER.warn("File {} was intercepted. Content type: {}, size: {} (kb), Byte content: {}",
                fileName, contentType, size, content);
    }

    private MultipartFile readFileFromRequest(StandardMultipartHttpServletRequest request) {
        return request.getMultiFileMap().get("file").get(0);
    }


}
