import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import java.io.File; 
import java.io.FilenameFilter; 
import javax.swing.*; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.nio.file.Files; 
import java.nio.file.FileVisitResult; 
import java.nio.file.FileVisitor; 
import java.nio.file.SimpleFileVisitor; 
import java.nio.file.StandardCopyOption; 
import java.nio.file.attribute.BasicFileAttributes; 
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING; 
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES; 
import java.io.FileOutputStream; 
import java.io.ObjectOutputStream; 
import java.io.FileInputStream; 
import java.io.ObjectInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.FilePermission; 
import javax.swing.*; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ModChangerForAmongUs extends PApplet {































String folderType;
final String EPIC = "Epic Games";
final String STEAM = "Steam";

ObjectOutputStream oos = null;

ControlP5 cp5;

String selectedFolder = null;
ArrayList<String> mods;

AlertApplet alert;

public void settings(){
  size(450,800);
}

public void setup(){
  textFont(createFont("Meiryo",20));
  fill(0);
  
  cp5 = new ControlP5(this);
  cp5.setFont(createFont("Meiryo",20));
  
  cp5.addButton("folder")
    .setPosition(20,20)
    .setSize(100,30)
    .setCaptionLabel("フォルダ")
    ;
  
  for(int i=0;i<8;i++){
    cp5.addButton("mod"+i)
      .setPosition(20,100+75*i)
      .setSize(410,50)
      .setCaptionLabel("EMPTY")
      .setColorBackground(0)
      .setColorValue(255)
      .hide()
      ;
  }
  
  cp5.addButton("addmod")
    .setPosition(20,height-50)
    .setSize(100,30)
    .setCaptionLabel("Mod追加")
    ;
  
  mods = new ArrayList<String>();
  
  try{
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sketchPath()+"\\data.bin"));
    selectedFolder = (String)ois.readObject();
    File saveFolder = new File(selectedFolder);
    loadMods(saveFolder);
  }catch(Exception e){
    e.printStackTrace();
  }
}

public void draw(){
  background(16);
  fill(255);textSize(20);textAlign(LEFT);
  if(selectedFolder != null){
    text(selectedFolder,120,40);
  }else{
    text("フォルダを選択してください。",120,40); 
  }
  
  for(int i=0;i<8;i++){
    fill(255);
    rect(20,100+75*i,410,50);
    fill(0);textAlign(CENTER);
    stroke(255);strokeWeight(8);
    text("EMPTY",width/2,100+75*i+30);
  }
  
  fill(255);textSize(15);textAlign(LEFT);
  textLeading(20);
  if(selectedFolder != null){
    text("新しくできた「AmongUs._modname_」の\n名称を変更してご利用ください。",120,height-40);
  }else{
    text("フォルダを選択してから押してください。",120,height-40); 
  }
}

public void folder(){
  selectFolder("「AmongUs」フォルダの格納場所を選択してください。","folderSelected");
}

public void folderSelected(File selection) {
  if(selection == null)return;
  
  selectedFolder = selection.getAbsolutePath();
  println("選択フォルダ："+selectedFolder);
  
  try{
    oos = new ObjectOutputStream(new FileOutputStream(sketchPath()+"\\data.bin"));
    oos.writeObject(selectedFolder);
    oos.close();
  }catch(Exception e){
    e.printStackTrace();
  }
  
  loadMods(selection);
}

public void loadMods(File selection){  
  folderType = (selectedFolder.contains(EPIC))?"AmongUs":"Among Us";
  
  int index = 1;
  File[] files = selection.listFiles();
  ArrayList<String> folderNameList = new ArrayList<String>();
  for(File file:files){
    if(file.isDirectory()){
      folderNameList.add(file.getName());
    }
  }
  
  mods = new ArrayList<String>();
  for(String folderName:folderNameList){
    String absolutePath = selectedFolder+"/"+folderName;
    
    //一時フォルダ(.tmp)の削除
    if(folderName.startsWith(folderType) && folderName.endsWith(".tmp")){
      deleteFolder(absolutePath);
    }
    
    String modName = folderName.replace(folderType+".","");
    if(folderName.endsWith(".tmp"))continue;
    if(folderName.equals(folderType)){
      if(!folderNameList.contains(folderType+".vanilla")){
        modName = "vanilla";
        mods.add(0,"vanilla");
        cp5.get("mod"+0).setCaptionLabel("vanilla");
        cp5.get("mod"+0).show();
        copyFolder(absolutePath,absolutePath+".vanilla");
      }
    }else if(folderName.equals(folderType+".vanilla") && !mods.contains("vanilla")){
      mods.add(0,modName);
      cp5.get("mod"+0).setCaptionLabel(modName);
      cp5.get("mod"+0).show();
    }else{
      mods.add(modName);
      cp5.get("mod"+index).setCaptionLabel(modName);
      cp5.get("mod"+index).show();
      index++;
    };
    println("フォルダ名："+folderName+"\tMod名："+modName);
  }
  println(mods.toString());
}

public void mod0(){
  if(selectedFolder == null)return;
  String modName = mods.get(0);
  startGame(modName);
}

