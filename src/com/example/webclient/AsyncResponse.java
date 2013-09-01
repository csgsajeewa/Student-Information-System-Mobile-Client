package com.example.webclient;



public interface AsyncResponse {
    void processFinish(User user);
    void processFinish(String message);
  
}