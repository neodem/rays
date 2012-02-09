package com.neodem.graphics.dd.engine.test;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.net.URL;

///////////////////////////////////////////////////////////////////////////
//
//  File: DBufAnim.java
//
//  Double Buffering animation applet
//
//  Demonstrates double-buffered graphics, within a "runnable" applet
//  to get smooth animation, as well as what I consider the best method
//  to deal with Java's rather inaccurate timer precision.
//
//  Please note that compiling this source with a current Java compiler
//  will generate deprecated warnings (or possibly errors). As of
//  JDK 1.2.1, the source will still compile correctly.  The reason I
//  stuck with Java 1.0 is so that the applet would run on as many
//  web-browsers as possible.  If you are using Java to compile native
//  code or servlets this source would probably not be useful to you
//  anyways.
//
//  Tutorial + source (c) 1999-2000 by Mike Linkovich
//
//  Last updated: Dec 26 2000
//
//  This source is intended for the purpose of learning, not as a final
//  applet. You may use and/or modify this source however you like.
//  Links to spacejack.org always appreciated.
//  Bug reports & comments welcome.
//
//  Contact: link@spacejack.org
//
///////////////////////////////////////////////////////////////////////////


//  A simple 2D vector class
//  (Java v1.2 has a more substantial Vector2D class, but most
//  browsers do not have v1.2 VMs as of this writing)
//
class Vect2D
{
  public float x = 0.0f,
               y = 0.0f;

  public Vect2D()  { }
  public Vect2D(float newx, float newy)
    { x = newx; y = newy; }
}


//  A little utility class we'll use for our bouncing ball
//  This is used more like a public C struct than a proper
//  Java class, for our simple little applet.
//
class Ball
{
  public Image     img = null;             //  Ball image
  public float     radius = 0.0f;          //  Radius
  public Vect2D    pos = new Vect2D();     //  Position
  public Vect2D    vel = new Vect2D();     //  Velocity (pixels/second)
  public int       xPrev = 0,
                   yPrev = 0;              //  Previous draw position -- for erasing

  public Ball()  { }
}


//  This class will take the current time, and return
//  an "averaged" time-slice back.  It gives us better
//  results than simply using Java's timer at face value.
//
class AverageTimer
{
  final static int  NUM_SAMPLES = 6;      //  # of samples to use for an average
  final static long INIT_MSPF = 30;       //  Initial assumed millis per frame (30ms is approx. 30fps)
  final static long MAX_DT = 100;         //  Max time for 1 frame.  This weeds out "spikes"
                                          //  that would advance the sim too much for 1 frame
  long  m_tick[] = new long[NUM_SAMPLES]; //  Array to remember samples
  int   m_index = 0;                      //  Current index to "circular" array

  public AverageTimer( long t )
  {
    int  i;

    //  Fill array with an assumed rate so far.. (INIT_MSPF)
    //  The actual times will quickly fix this, after NUM_SAMPLES samples.
    //
    for( i = NUM_SAMPLES - 1; i >= 0; i-- )
      m_tick[i] = t - ((NUM_SAMPLES - i) * INIT_MSPF);

    m_index = 0;
  }

  //  By supplying the current time (by using System.currentTimeMillis())
  //  we calculate the average time over the past NUM_SAMPLES frames, and record
  //  this current time to continue keeping the average accurate as possible.
  //
  public long deltaT( long cur_t )
  {
    int id = m_index-1;                       //  Calc index to previous sample
    if( id < 0 )                              //  Wrap if necessary..
      id += NUM_SAMPLES;

    long dt = cur_t - m_tick[id];             //  Dif time since prev sample

    if( dt > MAX_DT )                         //  If the slice was too big,
    {                                         //  we have to advance all the previous
      long ct = dt - MAX_DT;                  //  times by the diff..
      for( int i = 0; i < NUM_SAMPLES; i++ )
        m_tick[i] += ct;
    }

    long t = cur_t - m_tick[m_index];         //  Time dif since oldest time recorded
    m_tick[m_index] = cur_t;                  //  Save the current one now
    m_index = (m_index + 1) % NUM_SAMPLES;    //  Advance the index

    dt = t / (long)NUM_SAMPLES;               //  Calculate the final average time

    return dt;
  }
}


///////////////////////////////////////////////////////////
//
//  The DoubleBuffer Applet
//
//
public class DBufAnim extends Applet implements Runnable
{
  //  Note -- my coding convention prefaces private class member variables with "m_"
  //          I find this helps to distinguish between locals and class vars.
  //          Constants are indicated by ALL CAPS.

  final static int    APPWIDTH  = 320,           //  Applet dimensions
                      APPHEIGHT = 240;

