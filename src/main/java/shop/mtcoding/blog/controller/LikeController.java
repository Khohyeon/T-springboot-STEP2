package shop.mtcoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.like.LikeReq.LikeReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.model.LikeRepository;

@Controller
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @PostMapping("/like/{id}")
    public @ResponseBody ResponseEntity<?> like(@RequestBody LikeReqDto likeReqDto, @PathVariable int id, Model model) {
        if (likeReqDto.getBoardId() != 0 && likeReqDto.getUserId() != 0) {
            likeReqDto.setLikeNum(1);
        }

        int result = likeRepository.insert(likeReqDto);
        if (result != 1) {
            throw new CustomApiException("좋아요 실패");
        }

        model.addAttribute("like", result);
        return new ResponseEntity<>(new ResponseDto<>(1, "", null), HttpStatus.CREATED);
    }
}
