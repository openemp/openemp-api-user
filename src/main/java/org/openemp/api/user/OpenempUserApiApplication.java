package org.openemp.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * OpenEMP User API Application.
 */
@SpringBootApplication
@EnableJpaAuditing
public class OpenempUserApiApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
        SpringApplication.run(OpenempUserApiApplication.class, args);
    }

}
