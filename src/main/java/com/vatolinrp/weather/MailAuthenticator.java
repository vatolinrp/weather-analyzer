package com.vatolinrp.weather;

import com.vatolinrp.weather.util.StormConstants;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator implements StormConstants{

  @Override
  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication( MAIL_FROM, MAIL_FROM_PASSWORD );
  }
}
