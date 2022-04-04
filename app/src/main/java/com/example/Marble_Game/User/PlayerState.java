package com.example.Marble_Game.User;

/**
 * Created by g3863 on 2017/10/14.
 */

public class PlayerState {

    private UserAbi abi;
    private UserRecord record;

    public static PlayerState newInstance(UserProfile profile){
        return new PlayerState(profile.getAbi(),profile.getRecord());
    }

    private PlayerState(UserAbi abi, UserRecord record){
        this.abi = abi;
        this.record = record;
    }
}
