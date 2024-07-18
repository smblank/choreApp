package com.example.bettersweepy;

public interface IRoom {
    void addChore(IChore newChore);
    IChore[] getChores();
}
