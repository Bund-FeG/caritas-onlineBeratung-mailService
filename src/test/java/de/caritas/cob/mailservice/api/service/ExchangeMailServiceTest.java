package de.caritas.cob.mailservice.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import de.caritas.cob.mailservice.api.exception.ExchangeMailServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeMailServiceTest {

  public final String SENDER = "name@domain.de";
  public final String RECIPIENT = "name@domain.de";
  public final String SUBJECT = "subject";
  public final String TEMPLATE = "test";
  public final String BODY = "test";

  public final String EXCHANGE_USER_FIELD_NAME = "exchangeUser";
  public final String EXCHANGE_PASSWORD_FIELD_NAME = "exchangePassword";
  public final String EXCHANGE_URL_FIELD_NAME = "exchangeUrl";
  public final String EXCHANGE_VERSION_FIELD_NAME = "exchangeVersion";
  public final String EXCHANGE_USER_VALUE = "dummyUser";
  public final String EXCHANGE_PASSWORD_VALUE = "dummyPassword";
  public final String EXCHANGE_URL_VALUE = "dummyULR";
  public final String EXCHANGE_VERSION_VALUE = "Exchange2007_SP1";

  private ExchangeMailService mailService;

  @Before
  public void setup() throws Exception {
    this.mailService = new ExchangeMailService();
    FieldSetter.setField(this.mailService,
        this.mailService.getClass().getDeclaredField(EXCHANGE_USER_FIELD_NAME),
        EXCHANGE_USER_VALUE);
    FieldSetter.setField(this.mailService,
        this.mailService.getClass().getDeclaredField(EXCHANGE_PASSWORD_FIELD_NAME),
        EXCHANGE_PASSWORD_VALUE);
    FieldSetter.setField(this.mailService,
        this.mailService.getClass().getDeclaredField(EXCHANGE_URL_FIELD_NAME), EXCHANGE_URL_VALUE);
    FieldSetter.setField(this.mailService,
        this.mailService.getClass().getDeclaredField(EXCHANGE_VERSION_FIELD_NAME),
        EXCHANGE_VERSION_VALUE);
  }

  @Test
  public void prepareAndSendHtmlMail_Should_ThrowServiceException_When_SenderMailAddressIsNotSet() {
    try {
      mailService.prepareAndSendHtmlMail(RECIPIENT, SUBJECT, TEMPLATE, null);
      fail("Expected exception: ServiceException");
    } catch (ExchangeMailServiceException serviceException) {
      assertTrue("Excepted ServiceException thrown", true);
      assertEquals("No sender mail address set", serviceException.getMessage());
    }
  }

  @Test
  public void prepareAndSendHtmlMail_Should_ThrowServiceException_When_MailCouldNotBeSend()
      throws NoSuchFieldException, SecurityException {

    FieldSetter.setField(mailService, mailService.getClass().getDeclaredField("mailSender"),
        String.valueOf(SENDER));

    try {
      mailService.prepareAndSendHtmlMail(RECIPIENT, SUBJECT, TEMPLATE, null);
      fail("Expected exception: ServiceException");
    } catch (ExchangeMailServiceException serviceException) {
      assertTrue("Excepted ServiceException thrown", true);
      assertEquals("Error while sending email", serviceException.getMessage());
    }
  }

  @Test
  public void prepareAndSendTextMail_Should_ThrowServiceException_When_SenderMailAddressIsNotSet() {
    try {
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
      fail("Expected exception: ServiceException");
    } catch (ExchangeMailServiceException serviceException) {
      assertTrue("Excepted ServiceException thrown", true);
      assertEquals("No sender mail address set", serviceException.getMessage());
    }
  }

  @Test
  public void prepareAndSendTextMail_Should_ThrowServiceException_When_MailCouldNotBeSend()
      throws NoSuchFieldException, SecurityException {

    FieldSetter.setField(mailService, mailService.getClass().getDeclaredField("mailSender"),
        String.valueOf(SENDER));

    try {
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
      fail("Expected exception: ServiceException");
    } catch (ExchangeMailServiceException serviceException) {
      assertTrue("Excepted ServiceException thrown", true);
      assertEquals("Error while sending email", serviceException.getMessage());
    }
  }

  @Test(expected = ExchangeMailServiceException.class)
  public void prepareAndSendTextMail_Should_ThrowExchangeMailServiceException_When_MailUrlIsInvalid()
      throws NoSuchFieldException, ExchangeMailServiceException {
    FieldSetter.setField(this.mailService,
        this.mailService.getClass().getDeclaredField(EXCHANGE_URL_FIELD_NAME), "Invalid");
    mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
  }

  @Test(expected = ExchangeMailServiceException.class)
  public void prepareAndSendTextMail_Should_ThrowExchangeMailServiceException_When_ParametersAreNull()
      throws ExchangeMailServiceException, NoSuchFieldException {
    FieldSetter.setField(mailService, mailService.getClass().getDeclaredField("mailSender"),
        SENDER);
    mailService.prepareAndSendTextMail(null, null, null);
  }

}