  final static float  FAPPWIDTH  = (float)APPWIDTH,     //  In floating point (useful later..)
                      FAPPHEIGHT = (float)APPHEIGHT;

  final static float  GRAVITY = 100.0f;       //  Accel due to gravity (pixels/second/second)

  Thread    m_thrApp = null;                  //  Main applet thread

  Image     m_imgOffScr = null;               //  Image for off-screen drawing -- "back buffer"
  Graphics  m_gOffScr = null;                 //  Graphics drawing interface to offscr image
  Color     m_clrBg = new Color(0, 0, 100);   //  Background colour

  Ball      m_ball = null;                    //  The bouncing ball object

  AverageTimer  m_avgTimer = null;


  ///////////////////////////////////////////////////////////////////
  //
  //  Applet Methods follow
  //

  //  I like to set up the double-buffer first.  Then
  //  if other errors occur later, we at least have our
  //  display system up & running and may use it to display
  //  messages or other info.
  //
  public void init()
  {
    System.out.println("DBufAnim - 2000 - by Mike Linkovich");
    System.out.println("e: link@spacejack.org");
    System.out.println("===================================");

    setBackground(m_clrBg);         //  Set the background color
    resize(APPWIDTH, APPHEIGHT);    //  Set applet size

    //  Create an off-screen drawing buffer
    //  for double-buffered graphics...
    //
    try
    {
      m_imgOffScr = createImage(APPWIDTH, APPHEIGHT);
      m_gOffScr = m_imgOffScr.getGraphics();
    }
    catch (Exception e)
    {
      System.out.println("Failed to set up double-buffered graphics");
      m_imgOffScr = null;
      m_gOffScr = null;
      return;
    }

    m_gOffScr.setColor( m_clrBg );
    m_gOffScr.fillRect(0, 0, APPWIDTH, APPHEIGHT);
  }


  public void start()
  {
    if( m_thrApp == null )
    {
      m_thrApp = new Thread(this);
      m_thrApp.start();             //  This invokes run() (see below)
    }
  }

  public void stop()
  {
    //  Kill the thread
    //
    //  Note, JDK docs 1.2.x recommend this way to kill threads,
    //  even with Java 1.0.  suspend() was/is highly toxic
    //  (it can deadlock, leaving the browser hung -- I've seen
    //  it happen on NS3)  Note how run() will notice by checking
    //  for null and exit.
    //
    m_thrApp = null;
  }


  //
  //  Note that update/paint are not used to update/paint the ball.
  //
  //  I'm pretty sure this helps to cut down overhead of using
  //  repaint() in our main loop.  It also assuages my fears that
  //  update/paint will be called by the VM to clean up at the SAME
  //  time my run() is calling repaint.
  //
  //  This is just used to "clean up" the screen in the event it
  //  was obscured by another window, resized, etc.  Another
  //  advantage to separating them is that it helps us to
  //  distinguish between "clean-up" paints, and actual update paints.
  //
  //  See run() for my updateApp/paintApp alternative.
  //
  public void paint(Graphics g)
  {
    if( m_imgOffScr != null )
      g.drawImage(m_imgOffScr, 0, 0, this);   //  Just display the "back" buffer
  }

  public void update(Graphics g)
  {
    paint(g);
  }


  //
  //  The main applet thread method -- kinda like C's main()
  //  This function will start after init() and start() have
  //  been called by the Java VM.
  //
  //  Note that once run() exits, the program will stop.
  //
  public void run()
  {
    System.out.println("applet starting...");

    if( m_gOffScr == null )   //  Check that our double-buffer was created..
      return;                 //  If not, returning will finish thread's execution

    if( !initApp() )          //  If inits fail, abort & exit
      return;

    Graphics g = getGraphics();

    System.out.println("entering main loop...");

    while( m_thrApp != null ) //  Keep looping until thread is killed (see stop() )
    {
      updateApp();            //  Do all logic updates
      eraseApp();             //  Erase prev frame on "back" buffer
      paintApp(g);            //  Do all graphics, display on screen

      try                     //  Give back some CPU, otherwise
      {                       //  we'll get very erratic perf. with some VMs
        Thread.sleep(10);     //  10ms seems to be the sweet spot (?)
      } catch(InterruptedException e) { stop(); }
    }

    System.out.println("...applet terminated properly! cya.");
  }


  //  Handle Mouse button presses
  //
  public boolean mouseDown( Event evt, int x, int y )
  {
    if( m_ball != null )
    {
      //  Re-set the ball position..
      //
      m_ball.pos.x = APPWIDTH / 2;
      m_ball.pos.y = APPWIDTH / 5;
      m_ball.vel.y = 0.0f;
      m_ball.vel.x = (float)Math.random() * 100.0f - 50.0f;
    }
    return true;
  }


