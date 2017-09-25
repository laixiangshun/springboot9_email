package com.lai;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEmailApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * 由于Spring Boot的starter模块提供了自动化配置，所以在引入了spring-boot-starter-mail依赖之后，
	 * 会根据配置文件中的内容去创建JavaMailSender实例，
	 * 因此我们可以直接在需要使用的地方直接@Autowired来引入邮件发送对象
	 */
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * 发送简单邮件
	 * @throws Exception
	 */
//	@Test
//	public void sendSimpleMail() throws Exception{
//		SimpleMailMessage mailMessage=new SimpleMailMessage();
//		mailMessage.setFrom("739156890@qq.com");
//		mailMessage.setTo("971885608@qq.com");
//		mailMessage.setSubject("测试");
//		mailMessage.setText("测试邮件内容");
//		javaMailSender.send(mailMessage);
//	}

	/**
	 * 发送带有附件的邮件
	 * @throws Exception
	 */
//	@Test
//	public void sendAttachmentsMail() throws Exception{
//		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
//		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
//		helper.setFrom("739156890@qq.com");
//		helper.setTo("971885608@qq.com");
//		helper.setSubject("测试2");
//		helper.setText("有附件的邮件");
//		FileSystemResource file=new FileSystemResource(new File("1.jpg"));
//		helper.addAttachment("附件一.jpg",file);
//		helper.addAttachment("附件二.jpg",file);
//		javaMailSender.send(mimeMessage);
//	}

	/**
	 * 发送嵌入静态资源的邮件
	 * @throws Exception
	 */
//	@Test
//	public void sendInlineMail() throws Exception{
//		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
//		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
//		helper.setFrom("739156890@qq.com");
//		helper.setTo("971885608@qq.com");
//		helper.setSubject("嵌入静态图片");
//		helper.setText("<html><body><img src=\"cid:weixin\"><p>这个小女孩好不好看?</p></body></html>",true);
//		FileSystemResource file=new FileSystemResource(new File("1.jpg"));
//		//需要注意的是addInline函数中资源名称weixin需要与正文中cid:weixin对应起来
//		helper.addInline("weixin",file);
//		javaMailSender.send(mimeMessage);
//	}

	@Autowired
	private Configuration configuration;
	@Test
	public void sendTemplateMail() throws Exception{
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
		helper.setFrom("739156890@qq.com");
		helper.setTo("971885608@qq.com");
		helper.setSubject("模板邮件");
		Map<String,Object> model=new HashMap<>();
		model.put("username","刘雄花");
		model.put("fromUser","赖哥");
		model.put("time", new Date());
		model.put("src","cid:image1");
		Template t=configuration.getTemplate("template.html");
		String text= FreeMarkerTemplateUtils.processTemplateIntoString(t,model);
		System.out.println(text);
		helper.setText(text, true);
		FileSystemResource file=new FileSystemResource(new File("1.jpg"));
		helper.addInline("image1",file);
		javaMailSender.send(mimeMessage);
	}
}
