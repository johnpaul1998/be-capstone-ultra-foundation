package teamkakana.ultrafoundation.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/helloWorld")
public class HelloWorldController {

    @GetMapping("")
    public String getHelloWorldApi() {
        return "Hello World!";
    }

}