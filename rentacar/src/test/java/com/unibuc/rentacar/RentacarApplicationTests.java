package com.unibuc.rentacar;

import com.unibuc.rentacar.controllers.BookingControllerTest;
import com.unibuc.rentacar.controllers.CarControllerTest;
import com.unibuc.rentacar.controllers.UserControllerTest;
import com.unibuc.rentacar.services.BookingServiceTest;
import com.unibuc.rentacar.services.CarServiceTest;
import com.unibuc.rentacar.services.UserServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BookingServiceTest.class, BookingControllerTest.class, UserServiceTest.class, UserControllerTest.class, CarServiceTest.class, CarControllerTest.class})
class RentacarApplicationTests {

	@Test
	void contextLoads() {
	}

}
