package dev.twozer00.projectm.model;

import java.io.Serializable;

public class Author_details implements Serializable {
    private String name;
    private String username;
    private String avatar_path;
    private Integer rating;

    public Author_details(String name, String username, String avatar_path, Integer rating) {
        this.name = name;
        this.username = username;
        this.avatar_path = avatar_path;
        this.rating = rating;
    }

    // Getter Methods

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public Integer getRating() {
        return rating;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "" +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", avatar_path='" + avatar_path + '\'' +
                ", rating=" + rating +
                '}';
    }
}
