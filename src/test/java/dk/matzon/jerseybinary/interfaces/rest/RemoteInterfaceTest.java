package dk.matzon.jerseybinary.interfaces.rest;

import dk.matzon.jerseybinary.JerseyBinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JerseyBinary.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RemoteInterfaceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLoadNoContent() throws Exception {
        ResponseEntity<byte[]> noContent = restTemplate.getForEntity(String.format("%s/%s", RemoteInterface.STORAGE, UUID.randomUUID().toString()), byte[].class);
        assertEquals(204, noContent.getStatusCode().value());
    }

    @Test
    void testSave() throws Exception {
        byte[] blob = "testSave".getBytes(StandardCharsets.UTF_8);
        HttpEntity<byte[]> request = new HttpEntity<>(blob);
        ResponseEntity<byte[]> saveResponse = restTemplate.postForEntity(String.format("%s/%s", RemoteInterface.STORAGE, UUID.randomUUID().toString()), request, byte[].class);
        assertTrue(saveResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testSaveAndLoad() {
        byte[] blob = "testSaveAndLoad".getBytes(StandardCharsets.UTF_8);
        HttpEntity<byte[]> request = new HttpEntity<>(blob);
        String key = UUID.randomUUID().toString();
        ResponseEntity<byte[]> saveResponse = restTemplate.postForEntity(String.format("%s/%s", RemoteInterface.STORAGE, key), request, byte[].class);
        assertTrue(saveResponse.getStatusCode().is2xxSuccessful());

        ResponseEntity<byte[]> content = restTemplate.getForEntity(String.format("%s/%s", RemoteInterface.STORAGE, key), byte[].class);
        assertTrue(content.getStatusCode().is2xxSuccessful());
        assertNotNull(content.getBody());

        assertEquals(new String(blob, StandardCharsets.UTF_8), new String(content.getBody(), StandardCharsets.UTF_8));
    }
}