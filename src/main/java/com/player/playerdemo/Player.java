package com.player.playerdemo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Player
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "Player")
public class Player {
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String playerId;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;
    private String birthCountry;
    private String birthState;
    private String birthCity;
    private Integer deathYear;
    private Integer deathMonth;
    private Integer deathDay;
    private String deathCountry;
    private String deathState;
    private String deathCity;
    private String nameFirst;
    private String nameLast;
    private String nameGiven;
    private Integer height;
    private Integer weight;
    private String bats;
    private String gameThrows;
    private String debut;
    private String finalGame;
    private String retroId;
    private String bbrefId;

    /**
     * Default constructor for JPA
     */
    public Player() {}

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Player)) {
            return false;
        }
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(playerId, player.playerId)
                && Objects.equals(birthYear, player.birthYear) && Objects.equals(birthMonth, player.birthMonth)
                && Objects.equals(birthDay, player.birthDay) && Objects.equals(birthCountry, player.birthCountry)
                && Objects.equals(birthState, player.birthState) && Objects.equals(birthCity, player.birthCity)
                && Objects.equals(deathYear, player.deathYear) && Objects.equals(deathMonth, player.deathMonth)
                && Objects.equals(deathDay, player.deathDay) && Objects.equals(deathCountry, player.deathCountry)
                && Objects.equals(deathState, player.deathState) && Objects.equals(deathCity, player.deathCity)
                && Objects.equals(nameFirst, player.nameFirst) && Objects.equals(nameLast, player.nameLast)
                && Objects.equals(nameGiven, player.nameGiven) && Objects.equals(height, player.height)
                && Objects.equals(weight, player.weight) && Objects.equals(bats, player.bats)
                && Objects.equals(gameThrows, player.gameThrows) && Objects.equals(debut, player.debut)
                && Objects.equals(finalGame, player.finalGame) && Objects.equals(retroId, player.retroId)
                && Objects.equals(bbrefId, player.bbrefId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerId, birthYear, birthMonth, birthDay, birthCountry, birthState, birthCity,
                deathYear, deathMonth, deathDay, deathCountry, deathState, deathCity, nameFirst, nameLast, nameGiven,
                height, weight, bats, gameThrows, debut, finalGame, retroId, bbrefId);
    }
}
