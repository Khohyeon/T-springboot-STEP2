package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.util.Script;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private HttpSession session;

    @GetMapping({ "/", "/board" })
    public String main(Model model) {
        User principal = (User) session.getAttribute("principal");
        model.addAttribute("principal", principal);
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);
        return "board/main";
    }

    @PostMapping("/board/list")
    @ResponseBody
    public String board(String title, String content) {
        int result = boardRepository.insert(title, content);
        if (result != 1) {
            return Script.back("글쓰기 실패 다시 확인해주세요.");
        }
        return Script.href("글이 등록되었습니다.", "/board");
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model) {
        // User principal = (User) session.getAttribute("principal");
        // if (principal.getId() == id) {
        // return Script.href("권한이없습니다 로그인해주세요.", "/loginForm");
        // }
        Board board = boardRepository.findById(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        return "board/updateForm";
    }
}
