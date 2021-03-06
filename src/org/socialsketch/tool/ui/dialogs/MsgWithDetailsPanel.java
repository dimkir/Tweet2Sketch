package org.socialsketch.tool.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class MsgWithDetailsPanel extends javax.swing.JPanel {
    private final String mMsg;
    private final Exception mException;

    private ActionListener mActionListener;
    private final boolean mDisplayOkButton;
    
    
    public MsgWithDetailsPanel(String msg, Exception ex, boolean displayOkButton){
        initComponents();
        mDisplayOkButton = displayOkButton;
        
        mMsg = msg;
        mException = ex;
        
        btnOk.setVisible(mDisplayOkButton);
        
        lblErrorMessage.setText(msg);
        
        txtaErrorDetails.setText(ex.toString() + " \n\nStacktrace:\n"  + stackTraceToString(ex));
        
        setDetailsVisible(false);
//        txtaErrorDetails.setVisible(false);        
    }
    
    /**
     * Creates new form MsgWithDetailsPanel WITHOUT standalone OK button.
     * @param msg
     * @param ex
     */
    public MsgWithDetailsPanel(String msg, Exception ex) {
        this(msg, ex, false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblErrorMessage = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        btntogDetails = new javax.swing.JToggleButton();
        scrollErrorDetailsWrap = new javax.swing.JScrollPane();
        txtaErrorDetails = new javax.swing.JTextArea();

        lblErrorMessage.setText("Human readable error message");

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btntogDetails.setText("Details for nerds");
        btntogDetails.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btntogDetailsStateChanged(evt);
            }
        });

        txtaErrorDetails.setEditable(false);
        txtaErrorDetails.setColumns(20);
        txtaErrorDetails.setRows(5);
        scrollErrorDetailsWrap.setViewportView(txtaErrorDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(65, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btntogDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollErrorDetailsWrap)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btntogDetails))
                .addGap(18, 18, 18)
                .addComponent(scrollErrorDetailsWrap, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btntogDetailsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btntogDetailsStateChanged
        // TODO add your handling code here:
        JToggleButton src = (JToggleButton) evt.getSource();
        if ( src.isSelected() ){
            setDetailsVisible(true);
        }
        else{
            setDetailsVisible(false);
        }
    }//GEN-LAST:event_btntogDetailsStateChanged

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if ( mActionListener != null ){
            mActionListener.actionPerformed(evt);
        }
        
    }//GEN-LAST:event_btnOkActionPerformed

    
public void setActionListener(ActionListener callback){
    mActionListener = callback;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JToggleButton btntogDetails;
    private javax.swing.JLabel lblErrorMessage;
    private javax.swing.JScrollPane scrollErrorDetailsWrap;
    private javax.swing.JTextArea txtaErrorDetails;
    // End of variables declaration//GEN-END:variables

    
    /**
     * Sets details panel visible. 
     * 
     * @param b flag of whether it should be visible or not.
     */
    private void setDetailsVisible(boolean b) {
//        scrollErrorDetailsWrap.setVisible(b);
        txtaErrorDetails.setVisible(b);
    }
    
    
    /**
     * Let's test.
     * @param args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }

        });
    }
    private static void createAndShowGUI() {
        
            final JFrame frm = new JFrame();
            
            Exception fakeException = new Exception("This is error message of the fake exception");
            MsgWithDetailsPanel panel = new MsgWithDetailsPanel("This is test error message", fakeException);
            
            // when ok button is called in the action listener.
            panel.setActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    frm.dispose();
                }
            });
            
            frm.add(panel, BorderLayout.CENTER);
            frm.pack();
            frm.setVisible(true);
    }
    
    public static String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        if ( e.getStackTrace() == null || e.getStackTrace().length == 0 ){
            return "Stack trace is null.";
        }

        if ( e.getStackTrace() == null || e.getStackTrace().length == 0 ){
            return "Stack trace is empty. (array of 0 length)";
        }
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }    
}
