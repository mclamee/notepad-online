package com.wicky;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class NotepadOnlineApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NotepadOnlineApplication.class)
        		.bannerMode(Banner.Mode.OFF);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(NotepadOnlineApplication.class, args);
	}
}
