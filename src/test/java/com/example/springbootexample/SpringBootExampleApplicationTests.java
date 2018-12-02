package com.example.springbootexample;

import com.example.springbootexample.Mail.Config.MailContentEnum;
import com.example.springbootexample.Mail.Config.MailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootExampleApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public static void main(String[] args) throws Exception {

        new MailSender()
                .title("测试SpringBoot发送邮件")
                .content("简单文本内容发送")
                .cpntentType(MailContentEnum.TEXT)
                .targets(new ArrayList<String>() {{
                    add("895800655@qq.com");
                }})
                .send();
    }
}
