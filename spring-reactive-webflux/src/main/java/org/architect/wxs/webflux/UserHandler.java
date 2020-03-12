package org.architect.wxs.webflux;

import org.architect.wxs.domain.User;
import org.architect.wxs.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        Mono<User> mono = serverRequest.bodyToMono(User.class);

        Mono<Boolean> map = mono.map(user -> userRepository.save());

        return ServerResponse.ok().body(map,Boolean.class);
    }
}
