package top.toobee.spring.service;

import org.springframework.stereotype.Service;
import top.toobee.spring.entity.PlayerEntity;
import top.toobee.spring.repository.PlayerRepository;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Optional<Boolean> isFakeOf(String username) {
        return playerRepository.findFakeByName(username);
    }

    public PlayerEntity getPlayerByName(String name) {
        return playerRepository.findByName(name).orElse(null);
    }

    public void save(PlayerEntity player) {
        playerRepository.save(player);
    }
}
