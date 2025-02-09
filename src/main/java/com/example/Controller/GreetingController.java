package com.example.Controller;

import com.example.Anotation.*;;

@RestController
public class GreetingController {

	@GetMapping("/greeting")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return "Hola " + name;
	}
        
        @GetMapping("/greeting")
	public String pi(@RequestParam(value = "name", defaultValue = "World") String name) {
		return Double.toString(Math.PI);
	}
}
