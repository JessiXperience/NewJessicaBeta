package jessica.utils;

import java.net.*;
import java.io.*;

public class ProxyServer
{
    public static Socket client;
    public static Socket server;
    public static ServerSocket ss;
    
    static {
        ProxyServer.client = null;
        ProxyServer.server = null;
    }
    
    public static void proxyServer(final String host, final int remoteport) throws IOException {
        try {
            final int localport = 61245;
            runServer(host, remoteport, localport);
        }
        catch (Exception e) {
            try {
                ProxyServer.client.close();
                ProxyServer.server.close();
                ProxyServer.ss.close();
                ProxyServer.client = null;
                ProxyServer.server = null;
                ProxyServer.ss = null;
            }
            catch (Exception ex) {}
        }
    }
    
    public static void runServer(final String host, final int remoteport, final int localport) throws IOException {
        ProxyServer.ss = new ServerSocket(localport);
        final byte[] request = new byte[1024];
        final byte[] reply = new byte[4096];
        try {
            ProxyServer.client = ProxyServer.ss.accept();
            final InputStream streamFromClient = ProxyServer.client.getInputStream();
            final OutputStream streamToClient = ProxyServer.client.getOutputStream();
            try {
                ProxyServer.server = new Socket(host, remoteport);
            }
            catch (IOException e2) {
                ProxyServer.client.close();
                ProxyServer.client = null;
                ProxyServer.server = null;
            }
            final InputStream streamFromServer = ProxyServer.server.getInputStream();
            final OutputStream streamToServer = ProxyServer.server.getOutputStream();
            final Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        int bytesRead;
                        while ((bytesRead = streamFromClient.read(request)) != -1) {
                            streamToServer.write(request, 0, bytesRead);
                            streamToServer.flush();
                        }
                    }
                    catch (IOException ex) {}
                    try {
                        streamToServer.close();
                    }
                    catch (IOException ex2) {}
                }
            };
            t.start();
            try {
                int bytesRead;
                while ((bytesRead = streamFromServer.read(reply)) != -1) {
                    streamToClient.write(reply, 0, bytesRead);
                    streamToClient.flush();
                }
            }
            catch (IOException ex) {}
            streamToClient.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (ProxyServer.server != null) {
                    ProxyServer.server.close();
                }
                if (ProxyServer.client != null) {
                    ProxyServer.client.close();
                }
            }
            catch (IOException ex2) {}
        }
        try {
            if (ProxyServer.server != null) {
                ProxyServer.server.close();
            }
            if (ProxyServer.client != null) {
                ProxyServer.client.close();
            }
        }
        catch (IOException ex3) {}
    }
}
