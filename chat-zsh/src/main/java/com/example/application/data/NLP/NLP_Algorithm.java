package com.example.application.data.NLP;

public class NLP_Algorithm {

    private static final String API_KEY = "YOUR_API_KEY";
    private static final String ENGINE_ID = "davinci";

    private OpenaiAuth auth;
    private Engine engine;

    public NLP_Algorithm() throws ApiException {
        auth = new OpenaiAuth().setBearerToken(API_KEY);
        engine = new Engine().setId(ENGINE_ID);
    }

    public String generateResponse(String message) throws ApiException {
        // Prepare the OpenAI request
        OpenaiRequest request = new OpenaiRequest();
        request.setPrompt(message);
        request.setMaxTokens(64);
        request.setTemperature(0.5);
        request.setEngine(engine.getId());
        request.setStop(Arrays.asList("\n"));

        // Call the OpenAI API
        Completion completion = engine.completions.createCompletion(request, auth);

        // Parse the response
        String[] choices = completion.getChoices().get(0).getText().split("\n");
        String response = choices[0].trim();

        return response;
    }
}
