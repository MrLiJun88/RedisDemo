package string;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.PersonService;

/**指定spring容器的信息*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class StringTest {

    @Autowired
    private PersonService personService;

    @Test
    public void testString() {
        String result = personService.getString("app");
        System.out.println(result);
    }
}
