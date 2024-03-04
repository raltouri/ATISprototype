package gsix.ATISprototype.server;

import java.io.IOException;


public class SimpleChatServer
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3003);
        System.out.println("server is listening");
        server.listen();
    }
}
