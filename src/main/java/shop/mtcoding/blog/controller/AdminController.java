package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;

@Controller
public class AdminController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);
        List<Reply> replyList = replyRepository.findAll();
        model.addAttribute("replyList", replyList);
        return "admin/main";
    }

    @PostMapping("/admin/login")
    public String login(LoginReqDto loginReqDto) {
        String role = (String) session.getAttribute("role");
        userService.로그인(loginReqDto);
        if ("ADMIN".equals(role)) {
            return "admin/main";
        }
        return "admin/loginForm";
    }

    @GetMapping("/admin/userManagement")
    public String userManagement(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "admin/userManagement";
    }

    @GetMapping("/admin/boardManagement")
    public String boardManagement(Model model) {
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);
        return "admin/boardManagement";
    }

    @GetMapping("/admin/replyManagement")
    public String replyManagement(Model model) {
        List<Reply> replyList = replyRepository.findAll();
        model.addAttribute("replyList", replyList);
        return "admin/replyManagement";
    }

}
