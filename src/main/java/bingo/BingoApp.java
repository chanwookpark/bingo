package bingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chanwook
 */
@SpringBootApplication
@Controller
public class BingoApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BingoApp.class, args);
    }

    @RequestMapping("/")
    String index() {
        return "redirect:/entry";
    }

}