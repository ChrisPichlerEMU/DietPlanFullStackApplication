package com.dietPlan;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DietPlanApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main() {
		DietPlanApplication.main(new String[] {});
	}
}
