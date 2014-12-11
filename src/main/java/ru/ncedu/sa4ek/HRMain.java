package ru.ncedu.sa4ek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sa4ek on 16.11.14.
 */
public class HRMain {
    public static void main(String[] args) throws IOException {
        XMLWorker worker = new XMLWorker("file.xml");
        worker.printXML("");
        System.out.println("Actions: find <name>, add, modify <id>, delete <id>, exit");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            args = reader.readLine().split("\\s");

            if (args[0].equals("find")) {
                if(args.length < 2) worker.printXML("");
                else worker.printXML(args[1]);
            }
            else if (args[0].equals("add")) {
                System.out.println("Please enter: <name> <position> <office> <tel> <salary>");
                String[] request = reader.readLine().split("\\s");
                worker.addXMLElement(request[0], request[1], request[2], request[3], Double.parseDouble(request[4]));
            }
            else if (args[0].equals("modify")) {
                System.out.println("Please enter: <name> <position> <office> <tel> <salary>");
                String[] request = reader.readLine().split("\\s");
                worker.modifyXMLElement(Integer.parseInt(args[1]),request[0], request[1], request[2], request[3], Double.parseDouble(request[4]));
            }
            else if (args[0].equals("delete")) {
                worker.deleteXMLElement(Integer.parseInt(args[1]));
            }
            else if (args[0].equals("exit")) {
                System.exit(0);
            }
            else{
                System.out.println("Warning! Actions: find <name>, add, modify <id>, delete <id>, exit");
                continue;
            }
        }
    }
}
