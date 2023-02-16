package shop.mtcoding.blog.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @DeleteMapping("/user/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) { // responsebody를 붙이면 데이터를 응답한다.
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        // db가 없어서 여기서 권한 검사를 할 수 가없다.
        userService.회원삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);

    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/user/profileUpdateForm")
    public String profileUpdateForm(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }
        User userPs = userRepository.findById(principal.getId());
        model.addAttribute("user", userPs);
        return "user/profileUpdateForm";
    }

    @PutMapping("/user/profileUpdate")
    public String profileUpdatePut(MultipartFile profile) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }

        if (profile.isEmpty()) {
            throw new CustomApiException("사진이 전송되지 않았습니다");
        }

        // 사진이 아니면 ex 터뜨리기

        User userPS = userService.프로필사진수정1(profile, principal.getId());
        session.setAttribute("principal", userPS);

        return "redirect:/";
    }

    @PostMapping("/user/profileUpdate")
    public String profileUpdate(MultipartFile profile) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/loginForm";
        }

        if (profile.isEmpty()) {
            throw new CustomException("사진이 전송되지 않았습니다");
        }

        // 사진이 아니면 ex 터뜨리기

        User userPS = userService.프로필사진수정(profile, principal.getId());
        session.setAttribute("principal", userPS);

        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto) {
        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }

        userService.회원가입(joinReqDto);
        return "redirect:/loginForm"; // 302
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto) {
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        User principal = userService.로그인(loginReqDto);
        // session.setAttribute("role", principal);
        session.setAttribute("principal", principal);

        if (principal.getRole().equals("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}