package com.player.playerdemo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class PlayerController {
    private final PlayerRepository repository;
    private final PlayerModelAssembler assembler;

    /**
     * Constructs a Playercontroller.
     * 
     * @param repo      The repo for storing players.
     * @param assembler The assembler used to convert {@link Player} objects to
     *                  {@link EntityModel}s.
     */
    public PlayerController(PlayerRepository repo, PlayerModelAssembler assembler) {
        this.repository = repo;
        this.assembler = assembler;
    }

    /**
     * Gets all players contained in the {@link PlayerRepository}.
     * 
     * @return A collection of Entity Models containing the {@link Player}s.
     */
    @GetMapping("/api/players")
    CollectionModel<EntityModel<Player>> all() {
        List<EntityModel<Player>> players = repository.findAll().stream().map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(players, linkTo(methodOn(PlayerController.class).all()).withSelfRel());
    }

    /**
     * Gets a single player based on the player ID.
     * 
     * @param playerId The String player ID.
     * @return An Entity Model containing the {@link Player}.
     */
    @GetMapping("/api/players/{playerId}")
    EntityModel<Player> one(@PathVariable String playerId) {
        Player player = repository.findByPlayerId(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));

        return assembler.toModel(player);
    }

    /**
     * Adds a single player to the database. Throws a {@link DuplicatePlayerException} if the player already exists.
     * @param player The player to add.
     * @return An Entity Model containing the Player.
     */
    @PostMapping("/api/players")
    ResponseEntity<EntityModel<Player>> newPlayer(@RequestBody Player player) {
        if (repository.existsByPlayerId(player.getPlayerId())) {
            return ResponseEntity.status(HttpStatus.FOUND).body(assembler.toModel(player));
        }

        Player newPlayer = repository.save(player);
        return ResponseEntity.created(linkTo(methodOn(PlayerController.class).one(newPlayer.getPlayerId())).toUri()).body(assembler.toModel(newPlayer));
    }

    /**
     * Increments the given player's weight.
     * 
     * @param playerId The String player ID.
     * @return A Response Entity containing the updated player.
     */
    @PutMapping("/api/players/{playerId}/weight")
    ResponseEntity<?> incrementWeight(@PathVariable String playerId) {
        Player player = repository.findByPlayerId(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        player.setWeight(player.getWeight() + 1);
        repository.save(player);

        EntityModel<Player> entityModel = assembler.toModel(player);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    /**
     * Increments the given player's height.
     * 
     * @param playerId The String player ID.
     * @return A Response Entity containing the updated player.
     */
    @PutMapping("/api/players/{playerId}/height")
    ResponseEntity<?> incrementHeight(@PathVariable String playerId) {
        Player player = repository.findByPlayerId(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        player.setHeight(player.getHeight() + 1);
        repository.save(player);

        EntityModel<Player> entityModel = assembler.toModel(player);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
