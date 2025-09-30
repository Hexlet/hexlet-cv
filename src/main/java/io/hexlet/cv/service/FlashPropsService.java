package io.hexlet.cv.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;

@Service
public class FlashPropsService {

    public Map<String, Object> buildProps(String locale, HttpServletRequest request) {
        Map<String, Object> props = new HashMap<>();
        props.put("locale", locale);

        var flash = RequestContextUtils.getInputFlashMap(request);
        if (flash != null && flash.get("errors") != null) {
            props.put("errors", flash.get("errors"));
        }

        return props;
    }
}
