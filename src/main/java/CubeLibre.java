import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.awt.*;

public class CubeLibre implements GLEventListener {
    public static DisplayMode dm, dm_old;
    private GLU glu = new GLU();
    private static float angleV = 0f;
    private static float angleH = 0f;
    private static float r = 1, g = 0, b = 0;

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0f, 0f, -5.0f);

        // Rotate The Cube On X, Y & Z
        gl.glRotatef(angleH, 1f, 0, 0);
        gl.glRotatef(angleV, 0, 1f, 0);
        // giving different colors to different sides
        gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
        gl.glColor3f(r, g, b); // red color
        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
        gl.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)

        gl.glColor3f(r, g, b); // green color
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad

        gl.glColor3f(0,0,1); // blue color
        gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad

        gl.glColor3f(r, g, b); // yellow (red + green)
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
        gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)

        gl.glColor3f(r, g, b); // purple (red + green)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
        gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad

        gl.glColor3f(r, g, b); // sky blue (blue +green)
        gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
        gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad
        gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad
        gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
        gl.glEnd(); // Done Drawing The Quad
//        if (angleV >= 360.0f) angleV = 0.0f;
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) height = 1;

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public static void setR(int red) {
        if (r < 256 && r >= 0) r = red / 255f;
    }

    public static void setG(int green) {
        if (g < 256 && g >= 0) g = green / 255f;
    }

    public static void setB(int blue) {
        if (b < 256 && b >= 0) b = blue / 255f;
    }

    public static void setAngleVertical(Float angle) {
        angleV = (angleV + angle) % 360.0f;
    }
    public static void setAngleHorizontal(Float angle) {
        angleH = (angleH +  angle) % 360.0f;
    }
}