public void mod1(){
  if(selectedFolder == null)return;
  String modName = mods.get(1);
  startGame(modName);
}

public void mod2(){
  if(selectedFolder == null)return;
  String modName = mods.get(2);
  startGame(modName);
}

public void mod3(){
  if(selectedFolder == null)return;
  String modName = mods.get(3);
  startGame(modName);
}

public void mod4(){
  if(selectedFolder == null)return;
  String modName = mods.get(4);
  startGame(modName);
}

public void mod5(){
  if(selectedFolder == null)return;
  String modName = mods.get(5);
  startGame(modName);
}

public void mod6(){
  if(selectedFolder == null)return;
  String modName = mods.get(6);
  startGame(modName);
}

public void mod7(){
  if(selectedFolder == null)return;
  String modName = mods.get(7);
  startGame(modName);
}

public void addmod(){
  if(selectedFolder == null)return;
  copyFolder(
    selectedFolder+"\\"+folderType+".vanilla",
    selectedFolder+"\\"+folderType+"._modname_"
  );
}

public void startGame(String modName){
  println(modName+"を開始");

  alert = new AlertApplet();
  alert.runSketch(new String[]{""},alert);
  JFrame frame=(JFrame)
    ((processing.awt.PSurfaceAWT.SmoothCanvas)alert.getSurface()
    .getNative()).getFrame();
  // 該当Windowから、全てのWindow操作用イベントを削除し
    // 新しいイベントに書き換える
    for (final java.awt.event.WindowListener evt :
         frame.getWindowListeners()){
      // イベントを削除する
      frame.removeWindowListener(evt);
      // Window Close 動作を再定義する
      // → 登録されている任意の WindowListener を呼び出したあとで
      //   自動的にフレームを隠して破棄する
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
      // 新しいWindow 操作イベントをセットする
      frame.addWindowListener(new WindowManage(alert));
    }
         
  String execPathStr = selectedFolder+"/"+folderType;  
  String modPathStr = selectedFolder+"/"+folderType+"."+modName;
  try{
    //バニラフォルダ退避
    //copyFolder(execPathStr,selectedFolder+"/"+folderType+".tmp");
    
    //Modフォルダを退避
    copyFolder(modPathStr,modPathStr+".tmp");
    
    //元の実行用AmongUsフォルダを削除
    deleteFolder(execPathStr);
    //実行用AmongUsフォルダを上書き
    copyFolder(modPathStr,execPathStr);
  }catch(Exception e){
    e.printStackTrace(); 
  }
  
  if(alert!=null)alert.st = true;
  
  File saveFolder = new File(selectedFolder);
  loadMods(saveFolder);
}

public void deleteFolder(String path){
  try{
    File filePath = new File(path);
      String[] list = filePath.list();
      for(String file : list) {
          File f = new File(path + File.separator + file);
          if(f.isDirectory()) {
              deleteFolder(path + File.separator + file);
          }else {
              f.delete();
          }
      }
      filePath.delete();
  }catch(Exception e){
    e.printStackTrace();
  }
}

public void copyFolder(String from, String to){
  println(from+"を"+to+"にコピー");
  //コピー元
  final Path fromPath = Paths.get(from);
  //コピー先
  final Path toPath = Paths.get(to);

  //FileVisitorの定義
  FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
      //ディレクトリをコピーする
      Files.copy(dir, toPath.resolve(fromPath.relativize(dir)), StandardCopyOption.COPY_ATTRIBUTES);
      return FileVisitResult.CONTINUE;
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      //ファイルをコピーする
      Files.copy(file, toPath.resolve(fromPath.relativize(file)), StandardCopyOption.COPY_ATTRIBUTES);
      return FileVisitResult.CONTINUE;
    }
  };
  
  //ファイルツリーを辿ってFileVisitorの動作をさせる
  try {
    Files.walkFileTree(fromPath, visitor);
  } catch (IOException e) {
    e.printStackTrace();
  }
}



class AlertApplet extends PApplet{
  
  boolean st = false;
  String text0 = "入れ替え中";
  String text1 = "入れ替え完了!";
  
  public void settings(){
    this.size(200,100);
  }
  
  public void setup(){
    this.textSize(20);
    this.textFont(createFont("Meiryo",20));
    this.textAlign(CENTER);
  }

  public void draw(){
    this.background(255);
    this.fill(0);
    this.text((st)?text1:text0,width/2,height/2);
  }

  public void mousePressed(){
    if(st)this.close();
  }
  
  public void exit(){
    dispose();
  }
  
  public void close(){
    surface.setVisible(false); // ウィンドウを消すのに必要
    dispose(); // スレッドを終了するのに必要
  }
  
  public void all_close(){
    System.exit(0);
  }
}
class WindowManage extends WindowAdapter{
  PApplet subApp;
  
  // コンストラクタ
  public WindowManage( PApplet _subApp ){
    subApp = _subApp;
  }
  
  // Closeされた直後に動作する部分
  public void windowClosed(WindowEvent e) {
    // 自分を終了する
    subApp.noLoop();
    subApp.getSurface().stopThread();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ModChangerForAmongUs" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
