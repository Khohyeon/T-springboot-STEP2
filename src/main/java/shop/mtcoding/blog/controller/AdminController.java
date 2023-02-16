package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.BoardService;
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

    @Autowired
    private BoardService boardService;

    // @DeleteMapping("/admin/{id}")
    // public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) { //
    // responsebody를 붙이면 데이터를 응답한다.
    // User principal = (User) session.getAttribute("principal");
    // if (principal == null) {
    // throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
    // }
    // // db가 없어서 여기서 권한 검사를 할 수 가없다.
    // boardService.게시글삭제(id, principal.getId());
    // return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null),
    // HttpStatus.OK);

    // }

    @GetMapping("/admin")
    public String admin(LoginReqDto loginReqDto, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

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
        if (!"ADMIN".equals(role)) {
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
