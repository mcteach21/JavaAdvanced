package mc.apps.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import mc.apps.modele.People;
import mc.apps.modele.User;
import mc.apps.modele.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static mc.apps.tools.Utils.ResourcesPath;

/**
 * JAXB : Java Architecture for Xml Binding
 */
public class JAXBUtils {

    private static final String USERS_XML_FILE_NAME = "users.xml";

    public static void Demo(){
        /**
         * Marchalling / UnMarchalling..
         */

        List<User> users = UserRepository.list();
        People people = new People("Geeks", users);

        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(People.class);

            // (1) Marshaller : Java Object to XML content.

            Marshaller marchaller = context.createMarshaller();
            marchaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write to System.out
            marchaller.marshal(people, System.out);

            // Write to File
            String Xml_File_Path = ResourcesPath()+"/"+USERS_XML_FILE_NAME;
            File outFile = new File(Xml_File_Path);
            marchaller.marshal(people, outFile);

            System.out.println("Xml file : " + outFile.getAbsolutePath());


            // (2) Unmarshaller : Read XML content to Java Object.
            Unmarshaller um = context.createUnmarshaller();

            // XML file create before.
            people = (People) um.unmarshal(new FileReader(Xml_File_Path));
            people.getUsers().forEach(System.out::println);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
