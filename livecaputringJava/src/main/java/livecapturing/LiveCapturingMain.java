package livecapturing;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

public class LiveCapturingMain {
    private static float rotateX, rotateY, rotateZ;
    static GLWindow glWindow;


    public static void main(String[] args) {
        GLProfile glp = GLProfile.get("GL3");

        GLCapabilities caps = new GLCapabilities(glp);

        glWindow = GLWindow.create(caps);
        glWindow.setSize(800, 600);
        glWindow.setVisible(true);
        glWindow.setRealized(false);

        glWindow.addWindowListener(new WindowAdapter() {
            public void windowDestroyNotify(WindowEvent e) {
                System.exit(0);
            }
        });

        FPSAnimator animator = new FPSAnimator(glWindow, 60);
        animator.start();

        glWindow.addGLEventListener(glEventListener);
        glWindow.addKeyListener(keyListener);

        rotateX = 15;
        rotateY = 15;
        rotateZ = 0;
    }

    public static GLEventListener glEventListener = new GLEventListener() {
        @Override
        public void init(GLAutoDrawable drawable) {
            // called when the panel is created
            GL2 gl = drawable.getGL().getGL2();
            gl.glClearColor(0.8F, 0.8F, 0.8F, 1.0F);
            gl.glEnable(GL.GL_DEPTH_TEST);
            gl.glEnable(GL2.GL_LIGHTING);
            gl.glEnable(GL2.GL_LIGHT0);
            gl.glEnable(GL2.GL_COLOR_MATERIAL);
        }

        @Override
        public void dispose(GLAutoDrawable drawable) {

        }

        @Override
        public void display(GLAutoDrawable drawable) {
            // called when the panel needs to be drawn
            GL2 gl = drawable.getGL().getGL2();
            gl.glClearColor(0,0,0,0);
            gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );

            gl.glMatrixMode(GL2.GL_PROJECTION);  // Set up the projection.
            gl.glLoadIdentity();
            gl.glOrtho(-1,1,-1,1,-2,2);
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            gl.glLoadIdentity();             // Set up modelview transform.
            gl.glRotatef(rotateZ,0,0,1);
            gl.glRotatef(rotateY,0,1,0);
            gl.glRotatef(rotateX,1,0,0);

            cube(gl);
        }

        @Override
        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        }
    };

    public static KeyListener keyListener = new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ( key == KeyEvent.VK_LEFT )
                rotateY -= 15;
            else if ( key == KeyEvent.VK_RIGHT )
                rotateY += 15;
            else if ( key == KeyEvent.VK_DOWN)
                rotateX += 15;
            else if ( key == KeyEvent.VK_UP )
                rotateX -= 15;
            else if ( key == KeyEvent.VK_PAGE_UP )
                rotateZ += 15;
            else if ( key == KeyEvent.VK_PAGE_DOWN )
                rotateZ -= 15;
            else if ( key == KeyEvent.VK_HOME )
                rotateX = rotateY = rotateZ = 0;
            glWindow.windowRepaint(0, 0, glWindow.getWidth(),glWindow.getHeight());
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    // ----------------- define some drawing methods for this program ----------

    private static void square(GL2 gl, float r, float g, float b) {
        gl.glColor3f(r,g,b);         // The color for the square.
        gl.glTranslatef(0,0,0.5f);    // Move square 0.5 units forward.
        gl.glNormal3f(0,0,1);        // Normal vector to square (this is actually the default).
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(-0.5f,-0.5f);    // Draw the square (before the
        gl.glVertex2f(0.5f,-0.5f);     //   the translation is applied)
        gl.glVertex2f(0.5f,0.5f);      //   on the xy-plane, with its
        gl.glVertex2f(-0.5f,0.5f);     //   at (0,0,0).
        gl.glEnd();
    }

    private static void cube(GL2 gl) {

        gl.glPushMatrix();
        square(gl,1,0,0);        // front face is red
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180,0,1,0); // rotate square to back face
        square(gl,0,1,1);        // back face is cyan
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(-90,0,1,0); // rotate square to left face
        square(gl,0,1,0);        // left face is green
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90,0,1,0); // rotate square to right face
        square(gl,1,0,1);       // right face is magenta
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(-90,1,0,0); // rotate square to top face
        square(gl,0,0,1);        // top face is blue
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90,1,0,0); // rotate square to bottom face
        square(gl,1,1,0);        // bottom face is yellow^^^^1^
        gl.glPopMatrix();

    }
}
