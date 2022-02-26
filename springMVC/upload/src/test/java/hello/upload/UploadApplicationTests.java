package hello.upload;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class UploadApplicationTests {

	@Test
	void contextLoads() {
		ArrayList<String> list = new ArrayList<>();
		list.add(null);
		list.add("asd");
		list.add(null);
		System.out.println(list);

	}

}
