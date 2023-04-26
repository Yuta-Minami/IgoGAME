import java.net.*;
import java.io.*;
import javax.swing.*;
import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.ArrayList;

public class IgoGame extends JFrame implements MouseListener,MouseMotionListener {
	private JButton buttonArray[][], passButton, theButton, ToryoButton, greetButton, ruleButton, tryButton;//ボタン用の配列
	private JLabel myBlackLabel, myWhiteLabel, yourBlackLabel, yourWhiteLabel, 
	        myBlackCountLabel, myWhiteCountLabel, yourBlackCountLabel, 
			yourWhiteCountLabel, contextLabel, startBackLabel, myTurnLabel, yourTurnLabel, zabutonLabel,
			winLabel, loseLabel, drawLabel, ruleTextLabel;
	private int myColor, myTurn, count = 0, counter = 0,startCount = 0,
	            countRule = 0, tryCount = 0;
	private int blackCount = 0, whiteCount = 0;
	private ArrayList<Integer> checked = new ArrayList<Integer>();
	private ArrayList<Integer> checkList = new ArrayList<Integer>();
	private ImageIcon myIcon, yourIcon, myBlackIllust,myWhiteIllust, yourBlackIllust, yourWhiteIllust,
	        ToryoIcon, contextIcon, startBackIcon, greetIcon, myTurnIcon, yourTurnIcon, drawIcon, ruleIcon, ruleTextIcon,tryIcon;
	private JLayeredPane c;
	private ImageIcon blackIcon, whiteIcon, boardIcon,zabutonIcon, passIcon, loseIcon, winIcon;
	PrintWriter out;//出力用のライター

