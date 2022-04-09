package com.sayingu.ews;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.CalendarView;
import microsoft.exchange.webservices.data.search.FindItemsResults;

public class EwsExample {
    public static void main(String[] args) {
        final Properties props = new Properties();
        try {
            props.load(EwsExample.class.getResourceAsStream("/auth.properties"));
        } catch (IOException e) {
            System.out.println(
                    "Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
            return;
        }
        final String email = props.getProperty("ews.email");
        final String password = props.getProperty("ews.password");

        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(email, password);
        service.setCredentials(credentials);
        try {
            service.autodiscoverUrl(email, new RedirectionUrlCallback());

            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            cal.add(Calendar.YEAR, -2);
            Date beforeTwoYear = cal.getTime();

            CalendarFolder cf = CalendarFolder.bind(service, WellKnownFolderName.Calendar);
            FindItemsResults<Appointment> findResults = cf.findAppointments(new CalendarView(beforeTwoYear, now));
            for (Appointment appt : findResults.getItems()) {
                appt.load(PropertySet.FirstClassProperties);
                System.out.println("SUBJECT=====" + appt.getSubject());
                System.out.println("BODY========" + appt.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }
}
