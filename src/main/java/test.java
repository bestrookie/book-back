import com.fasterxml.jackson.databind.util.ClassUtil;
import org.springframework.util.ClassUtils;

import java.net.URL;

/**
 * @author : bestrookie
 * @date : 16:04 2020/10/5
 */
public class test {
    public static void main(String[] args) {

        URL url = ClassUtils.getDefaultClassLoader().getResource("classpath:static/avatar/"+"01.png");
        System.out.println(url);
    }
}
