package com.xpert.maker;

import com.xpert.utils.SwingUtils;
import java.awt.Color;
import javax.swing.JTextArea;

/**
 *
 * Swing dialog tha show a textarea, with option to copy
 * 
 * @author ayslan
 */
public class DialogShowText extends javax.swing.JDialog {

    private static final Color BLUE = new Color(66, 139, 202);

    /**
     * Creates new form DialogShowText
     */
    public DialogShowText(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JTextArea getTextAreaCopy() {
        return textAreaCopy;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneMain = new javax.swing.JScrollPane();
        textAreaCopy = new javax.swing.JTextArea();
        buttonCopyI18n = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textAreaCopy.setColumns(20);
        textAreaCopy.setRows(5);
        scrollPaneMain.setViewportView(textAreaCopy);

        buttonCopyI18n.setBackground(BLUE);
        buttonCopyI18n.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        buttonCopyI18n.setForeground(new java.awt.Color(255, 255, 255));
        buttonCopyI18n.setText("Copy");
        buttonCopyI18n.setToolTipText("Copy to clipboard");
        buttonCopyI18n.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonCopyI18n.setBorderPainted(false);
        buttonCopyI18n.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCopyI18nActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCopyI18n, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonCopyI18n)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCopyI18nActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCopyI18nActionPerformed
        SwingUtils.copyToClipboard(textAreaCopy);
    }//GEN-LAST:event_buttonCopyI18nActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCopyI18n;
    private javax.swing.JScrollPane scrollPaneMain;
    private javax.swing.JTextArea textAreaCopy;
    // End of variables declaration//GEN-END:variables
}
