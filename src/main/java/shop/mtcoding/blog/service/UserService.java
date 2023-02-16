package shop.mtcoding.blog.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.util.PathUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

    @Transactional
    public void 회원가입(JoinReqDto joinReqDto) {
        User sameUser = userRepository.findByUsername(joinReqDto.getUsername());
        if (sameUser != null) {
            throw new CustomException("동일한 username이 존재합니다");
        }

        int result = userRepository.insert(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }
    };

    @Transactional(readOnly = true)
    public User 로그인(LoginReqDto loginReqDto) {
        User principal = userRepository.findByUsernameAndPassword(
                loginReqDto.getUsername(), loginReqDto.getPassword());
        if (principal == null) {
            throw new CustomException("유저네임 혹은 패스워드가 잘못 입력되었습니다");
        }
        return principal;
    }

    @Transactional
    public User 프로필사진수정(MultipartFile profile, int pricipalId) {
        // 1번 사진을 /static/image에 UUID로 변경해서 저장
        String uuidImageName = PathUtil.writeImageFile(profile);

        // 2번 저장된 파일의 경로를 DB에 저장
        User userPS = userRepository.findById(pricipalId);
        userPS.setProfile(uuidImageName);
        userRepository.updateById(userPS.getId(), userPS.getUsername(), userPS.getPassword(), userPS.getEmail(),
                userPS.getProfile(), userPS.getCreatedAt());
        return userPS;
    }

    @Transactional
    public User 프로필사진수정1(MultipartFile profile, int pricipalId) {
        // 1번 사진을 /static/image에 UUID로 변경해서 저장
        String uuidImageName = PathUtil.writeImageFile1(profile);

        // 2번 저장된 파일의 경로를 DB에 저장
        User userPS = userRepository.findById(pricipalId);
        userPS.setProfile(uuidImageName);
        userRepository.updateById(userPS.getId(), userPS.getUsername(), userPS.getPassword(), userPS.getEmail(),
                userPS.getProfile(), userPS.getCreatedAt());
        return userPS;
    }

    @Transactional
    public void 회원삭제(int id, int userId) {
        User userPS = userRepository.findById(id);
        if (userPS == null) {
            throw new CustomApiException("없는 회원을 삭제할 수 없습니다.");
        }
        // if (boardPS.getUserId() != userId) {
        // throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        // }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            // 로그를 남겨야 함 (DB or File)

        }
    }
}