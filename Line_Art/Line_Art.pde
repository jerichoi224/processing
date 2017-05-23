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
    //radius = random(60, 150);
    radius = 30;
  }
  void move(){
    if(xpos > width || xpos < 0){
      xspeed *= -1;
    }
    if(ypos > height || ypos < 0){
      yspeed *= -1;
    }
    xpos += xspeed;
    ypos += yspeed;
  }
}
point[] points = new point[1000];
void setup(){
  size(500, 500);
  for(int i = 0; i < points.length; i++){
    points[i] = new point();
  }
}
void draw(){
  stroke(100);
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
    }
  }
}