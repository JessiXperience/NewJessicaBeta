package jessica.gui;

import jessica.utils.ColorUtils;
import jessica.utils.MiscUtils;
import jessica.utils.RenderUtils;
import jessica.utils.TimerUtils;

public class MatrixEffect {
  public int scw;
  
  public int sch;
  
  public TimerUtils time;
  
  public int cellsize;
  
  public int cellmaxticks;
  
  public int ticksdelay;
  
  public int xsize;
  
  public int ysize;
  
  public char[][] callChar;
  
  public int[][] cellColor;
  
  public int[][] cellTicks;
  
  public MatrixEffect(int screenwidth, int screenheight, int ticksdelay) {
    this.scw = screenwidth;
    this.sch = screenheight;
    this.time = new TimerUtils();
    this.ticksdelay = ticksdelay;
    this.cellsize = 9;
    this.cellmaxticks = 16;
    this.xsize = screenwidth / this.cellsize + 1;
    this.ysize = screenheight / this.cellsize + 1;
    this.callChar = new char[this.xsize][this.ysize];
    this.cellColor = new int[this.xsize][this.ysize];
    this.cellTicks = new int[this.xsize][this.ysize];
    init();
  }
  
  public void init() {
    int x;
    for (x = 0; x < this.xsize; x++) {
      for (int y = 0; y < this.ysize; y++)
        delete(x, y); 
    } 
    for (x = 0; x < this.xsize; x++)
      create(x, 0); 
  }
  
  private void create(int x, int y) {
    this.callChar[x][y] = randomChar();
    this.cellTicks[x][y] = this.cellmaxticks;
  }
  
  private void delete(int x, int y) {
    this.callChar[x][y] = ' ';
    this.cellColor[x][y] = -16777216;
    this.cellTicks[x][y] = 0;
  }
  
  private void tick() {
    int x;
    for (x = 0; x < this.xsize; x++) {
      for (int y = this.ysize - 1; y >= 0; y--)
        update(x, y); 
    } 
    for (x = 0; x < this.xsize; x++) {
      int rng = MiscUtils.random(0, 60);
      if (rng == 0 && this.cellTicks[x][0] == 0)
        create(x, 0); 
    } 
  }
  
  private void update(int x, int y) {
    if (this.cellTicks[x][y] == this.cellmaxticks && 
      y + 1 != this.ysize)
      create(x, y + 1); 
    if (this.cellTicks[x][y] == 0) {
      delete(x, y);
    } else {
      float factor = 1.0F / (this.cellmaxticks / this.cellTicks[x][y]);
      this.cellColor[x][y] = ColorUtils.darker(this.cellColor[x][y], factor);
      this.cellTicks[x][y] = this.cellTicks[x][y] - 1;
    } 
  }
  
  public void render() {
    if (this.ticksdelay > 0) {
      this.ticksdelay--;
      return;
    } 
    if (this.time.hasReached(5L)) {
      tick();
      this.time.reset();
    } 
    for (int x = 0; x < this.xsize; x++) {
      for (int y = 0; y < this.ysize; y++) {
        if (this.cellTicks[x][y] != 0) {
          String str = String.valueOf(this.callChar[x][y]);
          RenderUtils.drawStringWithShadow(str, (x * this.cellsize), (y * this.cellsize), this.cellColor[x][y]);
        } 
      } 
    } 
  }
  
  private char randomChar() {
    //String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
    String chars = "あいうえおかきくけこがぎぐげごさしすせそざじずぜぞたちつてとだぢづでどなにぬねのはひふへほばびぶべぼぱぴぷぺぽまみむめもやゆよらりるれろわをん日土月人五水語一二三国十四行木本休火九八入大天六生先白百万千七上下右左小子学男女出年川見中気金雨円名車校今友何山東書長高来食話聞読外前間後父母午半毎時西電南北";
    int rng = MiscUtils.random(0, chars.length() - 1);
    return chars.charAt(rng);
  }
}
