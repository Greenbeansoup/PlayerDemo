package com.player.playerdemo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Assembler for the Player Class
 */
@Component
public class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<Player>>{
    @Override
    public EntityModel<Player> toModel(Player player) {
        return EntityModel.of(player, linkTo(methodOn(PlayerController.class).one(player.getPlayerId())).withSelfRel(),
            linkTo(methodOn(PlayerController.class).all()).withRel("players"));
    }
}
