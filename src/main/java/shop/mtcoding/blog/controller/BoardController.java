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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateRespDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardLikeCountRespDto;
import shop.mtcoding.blog.dto.like.LikeReq.LikeReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.Like;
import shop.mtcoding.blog.model.LikeRepository;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/searchForm")
    public String search(String title, Model model) {
        List<Board> searchList = boardService.search(title);
        model.addAttribute("likeList", likeRepository.findAll());
        model.addAttribute("boardList", boardRepository.findAll());
        model.addAttribute("searchList", searchList);

        return "board/searchForm";
    }

    @DeleteMapping("/board/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) { // responsebody를 붙이면 데이터를 응답한다.
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        // db가 없어서 여기서 권한 검사를 할 수 가없다.
        boardService.게시글삭제(id, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);

    }

    @PostMapping("/board")
    public @ResponseBody ResponseEntity<?> save(@RequestBody BoardSaveReqDto BoardSaveReqDto) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        if (BoardSaveReqDto.getTitle() == null || BoardSaveReqDto.getTitle().isEmpty()) {
            throw new CustomApiException("title을 작성해주세요");
        }
        if (BoardSaveReqDto.getContent() == null || BoardSaveReqDto.getContent().isEmpty()) {
            throw new CustomApiException("content를 작성해주세요");
        }
        if (BoardSaveReqDto.getTitle().length() > 100) {
            throw new CustomApiException("title의 길이가 100자 이하여야 합니다");
        }

        boardService.글쓰기(BoardSaveReqDto, principal.getId());

        return new ResponseEntity<>(new ResponseDto<>(1, "글쓰기 성공", null), HttpStatus.CREATED);
    }

    @GetMapping({ "/", "/board" })
    public String main(Model model, BoardLikeCountRespDto boardlikeCountRespDto) {
        // model.addAttribute("count",
        // boardRepository.likeCount(boardlikeCountRespDto));
        model.addAttribute("dtos", boardRepository.findAllWithUser());
        return "board/main";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model, LikeReqDto likeReqDto) throws Exception {
        User principal = (User) session.getAttribute("principal");

        // likeReqDto.setUserId(principal.getId());
        likeReqDto.setBoardId(id);
        BoardDetailRespDto boardDto = boardRepository.findByIdWithUser(id);
        if (likeReqDto.getUserId() != 0 && likeReqDto.getBoardId() != 0) {
            boardDto.setLikeNum(1);
        }

        System.out.println("boardId : " + id + " userId : " + likeReqDto.getUserId()
                + " likeNum : " + likeReqDto.getLikeNum());
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("replyDtos", replyRepository.findByBoardIdWithUser(id));
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        Board boardPS = boardRepository.findById(id);
        if (boardPS == null) {
            throw new CustomException("없는 게시글을 수정할 수 없습니다.");
        }
        if (boardPS.getUserId() != principal.getId()) {
            throw new CustomException("해당 게시글을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        model.addAttribute("board", boardPS); // 수정해야댐
        return "board/updateForm";
    }

    @PutMapping("/board/{id}/update")
    public @ResponseBody ResponseEntity<?> update(@PathVariable int id,
            @RequestBody BoardUpdateRespDto boardUpdateRespDto)
            throws Exception {
        // System.out.println(boardUpdateRespDto.getTitle());
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        if (boardUpdateRespDto.getTitle() == null || boardUpdateRespDto.getTitle().isEmpty()) {
            throw new CustomApiException("title을 작성해주세요");
        }
        if (boardUpdateRespDto.getContent() == null || boardUpdateRespDto.getContent().isEmpty()) {
            throw new CustomApiException("content를 작성해주세요");
        }
        if (boardUpdateRespDto.getTitle().length() > 100) {
            throw new CustomApiException("title의 길이가 100자 이하여야 합니다");
        }

        boardService.게시글수정(id, boardUpdateRespDto, principal.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "수정성공", null), HttpStatus.OK);
    }
}