package net.eqno.session.integrations;

import net.eqno.session.Controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTests {
	@Autowired
	private Controller controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
