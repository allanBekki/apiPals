package com.example.demo.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Getter
@Setter
@Document
public class Pals
{
    int id;
    int key;
    String name;
    String wiki;
    ArrayList<String> types;
    ArrayList<Suitability> suitabilities;
    ArrayList<String> drops;
    ArrayList<Skills> skills;

    int rarity;
    String price;
    String size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public ArrayList<Suitability> getSuitabilities() {
        return suitabilities;
    }

    public void setSuitabilities(ArrayList<Suitability> suitabilities) {
        this.suitabilities = suitabilities;
    }

    public ArrayList<String> getDrops() {
        return drops;
    }

    public void setDrops(ArrayList<String> drops) {
        this.drops = drops;
    }

    public ArrayList<Skills> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skills> skills) {
        this.skills = skills;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
