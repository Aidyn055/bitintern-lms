package BitlabAcademycom.example.bitIntern.service.impl;


import BitlabAcademycom.example.bitIntern.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${keycloak.auth-server-url:http://localhost:8081}")
    private String authServerUrl;

    @Value("${keycloak.realm:bitintern-realm}")
    private String realm;

    @Value("${keycloak.client-id:bitintern-client}")
    private String clientId;

    @Value("${keycloak.client-secret:sgvsldxzrOtkAeDzm3LjRXwenCC55NiB}")
    private String clientSecret;

    public JwtResponse login(LoginRequest loginRequest) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", loginRequest.getUsername());
        formData.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                JwtResponse jwtResponse = new JwtResponse();
                jwtResponse.setAccessToken((String) body.get("access_token"));
                jwtResponse.setRefreshToken((String) body.get("refresh_token"));
                jwtResponse.setExpiresIn(((Number) body.get("expires_in")).longValue());
                jwtResponse.setRefreshExpiresIn(((Number) body.get("refresh_expires_in")).longValue());
                return jwtResponse;
            }
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.out.println("=== КРИТИЧЕСКАЯ ОШИБКА ОТ KEYCLOAK ===");
            System.out.println("Статус код: " + e.getStatusCode());
            System.out.println("Тело ответа: " + e.getResponseBodyAsString());
            System.out.println("======================================");

            throw new RuntimeException("Keycloak вернул ошибку: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Общая ошибка: " + e.getMessage());
        }

        throw new RuntimeException("Не удалось получить токен от Keycloak");
    }

    public JwtResponse refreshToken(TokenRefreshRequest refreshRequest) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshRequest.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            Map<String, Object> body = response.getBody();

            if (body != null && response.getStatusCode().is2xxSuccessful()) {
                JwtResponse jwtResponse = new JwtResponse();
                jwtResponse.setAccessToken((String) body.get("access_token"));
                jwtResponse.setRefreshToken((String) body.get("refresh_token"));
                jwtResponse.setExpiresIn(((Number) body.get("expires_in")).longValue());
                jwtResponse.setRefreshExpiresIn(((Number) body.get("refresh_expires_in")).longValue());
                return jwtResponse;
            }
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.out.println("=== ОШИБКА REFRESH TOKEN В KEYCLOAK ===");
            System.out.println("Тело ответа: " + e.getResponseBodyAsString());
            throw new RuntimeException("Keycloak не смог обновить токен: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Общая ошибка при обновлении токена: " + e.getMessage());
        }

        throw new RuntimeException("Не удалось обновить токен через Keycloak");
    }

    private String getAdminToken() {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
        return (String) response.getBody().get("access_token");
    }

    public void registerUser(UserRegisterRequest registerRequest) {
        String adminToken = getAdminToken();
        String usersUrl = authServerUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        Map<String, Object> userBody = new java.util.HashMap<>();
        userBody.put("username", registerRequest.getUsername());
        userBody.put("email", registerRequest.getEmail());
        userBody.put("firstName", registerRequest.getFirstName());
        userBody.put("lastName", registerRequest.getLastName());
        userBody.put("enabled", true);

        Map<String, Object> credential = new java.util.HashMap<>();
        credential.put("type", "password");
        credential.put("value", registerRequest.getPassword());
        credential.put("temporary", false);
        userBody.put("credentials", java.util.List.of(credential));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(userBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(usersUrl, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String locationHeader = response.getHeaders().getLocation().getPath();
                String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);

                String roleUrl = authServerUrl + "/admin/realms/" + realm + "/roles/" + registerRequest.getRole();
                ResponseEntity<Map> roleResponse = restTemplate.exchange(
                        roleUrl, org.springframework.http.HttpMethod.GET, new HttpEntity<>(headers), Map.class
                );

                Map<String, Object> roleMap = roleResponse.getBody();
                String roleId = (String) roleMap.get("id");
                String roleName = (String) roleMap.get("name");

                String mappingUrl = authServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";

                Map<String, Object> roleMapping = new java.util.HashMap<>();
                roleMapping.put("id", roleId);
                roleMapping.put("name", roleName);

                HttpEntity<java.util.List<Map<String, Object>>> mappingEntity =
                        new HttpEntity<>(java.util.List.of(roleMapping), headers);

                restTemplate.postForEntity(mappingUrl, mappingEntity, String.class);
                System.out.println("Роль " + roleName + " успешно назначена пользователю!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка регистрации или назначения роли: " + e.getMessage());
        }
    }

    public void updateUser(String userId, UserUpdateRequest updateRequest, boolean isAdmin) {
        String adminToken = getAdminToken();
        String userUrl = authServerUrl + "/admin/realms/" + realm + "/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        if (updateRequest.getRole() != null && !isAdmin) {
            throw new org.springframework.security.access.AccessDeniedException("У вас нет прав для изменения роли!");
        }

        Map<String, Object> updateBody = new java.util.HashMap<>();
        if (updateRequest.getEmail() != null) updateBody.put("email", updateRequest.getEmail());
        if (updateRequest.getFirstName() != null) updateBody.put("firstName", updateRequest.getFirstName());
        if (updateRequest.getLastName() != null) updateBody.put("lastName", updateRequest.getLastName());

        if (updateRequest.getPassword() != null) {
            Map<String, Object> credential = new java.util.HashMap<>();
            credential.put("type", "password");
            credential.put("value", updateRequest.getPassword());
            credential.put("temporary", false);
            updateBody.put("credentials", java.util.List.of(credential));
        }

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(updateBody, headers);

        try {
            restTemplate.exchange(userUrl, org.springframework.http.HttpMethod.PUT, entity, Void.class);

            if (updateRequest.getRole() != null && isAdmin) {
                String roleUrl = authServerUrl + "/admin/realms/" + realm + "/roles/" + updateRequest.getRole();
                ResponseEntity<Map> roleResponse = restTemplate.exchange(roleUrl, org.springframework.http.HttpMethod.GET, new HttpEntity<>(headers), Map.class);
                Map<String, Object> roleMap = roleResponse.getBody();

                String roleId = (String) roleMap.get("id");
                String roleName = (String) roleMap.get("name");

                String mappingUrl = authServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
                Map<String, Object> roleMapping = new java.util.HashMap<>();
                roleMapping.put("id", roleId);
                roleMapping.put("name", roleName);

                HttpEntity<java.util.List<Map<String, Object>>> mappingEntity = new HttpEntity<>(java.util.List.of(roleMapping), headers);
                restTemplate.postForEntity(mappingUrl, mappingEntity, String.class);
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении данных в Keycloak: " + e.getMessage());
        }
    }
}