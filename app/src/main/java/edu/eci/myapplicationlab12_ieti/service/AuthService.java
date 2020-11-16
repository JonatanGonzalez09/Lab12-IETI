package edu.eci.myapplicationlab12_ieti.service;

import edu.eci.myapplicationlab12_ieti.config.Token;
import edu.eci.myapplicationlab12_ieti.model.LoginWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("taskPlanner/user/login")
    Call<Token> loginUser(@Body LoginWrapper userLogin);
}
