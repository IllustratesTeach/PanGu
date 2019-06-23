package nirvana.hall.webservice;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import cherish.component.jpa.ImageData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KryoListConvertUtils {

    public static  byte[] serializationList(List<ImageData> obj) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(ImageData.class, new JavaSerializer());
        //serializer.setElementClass(ImageData.class, new BeanSerializer(kryo,ImageData.class));
        serializer.setElementsCanBeNull(false);

        kryo.register(ImageData.class, new JavaSerializer());
        //kryo.register(ImageData.class, new BeanSerializer(kryo,ImageData.class));
        kryo.register(ArrayList.class, serializer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();

        byte[] b = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public static  List<ImageData> deserializationList(byte[] data) {
        Kryo kryo = new Kryo();
        kryo.setReferences(false);
        kryo.setRegistrationRequired(true);

        CollectionSerializer serializer = new CollectionSerializer();
        serializer.setElementClass(ImageData.class, new BeanSerializer(kryo,ImageData.class));
        serializer.setElementsCanBeNull(false);

        kryo.register(ImageData.class, new BeanSerializer(kryo,ImageData.class));
        kryo.register(ArrayList.class, serializer);

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        Input input = new Input(bais);
        return (List<ImageData>) kryo.readObject(input, ArrayList.class, serializer);
    }
}
