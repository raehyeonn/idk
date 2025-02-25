package team.onepoom.idk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import team.onepoom.idk.member.domain.DeletedMember;
import team.onepoom.idk.member.domain.Member;
import team.onepoom.idk.member.repository.MemberRepository;

@SpringBootApplication
public class IdkApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdkApplication.class, args);
    }

    @Bean
    public CommandLineRunner createDeletedUser(MemberRepository memberRepository) {
        return args -> {
            if (!memberRepository.existsByEmail("deleted@user.com")) {
                Member deletedUser = DeletedMember.createDeletedMember();
                memberRepository.save(deletedUser);
            }
        };
    }
}
