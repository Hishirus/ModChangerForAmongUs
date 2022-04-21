
class AlertApplet extends PApplet{
  
  boolean st = false;
  String text0 = "入れ替え中";
  String text1 = "入れ替え完了!";
  
  void settings(){
    this.size(200,100);
  }
  
  void setup(){
    this.textSize(20);
    this.textFont(createFont("Meiryo",20));
    this.textAlign(CENTER);
  }

  void draw(){
    this.background(255);
    this.fill(0);
    this.text((st)?text1:text0,width/2,height/2);
  }

  void mousePressed(){
    if(st)this.close();
  }
  
  void exit(){
    dispose();
  }
  
  void close(){
    surface.setVisible(false); // ウィンドウを消すのに必要
    dispose(); // スレッドを終了するのに必要
  }
  
  void all_close(){
    System.exit(0);
  }
}
