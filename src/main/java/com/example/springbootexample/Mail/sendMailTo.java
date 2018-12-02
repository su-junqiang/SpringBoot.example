package com.example.springbootexample.Mail;

import com.example.springbootexample.Mail.Config.MailContentEnum;
import com.example.springbootexample.Mail.Config.MailSender;

import java.util.ArrayList;

public class sendMailTo {
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
