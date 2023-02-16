package shop.mtcoding.blog.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import shop.mtcoding.blog.model.Like;

@Service
public class LikeService {
    public Like findHeart(long e_number, long m_number) {
        // 2개의 parameter를 보내기 위해 Map 선언 및 Map에 데이터 삽입
        Map<String, Long> number = new HashMap<String, Long>();
        number.put("e_number", e_number);
        number.put("m_number", m_number);
        return ;
    }
}
