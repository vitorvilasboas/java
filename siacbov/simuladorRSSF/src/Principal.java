
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Principal extends javax.swing.JFrame {
    public Principal() {
        initComponents();
        lbUltimaExec.setText("");
        this.setLocationRelativeTo(null);
    }    
    
    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
    Timer timer = new Timer();
    long delay = 500;
    int intervalo = 60000;
    
    //Toolkit tk = Toolkit.getDefaultToolkit();
    //tk.beep();
    
    public void executar(boolean comando){
        timer.schedule( new TimerTask(){
        //timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(comando){
                    try {
                        Simulador.simular();
                        System.out.println("Simulador de Sensoriamento >> Executou em: " + formatoCompletoBr.format(new Date()));
                        lbUltimaExec.setText("Última Transmissão em: "+ formatoCompletoBr.format(new Date()));
                    } catch (ParseException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                } else {
                    timer.cancel();
                    //timer.purge();
                }
            }
        }, delay, intervalo);
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        btnParar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfIntervalo = new javax.swing.JTextField();
        lbStatus = new javax.swing.JLabel();
        lbUltimaExec = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 0, 102));

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(102, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(204, 204, 204));
        jLabel1.setFont(new java.awt.Font("Neuropol", 0, 21)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("SIMULADOR DE SENSORIAMENTO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 400, -1));

        jLabel2.setBackground(new java.awt.Color(102, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Copyright 2017 | Todos os Direitos Reservados");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, 20));

        btnIniciar.setBackground(new java.awt.Color(255, 255, 255));
        btnIniciar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(0, 102, 0));
        btnIniciar.setText("INICIAR");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        jPanel1.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 150, 60));

        btnParar.setBackground(new java.awt.Color(255, 255, 255));
        btnParar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnParar.setForeground(new java.awt.Color(102, 0, 0));
        btnParar.setText("PARAR");
        btnParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPararActionPerformed(evt);
            }
        });
        jPanel1.add(btnParar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, 150, 60));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Intervalo de Execução (em segundos): ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 40));

        tfIntervalo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tfIntervalo.setText("60");
        jPanel1.add(tfIntervalo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 70, 40));

        lbStatus.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(204, 204, 0));
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbStatus.setText("PARADO");
        jPanel1.add(lbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 110, -1));

        lbUltimaExec.setForeground(new java.awt.Color(204, 204, 204));
        lbUltimaExec.setText("Última Transmissão em: ");
        jPanel1.add(lbUltimaExec, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, -1, 20));

        jLabel4.setFont(new java.awt.Font("Neuropol", 1, 21)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("SIAC");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if(tfIntervalo.getText() != ""){
            intervalo = Integer.parseInt(tfIntervalo.getText())*1000;
        } else {
            intervalo = 60000;
        }
        executar(true);
        lbStatus.setText("EXECUTANDO");
        tfIntervalo.setEnabled(false);
        btnIniciar.setEnabled(false);    
    }//GEN-LAST:event_btnIniciarActionPerformed

private void btnPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPararActionPerformed
        //intervalo = Integer.parseInt(tfIntervalo.getText())*1000;
        executar(false);
        lbStatus.setText("PARADO");
        //btnParar.setEnabled(false); 
        //tfIntervalo.setEnabled(true);
        //btnIniciar.setEnabled(true);
}//GEN-LAST:event_btnPararActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            

            @Override
            public void run() {
                //JOptionPane.showMessageDialog(null, "Simulador de Sensoriamento\n\nDesenvolvido por:\nVitor Vilas Boas");
                new Principal().setVisible(true);               
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnParar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbUltimaExec;
    private javax.swing.JTextField tfIntervalo;
    // End of variables declaration//GEN-END:variables
}
