package io.hexlet.cv.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String path = request.getRequestURI();
                String[] parts = path.split("/");
                if (parts.length > 1) {
                    String lang = parts[1];
                    if ("ru".equalsIgnoreCase(lang)) {
                        return new Locale("ru");
                    }
                    if ("en".equalsIgnoreCase(lang)) {
                        return Locale.ENGLISH;
                    }
                }
                return Locale.ENGLISH; // default
            }

            @Override
            public void setLocale(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Locale locale) {
                // пусто, если не нужно динамически менять локаль
            }
        };
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasenames("messages/messages");
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(false);
        return ms;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource());
        return factory;
    }
}
