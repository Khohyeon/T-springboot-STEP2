package shop.mtcoding.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.reply.ReplyReq.ReplySaveReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.ReplyRepository;

@Transactional(readOnly = true)
@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private HttpSession session;
    // 1. content 내용을 document로 받고, img 찾아내서 (0,1,2) src를 찾아서 thumbnail 추가
    // 여기서 코드 짜고 util로 옮기기

    // where 절에 걸리는 파라미터를 앞에 받기
    @Transactional
    public void 댓글쓰기(ReplySaveReqDto replySaveReqDto, int principalId) {
        int result = replyRepository.insert(replySaveReqDto.getComment(), replySaveReqDto.getBoardId(), principalId);

        if (result != 1) {
            throw new CustomApiException("댓글쓰기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void 게시글삭제(int id, int userId) {
        Reply replyPS = replyRepository.findById(id);
        if (replyPS == null) {
            throw new CustomApiException("없는 게시글을 삭제할 수 없습니다.");
        }
        // if (replyPS.getUserId() != userId) {
        // throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        // }
        try {
            replyRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야 함 (DB or File)

        }
    }

}
