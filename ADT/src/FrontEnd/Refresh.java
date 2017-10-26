/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Pepe
 */
public class Refresh implements Runnable {

    AdminClientConsult clientc;
    SellerMain sellerm;
    Timer timer;
    long inicio;
    float descargadosi = 0;

    public Refresh(AdminClientConsult frame) {
        this.clientc = frame;

    }

    public Refresh(SellerMain frame) {
        this.sellerm = frame;

    }

    public void Stop() {
        timer.stop();
    }

    @Override
    public void run() {
        System.out.println("reloj iniciado");
        inicio = System.nanoTime();

        timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (true) {
                    long nanos = System.nanoTime() - inicio;
                    long milis = (nanos / 1000000);
                    
                } else {
                    Stop();
                }
            }
        });
        timer.start();

    }

}
