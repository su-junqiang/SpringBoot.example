package com.example.springbootexample.Mail.Config;

import com.example.springbootexample.Mail.Until.PropertiesUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;
import com.sun.mail.util.MailSSLSocketFactory;

public class MailSender {
    //邮件实体
    private static MailEntity mail = new MailEntity();

    //设置邮件标题
    public MailSender title(String title) {
        mail.setTitle(title);
        return this;
    }

    //设置邮件内容
    public MailSender content(String content) {
        mail.setContent(content);
        return this;
    }

    //设置邮件格式
    public MailSender cpntentType(MailContentEnum typeEnum) {
        mail.setContentType(typeEnum.getValue());
        return this;
    }

    //设置请求邮件地址
    public MailSender targets(List<String> targets) {
        mail.setList(targets);
        return this;
    }

    //执行发送邮件
    public void send() throws Exception {
        //默认使用html发送
        if (mail.getContentType() == null) {
            mail.setContentType(MailContentEnum.HTML.getValue());
        }
        if (mail.getTitle() == null || mail.getTitle().trim().length() == 0) {
            throw new Exception("邮件标题没有设置，请设置有邮件标题");
        }
        if (mail.getContent() == null || mail.getContent().trim().length() == 0) {
            throw new Exception("邮件内容没有设置，请设置邮件内容");
        }
        if (mail.getList().size() == 0) {
            throw new Exception("没有接收者邮箱地址");
        }

        //读取/resource/mall_zh_CN.properties中的内容
        final PropertiesUtil properties = new PropertiesUtil("mail");
        //创建Properties类用于记录邮箱的一些属性
        final Properties props = new Properties();
        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        //表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写smtp服务器
        props.put("mail.smtp.host", properties.getValue("mail.smtp.service"));
        //设置端口。QQ邮箱给出的俩个端口465//587
        props.put("mail.smtp.port", properties.getValue("mail.smtp.port"));
        //设置发送邮箱
        props.put("mail.user", properties.getValue("mail.from.address"));
        //设置发送邮箱的16位SMTP口令
        props.put("mail.password", properties.getValue("mail.from.smtp.pwd"));


        //构建授权信息，用于进行SMTP省份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //用户名，密码
                String userName = props.getProperty("mail.user");
                String passWord = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, passWord);
            }
        };

        //使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        mailSession.setDebug(true);
        //创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        //设置发件人
        String nickName = MimeUtility.encodeText(properties.getValue("mail.from.nickname"));
        InternetAddress form = new InternetAddress(nickName + "<" + props.getProperty("mail.user") + ">");
        message.setFrom(form);
        //设置邮件标题
        message.setSubject(mail.getTitle());
        //html发送邮件
        if (mail.getContentType().equals(MailContentEnum.HTML.getValue())) {
            //设置邮件标题
            message.setContent(mail.getContent(), mail.getContentType());
        }
        //文本发送邮件
        else if (mail.getContentType().equals(MailContentEnum.TEXT.getValue())) {
            message.setText(mail.getContent());
        }
        //发送邮箱地址
        List<String> targets = mail.getList();
        for (int i = 0; i < targets.size(); i++) {
            try {
                //设置收件人的邮箱
                InternetAddress to = new InternetAddress(targets.get(i));
                message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{to});
                //发送邮件
                Transport.send(message);
            } catch (Exception e) {
                continue;
            }
        }
    }
}
