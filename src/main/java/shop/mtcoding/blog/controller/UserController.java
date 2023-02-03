package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
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
        if (username == null || username.isEmpty()) {
            return Script.back("username 입력해주세요.");
        }
        if (password == null || password.isEmpty()) {
            return Script.back("password를 입력해주세요.");
        }
        if (pincipal == null) {
            return Script.back("username 이나 password를 잘못 입력하셨습니다.");
        }
        session.setAttribute("pincipal", pincipal);
        return Script.href("/");
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

    @GetMapping("/user/usernameSameCheck")
    public @ResponseBody ResponseDto<?> check(String username) {
        if (username == null || username.isEmpty()) {
            return new ResponseDto<>(-1, "username 쿼리스트링을 전달해주세요..", null);
        }
        if (username.equals("ssar")) { // db에 있음
            return new ResponseDto<>(1, "동일한 username이 존재합니다.", false);
        } else {
            return new ResponseDto<>(1, "해당 username으로 회원가입이 가능합니다.", true);
        }
    }
}
