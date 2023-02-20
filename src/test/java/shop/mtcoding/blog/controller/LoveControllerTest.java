package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateRespDto;
import shop.mtcoding.blog.dto.board.BoardResp;
import shop.mtcoding.blog.dto.reply.ReplyResp;
import shop.mtcoding.blog.model.User;

@Transactional // 메서드 실행 직후에 롤백!
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class LoveControllerTest {

    @Autowired
    private MockMvc mvc;

    private MockHttpSession mockSession;

    @Autowired
    private ObjectMapper om;

    // @BeforeAll
    // public static void 테이블차리기() {
    // // 테이블 차리기
    // }

    // @AfterAll
    // public void teardown() {
    // // 데이터 지우고, 다시 인서트
    // }

    @BeforeEach // Test 메서드 실행 직전 마다에 호출됨.
    public void setUp() {
        // 데이터 인서트
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void update_test() throws Exception {
        // given
        int id = 1;
        BoardUpdateRespDto boardUpdateRespDto = new BoardUpdateRespDto();
        boardUpdateRespDto.setTitle("제목1-수정");
        boardUpdateRespDto.setContent("내용1-수정");

        String requestBody = om.writeValueAsString(boardUpdateRespDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(
                put("/board/1/update")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }

    @Test
    public void delete_test() throws Exception {
        // given
        int id = 1;

        // when
        ResultActions resultActions = mvc.perform(delete("/board/" + id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        /*
         * jsonPath
         * 최상위 : $
         * 객체탐색 : 닷(.)
         * 배열 : [0] 번지수 찾기
         */
        // then
        resultActions.andExpect(jsonPath("$.code").value(1));
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void detail_test() throws Exception {
        // given
        int id = 1;
        // when
        ResultActions resultActions = mvc.perform(
                get("/board/" + id));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardResp.BoardDetailRespDto boardDto = (BoardResp.BoardDetailRespDto) map.get("boardDto");
        String boardJson = om.writeValueAsString(boardDto);
        List<ReplyResp.ReplyDetailRespDto> replyDtos = (List<ReplyResp.ReplyDetailRespDto>) map.get("replyDtos");
        String replyListjson = om.writeValueAsString(replyDtos);
        System.out.println("테스트 : size : " + boardJson);
        System.out.println("테스트 : size : " + replyListjson);

        // then
        resultActions.andExpectAll(status().isOk());
        Assertions.assertThat(status().isOk());
        assertThat(boardDto.getUsername()).isEqualTo("ssar");
        assertThat(boardDto.getId()).isEqualTo(1);
        assertThat(boardDto.getTitle()).isEqualTo("첫 번째 제목입니다.");
        assertThat(replyDtos.get(1).getComment()).isEqualTo("세 번째 댓글");
        assertThat(replyDtos.get(1).getUsername()).isEqualTo("love");

    }

    @Test
    public void main_test() throws Exception {
        // given
        // when
        ResultActions resultActions = mvc.perform(
                get("/"));
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        List<BoardResp.BoardMainRespDto> dtos = (List<BoardResp.BoardMainRespDto>) map.get("dtos");
        System.out.println("테스트 : size : " + dtos.size());
        String model = om.writeValueAsString(dtos);
        System.out.println("테스트 : size : " + model);

        // then
        resultActions.andExpectAll(status().isOk());
        Assertions.assertThat(status().isOk());
        assertThat(dtos.size()).isEqualTo(6);
        assertThat(dtos.get(0).getUsername()).isEqualTo("ssar");
        assertThat(dtos.get(0).getTitle()).isEqualTo("첫 번째 제목입니다.");

    }

    @Test
    public void save_test() throws Exception {
        // given
        BoardSaveReqDto boardSaveReqDto = new BoardSaveReqDto();
        boardSaveReqDto.setTitle("제목");
        boardSaveReqDto.setContent("내용");

        String requestBody = om.writeValueAsString(boardSaveReqDto);
        // when
        ResultActions resultActions = mvc.perform(
                post("/board")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .session(mockSession));

        System.out.println("save_test : ");
        // then
        resultActions.andExpect(status().isCreated());
    }
}