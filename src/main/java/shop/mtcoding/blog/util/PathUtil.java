package shop.mtcoding.blog.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;

public class PathUtil {

    private static String getStaticFolder() {
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\"; // 현재폴더 경로
    }

    public static String writeImageFile(MultipartFile profile) {
        UUID uuid = UUID.randomUUID();
        String uuidImageDBName = "/images/" + uuid + "_" + profile.getOriginalFilename();
        String uuidImageRealName = "\\images\\" + uuid + "_" + profile.getOriginalFilename();
        String staticFolder = getStaticFolder();
        Path imageFilePath = Paths.get(staticFolder + uuidImageRealName);
        try {
            Files.write(imageFilePath, profile.getBytes());
        } catch (Exception e) {
            throw new CustomException("사진을 웹서버에 저장하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidImageDBName;
    }

    public static String writeImageFile1(MultipartFile profile) {
        UUID uuid = UUID.randomUUID();
        String uuidImageDBName = "/images/" + uuid + "_" + profile.getOriginalFilename();
        String uuidImageRealName = "\\images\\" + uuid + "_" + profile.getOriginalFilename();
        String staticFolder = getStaticFolder();
        Path imageFilePath = Paths.get(staticFolder + uuidImageRealName);
        try {
            Files.write(imageFilePath, profile.getBytes());
        } catch (Exception e) {
            throw new CustomApiException("사진을 웹서버에 저장하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidImageDBName;
    }

}