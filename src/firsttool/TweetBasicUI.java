package firsttool;

import firsttool.ServiceLocator.ServiceRecord;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.Timer;

/**
 * Offer UI for displaying processing-related-tweets and convenient
 * API so that Processing Tool can conveniently communicate with the UI.
 * 
 * What's the purpose (conforming to SingleResponsibilityPrinciple)
 * of this class?
 * -construct frame capable of displaying Tweets? 
 *                                          ^^^^ ( how exactly do we define this "Tweet")?
 * -display frame with updated tweets?
 * -implement events when user "clicks" on certain things??
 * 
 * At what abstraction level do we work?
 * -----------------------------------------
 * 
 * 
 * 
 * Which "abstract" concepts does this class control?
 * 
 * @author Dimitry Kireyenkov <dimitry@languagekings.com>
 */
public class TweetBasicUI extends javax.swing.JFrame
implements IProTweetUI
{

    private IQueueAccessPoint mQueueAccessPoint;
    private final Timer timer;
    /**
     * Creates new form NewJFrame
     */
    public TweetBasicUI() {
        initComponents();
        //jList1.setModel(new DefaultListModel<AbstractTweet>());
        jList1.setCellRenderer(new TweetCellRender());
        
        
        jList1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                JList list = (JList) e.getSource();
                System.out.println("JList got click event, at the moment: " + e.getClickCount() + " click(s) event");
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    if ( index < 0 ){
                        return; // no supportive model.
                    }
                    ListModel dlm = list.getModel();
                    Object item = dlm.getElementAt(index);
                    list.ensureIndexIsVisible(index); // what is this? scrolling to visible?
                    System.out.println("Double clicked on " + item);
                }
            }
        }
    );
        
        mQueueAccessPoint = startTweetFetchService(); // we start thread, but we don't
                                                     // care much about the fact that it's thread
                                                     // fetching. All we care is that 
                                                     // we get reference to IQueueAccessPoint
                                                     // which we can poll at any time (thead safe)
                                                     // and we can get latest items.
 
        // now we need to start timer. this timer will call every now and then and will be firing ON SWING THREAD
        // when there's availble tweet.
        timer = new Timer(1000, new ActionListener() {  // technically we don't need update more frequently than thred is updating, but that doesn't matter.
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<AbstractTweet> arlis = null;
                AbstractTweet aTweet = null;
                while ( ( aTweet = mQueueAccessPoint.getNextOrNull() ) != null ){
                    if ( arlis == null ){ arlis = new ArrayList<AbstractTweet>(); }
                    arlis.add(aTweet);
                }
                
                if ( arlis != null){
                    fireNewTweetsEvent(arlis);
                }
            }
        });
        
        timer.setInitialDelay(500); // wait half a second, util we set up the thing
        timer.start();
    }

    // -------------------------------------------------------------------------------
    /**
     * This is interface observer may subscribe to.
     */
    interface IOnNewTweetsListener
    {
        void onNewTweets(ArrayList<AbstractTweet> newTweets);
    }
    
    
    private IOnNewTweetsListener mOnNewTweetsListener;
    
    protected void fireNewTweetsEvent(ArrayList<AbstractTweet> newTweets){
         if ( mOnNewTweetsListener != null ){
                mOnNewTweetsListener.onNewTweets(newTweets);
         }
    }
    
    void setOnNewTweetsListener(IOnNewTweetsListener listener){
         mOnNewTweetsListener = listener;
    }
    // --------------------------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        btnInsertIntoPDE = new javax.swing.JButton();
        btnReplaceInPDE = new javax.swing.JButton();
        chkAlwaysOnTop = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Tweets");

        btnInsertIntoPDE.setText("Insert into PDE");
        btnInsertIntoPDE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertIntoPDEActionPerformed(evt);
            }
        });

        btnReplaceInPDE.setText("Replace in PDE");

        chkAlwaysOnTop.setText("Set Always On Top");
        chkAlwaysOnTop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAlwaysOnTopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(92, 92, 92)
                        .addComponent(btnInsertIntoPDE, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReplaceInPDE, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkAlwaysOnTop)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInsertIntoPDE)
                        .addComponent(btnReplaceInPDE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkAlwaysOnTop)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertIntoPDEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertIntoPDEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInsertIntoPDEActionPerformed

    private void chkAlwaysOnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAlwaysOnTopActionPerformed
        // TODO add your handling code here:
        JCheckBox chkbox =  (JCheckBox) evt.getSource();
        if ( chkbox.isSelected() ){
            setAlwaysOnTop(true);
        }
        else{
            setAlwaysOnTop(false);
        }
    }//GEN-LAST:event_chkAlwaysOnTopActionPerformed

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
            java.util.logging.Logger.getLogger(TweetBasicUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TweetBasicUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TweetBasicUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TweetBasicUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TweetBasicUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertIntoPDE;
    private javax.swing.JButton btnReplaceInPDE;
    private javax.swing.JCheckBox chkAlwaysOnTop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    protected void setListModel(ListModel lmodel){
        jList1.setModel(lmodel);
    }

//    // there's no need to keep this reference here.
//    // as NewJFrame only thinks of this as IQueueAccessPoint
//    // and doesn't care of it's implementation. NewjFrame simply knows
//    // that it should poll at regular intervals the queue. but how it's 
//    // implemented it's not his business.
//    /**
//     * This is the thread which is doing the polling job.
//     */
//    protected TweetFetchThread thread;
    
    private IQueueAccessPoint startTweetFetchService() {
//        TweetFetchThread thread = new TweetFetchThread();
//        thread.start();
//        return thread;
        ServiceRecord service = ServiceLocator.getSerivce(ServiceLocator.SVC_LIVE_TWEET_QUEUE);
        return (IQueueAccessPoint) service;  // yeah we have cast here, but hurray! we're decoupled
                                             // now from instantiation of the service
    }
    
    /**
     * Our super class knows that INCUIM we use
     * button as a way to select tweet.
     * TODO: need to make these abstraction layers more clear. 
     * Maybe need to draw a diagram.
     * @return 
     */
    protected JButton getSelectButton(){
        return btnInsertIntoPDE;
    }
    

    /**
     * Just returns selected in the list tweet. Or null
     * @return  NULL on nothing selected or error casting.
     */
    protected AbstractTweet getSelectedInListTweet() {
        Object selection = jList1.getSelectedValue();
        if ( selection == null ){ 
            return null;
        }
        if  ( selection instanceof AbstractTweet ){
            return (AbstractTweet) selection;
        }
        System.out.println("Error casting JList element to AbstractTweet. Probably just string was in the list");
        return null;
    }
    
    
}