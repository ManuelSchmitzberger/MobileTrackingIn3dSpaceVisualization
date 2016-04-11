from OpenGL.GL import *
from OpenGL.GLUT import *
from OpenGL.GLU import *
import socket
import sys
import os
import threading
 
ESCAPE = '\033'
RIGHT = 'a'
LEFT = 'd'
UP = 'w'
DOWN = 's'
PAGE_UP = 'q'
PAGE_DOWN = 'e'
HOME = 'h'
 
window = 0
 
#rotation
rotateX = 0.0
rotateY = 0.0
rotateZ = 0.0
 
DIRECTION = 1
 
 
def InitGL(Width, Height):
        glClearColor(0.0, 0.0, 0.0, 0.0)
        glClearDepth(1.0) 
        glDepthFunc(GL_LESS)
        glEnable(GL_DEPTH_TEST)
        glShadeModel(GL_SMOOTH)   
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        gluPerspective(45.0, float(Width)/float(Height), 0.1, 100.0)
        glMatrixMode(GL_MODELVIEW)
 
def keyPressed(*args):
        global rotateX,rotateZ,rotateY

        if args[0] == ESCAPE:
                sys.exit()
        elif args[0] == LEFT:
                rotateY -= 15;
        elif args[0] == RIGHT:
                rotateY += 15;
        elif args[0] == DOWN:
                rotateX += 15;
        elif args[0] == UP:
                rotateX -= 15;
        elif args[0] == PAGE_UP:
                rotateZ += 15;
        elif args[0] == PAGE_DOWN:
                rotateZ -= 15;
        elif args[0] == HOME:
                rotateX = rotateY = rotateZ = 0;

 
 
def DrawGLScene():
        global rotateX,rotateY,rotateZ
        global DIRECTION
 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
 
        glLoadIdentity()
        glTranslatef(0.0,0.0,-6.0)
 
        glRotatef(rotateX,1.0,0.0,0.0)
        glRotatef(rotateY,0.0,1.0,0.0)
        glRotatef(rotateZ,0.0,0.0,1.0)
 
        # Draw Cube (multiple quads)
        glBegin(GL_QUADS)
 
        glColor3f(0.0,1.0,0.0)

        glVertex3f( 2.0, 0.2,-1.0)
        glVertex3f(-2.0, 0.2,-1.0)
        glVertex3f(-2.0, 0.2, 1.0)
        glVertex3f( 2.0, 0.2, 1.0)
 
        glColor3f(0.5,0.5,0.5)
        glVertex3f( 2.0,-0.2, 1.0)
        glVertex3f(-2.0,-0.2, 1.0)
        glVertex3f(-2.0,-0.2,-1.0)
        glVertex3f( 2.0,-0.2,-1.0)
 
        glColor3f(0.5,0.5,0.5)
        glVertex3f( 2.0, 0.2, 1.0)
        glVertex3f(-2.0, 0.2, 1.0)
        glVertex3f(-2.0,-0.2, 1.0)
        glVertex3f( 2.0,-0.2, 1.0)
 
        glColor3f(0.5,0.5,0.5)
        glVertex3f( 2.0,-0.2,-1.0)
        glVertex3f(-2.0,-0.2,-1.0)
        glVertex3f(-2.0, 0.2,-1.0)
        glVertex3f( 2.0, 0.2,-1.0)
 
        glColor3f(0.5,0.5,0.5)
        glVertex3f(-2.0, 0.2, 1.0)
        glVertex3f(-2.0, 0.2,-1.0)
        glVertex3f(-2.0,-0.2,-1.0)
        glVertex3f(-2.0,-0.2, 1.0)
 
        glColor3f(0.5,0.5,0.5)
        glVertex3f( 2.0, 0.2,-1.0)
        glVertex3f( 2.0, 0.2, 1.0)
        glVertex3f( 2.0,-0.2, 1.0)
        glVertex3f( 2.0,-0.2,-1.0)

        glEnd()
 
        glutSwapBuffers()

        updatePhoneRotations()

def updatePhoneRotations():
        global rotateX,rotateY,rotateZ



        return

 
def main():
        global window

        # SETUP TCP/IP SOCKET
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Bind the socket to the port
        server_address = ('10.3.1.125', 4000)
        print sys.stderr, 'starting up on %s port %s' % server_address
        sock.bind(server_address)
        # Listen for incoming connections
        sock.listen(1)

        # while True:
        #     # Wait for a connection
        #     print >>sys.stderr, 'waiting for a connection'
        #     connection, client_address = sock.accept()
        #
        #     try:
        #         print >>sys.stderr, 'connection from', client_address
        #         # Receive the data in small chunks and retransmit it
        #         while True:
        #             data = connection.recv(200)
        #             if data:
        #                 #print >>sys.stderr, 'received "%s"' % data
        #                 print >>sys.stderr, data
        #                 #print >>sys.stderr, 'sending data back to the client'
        #                 #connection.sendall(data)
        #             else:
        #                 #print >>sys.stderr, 'no more data from', client_address
        #                 break
        #     finally:
        #         # Clean up the connection
        #         connection.close()




        # SETUP OPENGL
        glutInit(sys.argv)
        glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH)
        glutInitWindowSize(640,480)
        glutInitWindowPosition(200,200)

        window = glutCreateWindow('OpenGL Python Cube')

        glutDisplayFunc(DrawGLScene)
        glutIdleFunc(DrawGLScene)
        glutKeyboardFunc(keyPressed)
        InitGL(640, 480)
        glutMainLoop()

 
if __name__ == "__main__":
        main() 