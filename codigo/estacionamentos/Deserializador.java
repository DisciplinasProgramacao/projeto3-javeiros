package business;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Deserializador {

    public Deserializador(){}

    /**
     * Método que serializa objetos
     * @param path do tipo String, contém o caminho para o arquivo onde será armazenado o Objeto convertido em bytes
     * @param object do tipo Object, contém o Objeto que iremos transformar em bytes para armazenamento
     * @throws Exception 
     */

    /**
     * 
     * Método que deserializa objetos, ou seja, transforma bytes em objeto
     * @param path do tipo String, contém o caminho para o arquivo onde será armazenado o Objeto convertido em bytes
     * @return a transformação de bytes para objeto
     * @throws Exception
     */
    public Object deserializar(String path) throws Exception{
        FileInputStream inFile = new FileInputStream(path);
        ObjectInputStream by = new ObjectInputStream(inFile);
        Object object = by.readObject();
        by.close();
        return object;
    }    
}
