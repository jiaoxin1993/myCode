package netty.Serializable;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by admin on 2019/10/31.
 */
public class SerializableFactoryMarshalling {
    /*
        创建Jboss Marshalling解码器 MarshallingDecoder
        return MarshallingDecoder
     */
    public static MarshallingDecoder buildMarshallingDecoder(){
        //首先通过Marshalling工具类的静态方法获取Marshalling实例对象，参数serial标识创建的是Java序列化工厂对象
        //jboss-marshalling-serial 包提供
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建MarshallingConfiguration对象，配置了版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        //序列化版本，只要使用jdk5以上，Version只能定义为5
        configuration.setVersion(5);
        //根据MarshallerFactory和MarshallingConfiguration创建provider
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        //构建Netty的MarshallingDecoder对象，两个参数分别为provider和单个消息序列化后最大长度
        MarshallingDecoder decoder = new MarshallingDecoder(provider,1024*1024*1);
        return  decoder;
    }
    /*
        创建Jboss Marshalling编码器 MarshallingEncoder
        return MarshallingEncoder
     */
    public static MarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory,configuration);
        //构建Netty的MarshallingEncoder，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
