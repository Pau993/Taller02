package com.example;

//Todo lo que reciba llegará el método
@FunctionalInterface
public interface Route {

    Object handle(Request req, Response res) throws Exception;
}