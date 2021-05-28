package com.player.playerdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is a preloader for the jpa repo
 */
@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /**
     * Initializes the jpa repository with some data, assuming it doesn't already exist in the H2 db.
     * @param repository The player repository
     * @throws FileNotFoundException
     */
    @Bean
    CommandLineRunner initDatabase(PlayerRepository repository) throws FileNotFoundException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Players.csv");
        Scanner sc = new Scanner(new InputStreamReader(inputStream));
        sc.useDelimiter("\\n");
        List<Player> players = new ArrayList<>();
        String[] templateArr = sc.next().split(",");
        templateArr[templateArr.length - 1] = templateArr[templateArr.length - 1].trim();
        while (sc.hasNext()) {
            String[] playerArr = sc.next().split(",");
            Map<String, String> playerMap = new HashMap<>();

            for (int i = 0; i < templateArr.length; i++) {
                playerMap.put(templateArr[i], playerArr[i]);
            }

            if (repository.findByPlayerId(playerMap.get("playerID")).orElse(null) == null) {
                Player player = Player.builder().playerId(playerMap.get("playerID"))
                        .birthYear(parseAsInt(playerMap.get("birthYear")))
                        .birthMonth(parseAsInt(playerMap.get("birthMonth")))
                        .birthDay(parseAsInt(playerMap.get("birthDay"))).birthCountry(playerMap.get("birthCountry"))
                        .birthState(playerMap.get("birthState")).birthCity(playerMap.get("birthCity"))
                        .deathYear(parseAsInt(playerMap.get("deathYear")))
                        .deathMonth(parseAsInt(playerMap.get("deathMonth")))
                        .deathDay(parseAsInt(playerMap.get("deathDay"))).deathCountry(playerMap.get("deathCountry"))
                        .deathState(playerMap.get("deathState")).deathCity(playerMap.get("deathCity"))
                        .nameFirst(playerMap.get("nameFirst")).nameLast(playerMap.get("nameLast"))
                        .nameGiven(playerMap.get("nameGiven")).weight(parseAsInt(playerMap.get("weight")))
                        .height(parseAsInt(playerMap.get("height"))).bats(playerMap.get("bats"))
                        .gameThrows(playerMap.get("throws")).debut(playerMap.get("debut"))
                        .finalGame(playerMap.get("finalGame")).bbrefId(playerMap.get("bbrefID"))
                        .retroId(playerMap.get("retroID")).build();
                players.add(player);
            }
        }

        sc.close();

        return args -> {
            for (Player player : players) {
                log.info("Preloading " + repository.save(player));
            }
        };
    }

    /**
     * Convenience method for parsing a string into an Integer without throwing an excpetion.
     * @param s The string to parse.
     * @return The Integer representation, or null if the string is blank.
     */
    private Integer parseAsInt(String s) {
        if (s.isBlank())
            return null;
        return Integer.parseInt(s);
    }
}
