package shop.mtcoding.blog.util;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UUIDTESTest {

    @Test
    public void uuid_test() throws Exception {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);

    }
}
