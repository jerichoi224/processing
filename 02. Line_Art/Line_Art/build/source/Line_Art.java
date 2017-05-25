import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Line_Art extends PApplet {

class point{
  float xpos, ypos; // coordinate of circle
  float xspeed, yspeed; // moving speed of circle
  float radius;
  int lineCount;
  point(){
    xspeed = random(- 3, 3);
    yspeed = random(- 3, 3);
    xpos = random(0, width);
    ypos = random(0, height);
    radius = random(50, 80);
  }
  public void move(){
    if(xpos > width + 20 || xpos < - 20){
      xspeed *= -1;
    }
    if(ypos > height + 20 || ypos < - 20){
      yspeed *= -1;
    }
    xpos += xspeed;
    ypos += yspeed;
  }
}
point[] points = new point[1000];
public void setup(){
  
  for(int i = 0; i < points.length; i++){
    points[i] = new point();
  }
}
public void draw(){
  if(mousePressed)
    saveFrame("../screenshots/img####.jpg");
  stroke(0);
  background(255);
  for(int i = 0; i < points.length; i++){
    points[i].move();
    points[i].lineCount = 0;
    for(int j = i+1; j < points.length; j++){
      int dis = (int)sqrt(pow((points[j].xpos - points[i].xpos), 2) + pow((points[j].ypos - points[i].ypos), 2));
      if(dis < points[i].radius){
        line(points[i].xpos, points[i].ypos, points[j].xpos, points[j].ypos);
        points[i].lineCount++;
      }
      if(points[i].lineCount == 10){
        break;
      }
    }
  }
}
  public void settings() {  size(1000, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Line_Art" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
