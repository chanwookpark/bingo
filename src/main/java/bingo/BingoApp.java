package bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chanwook
 */
@SpringBootApplication
@Controller
public class BingoApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BingoApp.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BingoApp.class, args);
    }

    @RequestMapping("/")
    String index() {
        return "redirect:/entry.v";
    }

}