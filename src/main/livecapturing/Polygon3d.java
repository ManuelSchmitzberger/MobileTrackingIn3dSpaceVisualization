package main.livecapturing;

//polygon3d.java
import javax.media.opengl.GL2;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.*;

//import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.*;
import java.awt.event.*;

public class Polygon3d implements GLEventListener {
    private static GraphicsEnvironment graphicsEnvironment;
    private static boolean isFullScreen = false;
    public static DisplayMode dm,dm_old;
    private static Dimension xgraphic;
    private static Point point = new Point(0,0);
    private GLU glu  = new GLU();
    private float rquad=0.0f;
    int i = 0;
    @Override
    public void init(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glClearColor(0f,0f,0f,1.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL2.GL_NICEST);
    }
    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();
    }
    @Override
    public void display(GLAutoDrawable drawable) {
        i++;
        GLUT glut = new GLUT();
        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();//important
        gl.glEnable(GL2.GL_BLEND);
        //gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glTranslatef(0.0f,0.0f,-6.0f); //important
        //gl.glRotatef(rquad,2.0f,0.0f,0.0f);
        gl.glRotatef(rquad, 0, 2.0f, 0.0f);
        gl.glRotatef(rquad,2.0f,0.0f,0.0f);

        gl.glBegin(GL2.GL_QUADS);
        gl.glColor4f(1,0,0,0);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f( 1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f,  1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        // BACK:
        gl.glColor4f(0,1,0,0);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glVertex3f(1.0f,  1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        // LEFT:
        gl.glColor4f(0,0,1,1);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f,  1.0f, 1.0f);
        gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        // RIGHT
        gl.glColor4f(1,1,0.01f,1);
        gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f, 1.0f, -1.0f);
        gl.glVertex3f( 1.0f,  1.0f, 1.0f);
        gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        // TOP
        gl.glColor4f(0,1,1,1);
        gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glVertex3f( 1.0f,  1.0f, 1.0f);
        gl.glVertex3f( 1.0f,  1.0f,- 1.0f);
        gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        // BOTTOM:
        gl.glColor4f(1,0,1,1);
        gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f( 1.0f, -1.0f,  1.0f);

        gl.glEnd();


        gl.glFlush();
        rquad -= 0.2f;//velocita' di rotazione
        System.out.println(i);
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();

        if(height<=0) height = 1;
        final float h = (float) width/(float)height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f,h,1.0,20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    public static void main(String[] args){

        final GLProfile profile;
        profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities (profile);

        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Polygon3d r = new Polygon3d();
        glcanvas.addGLEventListener(r);
        glcanvas.setSize(400,400);
        final FPSAnimator animator = new FPSAnimator (glcanvas,300,true);
        final JFrame frame = new JFrame ("animazione");
        frame.getContentPane().add(glcanvas);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(animator.isStarted()) animator.stop();
                System.exit(0);
            }
        });
        frame.setSize(frame.getContentPane().getPreferredSize());
        graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
        dm_old = devices[0].getDisplayMode();
        dm = dm_old;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowX = Math.max(0, (screenSize.width - frame.getWidth())/2);
        int windowY = Math.max(0, (screenSize.height - frame.getWidth())/2);
        frame.setLocation(windowX,windowY);
        frame.setVisible(true);
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(0,0));
        frame.add(p,BorderLayout.SOUTH);
        keyBindings(p,frame,r);
        animator.start();

    }

    private static void keyBindings(JPanel p, final JFrame frame, Polygon3d r){
        ActionMap actionMap = p.getActionMap();
        InputMap inputMap = p.getInputMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0), "F1");
        actionMap.put("F1", new AbstractAction(){

            //private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                fullScreen(frame);
            }

        });
    }
    protected static void fullScreen(JFrame f){
        if(!isFullScreen){
            f.dispose();
            f.setUndecorated(true);
            f.setVisible(true);
            f.setResizable(false);
            xgraphic = f.getSize();
            point = f.getLocation();
            f.setLocation(0,0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            f.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
            isFullScreen = true;
        }
        else{
            f.dispose();
            f.setUndecorated(false);
            f.setResizable(true);
            f.setLocation(point);
            f.setSize(xgraphic);
            f.setVisible (true);
        }
        isFullScreen= false;
    }
}