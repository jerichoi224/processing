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

public class Circle_Testing extends PApplet {

class circle{
  int c;
  float diameter, radius;
  float xpos, ypos; // coordinate of circle
  float xspeed, yspeed; // moving speed of circle
  float xdis, ydis; // distance from mouse pointer when clicked
  int alpha;
  boolean clicked;
  circle(){
    if(random(0, 2) > 1){
      c = color(221, 236, 200);
    }else{
      c = color(158, 219, 236);
    }
    diameter = random(60, 130);
    radius = diameter / 2;
    xpos = random(diameter, width - diameter);
    ypos = random(diameter, height - diameter);
    xspeed = random(-.75f, .75f);
    yspeed = random(-.75f, .75f);
    clicked = false;
  }
  public void nonClickDisplay(){
    noStroke();
    int dr = width / 4; // disappearing range;
    //gets more transparent as it gets closer to the border
    float[] list = {(xpos-radius)/dr, (ypos-radius)/dr, (height - ypos - radius)/dr, (width - xpos - radius)/dr, 1};
    alpha = (int)(150 * min(list));
    float mouseDis = sqrt(pow(xpos - mouseX, 2) + pow((ypos - mouseY), 2));
    //else just continue what it was doing
    if(mouseDis < radius){
      fill(255, 204, 204, alpha);
    }else{
      fill(c, alpha);
    }
    ellipse(xpos, ypos, diameter, diameter);
    fill(100, alpha);
    rect(xpos - 2, ypos - 2, 4, 4);
  }
  public void clickDisplay(){
    noStroke();
    int dr = width / 4; // disappearing range;
    //gets more transparent as it gets closer to the border
    float[] list = {(xpos-radius)/dr, (ypos-radius)/dr, (height - ypos - radius)/dr, (width - xpos - radius)/dr, 1};
    alpha = (int)(150 * min(list));
    //if mouse clicked, change to red, and move it.
    float mouseDis = sqrt(pow(xpos - mouseX, 2) + pow((ypos - mouseY), 2));
    if(!clicked && mouseDis < radius && mousePressed){
      clicked = true;
      xdis = xpos - mouseX;
      ydis = ypos - mouseY;
    }
    if(clicked){
      xpos = mouseX + xdis;
      ypos = mouseY + ydis;
      fill(255, 204, 204, alpha);
      ellipse(xpos, ypos, diameter, diameter);
      fill(100, alpha);
      rect(xpos - 2, ypos - 2, 4, 4);
    }
  }
  public void move(){
    if(xpos > width - radius || xpos < radius){
      xspeed *= -1;
    }
    if(ypos > height - radius || ypos < radius){
      yspeed *= -1;
    }
    if(!clicked){
      xpos += xspeed;
      ypos += yspeed;
    }
  }
}

circle[] circles = new circle[20];
public void setup(){
  
  for(int i = 0; i < circles.length; i++){
    circles[i] = new circle();
  }
}
public void draw(){
  background(255);
  for(int i = 0; i < circles.length; i++){
    float mouseDis = sqrt(pow(circles[i].xpos - mouseX, 2) + pow((circles[i].ypos - mouseY), 2));
    if(mousePressed && mouseDis < circles[i].radius){
      boolean prevClicked = false;
      for(int j = 0; j < circles.length; j ++){
        if(circles[j].clicked && j != i){
          prevClicked = true;
        }
      }
      if(prevClicked){
        circles[i].nonClickDisplay();
      }else{
        circles[i].clickDisplay();
      }
    }else{
      circles[i].nonClickDisplay();
      circles[i].clicked = false;
    }
    circles[i].move();
    for(int j = i+1; j < circles.length; j++){
      int dis = (int)sqrt(pow((circles[j].xpos - circles[i].xpos), 2) + pow((circles[j].ypos - circles[i].ypos), 2));
      if(dis < circles[i].radius){
        int a = min(circles[i].alpha, circles[j].alpha);
        stroke(100, a);
        line(circles[i].xpos, circles[i].ypos, circles[j].xpos, circles[j].ypos);
      }
    }
  }
}
  public void settings() {  size(500, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Circle_Testing" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
