package controllers;

import em.server.HttpRequest;
import em.server.HttpResponse;
import em.server.annotations.HttpMap;
import em.server.enums.HttpMethod;
import entities.ContactMe;
import services.EmailSender;

@HttpMap(method = HttpMethod.POST, path = "")
public class ContactCtrl {

    @HttpMap(method = HttpMethod.POST, path = "/contactMe")
    public void contactMe(HttpRequest request, HttpResponse httpResponse){
        String name = request.getParam("name");
        String email = request.getParam("email");
        String phone = request.getParam("phone");
        String message = request.getParam("message");

        ContactMe contactMe = new ContactMe(name, email, phone, message);
        EmailSender.sendContactMeMessage(contactMe);

        int i = 0;
    }


}
