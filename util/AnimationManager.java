package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Manages animations for the game
 */
public class AnimationManager {
    
    /**
     * Animate tile flip with rotation effect
     */
    public static void animateTileFlip(JButton button, boolean flipToFront, Runnable onComplete) {
        Timer timer = new Timer(50, new ActionListener() {
            private int step = 0;
            private final int totalSteps = 6;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                double progress = (double) step / totalSteps;
                
                if (progress <= 0.5) {
                    // First half: scale down
                    double scale = 1.0 - (progress * 2.0);
                    button.setPreferredSize(new Dimension(
                        (int)(button.getWidth() * scale),
                        (int)(button.getHeight() * scale)
                    ));
                } else {
                    // Second half: scale up
                    double scale = (progress - 0.5) * 2.0;
                    button.setPreferredSize(new Dimension(
                        (int)(button.getWidth() * scale),
                        (int)(button.getHeight() * scale)
                    ));
                }
                
                button.revalidate();
                button.repaint();
                
                if (step >= totalSteps) {
                    ((Timer)e.getSource()).stop();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }
        });
        timer.start();
    }
    
    /**
     * Animate tile disappearance when matched
     */
    public static void animateTileDisappear(JButton button, Runnable onComplete) {
        Timer timer = new Timer(30, new ActionListener() {
            private int step = 0;
            private final int totalSteps = 10;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                double progress = (double) step / totalSteps;
                
                // Fade out and scale down
                float alpha = 1.0f - (float)progress;
                button.setBackground(new Color(
                    button.getBackground().getRed(),
                    button.getBackground().getGreen(),
                    button.getBackground().getBlue(),
                    (int)(alpha * 255)
                ));
                
                double scale = 1.0 - progress * 0.3;
                button.setPreferredSize(new Dimension(
                    (int)(button.getWidth() * scale),
                    (int)(button.getHeight() * scale)
                ));
                
                button.revalidate();
                button.repaint();
                
                if (step >= totalSteps) {
                    ((Timer)e.getSource()).stop();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                }
            }
        });
        timer.start();
    }
    
    /**
     * Pulse animation for buttons
     */
    public static void animateButtonPulse(JButton button) {
        Timer timer = new Timer(100, new ActionListener() {
            private int step = 0;
            private final int totalSteps = 6;
            private boolean growing = true;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                
                if (step <= totalSteps / 2) {
                    // Grow
                    double scale = 1.0 + (step * 0.1);
                    button.setPreferredSize(new Dimension(
                        (int)(button.getWidth() * scale),
                        (int)(button.getHeight() * scale)
                    ));
                } else {
                    // Shrink
                    double scale = 1.0 + ((totalSteps - step) * 0.1);
                    button.setPreferredSize(new Dimension(
                        (int)(button.getWidth() * scale),
                        (int)(button.getHeight() * scale)
                    ));
                }
                
                button.revalidate();
                button.repaint();
                
                if (step >= totalSteps) {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.start();
    }
} 