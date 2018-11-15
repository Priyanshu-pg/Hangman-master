package com.example.priyanshu.hangman_master.activities.Model;

public class Word {
    private int id;
    private String name;
    private String category;
    private String hint;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getHint(){
        return hint;
    }

    public void setHint(String hint){
        this.hint = hint;
    }
}
