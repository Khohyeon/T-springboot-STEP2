package shop.mtcoding.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Thumbnail {
    public static String thum(String html) {
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");
        String thumbnail = "";
        // System.out.println(els);
        if (els.size() == 0) {
            // 임시사진
            // 디비 섬네일 -> /image/profile.png
        } else {
            Element el = els.get(0);
            thumbnail = el.attr("src");
        }
        return thumbnail;
    }
}
