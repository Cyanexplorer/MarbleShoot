package com.example.Marble_Game.Activity;

/**
 * Created by User on 2017/1/6.
 */

public interface AsyncTaskResult<T extends Object>
{
    // T是執行結果的物件型態

    public void taskFinish(T result);
}
