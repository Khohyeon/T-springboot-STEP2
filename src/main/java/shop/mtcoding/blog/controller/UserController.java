package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.util.Script;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(String username, String password, String email) {
        int result = userRepository.insert(username, password, email);
        if (result != 1) {
            return Script.back("회원가입 실패 다시 해주세요");
        }
        return Script.href("/loginForm");

    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String username, String password, Model model) {
        User pincipal = userRepository.findByUsernameAndPassword(username, password);
        if (pincipal == null) {
            return Script.back("username 이나 password를 잘못 입력하셨습니다.");
        }
        session.setAttribute("pincipal", pincipal);
        return Script.href("/board");
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout() {
        session.invalidate();
        return Script.href("로그아웃이 되었습니다.", "/");
    }
}