  /////////////////////////////////////////////////////////
  //
  //  Methods that follow are MY functions (i.e., not
  //  overrides of Applet.)
  //

  //
  //  Simple method to load & wait for image synchronously
  //  rather than deal with threading.  I find this is
  //  usually more useful, however if you needed to perform
  //  other tasks while loading, then you should take advantage
  //  of Java's asynchronous getImage loads.
  //
  Image loadImageSync( String imgFile )
  {
    MediaTracker  mt;
    Image         img;

    //  Start loading the graphics...
    //
    URL codeBase = getCodeBase();
    img = getImage(codeBase, imgFile);
    mt = new MediaTracker(this);
    mt.addImage( img, 0 );

    //  Wait here 'till it's done
    //
    try
    {
      mt.waitForAll();
    } catch(Exception e) { img = null; };

    if( img == null || img.getWidth(this) < 0 )
      System.err.println( "Failed to load image: '" + imgFile + "'" );

    return img;
  }


  //
  //  run() will call this.  So this occurs *after* init().
  //  By doing this, we could display a loader bar while
  //  resources are loaded, and we can abort if problems occur.
  //
  boolean initApp()
  {
    //  Set up the ball object
    //
    m_ball = new Ball();

    m_ball.img = loadImageSync("ball.gif");
    if( m_ball.img == null )
      return false;     //  Failed.

    m_ball.radius = (float)m_ball.img.getWidth(this) * 0.5f;  //  Assumes img is square and even # width!
    m_ball.pos.x = (float)(APPWIDTH / 2);                     //  Start pos
    m_ball.pos.y = (float)(APPWIDTH / 5);
    m_ball.vel.x = (float)Math.random() * 100.0f - 50.0f;     //  Random x vel
    m_ball.vel.y = 0.0f;

    //  Start our average timer now..
    //
    m_avgTimer = new AverageTimer( System.currentTimeMillis() );

    //  Got here?  Inits successful!  Ready to animate & do cool stuff!
    //
    return true;
  }


  //
  //  paintApp and updateApp are the guts of our program.
  //  run() calls these each frame in its main loop.
  //
  //
  void paintApp(Graphics g)
  {
    //  Draw the ball
    //  (and record the exact int position for the eraser)
    //
    m_ball.xPrev = (int)(m_ball.pos.x - m_ball.radius);
    m_ball.yPrev = (int)(m_ball.pos.y - m_ball.radius);

    m_gOffScr.drawImage( m_ball.img, m_ball.xPrev, m_ball.yPrev, this );

    //  Now that the back buffer has been fully re-drawn,
    //  display it on the "front" buffer
    //
    g.drawImage(m_imgOffScr, 0, 0, this);
  }


  //  This just erases the previous ball position on
  //  the back buffer -- not the whole buffer.
  //
  void eraseApp()
  {
    int r = (int)m_ball.radius * 2;
    m_gOffScr.fillRect( m_ball.xPrev, m_ball.yPrev, r, r );
  }


  //  Do logic updates for one time-slice.  This moves the
  //  ball according to the force of gravity, and checks for
  //  "bounces" off walls.
  //
  void updateApp()
  {
    //  Get the elapsed (and averaged) time slice
    //
    long  dt = m_avgTimer.deltaT( System.currentTimeMillis() );

    //  Convert to a float fraction of 1 sec
    //
    float ft = (float)dt * 0.001f;

    //  Now we update the ball's position.
    //  The ball moves horizontally at a constant velocity,
    //  while vertically it is accelerated by gravity
    //
    //  First along the x-axis..
    //
    m_ball.pos.x += m_ball.vel.x * ft;

    //  Check if hitting sides..
    //
    if( (m_ball.vel.x > 0.0f && m_ball.pos.x + m_ball.radius >= FAPPWIDTH) ||
        (m_ball.vel.x < 0.0f && m_ball.pos.x - m_ball.radius <= 0.0f) )
    {
      m_ball.vel.x = -m_ball.vel.x;   //  Reverse the x velocity -- bounce it
    }

    //  Now do the y -- accelerated due to gravity
    //
    float vel0 = m_ball.vel.y;      //  Save this for our cheap integrator
    m_ball.vel.y += GRAVITY * ft;   //  Increase vel by gravity
    m_ball.pos.y += (m_ball.vel.y + vel0) * 0.5f * ft;  //  New y pos

    //  Check if hit bottom..
    //
    if( m_ball.vel.y > 0.0f && m_ball.pos.y + m_ball.radius >= FAPPHEIGHT )
    {
      m_ball.vel.y = -m_ball.vel.y;   //  Reverse y vel
    }
  }
}

