package estacionamentos;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class Serializador {

    public Serializador(){}

    /**
     * Método que serializa objetos, ou seja, transforma o objeto em bytes
     * @param path do tipo String, contém o caminho para o arquivo onde será armazenado o Objeto convertido em bytes
     * @param object do tipo Object, contém o Objeto que iremos transformar em bytes para armazenamento
     * @throws Exception 
     */
    public void serializar(String path, Object object) throws Exception{
        FileOutputStream outFile = new FileOutputStream(path);
        ObjectOutputStream serializar = new ObjectOutputStream(outFile);
        serializar.writeObject(object);
        serializar.close();    
        System.out.println("Objeto serializado!");    
    }
    
}