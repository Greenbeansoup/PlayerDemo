package com.player.playerdemo;

/**
 * Exception to be thrown when a player does not exist.
 */
public class PlayerNotFoundException extends RuntimeException{

    /**
     * Constructs a PlayerNotFoundException.
     * @param id the player's unique ID.
     */
    public PlayerNotFoundException(Long id) {
        super("Could not find player " + id);
    }

    /**
     * Constructs a PlayerNotFoundException.
     * @param id the player's player ID.
     */
    public PlayerNotFoundException(String id) {
        super("Could not find player " + id);
    }
}
