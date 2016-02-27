/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triquiclient;

import controllers.GameController;
import controllers.NotificationController;
import controllers.PlayersController;
import domain.Game;
import domain.Notification;
import domain.Player;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 *
 * @author jonathaneidelman
 */
public class LobbyUI extends javax.swing.JFrame {

    private Player player;
    
    private ArrayList<Player> onlinePlayers;
    
    /**
     * Creates new form lobby
     */
    public LobbyUI(Player p) {
        this.player = p;
        
        initComponents();
        
        
        
        lblPlayerName.setText(p.getName());
        new Thread(new CheckNotifications()).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtLobby = new javax.swing.JLabel();
        lblPlayerName = new javax.swing.JLabel();
        pnlPlayerList = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listPlayers = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        btnChallenge = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Triqui Lobby");
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(300, 350));

        jPanel1.setMinimumSize(new java.awt.Dimension(33, 38));
        jPanel1.setPreferredSize(new java.awt.Dimension(62, 24));
        jPanel1.setLayout(new java.awt.BorderLayout());

        txtLobby.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLobby.setText("   Lobby");
        jPanel1.add(txtLobby, java.awt.BorderLayout.WEST);

        lblPlayerName.setText("pName   ");
        jPanel1.add(lblPlayerName, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pnlPlayerList.setLayout(new java.awt.BorderLayout());

        listPlayers.setModel(generateListModel());
        jScrollPane3.setViewportView(listPlayers);

        pnlPlayerList.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlPlayerList, java.awt.BorderLayout.CENTER);

        btnChallenge.setText("Challenge Player");
        btnChallenge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChallengeActionPerformed(evt);
            }
        });
        jPanel2.add(btnChallenge);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChallengeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChallengeActionPerformed
        Player selectedPlayer;
        selectedPlayer = onlinePlayers.get(listPlayers.getSelectedIndex());
        
        Notification n = new Notification(-1, player, selectedPlayer,
                Notification.Type.GAMEINVITE, false);
        n = PlayersController.GetInstance().sendNotificationToPlayer(n);
        
        n = waitForInvitationResponse(n);
        
        if(n == null){//declined
            JOptionPane.showMessageDialog(this, "Invitation declined",
                    "Invitation Response", JOptionPane.INFORMATION_MESSAGE);
        } else { //accepted
            Game g = GameController.getInstance().fetchGame(n.getGame());
            new GameUI(g, player).setVisible(true);
        }
    }//GEN-LAST:event_btnChallengeActionPerformed

    private Notification waitForInvitationResponse(Notification n){
        this.setEnabled(false);
        System.out.println("waiting for invitation "+n.getId());
        boolean waiting = true;
        while (waiting) {
            n = NotificationController.GetInstance().
                    fetchNotification(n.getId());
            
            if(n==null || n.isAccepted()){
                waiting = false;
            }
        }
        this.setEnabled(true);
        return n;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Player p = new Player(1, "defaultPlayer",
                        "waiting", null);
                
                new LobbyUI(p).setVisible(true);
            }
        });
    }
    
    public ListModel generateListModel(){
        DefaultListModel pNames = new DefaultListModel();
        
        //TODO: get the online players from the server (minus the logged player)
        PlayersController pc = PlayersController.GetInstance();
        
        onlinePlayers = pc.listAllConnectedPlayers();
        
        for (Player curPlayer : onlinePlayers) {
            if(curPlayer.getId() != player.getId())
            pNames.addElement(curPlayer.getName());
        }
        return pNames;
    }

    //threads
    private class CheckNotifications implements Runnable {

        @Override
        public void run() {
            while(true){
                if(player.getStatus().equals("waiting")){
                    ArrayList<Notification> queue = PlayersController.GetInstance().
                            getPlayersNotifications(player);

                    if(!queue.isEmpty()){
                        showInvitation(queue.get(0));
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LobbyUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        private void showInvitation(Notification n){
            int selected = JOptionPane.showConfirmDialog(null,
                "Accept invitation to play from "+n.getSender().getName()+"?",
                "Game Invitation from " + n.getSender().getName(), 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE); 
            
            if(selected == 0){//yes
                //TODO: accept and create game
                player.setStatus("playing");
                PlayersController.GetInstance().updatePlayer(player);
                
                Game g = GameController.getInstance().
                        createGame(n.getTo(), n.getSender());
                
                NotificationController.GetInstance().
                        acceptNotification(n.getId(), g.getId());
                
                new GameUI(g, player).setVisible(true);
            } else {//no
                NotificationController.GetInstance().
                        deleteNotification(n.getId());
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChallenge;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JList listPlayers;
    private javax.swing.JPanel pnlPlayerList;
    private javax.swing.JLabel txtLobby;
    // End of variables declaration//GEN-END:variables
}
