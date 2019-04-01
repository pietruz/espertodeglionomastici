package it.trmn.espertodeglionomastici;/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.gson.Gson;
import it.trmn.espertodeglionomastici.model.Onomastico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.List;

public class EspertodegliOnomasticiApp extends DialogflowApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(EspertodegliOnomasticiApp.class);

  // Note: Do not store any state as an instance variable.
  // It is ok to have final variables where the variable is assigned a value in
  // the constructor but remains unchanged. This is required to ensure thread-
  // safety as the entry point (ActionServlet/ActionsAWSHandler) instances may
  // be reused by the server.

  @ForIntent("ASK_ONOMASTICO")
  public ActionResponse askOnomastico(ActionRequest request) {
    LOGGER.info("ASK_ONOMASTICO start.");
    String response =
        "La data in input è "
            + request.getParameter("date");

    Gson gson = new Gson();
    BufferedReader reader = null;
    try {
      Resource resource = new ClassPathResource("santi.json");
      InputStream resourceInputStream = resource.getInputStream();
      reader = new BufferedReader(new InputStreamReader(resourceInputStream));

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<Onomastico> data = gson.fromJson(reader, List<Onomastico.class>.class);

/*    InputStream is =
            EspertodegliOnomasticiApp.class.getResourceAsStream( "santi.json");
    String jsonTxt = null;
    try {
      jsonTxt = IOUtils.toString( is );
    } catch (IOException e) {
      e.printStackTrace();
    }
    Gson gson = new Gson();
    GiornoOnomastico go= gson.fromJson(jsonTxt, GiornoOnomastico.class);*/
    System.out.println(data.get(0).getSanto());

    ResponseBuilder responseBuilder = getResponseBuilder(request).add(response).endConversation();
    ActionResponse actionResponse = responseBuilder.build();
    LOGGER.info(actionResponse.toString());
    LOGGER.info("ASK_ONOMASTICO end.");
    return actionResponse;
  }
}