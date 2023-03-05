package fr.uge.jee.ugeoverflow.config;

import fr.uge.jee.ugeoverflow.converter.StringToRoleConverter;
import fr.uge.jee.ugeoverflow.converter.StringToSetOfUserConverter;
import fr.uge.jee.ugeoverflow.converter.StringToUserConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*@Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(new StringToUserConverter());
        //registry.addConverter(new StringToRoleConverter());
        //registry.addConverter(new StringToSetOfUserConverter());
    }*/

}
