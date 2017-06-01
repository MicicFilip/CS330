package util;

import android.util.Log;

import com.example.micic.cs330_v04.V09Activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Filip on 01-Jun-17.
 */

public class CommsThread extends Thread {

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    public CommsThread(Socket sock) {
        socket = sock;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
//---kreira inputstream i outputstream objekte
// za čitanje i pisanje preko socket-a---
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.d("SocketChat", e.getLocalizedMessage());
        }
        inputStream = tmpIn;
        outputStream = tmpOut;
    }

    public void run() {
//---bafer skladište za konkretan tok---
        byte[] buffer = new byte[1024];
//---vraćeni bajtovi iz read()---
        int bytes;
        while (true) {
            try {
//---čitanje iz inputStream---
                bytes = inputStream.read(buffer);
//---ažuriranje glavne aktivnosti - UI---
                V09Activity.UIupdater.obtainMessage(
                        0,bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
    }

    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) { }
    }
    //---pozivanje iz glavne aktivnosti
// za zatvaranje konekcije---
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }

}
