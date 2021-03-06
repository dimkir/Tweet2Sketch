/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package org.socialsketch.ui.sharedialog;

import org.socialsketch.ui.dialogcallbacks.IImageEnvelopeReceiver;
import org.socialsketch.ui.dialogcallbacks.MissingIcon;
import org.socialsketch.ui.dialogcallbacks.ImageScanAndWrapSWorker;
import org.socialsketch.ui.dialogcallbacks.SSDialogParams;
import org.socialsketch.ui.dialogcallbacks.AdvancedCallback;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

/**
 * This application is intended to demonstrate the loading of image files into icons
 * for use in a Swing user interface. It creates a toolbar with a thumbnail preview
 * of each image.  Clicking on the thumbnail will show the full image
 * in the main display area.
 *
 * IconDemoApp.java requires the following files: <br>
 * The following files are copyright 2006 spriggs.net and licensed under a
 * Creative Commons License (http://creativecommons.org/licenses/by-sa/3.0/)
 * <br>
 * images/sunw01.jpg <br>
 * images/sunw02.jpg <br>
 * images/sunw03.jpg <br>
 * images/sunw04.jpg <br>
 * images/sunw05.jpg <br>
 *
 * @author Collin Fagan
 * @date 7/25/2007
 * @version 2.0
 */
public class IconDemoApp extends JFrame {
    
    /**
     * This looks like the main BIG label in the center of the screen.
     */
    private JLabel photographLabel = new JLabel();
    private JToolBar buttonBar = new JToolBar();
    
    private String imagedir = "images/";
    
    private MissingIcon placeholderIcon = new MissingIcon();
    
    /**
     * List of all the descriptions of the image files. These correspond one to
     * one with the image file names
     */
    private String[] imageCaptions = { "Original SUNW Logo", "The Clocktower",
    "Clocktower from the West", "The Mansion", "Sun Auditorium"};
    
    /**
     * List of all the image files to load.
     */
    private String[] imageFileNames = { "sunw01.jpg", "sunw02.jpg",
    "sunw03.jpg", "sunw04.jpg", "sunw05.jpg"};
    
    /**
     * Main entry point to the demo. Loads the Swing elements on the "Event
     * Dispatch Thread".
     *
     * @param args
     */
    public static void main(String args[]) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 createGUI2();
            }
        });
    }
    
    private static void createGUI1(){
                String C_DIR_WITH_IMAGES = "c:\\Users\\Ernesto Guevara\\Desktop\\processing\\assets\\images\\bird\\";
                
                File dirWithImages = new File(C_DIR_WITH_IMAGES);
                IconDemoApp app = new IconDemoApp(dirWithImages, new Runnable() {

                    public void run() {
                        JOptionPane.showMessageDialog(null, "This is shown after frame is closed");
                    }
                });
                //app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                app.setVisible(true);        
    }

    /**
     * Creates IconDemoApp frame via constructor which takes SSDialogParams.
     */
    private static void createGUI2(){
                String C_DIR_WITH_IMAGES = "c:\\Users\\Ernesto Guevara\\Desktop\\processing\\assets\\images\\bird\\";
                File dirWithImages = new File(C_DIR_WITH_IMAGES);
                
                AdvancedCallback callback = new AdvancedCallback() {

                    @Override
                    public void onFinishedSelection(AdvancedCallback.FinishedSelectionEvent evt) {
                        String msg = "No item selected. Selection cancelled.";
                        if ( evt.hasSelected() ){
                            msg = "Selected image: " + evt.getSelectedImagePath();
                        }
                        JOptionPane.showMessageDialog(null, msg);
                    }
                };
                
                SSDialogParams ssparam = IconDemoApp.spawnEmptyParameters()
                                        .setDirectoryWithImages(dirWithImages)
                                        .setAdvancedCallback(callback)
                                        .setParentFrame(null);
                
                IconDemoApp app = new IconDemoApp(ssparam);
                
                
                app.setVisible(true);        
    }
    private File mOriginPath;
    private final AdvancedCallback mAdvancedCallback;
    
    /**
     * Makes given image be displayed in the central view of the frame.
     * @param icon 
     */
    void setCenterImage(Icon icon){
        photographLabel.setIcon(icon);
    }
    
    /**
     * This is constructor which uses "the evolvable" param SSDialogParams
     * to pass initialization params.
     * 
     * Also this constructor uses Advanced "onImagePicked" callback (with parameters, etc).
     * 
     * @param params 
     */
    public IconDemoApp(SSDialogParams params){
            initFrame(params.getParentFrame());
            initOkButtonAdvanced(params.getAdvancedCallback());
            mAdvancedCallback = params.getAdvancedCallback();
            setupButtonSupplyThread(params.getDirectoryWithImages());
    }
    
    
    //TODO: this class name should be changed to something like:
    // "Sketch share input dialog" or smth like that
    // becsause it's not only "image picking" what this class
    // is going to do.
    
    /**
     * Main constructor, initializes image picking window with
     * directory of images and with the callback "onPickListener"  implemented as regular Runnable()
     * 
     * 
     * @param directoryWithImages
     * @param listenerToRunafterClickOkOnEDT 
     */
    public IconDemoApp(File directoryWithImages, final Runnable listenerToRunafterClickOkOnEDT){
        mAdvancedCallback = null;
        initFrame(null);
        // start the image loading SwingWorker in a background thread
        
        // this is "OK" button which basically picks image and closes window.
        initOkButton(listenerToRunafterClickOkOnEDT);
        
        setupButtonSupplyThread(directoryWithImages);
    }
    
    /**
     * Constructs empty SSDialogParams instance. 
     * 
     * SSDialogParams is not supposed to be constructed from outside of the package,
     * thus this method is a public access point to create an instance of the params.
     * @return 
     */
    public static SSDialogParams spawnEmptyParameters(){
        return new SSDialogParams();
    }
    
