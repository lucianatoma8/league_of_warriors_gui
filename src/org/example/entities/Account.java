package org.example.entities;

import org.example.entities.characters.Character;

import java.util.ArrayList;
import java.util.Locale;
import java.util.SortedSet;

public class Account {
    private final Information information;
    private final ArrayList<Character> characters;
    private int gamesPlayed;

    public Account(ArrayList<Character> characters, int gamesPlayed, Information information) {
        if (characters == null || information == null) {
            throw new IllegalArgumentException("Characters and information cannot be null");
        }
        this.characters = characters;
        this.gamesPlayed = gamesPlayed;
        this.information = information;
    }

    public Information getInformation() {
        return information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void addCharacter(Character character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null");
        }
        characters.remove(character);
    }

    @Override
    public String toString() {
        return "Account{" +
                "information=" + information +
                ", characters=" + characters +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }

    public static class Information {
        private final Credentials credentials;
        private final SortedSet<String> favoriteGames;
        private final String name;
        private final String country;

        // private constructor for builder
        private Information(Builder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        // builder class
        public static class Builder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;

            public Builder() {
            }

            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setFavoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                if (credentials == null || favoriteGames == null || name == null || country == null) {
                    throw new IllegalArgumentException("All fields must be set");
                }
                return new Information(this);
            }
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        @Override
        public String toString() {
            return "Information{" +
                    "credentials=" + credentials +
                    ", favoriteGames=" + favoriteGames +
                    ", name='" + name + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }
}
