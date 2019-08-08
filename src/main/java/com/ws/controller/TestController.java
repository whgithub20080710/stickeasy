package com.ws.controller;

import org.springframework.stereotype.Controller;

import javax.ws.rs.*;

@Controller
@Path(value = "test")
public class TestController {
    @GET
    @Path(value = "/test")
    @Produces
    public String add() {
        return "success wanghao";
    }
}
