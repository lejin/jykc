package org.jesusyouth.jykc.jykcadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class JykcadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(JykcadminApplication.class, args);
  }
}

