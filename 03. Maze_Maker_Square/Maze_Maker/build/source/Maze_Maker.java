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

public class Maze_Maker extends PApplet {

class cell{
  int xpos, ypos, size;
  boolean up, down, left, right;
  cell(int x, int y, int s){
    xpos = x;
    ypos = y;
    size = s;
    up = true;
    down = true;
    left = true;
    right = true;
  }
  public void draw(){
    fill(0);
    if(up){rect(xpos, ypos, size, 1);}
    if(down){rect(xpos, ypos + size, size, 1);}
    if(left){rect(xpos, ypos, 1, size);}
    if(right){rect(xpos + size, ypos, 1, size);}
  }
  public boolean isConnected(int a, int b, cell[][] c){ //connected to c[a][b]??
    int[][] path = new int[c.length][c[0].length];
    for(int i = 0; i < c.length; i++){
      for(int j = 0; j < c[0].length; j++){
        path[i][j] = 0; // 0 = unvisited
      }
    }
    return true;
  }
  public int[][] connected(int a, int b, int[][] c){
    return null;
  }
}
/*---------------------------------------------------------------------------*/
int margin = 20;
int path = 10;
int r, c;
boolean done = false;
cell[][] cells;
boolean[][] connected;
public void setup(){
  
  stroke(0);
  fill(255);
  background(255);
  while(!done){
    cells = new cell[(height - 2*margin)/path][(width - 2*margin)/path];
    for(int i = 0; i < cells.length; i++){
      for(int j = 0; j < cells[0].length; j++){
        cells[i][j] = new cell((j*path) + margin, (i*path) + margin ,path);
        //set border
        if(i == 0){
          cells[i][j].up = true;
        }
        if(i == cells.length - 1){
          cells[i][j].down = true;
        }
        if(j == 0){cells[i][j].left = true;
        }
        if(j == cells[0].length - 1){
          cells[i][j].right = true;
        }
      }
    }
    connected = new boolean[cells.length][cells[0].length];
    for(int i = 0; i < connected.length; i++){
      for(int j = 0; j < connected[0].length; j++){
        connected[i][j] = false;
      }
    }
    int a = 0, b = 0, c;
    boolean possible;
    while(true){
      if(a == connected.length - 1 && b == connected[0].length - 1){
        connected[a][b] = true;
        done = true;
        break;
      }
      connected[a][b] = true;
      c = (int)random(0, 4); // up, down, left, right
      if(c == 0 && a != 0){
        if(!connected[a-1][b]){
          cells[a][b].up = false;
          cells[a-1][b].down = false;
          a -= 1;
        }
      }
      else if(c == 1 && a != connected.length - 1){
        if(!connected[a+1][b]){
          cells[a][b].down = false;
          cells[a+1][b].up = false;
          a += 1;
        }
      }
      else if(c == 2 && b != 0){
        if(!connected[a][b-1]){
          cells[a][b].left = false;
          cells[a][b-1].right = false;
          b -= 1;
        }
      }
      else if(c == 3 && b != connected[0].length - 1){
        if(!connected[a][b + 1]){
          cells[a][b].right = false;
          cells[a][b+1].left = false;
          b += 1;
        }
      }
      possible = false;
      if(a != 0 && !connected[a - 1][b]){possible = true;}
      if(b != 0 && !connected[a][b - 1]){possible = true;}
      if(a != connected.length - 1 && !connected[a+1][b]){possible = true;}
      if(b != connected[0].length - 1 && !connected[a][b+1]){possible = true;}
      if(!possible){break;}
    }
  }
  //Done making path. Now connecting rest of the cells
  boolean done = false;
  int a, b;
  for(int x = 0; x < cells.length-1; x++){
    for(int y = 0; y< cells[0].length - 1; y++){
      a = x;
      b = y;
      if((connected[a][b] && !connected[a][b+1])){
        done = false;
        cells[a][b].right = false;
        cells[a][b+1].left = false;
        b += 1;
        while(!done){
          connected[a][b] = true;
          c = (int)random(0, 4); // up, down, left, right
          if(c == 0 && a != 0){
            if(connected[a-1][b]){done = true;}
            else{
              cells[a][b].up = false;
              cells[a-1][b].down = false;
              a -= 1;
            }
          }
          else if(c == 1 && a != connected.length - 1){
            if(connected[a+1][b]){done = true;}
            else{
              cells[a][b].down = false;
              cells[a+1][b].up = false;
              a += 1;
            }
          }
          else if(c == 2 && b != 0){
            if(connected[a][b-1]){done = true;}
            else{
              cells[a][b].left = false;
              cells[a][b-1].right = false;
              b -= 1;
            }
          }
          else if(c == 3 && b != connected[0].length - 1){
            if(connected[a][b+1]){done = true;}
            else{
              cells[a][b].right = false;
              cells[a][b+1].left = false;
              b += 1;
            }
          }
        }
      }
      a = x;
      b = y;
      if((connected[a][b] && !connected[a+1][b])){
        done = false;
        cells[a][b].down = false;
        cells[a+1][b].up = false;
        a += 1;
        while(!done){
          connected[a][b] = true;
          c = (int)random(0, 4); // up, down, left, right
          if(c == 0 && a != 0){
            if(connected[a-1][b]){done = true;}
            else{
              cells[a][b].up = false;
              cells[a-1][b].down = false;
              a -= 1;
            }
          }
          else if(c == 1 && a != connected.length - 1){
            if(connected[a+1][b]){done = true;}
            else{
              cells[a][b].down = false;
              cells[a+1][b].up = false;
              a += 1;
            }
          }
          else if(c == 2 && b != 0){
            if(connected[a][b-1]){done = true;}
            else{
              cells[a][b].left = false;
              cells[a][b-1].right = false;
              b -= 1;
            }
          }
          else if(c == 3 && b != connected[0].length - 1){
            if(connected[a][b+1]){done = true;}
            else{
              cells[a][b].right = false;
              cells[a][b+1].left = false;
              b += 1;
            }
          }
        }
      }
    }
  }
  for(int i = 0; i < cells.length; i++){
    for(int j = 0; j< cells[0].length; j++){
      cells[i][j].draw();
    }
  }
  save("../screenshots/maze.jpg");
}
  public void settings() {  size(1000,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Maze_Maker" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
