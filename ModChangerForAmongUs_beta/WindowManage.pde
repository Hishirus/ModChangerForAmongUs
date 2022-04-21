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
