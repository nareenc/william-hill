package functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class ReadXMLData {
    public String value;

    public String getHorseRaceData(String node) {
        return readData(node, "src/data/HorseRaceData.xml");
    }

    // If we have a separate xml for Greyhound test data
    public String getGreyhoundRaceData(String node) {
        return readData(node, "src/data/GreyhoundRaceData.xml");
    }

    private String readData(String node, String dataSource) {
        try {
            value = null;
            File file = new File(dataSource);
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.loadFromXML(fileInput);
            fileInput.close();
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                if (((String) enuKeys.nextElement()).contains(node)) {
                    value = properties.getProperty(node);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}