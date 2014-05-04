package com.cowwen.demo.mina;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;

/**
 * Created by cowwen on 2014/5/4.
 */
public class Image2FileClient {
    public static final String HOSTNAME = "localhost";
    public static final int PORT = 33789;

    public static void main(String[] args) throws InterruptedException {
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ImageCodecFactory(true)));
        connector.setHandler(new ImageClientHandler());
        ImageRequest request = new ImageRequest(100,100, 10);

        IoSession session;
        for (;;) {
            try {
                ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
                future.awaitUninterruptibly();
                session = future.getSession();
                session.write(request);
                System.out.println("sent.");
                break;
            } catch (RuntimeIoException e) {
                System.err.println("Failed to connect.");
                e.printStackTrace();
                Thread.sleep(5000);
            }
        }
    }

    public static class ImageClientHandler extends IoHandlerAdapter {

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            ImageResponse response = (ImageResponse) message;
            FileOutputStream writeFile1 = new FileOutputStream("D:/image1.png");
            FileOutputStream writeFile2 = new FileOutputStream("D:/image2.png");
            ImageIO.write(response.getImage1(), "png", writeFile1);
            ImageIO.write(response.getImage2(), "png", writeFile2);
        }
    }
}
