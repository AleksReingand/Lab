import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.airdoctor.tools.database.dto.PersonDto;
import com.airdoctor.tools.database.enums.PolicyStatusEnum;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.airdoctor.tools.database.dto.AggregatorDto;
import com.airdoctor.tools.database.dto.CreditCardDto;
import com.airdoctor.tools.database.dto.IdDto;
import com.airdoctor.tools.database.dto.InsurancePolicyWithPeopleDto;
import com.airdoctor.tools.database.dto.ProfileRevisionDto;
import com.airdoctor.tools.database.dto.TokenStatusDto;
import com.airdoctor.tools.database.dto.UserUpdateDto;
import com.airdoctor.tools.database.enums.InsuranceIdFieldsEnum;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

public class HandlerData
{
    private static final String BASE_URL = "http://test.eu-west-3.compute.amazonaws.com/";
//        private static final String BASE_URL = "http://localhost:5000/";
    private static final String urlToken = BASE_URL + "api/v2/token"; // API to create new subscriber
    private static final String urlPolicies = BASE_URL + "api/v2/policies"; // API to create insurance police for user
    private static final String urlPoliciesUse = BASE_URL + "api/v2/token/policies"; // API to add policies to use
    private static final String urlProfile = BASE_URL + "api/v2/profiles"; // API to create new profile
    private static final String urlProfilePublish = BASE_URL + "api/v2/profiles/publish"; // API to publish list profile
    private static final String urlAggregators = BASE_URL + "api/v2/aggregators"; // API to create new aggregator
    private static final String urlUpdateCreditCard = BASE_URL + "api/v2/token/credit-card"; // API to create credit card
    private static final String urlUserOutsideOfPolicy = BASE_URL + "api/v2/token/people"; // API for the user outside of policy

    public static void handler()
    {
        DbManager dbManager = new DbManager();
        String token = dbManager.fetchAdminToken();

        Map<UserUpdateDto, List<InsurancePolicyWithPeopleDto>> subscribers = FactoryDto.createUsers();

        Map<ProfileRevisionDto, AggregatorDto> profiles = FactoryDto.createProfiles();

        List<CreditCardDto> cards = FactoryDto.createCreditCard();

        addSubscribersToDb(token, subscribers);

        List<Integer> ids = addProfilesToDb(token, profiles, dbManager);

        publishProfiles(token, ids);

        addCreditCartToDb(token, cards);
    }

    @SneakyThrows
    private static void addCreditCartToDb(String token, List<CreditCardDto> cards)
    {
        HttpURLConnection con = httpURLConnection(urlUpdateCreditCard, "", token);

        for(CreditCardDto card : cards)
        {
            OutputStream os = con.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            osw.write(mapper.writeValueAsString(card));
            osw.flush();
            osw.close();
            os.close();

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + urlUpdateCreditCard);
            System.out.println("Response Code : " + responseCode);

            if (responseCode == 200)
                System.out.println("Credit card: " + card.getCardSuffix() + " was updated");
            else
                System.out.println("Credit Card Request Failed " + responseCode);
        }

