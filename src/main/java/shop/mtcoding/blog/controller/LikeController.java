package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog.dto.like.LikeReq.LikeReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.Like;
import shop.mtcoding.blog.model.LikeRepository;
import shop.mtcoding.blog.model.User;

@Controller
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

    @PostMapping("/like/{id}")
    public @ResponseBody ResponseEntity<?> like(@RequestBody LikeReqDto likeReqDto, @PathVariable int id, Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }

        Like check = likeRepository.findByUserIdAndBoardId(principal.getId(), id);
        BoardDetailRespDto boardDto = boardRepository.findByIdWithUser(id);
        if (check == null) {
            boardDto.setLikeNum(0);
        }
        boardDto.setLikeNum(1);

        int result = likeRepository.insert(likeReqDto);
        if (result != 1) {
            throw new CustomApiException("실패");
        }
        model.addAttribute("likeDto", boardDto);

        // if (likeReqDto.getLikeNum() == 1) {
        // throw new CustomApiException("이미 좋아요를 눌렀습니다.");
        // }

        System.out.println("boardId : " + likeReqDto.getBoardId() + " userId : " + likeReqDto.getUserId()
                + " likeNum : " + likeReqDto.getLikeNum());

        return new ResponseEntity<>(new ResponseDto<>(1, "", null), HttpStatus.CREATED);
    }

    @DeleteMapping("/like/{id}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable int id) { // responsebody를 붙이면 데이터를 응답한다.
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            throw new CustomApiException("인증이 되지 않았습니다", HttpStatus.UNAUTHORIZED);
        }
        // db가 없어서 여기서 권한 검사를 할 수 가없다.
        likeRepository.deleteById(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제성공", null), HttpStatus.OK);

    }
}
