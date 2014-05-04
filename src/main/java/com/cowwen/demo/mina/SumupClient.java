package com.cowwen.demo.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * Created by cowwen on 2014/4/22.
 */
public class SumupClient {
    public static final long TIME_OUT = 10000;
    public static final boolean USE_CUSTOM_CODEC = false;

    public static void main(String[] args) {
        NioSocketConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(TIME_OUT);

        if (USE_CUSTOM_CODEC) {

        } else {
            connector.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        }

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        //connector.setHandler(new ClientSessionHandler(values));
        IoSession session;
    }
}