//    /**
//     * Default constructor form the original demo of images.
//     * Loads images from the package resources.
//     */
//    public IconDemoApp() {
//        mAdvancedCallback = null;
//        initFrame(null);
//        // start the image loading SwingWorker in a background thread
//        loadimages.execute();
//    }
    
    
    private ImageScanAndWrapSWorker  mImageScanAndWrapWorker;
    
//    /**
//     * SwingWorker class that loads the images a background thread and calls publish
//     * when a new one is ready to be displayed.
//     *
//     * We use Void as the first SwingWroker param as we do not need to return
//     * anything from doInBackground().
//     */
//    private SwingWorker<Void, ThumbnailAction> loadimages = new SwingWorker<Void, ThumbnailAction>() {
//        
//        /**
//         * Creates full size and thumbnail versions of the target image files.
//         */
//        @Override
//        protected Void doInBackground() throws Exception {
//            for (int i = 0; i < imageCaptions.length; i++) {
//                ImageIcon icon;
//                icon = createImageIconFromResource(imagedir + imageFileNames[i], imageCaptions[i]);
//                
//                ThumbnailAction thumbAction;
//                if(icon != null){
//                    
//                    ImageIcon thumbnailIcon = new ImageIcon(GraphicsUtils.getScaledImage(icon.getImage(), 32, 32));
//                    
//                    thumbAction = new ThumbnailAction(icon, thumbnailIcon, imageCaptions[i], null, org.socialsketch.ui.sharedialog.IconDemoApp.this);
//                    
//                }else{
//                    // the image failed to load for some reason
//                    // so load a placeholder instead
//                    thumbAction = new ThumbnailAction(placeholderIcon, placeholderIcon, imageCaptions[i],null, org.socialsketch.ui.sharedialog.IconDemoApp.this);
//                }
//                publish(thumbAction);
//            }
//            // unfortunately we must return something, and only null is valid to
//            // return when the return type is void.
//            return null;
//        }
//        
//        /**
//         * Process all loaded images.
//         */
//        @Override
//        protected void process(List<ThumbnailAction> chunks) {
//            for (ThumbnailAction thumbAction : chunks) {
//                JButton thumbButton = new JButton(thumbAction);
//                // add the new button BEFORE the last glue
//                // this centers the buttons in the toolbar
//                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
//            }
//        }
//    };
    
    /**
     * Creates an ImageIcon if the path is valid.
     * @param String - resource path
     * @param String - description of the file
     */
    protected  ImageIcon createImageIconFromResource(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    


    /**
     * Inits components of the frame and position
     * @param parentFrame CAN BE null or reference to the parent frame. Used incuim
     *        only to position this frame neatly above the parent frame.
     */
    private void initFrame(JFrame parentFrame) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // when other swing frame is creating this frame.
                                                        // what should be the operation? 
                                                        // namely if I hide? then it's there?
                                                        // if I dispose: the caller will still have reference to me?
        addWindowListener(new MyWindowListener());
        
        
        setTitle("Icon Demo: Please Select an Image");
        
        // A label for displaying the pictures
        photographLabel.setVerticalTextPosition(JLabel.BOTTOM);
        photographLabel.setHorizontalTextPosition(JLabel.CENTER);
        photographLabel.setHorizontalAlignment(JLabel.CENTER);
        photographLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // We add two glue components. Later in process() we will add thumbnail buttons
        // to the toolbar inbetween thease glue compoents. This will center the
        // buttons in the toolbar.
        buttonBar.add(Box.createGlue());
        buttonBar.add(Box.createGlue());
        
        add(buttonBar, BorderLayout.SOUTH);
        add(photographLabel, BorderLayout.CENTER);
        
        setSize(400, 300);
        
        // this centers the frame on the screen
        setLocationRelativeTo(parentFrame); // null is valid value.
                
    }

    /**
     * Add thumb button to the thumbnail stripe.
     * 
     * Looks like this is method called from the outside to add the picture.
     * 
     * @param thumbButton 
     */
    void addThumbButton(JButton thumbButton) {
        buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);        
    }

    /**
     * Inits button (which user clicks when he had selected the picture).
     * 
     * @param listenerToRunafterClickOkOnEDT This callback will be run on EDT. this the callback which will be 
     *        called to inform observers that user has selected the frame.
     */
    private void initOkButton(final Runnable listenerToRunafterClickOkOnEDT) {
        JButton okButton = new JButton("OK I HAVE SELECTED");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // this happens when button is pressed.
                // TODO: add code for whether anythign was picked.
                IconDemoApp.this.setVisible(false);
                listenerToRunafterClickOkOnEDT.run();
                
                // After button is clicked, we want the default window close operation to be executed,
                // thus we "simulate" click on the [X] window close button.
                IconDemoApp.this.dispatchEvent(new WindowEvent(IconDemoApp.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        buttonBar.add(okButton, buttonBar.getComponentCount() - 1);
    }

   
    
    
    private void initOkButtonAdvanced(final AdvancedCallback advancedCallback) {
       JButton okButton = new JButton("OK,,,, I HAVE SELECTED");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // this happens when button is pressed.
                // TODO: add code for whether anythign was picked.
                IconDemoApp.this.setVisible(false);
                advancedCallback.onFinishedSelection(new AdvancedCallback.FinishedSelectionEvent(mOriginPath));
                
                // After button is clicked, we want the default window close operation to be executed,
                // thus we "simulate" click on the [X] window close button.
               // IconDemoApp.this.dispatchEvent(new WindowEvent(IconDemoApp.this, WindowEvent.WINDOW_CLOSING));
                IconDemoApp.this.dispose();
            }
        });
        buttonBar.add(okButton, buttonBar.getComponentCount() - 1);
    }

     
    
    
    
    /**
     * Sets up SwingWorker thread, which will scan directory for images,
     * and feed them back into this JFrame on EDT thread.
     * 
     * @param directoryWithImages 
     */
    private void setupButtonSupplyThread(File directoryWithImages) {
        // loadimages.execute();
        IImageEnvelopeReceiver imageEnvelopeReceiver = new IImageEnvelopeReceiver() {

            /**
             * Receives envelope with images. Creates Thumbnail actions and creates buttons
             * and adds them to the JFrame.
             */
            @Override
            public void submitImageEnvelope(ImageScanAndWrapSWorker.ImageEnvelope imageEnvelope) {
                ThumbnailAction thAction = new ThumbnailAction(imageEnvelope.getMainImage(), 
                                                               imageEnvelope.getThumbnailImage(), 
                                                               imageEnvelope.getDescription(), 
                                                               imageEnvelope.getOriginPath(),
                                                               IconDemoApp.this);
                JButton thButton = new JButton(thAction);
                //thButton.setAction(thAction);
                addThumbButton(thButton);
            }
        };
        mImageScanAndWrapWorker = new ImageScanAndWrapSWorker(directoryWithImages, imageEnvelopeReceiver);
        mImageScanAndWrapWorker.execute();    
    }

    /**
     * Sets currently selected file origin path.
     * 
     * @param mOriginPath 
     */
    void setSelectedImageOriginPath(File originPath) {
        mOriginPath = originPath;
        System.out.println("Selected image origin path: " + mOriginPath.getAbsolutePath());
    }

   /**
     * Exception which is thrown by IconDemoApp
     */
    public static class IconDemoEx extends Exception {

        public IconDemoEx(String message) {
            super(message);
        }

        public IconDemoEx(Throwable cause) {
            super(cause);
        }
        
    }

    
    private class MyWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            // fire call back.
            mAdvancedCallback.onFinishedSelection(new AdvancedCallback.FinishedSelectionEvent(null));
        }
    }
}
