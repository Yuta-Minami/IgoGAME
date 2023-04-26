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
	private JButton buttonArray[][], passButton, theButton;//ボタン用の配列
	private int myColor, myTurn, count = 0, myCount = 0, yourCount = 0, boardCount = 4, counter = 0, prex, prey;
	private ArrayList<Integer> checked = new ArrayList<Integer>();
	private ImageIcon myIcon, yourIcon;
	private Container c;
	private ImageIcon blackIcon, whiteIcon, boardIcon, boardIcon2, boardIcon3, boardIcon4, 
	boardIcon5, boardIcon6, boardIcon7, boardIcon8, boardIcon9, passIcon;
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
		setSize(600,500);//ウィンドウのサイズを設定する
		c = getContentPane();//フレームのペインを取得する
		c.setLayout(null);//自動レイアウトの設定を行わない

		//アイコンの設定
		whiteIcon = new ImageIcon("White2.jpg");
		blackIcon = new ImageIcon("Black2.jpg");
		boardIcon = new ImageIcon("Igo1.jpg");
		boardIcon2 = new ImageIcon("Igo2.jpg");
		boardIcon3 = new ImageIcon("Igo3.jpg");
		boardIcon4 = new ImageIcon("Igo4.jpg");
		boardIcon5 = new ImageIcon("Igo5.jpg");
		boardIcon6 = new ImageIcon("Igo6.jpg");
		boardIcon7 = new ImageIcon("Igo7.jpg");
		boardIcon8 = new ImageIcon("Igo8.jpg");
		boardIcon9 = new ImageIcon("Igo9.jpg");
		passIcon = new ImageIcon("Pass.jpg");
		
		passButton = new JButton(passIcon);
        
		theButton = new JButton();//画像を貼り付けるラベル
        ImageIcon theImage = new ImageIcon("win.jpg");//なにか画像ファイルをダウンロードしておく
        theButton.setIcon(theImage);//ラベルを設定
        theButton.setBounds(0,0,600,600);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
		
		c.add(theButton);
		
        
		theButton.setVisible(false);
		
		//ボタンの生成
		buttonArray = new JButton[11][11];//ボタンの配列を9個作成する[0]から[8]まで使える
		for(int i=1;i<10;i++){
			for (int j = 1;j<10;j++) {
			  
			  buttonArray[i][j] = new JButton(boardIcon);//ボタンにアイコンを設定する
			  c.add(buttonArray[i][j]);//ペインに貼り付ける
			  buttonArray[i][j].setBounds(j*45,i*45,45,45);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
			  buttonArray[i][j].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
			
			  buttonArray[i][j].addMouseMotionListener(this);//ボタンをマウスで動かそうとしたときに反応するようにする
			
			  buttonArray[i][j].setActionCommand(Integer.toString(j*11+i));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
			  buttonArray[i][j].setBorderPainted(false);
			}
		}
		
		c.add(passButton);
		passButton.setBounds(350, 400, 50, 50);
		passButton.addMouseListener(this);
		
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
				} else {
					myColor = 1;
					myTurn = 0;
					myIcon = blackIcon;
					yourIcon = whiteIcon;
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
						} else if (cmd.equals("REMOVE")) {
							String theBName = inputTokens[1];
							int theBnum = Integer.parseInt(theBName);
							int i = theBnum / 11;
							int j = theBnum % 11;
							buttonArray[j][i].setIcon(boardIcon);
							/*
							for (int k = -1; k < 2; k++) {
								for (int l = -1; l < 2; l++) {
									if ((l == -1 && k == 0) || (l == 0 && k == -1) || (l == 1 && k == 0) || (l == 0 && k == 1)) {
										if(buttonArray[j+k][i+l].getIcon() == yourIcon) {
											checked.add(j*9+i);
											/*prex = i;
											prey = j;*/
										   /* judgeButtonFirst(j+k, i+l);
											counter = 0;
									    }
									}
									
								}
							}/*
							//相手のアイコンを囲んでいるかの判定
							myTurn = 1 - myTurn;
							count = 0;
							boardCount++;
							if (boardCount == 64) {
								judgeGame();
							}
						} else if (cmd.equals("FLIP")) {
							String theBName = inputTokens[1];
							int theBnum = Integer.parseInt(theBName);
							int i = theBnum / 9;
							int j = theBnum % 9;
							int theColor = Integer.parseInt(inputTokens[2]);
							buttonArray[j][i].setIcon(boardIcon);
							/*if (theColor == myColor) {
								buttonArray[j][i].setIcon(myIcon);
							} else {
								buttonArray[j][i].setIcon(yourIcon);
							}*/
						/*} else if (cmd.equals("Pass")) {
							int theColor = Integer.parseInt(inputTokens[1]);
							myTurn = 1 - myTurn;
							count++;
							if (count == 2) {
								judgeGame();
							}*/
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
	    if (myTurn == 0) {
		   System.out.println("クリック");
		   JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		   String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す
        
		   Icon theIcon = theButton.getIcon();
		    /*if (theIcon == passIcon) {
				System.out.println("パスボタンが押されました");
				String msg = "Pass" + " " + myColor;
				out.println(msg);
				out.flush();
			
			} else if (theIcon == boardIcon) {
		     int temp = Integer.parseInt(theArrayIndex);
		     int x = temp / 8;
		     int y = temp % 8;
		       if (judgeButton(y, x)) {
			     String msg = "PLACE" + " " + theArrayIndex + " " + myColor;
		         out.println(msg);
		         out.flush();
		         repaint();//画面のオブジェクトを描画し直す
		       } else {
			     System.out.println("そこに配置できません");
		       }
		    }*/
			int temp = Integer.parseInt(theArrayIndex);
			int x = temp / 11;
			int y = temp % 11;
				
			if (judgeButton(y, x)) {//TRUEなら合法手(その位置に石を置ける)
				  SetStones(y, x, theArrayIndex);
				  
			} else {
					System.out.println("そこに配置できません");
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
	public boolean judgeSuicide(int y, int x) {
		boolean around;
		buttonArray[y][x].setIcon(myIcon);//仮に自分のアイコンをセットする
		checked.clear();//中身をクリア
		around = judgeMyRemove(y, x);//その点に自分の石を置けば、相手の石に囲まれているかの判断をする
		if (around == true) {//相手の石に囲まれている
		    if (x > 1) {
				if (buttonArray[y][x-1].getIcon() == yourIcon) {
					checked.clear();
					around = judgeYourRemove(y, x-1);
					if (around == true) {
						buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
			}
			if (y > 1) {
				if (buttonArray[y-1][x].getIcon() == yourIcon) {
					checked.clear();
					around = judgeYourRemove(y-1, x);
					if (around == true) {
					   buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
 			}
			if (x < 9) {
				if (buttonArray[y][x+1].getIcon() == yourIcon) {
					checked.clear();
					around = judgeYourRemove(y, x+1);
					if (around == true) {
					 buttonArray[y][x].setIcon(boardIcon);
					 return false;
					}
				}
			}
			if (y < 9) {
				if (buttonArray[y+1][x].getIcon() == yourIcon) {
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
		checked.add(y*11+x);//調べたことをチェックする
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
		checked.add(y*11+x);//調べたことをチェックする
		
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
	public void SetStones(int y, int x, String theArrayIndex) {
		int prisonerN = 0;
		int prisonerE = 0;
		int prisonerS = 0;
		int prisonerW = 0;
		int prisonerAll;
		String msg = "PLACE" + " " + theArrayIndex + " " + myColor;
				  out.println(msg);
				  out.flush();
				  repaint();
		if (y > 1) {
			prisonerN = RemoveStones(y-1, x, theArrayIndex);
		}
		if (x > 1) {
			prisonerW = RemoveStones(x-1, y, theArrayIndex);
		}
		if (y < 9) {
			prisonerS = RemoveStones(x, y+1, theArrayIndex);
		}
		if (x < 9) {
			prisonerE = RemoveStones(x+1, y, theArrayIndex);
		}
		prisonerAll = prisonerN+prisonerW+prisonerS+prisonerE;
		/*if (prisonerAll > 0) {
			if (myColor == 1) {
			
		    } else {
			
		    }
		}*/
		
	}	
	public int RemoveStones(int y, int x, String theArrayIndex) {
		int prisoner;
		if (buttonArray[y][x].getIcon() == myIcon) {
			return 0;
		}
		if (buttonArray[y][x].getIcon() == boardIcon) {
			return 0;
		}
		checked.clear();
		if (judgeYourRemove(y, x) == true) {
			prisoner = DoRemoveStone(y, x, 0, theArrayIndex);
			return prisoner;
		}
		return 0;
	}
	public int DoRemoveStone(int y, int x, int prisoner, String theArrayIndex) {
		if (buttonArray[y][x].getIcon() == myIcon) {
			prisoner++;
			String msg = "REMOVE" + " " + theArrayIndex + " " + myColor;
				  out.println(msg);
				  out.flush();
				  repaint();
			if (x > 1) {
				prisoner = DoRemoveStone(y, x-1, prisoner, theArrayIndex);
			}
			if (y > 1) {
				prisoner = DoRemoveStone(y-1, x, prisoner, theArrayIndex);
			}
			if (x < 9) {
				prisoner = DoRemoveStone(y, x+1, prisoner, theArrayIndex);
			}
			if (x < 9) {
				prisoner = DoRemoveStone(y+1, x, prisoner, theArrayIndex);
			}
		}
		return prisoner;
	}
	/*public int flipButtons(int y, int x, int j, int i) {
		int flipNum = 0;
		for (int dy=j, dx=i; ; dy+=j, dx+=i) {
			if (y+dy < 0 || y+dy > 7) {
				return 0;
			} else if (x+dx < 0 || x+dx > 7) {
				return 0;
			} else {
				if (buttonArray[y+dy][x+dx].getIcon() == boardIcon) {
					return 0;
				} else if (buttonArray[y+dy][x+dx].getIcon() == myIcon) {
					return flipNum;
				} else {
					flipNum++;
				}
			}
			
		}
	}
	
	public void judgeGame() {
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if (buttonArray[j][i].getIcon() == myIcon) {
					myCount++;
					buttonArray[j][i].setIcon(boardIcon);
				} else if (buttonArray[j][i].getIcon() == yourIcon) {
					yourCount++;
					buttonArray[j][i].setIcon(boardIcon);
				}
			}
		}
		if (myCount > yourCount) {
			System.out.println("You Win!");
			theButton.setVisible(true);
		} else if (myCount < yourCount) {	
			System.out.println("You Lose!");
		} else {
			System.out.println("Draw!");
		}
		buttonArray[3][3].setIcon(whiteIcon);
		buttonArray[4][4].setIcon(whiteIcon);
		buttonArray[3][4].setIcon(blackIcon);
		buttonArray[4][3].setIcon(blackIcon);
	}*/
}
