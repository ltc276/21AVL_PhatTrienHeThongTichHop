package network;
 import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostInspector {
   public static void main(String[] args){
    if(args.length!=1){
        System.out.println("Usage: java network.HostInspector <hostname>");
        return ;
    }
    try{
        InetAddress[] addresses = InetAddress.getAllByName(args [0]);
        System.out.print("Host: " +args[0]);

        for (InetAddress address : addresses){
            System.out.println("-IP:" + address.getHostAddress());

            System.out.println(
                "Canonical:" + address.getCanonicalHostName()
            );
            System.out.println(
                "Loopback: "+ address.isLoopbackAddress()
            );
            System.out.println(
                "Site local: "+address.isSiteLocalAddress()
            );
        }
    }
    catch (UnknownHostException e){
        System.err.print("Khong phan gia duoc host:" + args[0]);
    }
   }

}
