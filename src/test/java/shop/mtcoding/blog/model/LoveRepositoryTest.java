package shop.mtcoding.blog.model;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

@MybatisTest
public class LoveRepositoryTest {

    @Autowired
    private LoveRepository loveRepository;

    @Test
    public void findByBoardIdAndUserId_test() throws Exception {
        // given
        int boardId = 1;
        int userId = 1;
        ObjectMapper om = new ObjectMapper(); // Jackson이라는 라이브러리가 들고있음

        // when
        Love lovePs = loveRepository.findByBoardIdAndUserId(boardId, userId);
        String responseBody = om.writeValueAsString(lovePs);
        System.out.println("테스트 : " + responseBody); // JSON VIEW로 TEST 하는 방법

        // then
        // assertThat(boardMainRespDtos.get(5).getUsername()).isEqualTo("love");

    }
}
