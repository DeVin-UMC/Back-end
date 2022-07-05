package UMC.DeVin;

import UMC.common.CustomRestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 *  H2 database 또는 MySQL 설정이 되어있지 않으면 컴파일 에러가 발생합니다.
 */
@SpringBootApplication
@Import(CustomRestExceptionHandler.class)
public class DeVinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeVinApplication.class, args);
	}

}
