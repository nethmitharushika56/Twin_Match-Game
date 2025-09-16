package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Manages all animations for the game (tiles, buttons, fireworks).
 */
public class AnimationManager {

    /* =========================================================
       TILE ANIMATIONS
       ========================================================= */

    /**
     * Animate tile flip with a simple scaling effect.
     */
    public static void animateTileFlip(JButton button, boolean flipToFront, Runnable onComplete) {
        Timer timer = new Timer(50, null);
        final int totalSteps = 6;
        final int[] step = {0};

        timer.addActionListener((ActionEvent e) -> {
            step[0]++;
            double progress = (double) step[0] / totalSteps;

            double scale = (progress <= 0.5)
                    ? 1.0 - (progress * 2.0)       // shrink
                    : (progress - 0.5) * 2.0;     // grow

            button.setPreferredSize(new Dimension(
                    (int) (button.getWidth() * scale),
                    (int) (button.getHeight() * scale)
            ));
            button.revalidate();
            button.repaint();

            if (step[0] >= totalSteps) {
                timer.stop();
                if (onComplete != null) onComplete.run();
            }
        });
        timer.start();
    }

    /**
     * Animate tile disappearance when matched (fade + shrink).
     */
    public static void animateTileDisappear(JButton button, Runnable onComplete) {
        Timer timer = new Timer(30, null);
        final int totalSteps = 10;
        final int[] step = {0};

        timer.addActionListener((ActionEvent e) -> {
            step[0]++;
            double progress = (double) step[0] / totalSteps;

            float alpha = 1.0f - (float) progress;
            button.setBackground(new Color(
                    button.getBackground().getRed(),
                    button.getBackground().getGreen(),
                    button.getBackground().getBlue(),
                    (int) (alpha * 255)
            ));

            double scale = 1.0 - progress * 0.3;
            button.setPreferredSize(new Dimension(
                    (int) (button.getWidth() * scale),
                    (int) (button.getHeight() * scale)
            ));

            button.revalidate();
            button.repaint();

            if (step[0] >= totalSteps) {
                timer.stop();
                if (onComplete != null) onComplete.run();
            }
        });
        timer.start();
    }

    /**
     * Pulse animation for buttons.
     */
    public static void animateButtonPulse(JButton button) {
        Timer timer = new Timer(100, null);
        final int totalSteps = 6;
        final int[] step = {0};

        timer.addActionListener((ActionEvent e) -> {
            step[0]++;

            double scale = (step[0] <= totalSteps / 2)
                    ? 1.0 + (step[0] * 0.1)  // grow
                    : 1.0 + ((totalSteps - step[0]) * 0.1); // shrink

            button.setPreferredSize(new Dimension(
                    (int) (button.getWidth() * scale),
                    (int) (button.getHeight() * scale)
            ));

            button.revalidate();
            button.repaint();

            if (step[0] >= totalSteps) {
                timer.stop();
            }
        });
        timer.start();
    }


    /* =========================================================
       FIREWORKS ANIMATION
       ========================================================= */

    /**
     * Fireworks panel to overlay when a level is won.
     */
    public static class FireworksPanel extends JPanel {
        private final List<Particle> particles = new ArrayList<>();
        private final Random random = new Random();
        private final Timer timer;

        public FireworksPanel() {
            setOpaque(false);

            // update 30 FPS
            timer = new Timer(33, e -> {
                spawnParticles();
                updateParticles();
                repaint();
            });
            timer.start();
        }

        public void stop() {
            timer.stop();
        }

        private void spawnParticles() {
            if (random.nextInt(8) == 0) { // occasional bursts
                int x = random.nextInt(Math.max(1, getWidth()));
                int y = random.nextInt(Math.max(1, getHeight() / 2));

                for (int i = 0; i < 40; i++) {
                    double angle = 2 * Math.PI * i / 40;
                    double speed = 2 + random.nextDouble() * 2;
                    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    particles.add(new Particle(x, y, angle, speed, color));
                }
            }
        }

        private void updateParticles() {
            Iterator<Particle> it = particles.iterator();
            while (it.hasNext()) {
                Particle p = it.next();
                p.update();
                if (!p.isAlive()) it.remove();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for (Particle p : particles) {
                g2.setColor(new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), (int) (p.alpha * 255)));
                g2.fillOval((int) p.x, (int) p.y, 6, 6);
            }
        }

        /* Particle class */
        private static class Particle {
            double x, y, dx, dy;
            float alpha = 1f;
            final Color color;

            Particle(int x, int y, double angle, double speed, Color color) {
                this.x = x;
                this.y = y;
                this.dx = Math.cos(angle) * speed;
                this.dy = Math.sin(angle) * speed;
                this.color = color;
            }

            void update() {
                x += dx;
                y += dy;
                dy += 0.05; // gravity
                alpha -= 0.02f;
            }

            boolean isAlive() {
                return alpha > 0;
            }
        }
    }
}
