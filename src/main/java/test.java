import com.fasterxml.jackson.databind.util.ClassUtil;
import org.springframework.util.ClassUtils;

import java.net.URL;
import java.util.Date;

/**
 * @author : bestrookie
 * @date : 16:04 2020/10/5
 */
public class test {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(new Date().getTime());

        System.out.println(System.currentTimeMillis());
    }
}
