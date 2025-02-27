package kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay;

import com.google.gson.Gson;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.request.*;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.ResToken;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.cancel.CancelData;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.cancel.CancelResponse;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.verify.VerifyData;
import kr.nzzi.msa.pmg.pomangamapimonilith.global.util.bootpay.model.response.verify.VerifyResponse;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.util.List;

public class BootpayApi {
    private final String BASE_URL = "https://api.bootpay.co.kr/";
    private final String URL_ACCESS_TOKEN = BASE_URL + "request/token.json";
    private final String URL_VERIFY = BASE_URL + "receipt";
    private final String URL_CANCEL = BASE_URL + "cancel.json";

    private final String URL_GET_SUBSCRIBE_BILLING_KEY = BASE_URL + "request/card_rebill.json";
    private final String URL_DESTROY_SUBSCRIBE_BILLING_KEY = BASE_URL + "subscribe/billing/";
    private final String URL_SUBSCRIBE_BILLING = BASE_URL + "subscribe/billing.json";
    private final String URL_SUBSCRIBE_BILLING_RESERVE = BASE_URL + "subscribe/billing/reserve.json";
    private final String URL_SUBSCRIBE_BILLING_RESERVE_CANCEL = BASE_URL + "subscribe/billing/reserve/";
    private final String URL_GET_USER_TOKEN = BASE_URL + "request/user/token";
    private final String URL_PAYMENT_LINK = BASE_URL + "request/payment";
    private final String URL_CERTIFICATE = BASE_URL + "certificate/";

    private String token;
    private final String application_id = "5cc70f38396fa67747bd0686";
    private final String private_key = "/LokN9TIOXtFpmIo9iUEl76GllpuKljfbR0ndCviINU=";

    public void setToken(String token) {
        this.token = token;
    }

    private HttpGet getGet(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        URI uri = new URIBuilder(get.getURI()).build();
        get.setURI(uri);
        return get;
    }

    private HttpGet getGet(String url, List<NameValuePair> nameValuePairList) throws Exception {
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json");
        get.setHeader("Content-Type", "application/json");
        get.setHeader("Accept-Charset", "utf-8");
        URI uri = new URIBuilder(get.getURI()).addParameters(nameValuePairList).build();
        get.setURI(uri);
        return get;
    }

