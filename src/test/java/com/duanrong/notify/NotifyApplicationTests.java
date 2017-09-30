package com.duanrong.notify;

import com.duanrong.notify.util.HashCrypt;
import com.duanrong.notify.util.MD5Encry;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.provider.MD5;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotifyApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMd5() {
		String str = "account=16&content=%E6%82%A8%E5%B7%B2%E6%88%90%E5%8A%9F%E6%8A%95%E8%B5%84%23param1%EF%BC%8C%E6%9C%9F%E9%99%90%23param2%EF%BC%8C%E9%87%91%E9%A2%9D%23param3%EF%BC%8C%E6%98%8E%E5%A4%A9%E7%9A%84%E8%B4%A2%E5%AF%8C%E6%BA%90%E8%87%AA%E4%BB%8A%E5%A4%A9%E7%9A%84%E7%A7%AF%E7%B4%AF%EF%BC%8C%E6%88%91%E4%BB%AC%E5%B0%86%E7%94%A8%E5%8A%AA%E5%8A%9B%E5%9B%9E%E6%8A%A5%E6%82%A8%E7%9A%84%E6%94%AF%E6%8C%81%E4%B8%8E%E4%BF%A1%E4%BB%BB%E3%80%82&mobile=18738529936f3f216b25b11c268368d7a1bb36295c5";
		String result1 = MD5Encry.Encry(str);
		System.out.println(result1);
		String result2 = HashCrypt.getDigestHash(str, "MD5");
		System.out.println(result2);
		String result3 = Md5Crypt.apr1Crypt(str, "MD5");
		System.out.println(result3);
	}

}