	public IgoGame() {
		//名前の入力ダイアログを開く
		String myName = JOptionPane.showInputDialog(null,"名前を入力してください","名前の入力",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//名前がないときは，"No name"とする
		}
		String conHost = JOptionPane.showInputDialog(null,"サーバのIPアドレスを入力してください","サーバのIPアドレスの入力",JOptionPane.QUESTION_MESSAGE);

		//ウィンドウを作成する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する
		setTitle("IgoGame");//ウィンドウのタイトルを設定する
		setSize(700,700);//ウィンドウのサイズを設定する
		c = new JLayeredPane();//フレームのペインを取得する
		this.getContentPane().add(c);
		c.setLayout(null);//自動レイアウトの設定を行わない

		//アイコンの設定
		whiteIcon = new ImageIcon("whiteIcon.jpg");
		blackIcon = new ImageIcon("blackIcon.jpg");
		boardIcon = new ImageIcon("Igo1.jpg");
		passIcon = new ImageIcon("pass.png");
		ToryoIcon = new ImageIcon("Toryo.png");
		contextIcon = new ImageIcon("tatami.png");
		startBackIcon = new ImageIcon("context3.png");
		greetIcon = new ImageIcon("greet2.png");
		myTurnIcon = new ImageIcon("myTurn.png");
		yourTurnIcon = new ImageIcon("yourTurn.png");
		zabutonIcon = new ImageIcon("zabuton2.png");
		loseIcon = new ImageIcon("Lose.png");
		winIcon = new ImageIcon("Win.png");
		drawIcon = new ImageIcon("draw.png");
		ruleIcon = new ImageIcon("rule.png");
		ruleTextIcon = new ImageIcon("ruleText.png");
		tryIcon = new ImageIcon("try.png");
        
		theButton = new JButton();//画像を貼り付けるラベル
        ImageIcon theImage = new ImageIcon("win.jpg");//なにか画像ファイルをダウンロードしておく
        theButton.setIcon(theImage);//ラベルを設定
        theButton.setBounds(0,0,700,700);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		
		c.add(theButton);
		
        
		theButton.setVisible(false);
		//スタート画面ラベルを作成
		startBackLabel = new JLabel(startBackIcon);
		c.add(startBackLabel);
		c.setLayer(startBackLabel, 30);
		startBackLabel.setBounds(0,0,700,700);
		//スタートボタンを作成
		greetButton = new JButton(greetIcon);
		c.add(greetButton);
		c.setLayer(greetButton, 50);
		greetButton.setBorderPainted(false);
		greetButton.setContentAreaFilled(false);
		greetButton.setBounds(220,360,300,75);
		greetButton.addMouseListener(this);
		//背景ラベルを設定
		contextLabel = new JLabel(contextIcon);
		c.add(contextLabel);
		c.setLayer(contextLabel, -10);
		contextLabel.setBounds(0,0,700,700);
		//zabutonラベルを設定
		zabutonLabel = new JLabel(zabutonIcon);
		c.add(zabutonLabel);
		c.setLayer(zabutonLabel, -5);
		zabutonLabel.setBounds(60,45, 550, 550);
		//myTurnラベルを作成
		myTurnLabel = new JLabel(myTurnIcon);
		c.add(myTurnLabel);
		c.setLayer(myTurnLabel, 10);
		//yourTurnラベルを作成
		yourTurnLabel = new JLabel(yourTurnIcon);
		c.add(yourTurnLabel);
		c.setLayer(yourTurnLabel, 10);
		//勝利ラベルを作成
		winLabel = new JLabel(winIcon);
		c.add(winLabel);
		c.setLayer(winLabel, 100);
		winLabel.setBounds(0,0,700,700);
		winLabel.setVisible(false);
		//敗北ラベルを作成
		loseLabel = new JLabel(loseIcon);
		c.add(loseLabel);
		c.setLayer(loseLabel, 100);
		loseLabel.setBounds(0,0,700,700);
		loseLabel.setVisible(false);
		//引き分けラベルを作成
		drawLabel = new JLabel(drawIcon);
		c.add(drawLabel);
		c.setLayer(drawLabel, 100);
		drawLabel.setBounds(0,0,700,700);
		drawLabel.setVisible(false);
		//もう一度！アイコンを作成
		tryButton = new JButton(tryIcon);
		c.add(tryButton);
		c.setLayer(tryButton, 200);
		tryButton.setBounds(250, 440, 210, 117);
		tryButton.setBorderPainted(false);
		tryButton.setVisible(false);
		tryButton.addMouseListener(this);
		//myBlackラベルを設定
		ImageIcon myBlackIllust = new ImageIcon("myBlack.png");
		myBlackLabel = new JLabel(myBlackIllust);
        c.add(myBlackLabel);
		//myWhiteラベルを設定
		ImageIcon myWhiteIllust = new ImageIcon("myWhite.png");
		myWhiteLabel = new JLabel(myWhiteIllust);
        c.add(myWhiteLabel);
        //yourBlackラベルを設定
		ImageIcon yourBlackIllust = new ImageIcon("yourBlack.png");
		yourBlackLabel = new JLabel(yourBlackIllust);
        c.add(yourBlackLabel);
		//yourWhiteラベルを設定
		ImageIcon yourWhiteIllust = new ImageIcon("yourWhite.png");
		yourWhiteLabel = new JLabel(yourWhiteIllust);
        c.add(yourWhiteLabel);
		//myCountラベルを設定
		myBlackCountLabel = new JLabel();
		myBlackCountLabel.setFont(new Font("HG行書体", Font.BOLD, 23));
		c.add(myBlackCountLabel);
		c.setLayer(myBlackCountLabel, 10);
		myBlackCountLabel.setBounds(197+80,524+70,30,30);
		yourBlackCountLabel = new JLabel();
		yourBlackCountLabel.setFont(new Font("HG行書体", Font.BOLD, 23));
		c.add(yourBlackCountLabel);
		c.setLayer(yourBlackCountLabel, 10);
		yourBlackCountLabel.setBounds(395+80,524+70,30,30);
		//yourCountラベルを設定
		myWhiteCountLabel = new JLabel();
		myWhiteCountLabel.setFont(new Font("HG行書体", Font.BOLD, 23));
		c.add(myWhiteCountLabel);
		c.setLayer(myWhiteCountLabel, 10);
		myWhiteCountLabel.setBounds(197+80,524+70,30,30);
		yourWhiteCountLabel = new JLabel();
		yourWhiteCountLabel.setFont(new Font("HG行書体", Font.BOLD, 23));
		c.add(yourWhiteCountLabel);
		c.setLayer(yourWhiteCountLabel, 10);
		yourWhiteCountLabel.setBounds(395+80,524+70,30,30);
		//投了アイコンを設定
		ToryoButton = new JButton(ToryoIcon);
		ToryoButton.setContentAreaFilled(false);
		ToryoButton.setBorderPainted(false);
		c.add(ToryoButton);
		ToryoButton.setBounds(500+80,300+70,70,70);
		ToryoButton.addMouseListener(this);
		//パスアイコンを設定
		passButton = new JButton(passIcon);
		passButton.setContentAreaFilled(false);
		passButton.setBorderPainted(false);
		c.add(passButton);
		passButton.setBounds(500+80,200+70,70,70);
		passButton.addMouseListener(this);
		//ルールアイコンを設定
		ruleButton = new JButton(ruleIcon);
		ruleButton.setContentAreaFilled(false);
		ruleButton.setBorderPainted(false);
		c.add(ruleButton);
		ruleButton.setBounds(500+80,100+70,70,70);
		ruleButton.addMouseListener(this);
		//ルールテキストラベルを設定
		ruleTextLabel = new JLabel(ruleTextIcon);
		c.add(ruleTextLabel);
		c.setLayer(ruleTextLabel, 300);
		ruleTextLabel.setBounds(230,100,350,500);
		ruleTextLabel.setVisible(false);
		//ボタンの生成
		buttonArray = new JButton[11][11];//ボタンの配列を9個作成する[0]から[8]まで使える
		for(int i=1;i<10;i++){
			for (int j = 1;j<10;j++) {
			  
			  buttonArray[i][j] = new JButton(boardIcon);//ボタンにアイコンを設定する
			  c.add(buttonArray[i][j]);//ペインに貼り付ける
			  buttonArray[i][j].setBounds(80+j*45,70+i*45,45,45);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
			  buttonArray[i][j].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
			
			  buttonArray[i][j].addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする
			
			  buttonArray[i][j].setActionCommand(Integer.toString(j*11+i));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
			  buttonArray[i][j].setBorderPainted(false);
			}
		}
		
		
		
		
		//サーバに接続する
		Socket socket = null;
		try {
			//"localhost"は，自分内部への接続．localhostを接続先のIP Address（"133.42.155.201"形式）に設定すると他のPCのサーバと通信できる
			//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
			if (conHost.equals("")) {
				socket = new Socket("localhost", 10000);
			} else {
			socket = new Socket(conHost, 10000);
			}
		} catch (UnknownHostException e) {
			System.err.println("ホストの IP アドレスが判定できません: " + e);
		} catch (IOException e) {
			 System.err.println("エラーが発生しました: " + e);
		}
		
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
		mrt.start();//スレッドを動かす（Runが動く）
	}
		