    private HttpPost getPost(String url, StringEntity entity) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept-Charset", "utf-8");
        post.setEntity(entity);
        return post;
    }

    private HttpDelete getDelete(String url) {
        HttpDelete delete = new HttpDelete(url);
        delete.setHeader("Accept", "application/json");
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("Accept-Charset", "utf-8");
        return delete;
    }

    public void getAccessToken() {
        try {
            if(this.application_id == null || this.application_id.isEmpty()) throw new Exception("application_id 값이 비어있습니다.");
            if(this.private_key == null || this.private_key.isEmpty()) throw new Exception("private_key 값이 비어있습니다.");

            Token token = new Token();
            token.application_id = this.application_id;
            token.private_key = this.private_key;

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = getPost(URL_ACCESS_TOKEN, new StringEntity(new Gson().toJson(token), "UTF-8"));

            HttpResponse res = client.execute(post);
            String str = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            ResToken resToken = new Gson().fromJson(str, ResToken.class);

            if(resToken.status == 200)
                this.token = resToken.data.token;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verify(String receipt_id, int paymentCost) {
        boolean isValid = false;
        try {
            if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = getGet(URL_VERIFY + "/" + receipt_id + ".json");
            get.setHeader("Authorization", this.token);

            HttpResponse res = client.execute(get);
            String str = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            System.out.println("verify res : "+str);

            VerifyResponse verifyResponse = new Gson().fromJson(str, VerifyResponse.class);
            if(verifyResponse.getStatus() == 200) {
                VerifyData verifyData = verifyResponse.getData();
                if(verifyData.getStatus() == 1 && verifyData.getPrice() == paymentCost) {
                    isValid = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public boolean cancel(Cancel cancel) {
        boolean isValid = false;
        try {
            if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = getPost(URL_CANCEL, new StringEntity(new Gson().toJson(cancel), "UTF-8"));
            post.setHeader("Authorization", this.token);
            HttpResponse res = client.execute(post);

            String str = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            System.out.println("verify res : "+str);

            CancelResponse cancelResponse = new Gson().fromJson(str, CancelResponse.class);
            if(cancelResponse.getStatus() == 200) {
                CancelData cancelData = cancelResponse.getData();
                if(cancelData.getCancelled_price() == cancel.getPrice().intValue()) {
                    isValid = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public HttpResponse get_subscribe_billing_key(SubscribeBilling subscribeBilling) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");
//         if(subscribeBilling.billing_key == null || subscribeBilling.billing_key.isEmpty()) throw new Exception("billing_key 값을 입력해주세요.");
        if(subscribeBilling.item_name == null || subscribeBilling.item_name.isEmpty()) throw new Exception("item_name 값을 입력해주세요.");
        if(subscribeBilling.order_id == null || subscribeBilling.order_id.isEmpty()) throw new Exception("order_id 주문번호를 설정해주세요.");
        if(subscribeBilling.pg == null || subscribeBilling.pg.isEmpty()) throw new Exception("결제하고자 하는 pg alias를 입력해주세요.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_GET_SUBSCRIBE_BILLING_KEY, new StringEntity(new Gson().toJson(subscribeBilling), "UTF-8"));
        post.setHeader("Authorization", this.token);
        return client.execute(post);
    }

    public HttpResponse destroy_subscribe_billing_key(String billing_key) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");
        if(billing_key == null || billing_key.isEmpty()) throw new Exception("billing_key 값이 비어있습니다.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = getDelete(URL_DESTROY_SUBSCRIBE_BILLING_KEY + billing_key + ".json");
        delete.setHeader("Authorization", this.token);
        return client.execute(delete);
    }

    public HttpResponse subscribe_billing(SubscribeBilling subscribeBilling) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");
        if(subscribeBilling.billing_key == null || subscribeBilling.billing_key.isEmpty()) throw new Exception("billing_key 값을 입력해주세요.");
        if(subscribeBilling.item_name == null || subscribeBilling.item_name.isEmpty()) throw new Exception("item_name 값을 입력해주세요.");
//        if(subscribeBilling.price <= 0) throw new Exception("price 금액을 설정을 해주세요.");
        if(subscribeBilling.order_id == null || subscribeBilling.order_id.isEmpty()) throw new Exception("order_id 주문번호를 설정해주세요.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_SUBSCRIBE_BILLING, new StringEntity(new Gson().toJson(subscribeBilling), "UTF-8"));
        post.setHeader("Authorization", this.token);
        return client.execute(post);
    }

    public HttpResponse subscribe_reserve_billing(SubscribeBillingReserve subscribeBillingReserve) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");
        if(subscribeBillingReserve.billing_key == null || subscribeBillingReserve.billing_key.isEmpty()) throw new Exception("billing_key 값을 입력해주세요.");
        if(subscribeBillingReserve.item_name == null || subscribeBillingReserve.item_name.isEmpty()) throw new Exception("item_name 값을 입력해주세요.");

        if(subscribeBillingReserve.order_id == null || subscribeBillingReserve.order_id.isEmpty()) throw new Exception("order_id 주문번호를 설정해주세요.");
        if(subscribeBillingReserve.execute_at == 0) throw new Exception("execute_at 실행 시간을 설정해주세요.");
        if(subscribeBillingReserve.scheduler_type == null || subscribeBillingReserve.scheduler_type.isEmpty()) subscribeBillingReserve.scheduler_type = "oneshot";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_SUBSCRIBE_BILLING_RESERVE, new StringEntity(new Gson().toJson(subscribeBillingReserve), "UTF-8"));
        post.setHeader("Authorization", this.token);
        return client.execute(post);
    }

    public HttpResponse subscribe_reserve_cancel(String reserve_id) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");
        if(reserve_id == null || reserve_id.isEmpty()) throw new Exception("reserve_id 값이 비어있습니다.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete delete = getDelete(URL_SUBSCRIBE_BILLING_RESERVE_CANCEL + reserve_id + ".json");
        delete.setHeader("Authorization", this.token);
        return client.execute(delete);
    }

    public HttpResponse getUserToken(User user) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");
        if(user == null)  throw new Exception("user 개체가 비어있습니다.");
        if(user.user_id == null || user.user_id.isEmpty())  throw new Exception("user_id 값이 비어있습니다.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_GET_USER_TOKEN, new StringEntity(new Gson().toJson(user), "UTF-8"));
        post.setHeader("Authorization", this.token);
        return client.execute(post);
    }

    public HttpResponse requestPayment(RequestLink requestPayment) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = getPost(URL_PAYMENT_LINK, new StringEntity(new Gson().toJson(requestPayment), "UTF-8"));
        post.setHeader("Authorization", this.token);
        return client.execute(post);
    }

    public HttpResponse certificate(String receipt_id) throws Exception {
        if(this.token == null || this.token.isEmpty()) throw new Exception("token 값이 비어있습니다.");

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = getGet(URL_CERTIFICATE + "/" + receipt_id + ".json");
        get.setHeader("Authorization", this.token);
        return client.execute(get);
    }
}
