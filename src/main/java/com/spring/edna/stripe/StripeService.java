package com.spring.edna.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.PaymentIntent;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String createConnectAccount(String email) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setCountry("BR")
                .setEmail(email)
                .build();

        Account account = Account.create(params);
        return account.getId();
    }

    public String createOnboardingLink(String accountId, String refreshUrl, String returnUrl) throws StripeException {
        AccountLinkCreateParams params = AccountLinkCreateParams.builder()
                .setAccount(accountId)
                .setRefreshUrl(refreshUrl)
                .setReturnUrl(returnUrl)
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();

        AccountLink accountLink = AccountLink.create(params);
        return accountLink.getUrl();
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency, String connectedAccountId) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // valor em centavos
                .setCurrency(currency)
                .setApplicationFeeAmount(amount * 10 / 100) // 10% de taxa
                .setTransferData(
                        PaymentIntentCreateParams.TransferData.builder()
                                .setDestination(connectedAccountId)
                                .build()
                )
                .build();

        return PaymentIntent.create(params);
    }
}
