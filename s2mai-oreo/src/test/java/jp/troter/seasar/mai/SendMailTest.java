/*
 * Copyright 2011 Takumi IINO
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.troter.seasar.mai;

import jp.troter.seasar.mai.MailSend.Mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.util.TextUtil;
import org.seasar.mai.mail.MailAddress;

@RunWith(Seasar2.class)
public class SendMailTest {

    private String mailAddress;

    @Test
    public void smoke() {
        send(new MailSendFactory(), MailCharset.UTF_8);
        send(new MailSendFactory(), MailCharset.ISO_2022_JP);
    }

    public void send(MailSendFactory factory, MailCharset charset) {
        MailSend sender = factory.getMailSender(charset);
        sender.sendMail(createMailForMultipart(mailAddress, mailAddress, "Multipartメールサンプル(" + charset.getCharset() + ")", TextUtil.readUTF8("sample.html"), TextUtil.readUTF8("sample.txt")));
        sender.sendMail(createMailForMultipart(mailAddress, mailAddress, "HTMLメールサンプル(" + charset.getCharset() + ")", TextUtil.readUTF8("sample.html"), null));
        sender.sendMail(createMailForMultipart(mailAddress, mailAddress, "TEXTメールサンプル(" + charset.getCharset() + ")", null, TextUtil.readUTF8("sample.txt")));
    }

    public Mail createMailForMultipart(String from, String to, String subject, String htmlBody, String textBody) {
        return createMail(from, to, subject, htmlBody, textBody);
    }

    public Mail createMail(String from, String to, String subject, String htmlBody, String textBody) {
        Mail m = new Mail();
        m.setFrom(new MailAddress(from));
        m.setTo(to);
        m.setSubject(subject);
        m.setHtmlBody(htmlBody);
        m.setTextBody(textBody);
        return m;
    }

}
