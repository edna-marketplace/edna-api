package com.spring.edna.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @Autowired
    private JavaMailSender mailSender;

    public String createConnectAccount(String email) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("BR")
                .setEmail(email)
                .build();

        Account account = Account.create(params);
        return account.getId();
    }

    public String createOnboardingLink(String email, String accountId) throws StripeException {
        String refreshUrl = "https://edna-web.vercel.app/signup/stripe-refresh";
        String returnUrl = "https://edna-web.vercel.app/signup/stripe-return";

        AccountLinkCreateParams params = AccountLinkCreateParams.builder()
                .setAccount(accountId)
                .setRefreshUrl(refreshUrl)
                .setReturnUrl(returnUrl)
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();

        AccountLink accountLink = AccountLink.create(params);

        sendOnboardingLinkEmail(email, accountLink.getUrl());

        return accountLink.getUrl();
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency, String connectedAccountId) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // valor em centavos
                .setCurrency(currency)
                .setApplicationFeeAmount(amount * 14 / 100) // 14% de taxa
                .setTransferData(
                        PaymentIntentCreateParams.TransferData.builder()
                                .setDestination(connectedAccountId)
                                .build()
                )
                .build();

        return PaymentIntent.create(params);
    }

    public Refund createFullRefund(String paymentIntentId) throws StripeException {
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntentId)
                .setReverseTransfer(true)
                .setRefundApplicationFee(true)
                .build();

        return Refund.create(params);
    }

    private void sendOnboardingLinkEmail(String email, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Edna Marketplace - Link para conexão com o Stripe");
        message.setText("Olá! " +
                "\nObrigado por se cadastrar em nossa plataforma. Para concluir seu cadastro e começar a utilizar os" +
                " nossos serviços, é necessário finalizar seu registro no Stripe. " +
                "\nVocê pode fazer isso acessando o link abaixo:" +
                "\n\n" + link +
                "\n\nSe você já completou o processo de conexão com o Stripe, ignore esse email." +
                "\n\nAtenciosamente," +
                "\nEquipe Edna Marketplace");

        mailSender.send(message);
    }
}
