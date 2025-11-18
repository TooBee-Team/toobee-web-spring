package top.toobee.spring.service;

import java.util.Optional;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import top.toobee.spring.entity.PlayerEntity;
import top.toobee.spring.repository.PlayerRepository;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Boolean> isFakeOf(String username) {
        return playerRepository.findFakeByName(username);
    }

    public @Nullable PlayerEntity getPlayerByName(String name) {
        return playerRepository.findByName(name).orElse(null);
    }

    public void save(PlayerEntity player) {
        playerRepository.save(player);
    }
}
