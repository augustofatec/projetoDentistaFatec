package controller;

/*
Nosso teste para enviar email via gmail
 */
import java.util.Properties;
import javax.mail.*;
//N�o funciona se n�o importar com o .internet depois
import javax.mail.internet.*;
import javax.swing.JOptionPane;

public class Notificar {

    //A gente pode tamb�m converter para ser uma fun��o, recebendo o email e ficando mais facil
    public void EnviarEmail(final Mensagem m) {
        Properties propriedades = new Properties();
        //Precisa desse tipo de variavel para configurar o email
        //Aqui em baixo as configura��es
        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.socketFactory.port", 465);
        //Pelo que entendi, n�o precisamos configurar um servidor, podemos usar esse, que � do google
        propriedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propriedades.put("mail.smtp.auth", true);
        propriedades.put("mail.smtp.port", 465);
        //Agora temos todas as propriedades de conex�o preparadas

        Session sess�o = Session.getDefaultInstance(propriedades,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(m.seuEmail, m.suaSenha);
            }
        });
        //Certo, tudo isso � um parametro do sess�o, � pra validar o email e come�ar o login no servidor
        sess�o.setDebug(true);
        //Vou falar que n�o fa�o ideia do porque temos que usar o setDebug
        //Mas agora precisamos utilizar um Try e um Catch, caso d� erro no email
        try {
            Message mensagem = new MimeMessage(sess�o);
            //A gente cria um corpo de mensagem, especificando o email que vai mandar
            mensagem.setFrom(new InternetAddress(m.seuEmail));
            //Remetente
            Address[] quemRecebe = InternetAddress.parse(m.emailRecebe);
            //Destinatario
            mensagem.setRecipients(Message.RecipientType.TO, quemRecebe);
            mensagem.setSubject(m.assunto); //A gente pode customizar o assunto
            mensagem.setText(m.corpo); //Corpo da mensagem
            Transport.send(mensagem);
            JOptionPane.showMessageDialog(null, "EMAIL ENVIADO COM SUCESSO");

        } catch (MessagingException e) {
            //Se por acaso der erro na mensagem, ele vem aqui e gera o erro
            JOptionPane.showMessageDialog(null, "ALGUM ERRO OCORREU");
            throw new RuntimeException(e);
        }
    }
    public void MassNot(Pacientes[] cadastro) {
        Mensagem m = new Mensagem();
        m.seuEmail = JOptionPane.showInputDialog("DIGITE SEU EMAIL");
        m.suaSenha = JOptionPane.showInputDialog("DIGITE SUA SENHA");
        for (int i = 0; i < cadastro.length; i++) {
            m.emailRecebe = cadastro[i].email;
            m.assunto = "SUA CONSULTA";
            m.corpo = "SUA CONSULTA SOBRE: " + cadastro[i].atividade + " DO DIA " + cadastro[i].data + " FOI CANCELADA";
            EnviarEmail(m);
        }
    }
}
