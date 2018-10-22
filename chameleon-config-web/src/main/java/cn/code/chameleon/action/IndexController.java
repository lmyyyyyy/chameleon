package cn.code.chameleon.action;

import cn.code.chameleon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liumingyu
 * @create 2018-05-30 下午3:08
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        return "register";
    }

    @GetMapping("/dashboard")
    public String home(Model model) throws Exception {
        return "dashboard";
    }
}
