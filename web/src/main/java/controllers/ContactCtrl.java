package controllers;

import database.dto.ContactMeDao;
import em.server.HttpResponse;
import em.server.annotations.FormParam;
import em.server.annotations.HttpMap;
import em.server.enums.HttpMethod;
import entities.ContactMe;
import services.EmailSender;

import java.sql.SQLException;
import java.util.List;

@HttpMap(method = HttpMethod.ANY, path = "")
public class ContactCtrl {

    @HttpMap(method = HttpMethod.POST, path = "/contactMe")
    public void contactMe(@FormParam(name = "name") String name,
                          @FormParam(name = "email") String email,
                          @FormParam(name = "phone") String phone,
                          @FormParam(name = "message") String message){
        ContactMe contactMe = new ContactMe(name, email, phone, message);
        try {
            ContactMeDao.getInstance().insert(contactMe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EmailSender.sendContactMeMessage(contactMe);
    }

    @HttpMap(method = HttpMethod.GET, path = "/contactMe")
    public void contactMePage(HttpResponse response){
        List<ContactMe> contactMeList = ContactMeDao.getInstance().getAll();
        int i = 0;
    }


}