	//メッセージ受信のためのスレッド
	public class MesgRecvThread extends Thread {
		
		Socket socket;
		String myName;
		
		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}
		
		//通信状況を監視し，受信データによって動作する
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//接続の最初に名前を送る
				String myNumberStr = br.readLine(); 
				int myNumberInt = Integer.parseInt(myNumberStr);
				if (myNumberInt % 2 == 0) {
					myColor = 0;
					myTurn = 1;
					myIcon = whiteIcon;
					yourIcon = blackIcon;
					myWhiteLabel.setBounds(43+80,470+70,200,100);
					yourBlackLabel.setBounds(244+80,470+70,200,100);
					myTurnLabel.setBounds(200,30,400,30);
					myTurnLabel.setVisible(false);
					yourTurnLabel.setBounds(200,30,400,30);
					
				} else {
					myColor = 1;
					myTurn = 0;
					myIcon = blackIcon;
					yourIcon = whiteIcon;
					myBlackLabel.setBounds(43+80,470+70,200,100);
					yourWhiteLabel.setBounds(244+80,470+70,200,100);
					myTurnLabel.setBounds(200,30,400,30);
					yourTurnLabel.setBounds(200,30,400,30);
					yourTurnLabel.setVisible(false);
				}
				while(true) {
					String inputLine = br.readLine();//データを一行分だけ読み込んでみる
					if (inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする
						System.out.println(inputLine);//デバッグ（動作確認用）にコンソールに出力する
						String[] inputTokens = inputLine.split(" ");	//入力データを解析するために、スペースで切り分ける
						String cmd = inputTokens[0];//コマンドの取り出し．１つ目の要素を取り出す
						if(cmd.equals("MOVE")){//cmdの文字と"MOVE"が同じか調べる．同じ時にtrueとなる
							//MOVEの時の処理(コマの移動の処理)
							String theBName = inputTokens[1];//ボタンの名前（番号）の取得
							int theBnum = Integer.parseInt(theBName);//ボタンの名前を数値に変換する
							int x = Integer.parseInt(inputTokens[2]);//数値に変換する
							int y = Integer.parseInt(inputTokens[3]);//数値に変換する
							int i = theBnum / 11;
							int j = theBnum % 11;
							buttonArray[i][j].setLocation(x,y);//指定のボタンを位置をx,yに設定する
						} else if (cmd.equals("PLACE")) {
							String theBName = inputTokens[1];
							int theBnum = Integer.parseInt(theBName);
							int i = theBnum / 11;
							int j = theBnum % 11;
							int theColor = Integer.parseInt(inputTokens[2]);
							if (theColor == myColor) {
							    buttonArray[j][i].setIcon(myIcon);
							} else {
								buttonArray[j][i].setIcon(yourIcon);
							}
							myTurn = 1 - myTurn;
							if (myTurn == 1) {
								yourTurnLabel.setVisible(true);
								myTurnLabel.setVisible(false);
							} else {
								myTurnLabel.setVisible(true);
								yourTurnLabel.setVisible(false);
							}
						} else if (cmd.equals("REMOVE")) {
							String theBName = inputTokens[1];
							int theBnum = Integer.parseInt(theBName);
							int i = theBnum / 11;
							int j = theBnum % 11;
							int theColor = Integer.parseInt(inputTokens[2]);
							
							if (buttonArray[j][i].getIcon() == whiteIcon) {
								blackCount += 1;
								if (myTurn == 0) {
								  myBlackCountLabel.setText(Integer.toString(blackCount));
								  myBlackCountLabel.setVisible(true);
								} else if (myTurn == 1){
								  yourBlackCountLabel.setText(Integer.toString(blackCount));
								  yourBlackCountLabel.setVisible(true);
								}
							} else if (buttonArray[j][i].getIcon() == blackIcon) {
								whiteCount += 1;
								if (myTurn == 0) {
								  myWhiteCountLabel.setText(Integer.toString(whiteCount));
								  myWhiteCountLabel.setVisible(true);
								} else {
								  yourWhiteCountLabel.setText(Integer.toString(whiteCount));
								  yourWhiteCountLabel.setVisible(true);
								}
							}
							buttonArray[j][i].setIcon(boardIcon);
							
							
						} else if (cmd.equals("Pass")) {
							int theColor = Integer.parseInt(inputTokens[1]);
							myTurn = 1 - myTurn;
							count++;
							if (count == 2) {
								count = 0;
								judgeGame();
							}
						} else if (cmd.equals("Start")) {
							startCount++;
							if (startCount == 2) {
								startBackLabel.setVisible(false);
							}
						} else if (cmd.equals("Judge1")) {
							int theColor = Integer.parseInt(inputTokens[1]);
							if (myIcon == blackIcon) {
								winLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("黒win");
							} else if (myIcon == whiteIcon){
								loseLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("白lose");
							}
						} else if (cmd.equals("Judge2")) {
							int theColor = Integer.parseInt(inputTokens[1]);
							if (myIcon == whiteIcon) {
								winLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("白win");
							} else if (myIcon == blackIcon){
								loseLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("黒lose");
							}
						} else if (cmd.equals("Toryo")) {
							if (myTurn == 0) {
								loseLabel.setVisible(true);
								tryButton.setVisible(true);
							} else {
								winLabel.setVisible(true);
								tryButton.setVisible(true);
							}
						} else if (cmd.equals("Draw")) {
							drawLabel.setVisible(true);
							tryButton.setVisible(true);
						} else if (cmd.equals("Try")) {
							tryCount++;
							System.out.println(tryCount);
							if (tryCount % 3 == 0) {
								
							  winLabel.setVisible(false);
				              loseLabel.setVisible(false);
							  drawLabel.setVisible(false);
							  myBlackCountLabel.setVisible(false);
							  yourBlackCountLabel.setVisible(false);
							  myWhiteCountLabel.setVisible(false);
							  yourWhiteCountLabel.setVisible(false);
							 
				                for(int i=1;i<10;i++){
			                        for (int j = 1;j<10;j++) {
				                       buttonArray[j][i].setIcon(boardIcon);
			                        }
		                        }
							}
						}
					}else{
						break;
					}
				
				}
				socket.close();
			} catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
			}
		}
	}

	public static void main(String[] args) {
		IgoGame net = new IgoGame();
		net.setVisible(true);
	}
  	
	public void mouseClicked(MouseEvent e) {//ボタンをクリックしたときの処理
	    JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
	    String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す
        
		Icon theIcon = theButton.getIcon();
		
		if (theIcon == tryIcon) {
			tryCount++;
			blackCount = 0;
			whiteCount = 0;
			System.out.println("tryボタンが押されました");
			tryButton.setVisible(false);
			String msg = "Try" + " " + myColor;
			out.println(msg);
			out.flush();
		}
		if (theIcon == greetIcon) {
			System.out.println("スタートボタンが押されました");
			greetButton.setVisible(false);
			String msg = "Start";
			out.println(msg);
			out.flush();
		}
		
	    if (myTurn == 0 && startCount == 2 && (tryCount % 3 == 0)) {
		   System.out.println("クリック");
		   
		    if (theIcon == passIcon) {
				System.out.println("パスボタンが押されました");
				String msg = "Pass" + " " + myColor;
				out.println(msg);
				out.flush();
			} else if (theIcon == ToryoIcon) {
			    System.out.println("投了ボタンが押されました");
				String msg = "Toryo" + " " + myColor;
				out.println(msg);
				out.flush();
			} else if (theIcon == ruleIcon) {
				System.out.println("ルール確認");
			    countRule++;
			    ruleTextLabel.setVisible(true);
			   if (countRule==2) {
				ruleTextLabel.setVisible(false);
				System.out.println("ルール確認終了");
				countRule = 0;
			   }
				
			} else {
			  int temp = Integer.parseInt(theArrayIndex);
			  int x = temp / 11;
			  int y = temp % 11;
				
			  if (judgeButton(y, x)) {//TRUEなら合法手(その位置に石を置ける)
				  SetStones(y, x);
			      
				  String msg = "PLACE" + " " + theArrayIndex + " " + myColor;
				  out.println(msg);
				  out.flush();
				  repaint();
			  } else {
					System.out.println("そこに配置できません");
			  }
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {//マウスがオブジェクトに入ったときの処理
		//System.out.println("マウスが入った");
	}
	
	public void mouseExited(MouseEvent e) {//マウスがオブジェクトから出たときの処理
		//System.out.println("マウス脱出");
	}
	
	public void mousePressed(MouseEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
		//System.out.println("マウスを押した");
	}
	
	public void mouseReleased(MouseEvent e) {//マウスで押していたオブジェクトを離したときの処理
		//System.out.println("マウスを放した");
	}
	
	public void mouseDragged(MouseEvent e) {//マウスでオブジェクトとをドラッグしているときの処理
	    /*
		System.out.println("マウスをドラッグ");
		JButton theButton = (JButton)e.getComponent();//型が違うのでキャストする
		String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す

		Point theMLoc = e.getPoint();//発生元コンポーネントを基準とする相対座標
		System.out.println(theMLoc);//デバッグ（確認用）に，取得したマウスの位置をコンソールに出力する
		Point theBtnLocation = theButton.getLocation();//クリックしたボタンを座標を取得する
		
		theBtnLocation.x += theMLoc.x-15;//ボタンの真ん中当たりにマウスカーソルがくるように補正する
		theBtnLocation.y += theMLoc.y-15;//ボタンの真ん中当たりにマウスカーソルがくるように補正する
		if (!(theArrayIndex.equals("0"))) { 
		theButton.setLocation(theBtnLocation);//マウスの位置にあわせてオブジェクトを移動する
      
		//送信情報を作成する（受信時には，この送った順番にデータを取り出す．スペースがデータの区切りとなる）
		String msg = "MOVE"+" "+theArrayIndex+" "+theBtnLocation.x+" "+theBtnLocation.y;

		//サーバに情報を送る
		out.println(msg);//送信データをバッファに書き出す
		out.flush();//送信データをフラッシュ  （ネットワーク上にはき出す）する

		repaint();//オブジェクトの再描画を行う
		}
		*/
	}

	public void mouseMoved(MouseEvent e) {//マウスがオブジェクト上で移動したときの処理
	    /*
		 System.out.println("マウス移動");
		int theMLocX = e.getX();//マウスのx座標を得る
		int theMLocY = e.getY();//マウスのy座標を得る
		System.out.println(theMLocX+","+theMLocY);//コンソールに出力する 
		*/
	}
	
	public boolean judgeButton(int y, int x) {//合法手(その位置に石が置ける)かを判断する
		// 空点じゃないと置けない
		if (buttonArray[y][x].getIcon() != boardIcon) {
			return false;
		}
		//自殺手なら、置けない
		if (judgeSuicide(y, x)) {
			return false;
		}
		return true;
	}
	public boolean judgeSuicide(int y, int x) {//自殺手（その点に自分の石を置けば、相手の石に囲まれている）の判断をする
		boolean around;
		buttonArray[y][x].setIcon(myIcon);//仮に自分のアイコンをセットする
		checked.clear();//中身をクリア
		around = judgeMyRemove(y, x);//その点に自分の石を置けば、相手の石に囲まれているかの判断をする
		if (around == true) {//相手の石に囲まれている
		    if (x > 1) {
				if (buttonArray[y][x-1].getIcon() == yourIcon) {//左の石を調べる
					checked.clear();
					around = judgeYourRemove(y, x-1);
					if (around == true) {
						buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
			}
			if (y > 1) {
				if (buttonArray[y-1][x].getIcon() == yourIcon) {//上の石を調べる
					checked.clear();
					around = judgeYourRemove(y-1, x);
					if (around == true) {
					   buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
 			}
			if (x < 9) {
				if (buttonArray[y][x+1].getIcon() == yourIcon) {//右の石を調べる
					checked.clear();
					around = judgeYourRemove(y, x+1);
					if (around == true) {
					 buttonArray[y][x].setIcon(boardIcon);
					 return false;
					}
				}
			}
			if (y < 9) {
				if (buttonArray[y+1][x].getIcon() == yourIcon) {//下の石を調べる
					checked.clear();
					around = judgeYourRemove(y+1, x);
					if (around == true) {
						buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
 			}
		     buttonArray[y][x].setIcon(boardIcon);
			return true;
		} else {
			buttonArray[y][x].setIcon(boardIcon);
			return false;
		}
		
	}
	public boolean judgeMyRemove(int y, int x) {//その点に自分の石を置けば、相手の石に囲まれているかの判断をする
		boolean rtn;
		if (checked.size() > 0) {
		   for (int n = 0; n < checked.size(); n++) {
			   int a = checked.get(n) / 11;
			   int b = checked.get(n) % 11;
			   if (x == a && y == b) {//もしすでに調べていたら探索終わり
				return true;
			   }
		    }
		}
		checked.add(x*11+y);//調べたことをチェックする
		if (buttonArray[y][x].getIcon() == boardIcon) {
			return false;
		}
		if (buttonArray[y][x].getIcon() == myIcon) {//自分と同じ色の石ならばその隣の石も調べる
			if (x > 1) {//その石の左(x-1, y)を調べる
				
				rtn = judgeMyRemove(y, x-1);
				if (rtn == false) {
					return false;
				}
			}
			if (y > 1) {//その石の上(x, y-1)を調べる
				//if (buttonArray[y-1][x].getIcon() != myIcon) {return false;}
				rtn = judgeMyRemove(y-1, x);
				if (rtn == false) {
					return false;
				}
			}
			if (x < 9) {//その石の右(x+1, y)を調べる
				//if (buttonArray[y][x+1].getIcon() != myIcon) {return false;}
				rtn = judgeMyRemove(y, x+1);
				if (rtn == false) {
					return false;
				}
			}
			if (y < 9) {//その石の下(x, y+1)を調べる
				//if (buttonArray[y+1][x].getIcon() != myIcon) {return false;}
				rtn = judgeMyRemove(y+1, x);
				if (rtn == false) {
					return false;
				}
			}
		}
		return true;//相手の色の石があった
	}
	public boolean judgeYourRemove(int y, int x) {//その点に自分の石を置けば、相手の石に囲まれているかの判断をする
		boolean rtn;
		if (checked.size() > 0) {
		   for (int n = 0; n < checked.size(); n++) {
			   int a = checked.get(n) / 11;
			   int b = checked.get(n) % 11;
			   if (x == a && y == b) {//もしすでに調べていたら探索終わり
				return true;
			   }
		    }
		}
		checked.add(x*11+y);//調べたことをチェックする
		
		if (buttonArray[y][x].getIcon() == boardIcon){return false;}
		
		if (buttonArray[y][x].getIcon() == yourIcon) {//自分と同じ色の石ならばその隣の石も調べる
			if (x > 1) {//その石の左(x-1, y)を調べる
				rtn = judgeYourRemove(y, x-1);
				if (rtn == false) {
					return false;
				}
			}
			if (y > 1) {//その石の上(x, y-1)を調べる
				rtn = judgeYourRemove(y-1, x);
				if (rtn == false) {
					return false;
				}
			}
			if (x < 9) {//その石の右(x+1, y)を調べる
				rtn = judgeYourRemove(y, x+1);
				if (rtn == false) {
					return false;
				}
			}
			if (y < 9) {//その石の下(x, y+1)を調べる
				rtn = judgeYourRemove(y+1, x);
				if (rtn == false) {
					return false;
				}
			}
		}
		return true;//相手の色の石があった
	}
	public void SetStones(int y, int x) {
		int prisonerN = 0;
		int prisonerE = 0;
		int prisonerS = 0;
		int prisonerW = 0;
		int prisonerAll = 0;
		
		buttonArray[y][x].setIcon(myIcon);
		//置いた石の周りの石が死んでいれば、取り除く
		if (y > 1) {
			prisonerN = RemoveStones(y-1, x);
		}
		if (x > 1) {
			prisonerW = RemoveStones(y, x-1);
		}
		if (y < 9) {
			prisonerS = RemoveStones(y+1, x);
		}
		if (x < 9) {
			prisonerE = RemoveStones(y, x+1);	
		}
		prisonerAll = prisonerN+prisonerW+prisonerS+prisonerE;
		System.out.println("取った石" + prisonerAll);
	}	
	public int RemoveStones(int y, int x) {
		int prisoner;
		if (buttonArray[y][x].getIcon() == myIcon) {
			
			return 0;
		}
		if (buttonArray[y][x].getIcon() == boardIcon) {
			
			return 0;
		}
		checked.clear();
		if (judgeYourRemove(y, x)) {
			checkList.clear();
			prisoner = DoRemoveStone(y, x, 0);
			return prisoner;
		}
		
		return 0;
	}
	public int DoRemoveStone(int y, int x, int prisoner) {//実際に相手の石を取り除く
		String theArrayIndex2;
		
	    if (buttonArray[y][x].getIcon() == yourIcon) {//取り除かれる石と同じ色なら石をとる
		    if (checkList.size() > 0) {
		       for (int n = 0; n < checkList.size(); n++) {
			     int c = checkList.get(n) / 11;
			     int d = checkList.get(n) % 11;
			     if (x == c && y == d) {//もしすでに調べていたら探索終わり
				    return 0;
			     }
		       }
		    }
			checkList.add(x*11+y);
			//ifで配列の中身を見て、入ってなかったら
				//配列にとる座標の値を入れる
				//以下の処理を行う
			
			int a = x*11+y;
			theArrayIndex2 = Integer.toString(a);
			prisoner++;
			System.out.println(prisoner);
			String msg = "REMOVE" + " " + theArrayIndex2 + " " + myColor;
			System.out.println(msg + "ダミー");
				  out.println(msg);
				  out.flush();
				  
			
			if (x > 1) {
				prisoner = DoRemoveStone(y, x-1, prisoner);//左の石を調べる	
			}
			if (y > 1) {
				prisoner = DoRemoveStone(y-1, x, prisoner);//上の石を調べる
			}
			if (x < 9) {
				prisoner = DoRemoveStone(y, x+1, prisoner);//右の石を調べる
			}
			if (y < 9) {
				prisoner = DoRemoveStone(y+1, x, prisoner);//下の石を調べる
			}
		}
		return prisoner;
	}
	public void judgeGame() {
        System.out.println("判定前");
		System.out.println("黒の取った石の数: " + blackCount);
		System.out.println("白の取った石の数: " + whiteCount);
		if (blackCount > whiteCount) {
			System.out.println("黒の勝ち");
			String msg = "Judge1" + " " + myColor;
			out.println(msg);
			out.flush();
		} else if (blackCount < whiteCount) {
			System.out.println("白の勝ち");
			String msg = "Judge2" + " " + myColor;
			out.println(msg);
			out.flush();
		} else {
			System.out.println("引き分け");
			String msg = "Draw";
			out.println(msg);
			out.flush();
		}
		for(int i=1;i<10;i++){
			for (int j = 1;j<10;j++) {
				buttonArray[j][i].setIcon(boardIcon);
			}
		}		
	}
}