        con.disconnect();
    }

    @SneakyThrows
    private static void publishProfiles(String token, List<Integer> ids)
    {
        if(!ids.isEmpty())
        {
            int responseCode = httpURLConnection(urlProfilePublish, encodeList(ids), token).getResponseCode();
            System.out.println("Sending 'POST' request to URL : " + urlProfilePublish);
            System.out.println("Post parameters : " + encodeList(ids));
            System.out.println("Response Code : " + responseCode);

            if (responseCode == 200)
                System.out.println("Profiles: " + Arrays.toString(ids.toArray()) + " were published");
            else
                System.out.println("Publish Request Failed " + responseCode);
        }
    }

    @SneakyThrows
    private static List<Integer> addProfilesToDb(String token, Map<ProfileRevisionDto, AggregatorDto> profiles, DbManager dbManager)
    {
        List<Integer> ids = new ArrayList<>();

        for(Map.Entry<ProfileRevisionDto, AggregatorDto> profile : profiles.entrySet())
        {
            if(profile.getValue() != null)
            {
                int id = 0;
                if(dbManager.isExistAggregator(profile.getValue().getName()) != null)
                {
                    id = Integer.parseInt(dbManager.isExistAggregator(profile.getValue().getName()));
                }
                else
                {
                    ResponseEntity<String> responseAggregator = pullAPI(profile.getValue(), urlAggregators, token);

                    if(responseAggregator.getStatusCode() == HttpStatus.OK)
                    {
                        IdDto idDto = new ObjectMapper().readValue(responseAggregator.getBody(), IdDto.class);
                        id = idDto.getId();
                    }
                    else
                        System.out.println("Aggregator Request Failed " + responseAggregator.getStatusCode());
                }


                profile.getKey().setAggregatorId(id);
                System.out.println("Set Aggregator ID into profile");
            }

            ResponseEntity<String> responseProfile = pullAPI(profile.getKey(), urlProfile, token);

            if (responseProfile.getStatusCode() == HttpStatus.OK)
            {
                IdDto id = new ObjectMapper().readValue(responseProfile.getBody(), IdDto.class);
                ids.add(id.getId());

                System.out.println("Profile: ID - " + id.getId() + " - " + profile.getKey().getPseudonym() + " was added to DB");
            }
            else
                System.out.println("Profile Request Failed " + responseProfile.getStatusCode());
        }

        return ids;
    }

    @SneakyThrows
    private static void addSubscribersToDb(String token, Map<UserUpdateDto, List<InsurancePolicyWithPeopleDto>> subscribers)
    {
        for(Map.Entry<UserUpdateDto, List<InsurancePolicyWithPeopleDto>> entry : subscribers.entrySet())
        {
            ResponseEntity<String> responseUser = pullAPI(entry.getKey(), urlToken, token);

            if (responseUser.getStatusCode() == HttpStatus.OK)
            {
                System.out.println("User " + entry.getKey().getEmail() + " was added to DB");
                if(entry.getValue() != null && !entry.getValue().isEmpty())
                {
                    addPoliciesToDb(token, entry.getValue());
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    TokenStatusDto tokenStatusDto = mapper.readValue(responseUser.getBody(), TokenStatusDto.class);
                    addPoliciesUse(tokenStatusDto, entry.getValue());
                }
                else
                {
                    PersonDto person = PersonDto.builder()
                            .personId(0)
                            .policyId(0)
                            .status(PolicyStatusEnum.ACTIVE)
                            .firstName(entry.getKey().getFirstName())
                            .lastName(entry.getKey().getLastName())
                            .gender(entry.getKey().getGender())
                            .birthday(entry.getKey().getBirthday()).build();

                    addPersonToInsuranceDetails(person, token);
                }
            }
            else
                System.out.println("User Request Failed " + responseUser.getStatusCode());
        }
    }

    private static void addPersonToInsuranceDetails(PersonDto person, String token)
    {
        ResponseEntity<String> responsePerson = pullAPI(person, urlUserOutsideOfPolicy, token);

        if (responsePerson.getStatusCode() == HttpStatus.OK)
        {
            System.out.println("Person " + person.getFirstName() + " " + person.getLastName() + " was added to Insurance details");
        }
        else
        {
            System.out.println("Person Request Failed " + person.getFirstName() + " " + person.getLastName() + " " + responsePerson.getStatusCode());
        }
    }

    private static void addPoliciesUse(TokenStatusDto tokenStatusDto, List<InsurancePolicyWithPeopleDto> policies)
    {
        HashMap<InsuranceIdFieldsEnum, String> map = new HashMap<>();

        String policyNumber = null;

        for(InsurancePolicyWithPeopleDto policy : policies)
        {
            if(policyNumber == null || !policyNumber.equals(policy.getPolicyNumber()))
            {
                policyNumber = policy.getPolicyNumber();
                map.put(InsuranceIdFieldsEnum.COMPANY, policy.getCompanyId() + "");
                map.put(InsuranceIdFieldsEnum.POLICY_NUMBER, policy.getPolicyNumber());
                if(!policy.getPeople().isEmpty())
                {
                    map.put(InsuranceIdFieldsEnum.DATE_OF_BIRTH, policy.getPolicyIdentifier());
                    map.put(InsuranceIdFieldsEnum.PERSONAL_ID_NUMBER, policy.getPeople().get(0).getPersonalIdNumber());
                    map.put(InsuranceIdFieldsEnum.FIRST_NAME, policy.getPeople().get(0).getFirstName());
                    map.put(InsuranceIdFieldsEnum.LAST_NAME, policy.getPeople().get(0).getLastName());
                    map.put(InsuranceIdFieldsEnum.GENDER, policy.getPeople().get(0).getGender().name());
                }
            }

            map.entrySet().removeIf(entry -> entry.getValue() == null);
        }

        ResponseEntity<String> responseProfile = pullAPI(map, urlPoliciesUse, tokenStatusDto.getToken());

        if (responseProfile.getStatusCode() == HttpStatus.OK)
            System.out.println("Policy: number - " + map.get(InsuranceIdFieldsEnum.POLICY_NUMBER) + " was added to Policy Use");
        else
            System.out.println("Policy to use Request Failed " + responseProfile.getStatusCode());
    }

    private static void addPoliciesToDb(String token, List<InsurancePolicyWithPeopleDto> policies)
    {
        ResponseEntity<String> responsePolicy = pullAPI(policies, urlPolicies, token);

        if(responsePolicy.getStatusCode() == HttpStatus.OK)
        {
            for(InsurancePolicyWithPeopleDto policy : policies)
            {
                System.out.println("Policy with number: " + policy.getPolicyNumber() + " was added to DB");
            }
        }
        else
            System.out.println("Policies Request Failed " + responsePolicy.getStatusCode());
    }

    private static String encodeList(List<Integer> ids)
    {
        if(ids == null || ids.size() == 0)
            return "";
        StringBuilder result = new StringBuilder("" + ids.get(0));
        for(int idx = 1; idx < ids.size(); idx++)
            result.append(",").append(ids.get(idx));
        return "?ids=" + result;
    }

    @SneakyThrows
    private static HttpURLConnection httpURLConnection(String urlRequest, String params, String token)
    {
        URL url = new URL(urlRequest + params);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("X-Token", token);
        con.setDoOutput(true);

        return con;
    }

    @SneakyThrows
    private static ResponseEntity<String> pullAPI(Object obj, String url, String token)
    {
        // Web Spring RestTemplate for calling API
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Token", token);

        // Jackson functional for transfer object to JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(obj), headers);

        return restTemplate.postForEntity(url, request, String.class);
    }
}