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
import com.google.gson.reflect.TypeToken;
import it.trmn.espertodeglionomastici.model.Onomastico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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

    String reqDate = (String) request.getParameter("date");

    String month = reqDate.substring(6,8);
    String day = reqDate.substring(9,11);

    Gson gson = new Gson();
    BufferedReader reader = null;
    try {
      Resource resource = new ClassPathResource("santi.json");
      InputStream resourceInputStream = resource.getInputStream();
      reader = new BufferedReader(new InputStreamReader(resourceInputStream));

    } catch (IOException e) {
      e.printStackTrace();
    }

    Type type = new TypeToken<Map<String, Onomastico>>(){}.getType();
    Map<String, Onomastico> onomasticiMap = gson.fromJson(reader, type);

    String date = month+day;
    Onomastico onomastico = onomasticiMap.get(date);

    StringBuilder response = new StringBuilder(onomastico.getSanto());

    response.append(". Altri santi sono: ");

    for (String altroSanto : onomastico.getAltri()){
      response.append(altroSanto).append(",");
    }


    ResponseBuilder responseBuilder = getResponseBuilder(request).add(response.toString()).endConversation();
    ActionResponse actionResponse = responseBuilder.build();
    LOGGER.info(actionResponse.toString());
    LOGGER.info("ASK_ONOMASTICO end.");
    return actionResponse;
  }
}