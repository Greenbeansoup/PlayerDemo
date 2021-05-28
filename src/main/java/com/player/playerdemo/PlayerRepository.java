package com.player.playerdemo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    
    Optional<Player> findByPlayerId(String playerId);
}
