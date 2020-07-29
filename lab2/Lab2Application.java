package fudan.se.lab2;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.domain.*;
import fudan.se.lab2.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transaction;
import javax.websocket.Session;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

/**
 * Welcome to 2020 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 */
//@SpringBootApplication

@SpringBootApplication

public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    /**
     * This is a function to create some basic entities when the application starts.
     * Now we are using a In-Memory database, so you need it.
     * You can change it as you like.
     */
    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, MeetingRepository meetingRepository, MeetingUserRepository meetingUserRepository, ApplicationRepository applicationRepository,InvitationRepository invitationRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                Logger logger = LoggerFactory.getLogger(AuthController.class);
                // Create authorities if not exist.
                Authority adminAuthority = getOrCreateAuthority("Admin", authorityRepository);
                Authority contributorAuthority = getOrCreateAuthority("Contributor", authorityRepository);
                Authority reviewerAuthority = getOrCreateAuthority("Reviewer", authorityRepository);

                // Create an admin if not exists.
                if (userRepository.findByUsername("admin") == null) {
                    logger.info("no admin");
                    User admin = new User(
                            "admin",
                            "password",
                            "libowen",
                            new HashSet<>(Collections.singletonList(adminAuthority))
                    );
                    userRepository.save(admin);
                }
                    if (userRepository.findByUsername("reviewer") == null) {
                        logger.info("no admin");
                        User reviewer = new User(
                                "reviewer",
                                "password",
                                "linxiaxue",
                                new HashSet<>(Collections.singletonList(reviewerAuthority))
                        );
                        userRepository.save(reviewer);

                        //logger.info("save admin");
                    }




                    //Transaction tr = session.beginTransaction();


            }

            private Authority getOrCreateAuthority(String authorityText, AuthorityRepository authorityRepository) {
                Authority authority = authorityRepository.findByAuthority(authorityText);
                if (authority == null) {
                    authority = new Authority(authorityText);
                    authorityRepository.save(authority);
                }
                return authority;
            }
        };
    }
}

