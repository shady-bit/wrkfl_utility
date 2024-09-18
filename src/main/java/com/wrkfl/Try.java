import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);

        ObjectMapper mapper = new ObjectMapper();
        try {
            // Path to the original JSON file
            File jsonFile = new File("src/main/resources/file.json");
            byte[] jsonData = Files.readAllBytes(Paths.get("src/main/resources/file.json"));
            List<ObjectNode> objectNodes = mapper.readValue(jsonData, new TypeReference<List<ObjectNode>>() {});

            for (ObjectNode node : objectNodes) {
                // Extract XML string
                String xmlData = node.path("xmlData").asText();
                System.out.println("Original XML Data: " + xmlData);

                // Validate and format XML
                String validatedXmlData = validateAndFormatXml(xmlData);

                // Modify XML
                String updatedXmlData = updateXml(validatedXmlData);

                // Update JSON object with modified XML data
                node.put("xmlData", updatedXmlData);
            }

            // Path to the new file
            File newJsonFile = new File("src/main/resources/updated_file.json");

            // Write updated JSON array to the new file
            mapper.writerWithDefaultPrettyPrinter().writeValue(newJsonFile, objectNodes);
            System.out.println("Updated JSON saved to new file: " + newJsonFile.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String validateAndFormatXml(String xmlData) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));
            document.getDocumentElement().normalize();
            // Convert Document back to a string (requires a Transformer)
            return convertDocumentToString(document);
        } catch (Exception e) {
            e.printStackTrace();
            return xmlData; // Return original if validation fails
        }
    }

    private static String convertDocumentToString(Document doc) {
        try {
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            java.io.StringWriter writer = new java.io.StringWriter();
            javax.xml.transform.StreamResult result = new javax.xml.transform.StreamResult(writer);
            transformer.transform(source, result);
            return writer.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String updateXml(String xmlData) {
        // Example XML modification
        // For simplicity, let's just append a comment to the XML
        return xmlData + "\n<!-- Updated -->";
    }
}
