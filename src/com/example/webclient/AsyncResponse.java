package com.example.webclient;

//interface used by activities to handle ASYNCTASK responses

public interface AsyncResponse {
    void processFinish(User user);
    void processFinish(String message);
  
}