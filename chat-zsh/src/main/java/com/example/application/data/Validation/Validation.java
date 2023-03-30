package com.example.application.data.Validation;

public class Validation {

    /**
     *
     * Validates user input for errors
     *
     * @param input question
     * @return true or false
     */
    public Boolean validateInput(String input) {
        if (input.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
