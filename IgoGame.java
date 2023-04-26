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
	private JButton buttonArray[][], passButton, theButton, ToryoButton, greetButton, ruleButton, tryButton;//�{�^���p�̔z��
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
	PrintWriter out;//�o�͗p�̃��C�^�[

	public IgoGame() {
		//���O�̓��̓_�C�A���O���J��
		String myName = JOptionPane.showInputDialog(null,"���O����͂��Ă�������","���O�̓���",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//���O���Ȃ��Ƃ��́C"No name"�Ƃ���
		}
		String conHost = JOptionPane.showInputDialog(null,"�T�[�o��IP�A�h���X����͂��Ă�������","�T�[�o��IP�A�h���X�̓���",JOptionPane.QUESTION_MESSAGE);

		//�E�B���h�E���쐬����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E�����Ƃ��ɁC����������悤�ɐݒ肷��
		setTitle("IgoGame");//�E�B���h�E�̃^�C�g����ݒ肷��
		setSize(700,700);//�E�B���h�E�̃T�C�Y��ݒ肷��
		c = new JLayeredPane();//�t���[���̃y�C�����擾����
		this.getContentPane().add(c);
		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�

		//�A�C�R���̐ݒ�
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
        
		theButton = new JButton();//�摜��\��t���郉�x��
        ImageIcon theImage = new ImageIcon("win.jpg");//�Ȃɂ��摜�t�@�C�����_�E�����[�h���Ă���
        theButton.setIcon(theImage);//���x����ݒ�
        theButton.setBounds(0,0,700,700);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		
		c.add(theButton);
		
        
		theButton.setVisible(false);
		//�X�^�[�g��ʃ��x�����쐬
		startBackLabel = new JLabel(startBackIcon);
		c.add(startBackLabel);
		c.setLayer(startBackLabel, 30);
		startBackLabel.setBounds(0,0,700,700);
		//�X�^�[�g�{�^�����쐬
		greetButton = new JButton(greetIcon);
		c.add(greetButton);
		c.setLayer(greetButton, 50);
		greetButton.setBorderPainted(false);
		greetButton.setContentAreaFilled(false);
		greetButton.setBounds(220,360,300,75);
		greetButton.addMouseListener(this);
		//�w�i���x����ݒ�
		contextLabel = new JLabel(contextIcon);
		c.add(contextLabel);
		c.setLayer(contextLabel, -10);
		contextLabel.setBounds(0,0,700,700);
		//zabuton���x����ݒ�
		zabutonLabel = new JLabel(zabutonIcon);
		c.add(zabutonLabel);
		c.setLayer(zabutonLabel, -5);
		zabutonLabel.setBounds(60,45, 550, 550);
		//myTurn���x�����쐬
		myTurnLabel = new JLabel(myTurnIcon);
		c.add(myTurnLabel);
		c.setLayer(myTurnLabel, 10);
		//yourTurn���x�����쐬
		yourTurnLabel = new JLabel(yourTurnIcon);
		c.add(yourTurnLabel);
		c.setLayer(yourTurnLabel, 10);
		//�������x�����쐬
		winLabel = new JLabel(winIcon);
		c.add(winLabel);
		c.setLayer(winLabel, 100);
		winLabel.setBounds(0,0,700,700);
		winLabel.setVisible(false);
		//�s�k���x�����쐬
		loseLabel = new JLabel(loseIcon);
		c.add(loseLabel);
		c.setLayer(loseLabel, 100);
		loseLabel.setBounds(0,0,700,700);
		loseLabel.setVisible(false);
		//�����������x�����쐬
		drawLabel = new JLabel(drawIcon);
		c.add(drawLabel);
		c.setLayer(drawLabel, 100);
		drawLabel.setBounds(0,0,700,700);
		drawLabel.setVisible(false);
		//������x�I�A�C�R�����쐬
		tryButton = new JButton(tryIcon);
		c.add(tryButton);
		c.setLayer(tryButton, 200);
		tryButton.setBounds(250, 440, 210, 117);
		tryButton.setBorderPainted(false);
		tryButton.setVisible(false);
		tryButton.addMouseListener(this);
		//myBlack���x����ݒ�
		ImageIcon myBlackIllust = new ImageIcon("myBlack.png");
		myBlackLabel = new JLabel(myBlackIllust);
        c.add(myBlackLabel);
		//myWhite���x����ݒ�
		ImageIcon myWhiteIllust = new ImageIcon("myWhite.png");
		myWhiteLabel = new JLabel(myWhiteIllust);
        c.add(myWhiteLabel);
        //yourBlack���x����ݒ�
		ImageIcon yourBlackIllust = new ImageIcon("yourBlack.png");
		yourBlackLabel = new JLabel(yourBlackIllust);
        c.add(yourBlackLabel);
		//yourWhite���x����ݒ�
		ImageIcon yourWhiteIllust = new ImageIcon("yourWhite.png");
		yourWhiteLabel = new JLabel(yourWhiteIllust);
        c.add(yourWhiteLabel);
		//myCount���x����ݒ�
		myBlackCountLabel = new JLabel();
		myBlackCountLabel.setFont(new Font("HG�s����", Font.BOLD, 23));
		c.add(myBlackCountLabel);
		c.setLayer(myBlackCountLabel, 10);
		myBlackCountLabel.setBounds(197+80,524+70,30,30);
		yourBlackCountLabel = new JLabel();
		yourBlackCountLabel.setFont(new Font("HG�s����", Font.BOLD, 23));
		c.add(yourBlackCountLabel);
		c.setLayer(yourBlackCountLabel, 10);
		yourBlackCountLabel.setBounds(395+80,524+70,30,30);
		//yourCount���x����ݒ�
		myWhiteCountLabel = new JLabel();
		myWhiteCountLabel.setFont(new Font("HG�s����", Font.BOLD, 23));
		c.add(myWhiteCountLabel);
		c.setLayer(myWhiteCountLabel, 10);
		myWhiteCountLabel.setBounds(197+80,524+70,30,30);
		yourWhiteCountLabel = new JLabel();
		yourWhiteCountLabel.setFont(new Font("HG�s����", Font.BOLD, 23));
		c.add(yourWhiteCountLabel);
		c.setLayer(yourWhiteCountLabel, 10);
		yourWhiteCountLabel.setBounds(395+80,524+70,30,30);
		//�����A�C�R����ݒ�
		ToryoButton = new JButton(ToryoIcon);
		ToryoButton.setContentAreaFilled(false);
		ToryoButton.setBorderPainted(false);
		c.add(ToryoButton);
		ToryoButton.setBounds(500+80,300+70,70,70);
		ToryoButton.addMouseListener(this);
		//�p�X�A�C�R����ݒ�
		passButton = new JButton(passIcon);
		passButton.setContentAreaFilled(false);
		passButton.setBorderPainted(false);
		c.add(passButton);
		passButton.setBounds(500+80,200+70,70,70);
		passButton.addMouseListener(this);
		//���[���A�C�R����ݒ�
		ruleButton = new JButton(ruleIcon);
		ruleButton.setContentAreaFilled(false);
		ruleButton.setBorderPainted(false);
		c.add(ruleButton);
		ruleButton.setBounds(500+80,100+70,70,70);
		ruleButton.addMouseListener(this);
		//���[���e�L�X�g���x����ݒ�
		ruleTextLabel = new JLabel(ruleTextIcon);
		c.add(ruleTextLabel);
		c.setLayer(ruleTextLabel, 300);
		ruleTextLabel.setBounds(230,100,350,500);
		ruleTextLabel.setVisible(false);
		//�{�^���̐���
		buttonArray = new JButton[11][11];//�{�^���̔z���9�쐬����[0]����[8]�܂Ŏg����
		for(int i=1;i<10;i++){
			for (int j = 1;j<10;j++) {
			  
			  buttonArray[i][j] = new JButton(boardIcon);//�{�^���ɃA�C�R����ݒ肷��
			  c.add(buttonArray[i][j]);//�y�C���ɓ\��t����
			  buttonArray[i][j].setBounds(80+j*45,70+i*45,45,45);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
			  buttonArray[i][j].addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
			
			  buttonArray[i][j].addMouseMotionListener(this);//�{�^�����}�E�X�œ��������Ƃ����Ƃ��ɔ�������悤�ɂ���
			
			  buttonArray[i][j].setActionCommand(Integer.toString(j*11+i));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
			  buttonArray[i][j].setBorderPainted(false);
			}
		}
		
		
		
		
		//�T�[�o�ɐڑ�����
		Socket socket = null;
		try {
			//"localhost"�́C���������ւ̐ڑ��Dlocalhost��ڑ����IP Address�i"133.42.155.201"�`���j�ɐݒ肷��Ƒ���PC�̃T�[�o�ƒʐM�ł���
			//10000�̓|�[�g�ԍ��DIP Address�Őڑ�����PC�����߂āC�|�[�g�ԍ��ł���PC�㓮�삷��v���O��������肷��
			if (conHost.equals("")) {
				socket = new Socket("localhost", 10000);
			} else {
			socket = new Socket(conHost, 10000);
			}
		} catch (UnknownHostException e) {
			System.err.println("�z�X�g�� IP �A�h���X������ł��܂���: " + e);
		} catch (IOException e) {
			 System.err.println("�G���[���������܂���: " + e);
		}
		
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//��M�p�̃X���b�h���쐬����
		mrt.start();//�X���b�h�𓮂����iRun�������j
	}
		
	//���b�Z�[�W��M�̂��߂̃X���b�h
	public class MesgRecvThread extends Thread {
		
		Socket socket;
		String myName;
		
		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}
		
		//�ʐM�󋵂��Ď����C��M�f�[�^�ɂ���ē��삷��
		public void run() {
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(myName);//�ڑ��̍ŏ��ɖ��O�𑗂�
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
					String inputLine = br.readLine();//�f�[�^����s�������ǂݍ���ł݂�
					if (inputLine != null) {//�ǂݍ��񂾂Ƃ��Ƀf�[�^���ǂݍ��܂ꂽ���ǂ������`�F�b�N����
						System.out.println(inputLine);//�f�o�b�O�i����m�F�p�j�ɃR���\�[���ɏo�͂���
						String[] inputTokens = inputLine.split(" ");	//���̓f�[�^����͂��邽�߂ɁA�X�y�[�X�Ő؂蕪����
						String cmd = inputTokens[0];//�R�}���h�̎��o���D�P�ڂ̗v�f�����o��
						if(cmd.equals("MOVE")){//cmd�̕�����"MOVE"�����������ׂ�D��������true�ƂȂ�
							//MOVE�̎��̏���(�R�}�̈ړ��̏���)
							String theBName = inputTokens[1];//�{�^���̖��O�i�ԍ��j�̎擾
							int theBnum = Integer.parseInt(theBName);//�{�^���̖��O�𐔒l�ɕϊ�����
							int x = Integer.parseInt(inputTokens[2]);//���l�ɕϊ�����
							int y = Integer.parseInt(inputTokens[3]);//���l�ɕϊ�����
							int i = theBnum / 11;
							int j = theBnum % 11;
							buttonArray[i][j].setLocation(x,y);//�w��̃{�^�����ʒu��x,y�ɐݒ肷��
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
								System.out.println("��win");
							} else if (myIcon == whiteIcon){
								loseLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("��lose");
							}
						} else if (cmd.equals("Judge2")) {
							int theColor = Integer.parseInt(inputTokens[1]);
							if (myIcon == whiteIcon) {
								winLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("��win");
							} else if (myIcon == blackIcon){
								loseLabel.setVisible(true);
								tryButton.setVisible(true);
								System.out.println("��lose");
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
				System.err.println("�G���[���������܂���: " + e);
			}
		}
	}

	public static void main(String[] args) {
		IgoGame net = new IgoGame();
		net.setVisible(true);
	}
  	
	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���
	    JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����
	    String theArrayIndex = theButton.getActionCommand();//�{�^���̔z��̔ԍ������o��
        
		Icon theIcon = theButton.getIcon();
		
		if (theIcon == tryIcon) {
			tryCount++;
			blackCount = 0;
			whiteCount = 0;
			System.out.println("try�{�^����������܂���");
			tryButton.setVisible(false);
			String msg = "Try" + " " + myColor;
			out.println(msg);
			out.flush();
		}
		if (theIcon == greetIcon) {
			System.out.println("�X�^�[�g�{�^����������܂���");
			greetButton.setVisible(false);
			String msg = "Start";
			out.println(msg);
			out.flush();
		}
		
	    if (myTurn == 0 && startCount == 2 && (tryCount % 3 == 0)) {
		   System.out.println("�N���b�N");
		   
		    if (theIcon == passIcon) {
				System.out.println("�p�X�{�^����������܂���");
				String msg = "Pass" + " " + myColor;
				out.println(msg);
				out.flush();
			} else if (theIcon == ToryoIcon) {
			    System.out.println("�����{�^����������܂���");
				String msg = "Toryo" + " " + myColor;
				out.println(msg);
				out.flush();
			} else if (theIcon == ruleIcon) {
				System.out.println("���[���m�F");
			    countRule++;
			    ruleTextLabel.setVisible(true);
			   if (countRule==2) {
				ruleTextLabel.setVisible(false);
				System.out.println("���[���m�F�I��");
				countRule = 0;
			   }
				
			} else {
			  int temp = Integer.parseInt(theArrayIndex);
			  int x = temp / 11;
			  int y = temp % 11;
				
			  if (judgeButton(y, x)) {//TRUE�Ȃ獇�@��(���̈ʒu�ɐ΂�u����)
				  SetStones(y, x);
			      
				  String msg = "PLACE" + " " + theArrayIndex + " " + myColor;
				  out.println(msg);
				  out.flush();
				  repaint();
			  } else {
					System.out.println("�����ɔz�u�ł��܂���");
			  }
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {//�}�E�X���I�u�W�F�N�g�ɓ������Ƃ��̏���
		//System.out.println("�}�E�X��������");
	}
	
	public void mouseExited(MouseEvent e) {//�}�E�X���I�u�W�F�N�g����o���Ƃ��̏���
		//System.out.println("�}�E�X�E�o");
	}
	
	public void mousePressed(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g���������Ƃ��̏����i�N���b�N�Ƃ̈Ⴂ�ɒ��Ӂj
		//System.out.println("�}�E�X��������");
	}
	
	public void mouseReleased(MouseEvent e) {//�}�E�X�ŉ����Ă����I�u�W�F�N�g�𗣂����Ƃ��̏���
		//System.out.println("�}�E�X�������");
	}
	
	public void mouseDragged(MouseEvent e) {//�}�E�X�ŃI�u�W�F�N�g�Ƃ��h���b�O���Ă���Ƃ��̏���
	    /*
		System.out.println("�}�E�X���h���b�O");
		JButton theButton = (JButton)e.getComponent();//�^���Ⴄ�̂ŃL���X�g����
		String theArrayIndex = theButton.getActionCommand();//�{�^���̔z��̔ԍ������o��

		Point theMLoc = e.getPoint();//�������R���|�[�l���g����Ƃ��鑊�΍��W
		System.out.println(theMLoc);//�f�o�b�O�i�m�F�p�j�ɁC�擾�����}�E�X�̈ʒu���R���\�[���ɏo�͂���
		Point theBtnLocation = theButton.getLocation();//�N���b�N�����{�^�������W���擾����
		
		theBtnLocation.x += theMLoc.x-15;//�{�^���̐^�񒆓�����Ƀ}�E�X�J�[�\��������悤�ɕ␳����
		theBtnLocation.y += theMLoc.y-15;//�{�^���̐^�񒆓�����Ƀ}�E�X�J�[�\��������悤�ɕ␳����
		if (!(theArrayIndex.equals("0"))) { 
		theButton.setLocation(theBtnLocation);//�}�E�X�̈ʒu�ɂ��킹�ăI�u�W�F�N�g���ړ�����
      
		//���M�����쐬����i��M���ɂ́C���̑��������ԂɃf�[�^�����o���D�X�y�[�X���f�[�^�̋�؂�ƂȂ�j
		String msg = "MOVE"+" "+theArrayIndex+" "+theBtnLocation.x+" "+theBtnLocation.y;

		//�T�[�o�ɏ��𑗂�
		out.println(msg);//���M�f�[�^���o�b�t�@�ɏ����o��
		out.flush();//���M�f�[�^���t���b�V��  �i�l�b�g���[�N��ɂ͂��o���j����

		repaint();//�I�u�W�F�N�g�̍ĕ`����s��
		}
		*/
	}

	public void mouseMoved(MouseEvent e) {//�}�E�X���I�u�W�F�N�g��ňړ������Ƃ��̏���
	    /*
		 System.out.println("�}�E�X�ړ�");
		int theMLocX = e.getX();//�}�E�X��x���W�𓾂�
		int theMLocY = e.getY();//�}�E�X��y���W�𓾂�
		System.out.println(theMLocX+","+theMLocY);//�R���\�[���ɏo�͂��� 
		*/
	}
	
	public boolean judgeButton(int y, int x) {//���@��(���̈ʒu�ɐ΂��u����)���𔻒f����
		// ��_����Ȃ��ƒu���Ȃ�
		if (buttonArray[y][x].getIcon() != boardIcon) {
			return false;
		}
		//���E��Ȃ�A�u���Ȃ�
		if (judgeSuicide(y, x)) {
			return false;
		}
		return true;
	}
	public boolean judgeSuicide(int y, int x) {//���E��i���̓_�Ɏ����̐΂�u���΁A����̐΂Ɉ͂܂�Ă���j�̔��f������
		boolean around;
		buttonArray[y][x].setIcon(myIcon);//���Ɏ����̃A�C�R�����Z�b�g����
		checked.clear();//���g���N���A
		around = judgeMyRemove(y, x);//���̓_�Ɏ����̐΂�u���΁A����̐΂Ɉ͂܂�Ă��邩�̔��f������
		if (around == true) {//����̐΂Ɉ͂܂�Ă���
		    if (x > 1) {
				if (buttonArray[y][x-1].getIcon() == yourIcon) {//���̐΂𒲂ׂ�
					checked.clear();
					around = judgeYourRemove(y, x-1);
					if (around == true) {
						buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
			}
			if (y > 1) {
				if (buttonArray[y-1][x].getIcon() == yourIcon) {//��̐΂𒲂ׂ�
					checked.clear();
					around = judgeYourRemove(y-1, x);
					if (around == true) {
					   buttonArray[y][x].setIcon(boardIcon);
						return false;
					}
				}
 			}
			if (x < 9) {
				if (buttonArray[y][x+1].getIcon() == yourIcon) {//�E�̐΂𒲂ׂ�
					checked.clear();
					around = judgeYourRemove(y, x+1);
					if (around == true) {
					 buttonArray[y][x].setIcon(boardIcon);
					 return false;
					}
				}
			}
			if (y < 9) {
				if (buttonArray[y+1][x].getIcon() == yourIcon) {//���̐΂𒲂ׂ�
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
	public boolean judgeMyRemove(int y, int x) {//���̓_�Ɏ����̐΂�u���΁A����̐΂Ɉ͂܂�Ă��邩�̔��f������
		boolean rtn;
		if (checked.size() > 0) {
		   for (int n = 0; n < checked.size(); n++) {
			   int a = checked.get(n) / 11;
			   int b = checked.get(n) % 11;
			   if (x == a && y == b) {//�������łɒ��ׂĂ�����T���I���
				return true;
			   }
		    }
		}
		checked.add(x*11+y);//���ׂ����Ƃ��`�F�b�N����
		if (buttonArray[y][x].getIcon() == boardIcon) {
			return false;
		}
		if (buttonArray[y][x].getIcon() == myIcon) {//�����Ɠ����F�̐΂Ȃ�΂��ׂ̗̐΂����ׂ�
			if (x > 1) {//���̐΂̍�(x-1, y)�𒲂ׂ�
				
				rtn = judgeMyRemove(y, x-1);
				if (rtn == false) {
					return false;
				}
			}
			if (y > 1) {//���̐΂̏�(x, y-1)�𒲂ׂ�
				//if (buttonArray[y-1][x].getIcon() != myIcon) {return false;}
				rtn = judgeMyRemove(y-1, x);
				if (rtn == false) {
					return false;
				}
			}
			if (x < 9) {//���̐΂̉E(x+1, y)�𒲂ׂ�
				//if (buttonArray[y][x+1].getIcon() != myIcon) {return false;}
				rtn = judgeMyRemove(y, x+1);
				if (rtn == false) {
					return false;
				}
			}
			if (y < 9) {//���̐΂̉�(x, y+1)�𒲂ׂ�
				//if (buttonArray[y+1][x].getIcon() != myIcon) {return false;}
				rtn = judgeMyRemove(y+1, x);
				if (rtn == false) {
					return false;
				}
			}
		}
		return true;//����̐F�̐΂�������
	}
	public boolean judgeYourRemove(int y, int x) {//���̓_�Ɏ����̐΂�u���΁A����̐΂Ɉ͂܂�Ă��邩�̔��f������
		boolean rtn;
		if (checked.size() > 0) {
		   for (int n = 0; n < checked.size(); n++) {
			   int a = checked.get(n) / 11;
			   int b = checked.get(n) % 11;
			   if (x == a && y == b) {//�������łɒ��ׂĂ�����T���I���
				return true;
			   }
		    }
		}
		checked.add(x*11+y);//���ׂ����Ƃ��`�F�b�N����
		
		if (buttonArray[y][x].getIcon() == boardIcon){return false;}
		
		if (buttonArray[y][x].getIcon() == yourIcon) {//�����Ɠ����F�̐΂Ȃ�΂��ׂ̗̐΂����ׂ�
			if (x > 1) {//���̐΂̍�(x-1, y)�𒲂ׂ�
				rtn = judgeYourRemove(y, x-1);
				if (rtn == false) {
					return false;
				}
			}
			if (y > 1) {//���̐΂̏�(x, y-1)�𒲂ׂ�
				rtn = judgeYourRemove(y-1, x);
				if (rtn == false) {
					return false;
				}
			}
			if (x < 9) {//���̐΂̉E(x+1, y)�𒲂ׂ�
				rtn = judgeYourRemove(y, x+1);
				if (rtn == false) {
					return false;
				}
			}
			if (y < 9) {//���̐΂̉�(x, y+1)�𒲂ׂ�
				rtn = judgeYourRemove(y+1, x);
				if (rtn == false) {
					return false;
				}
			}
		}
		return true;//����̐F�̐΂�������
	}
	public void SetStones(int y, int x) {
		int prisonerN = 0;
		int prisonerE = 0;
		int prisonerS = 0;
		int prisonerW = 0;
		int prisonerAll = 0;
		
		buttonArray[y][x].setIcon(myIcon);
		//�u�����΂̎���̐΂�����ł���΁A��菜��
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
		System.out.println("�������" + prisonerAll);
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
	public int DoRemoveStone(int y, int x, int prisoner) {//���ۂɑ���̐΂���菜��
		String theArrayIndex2;
		
	    if (buttonArray[y][x].getIcon() == yourIcon) {//��菜�����΂Ɠ����F�Ȃ�΂��Ƃ�
		    if (checkList.size() > 0) {
		       for (int n = 0; n < checkList.size(); n++) {
			     int c = checkList.get(n) / 11;
			     int d = checkList.get(n) % 11;
			     if (x == c && y == d) {//�������łɒ��ׂĂ�����T���I���
				    return 0;
			     }
		       }
		    }
			checkList.add(x*11+y);
			//if�Ŕz��̒��g�����āA�����ĂȂ�������
				//�z��ɂƂ���W�̒l������
				//�ȉ��̏������s��
			
			int a = x*11+y;
			theArrayIndex2 = Integer.toString(a);
			prisoner++;
			System.out.println(prisoner);
			String msg = "REMOVE" + " " + theArrayIndex2 + " " + myColor;
			System.out.println(msg + "�_�~�[");
				  out.println(msg);
				  out.flush();
				  
			
			if (x > 1) {
				prisoner = DoRemoveStone(y, x-1, prisoner);//���̐΂𒲂ׂ�	
			}
			if (y > 1) {
				prisoner = DoRemoveStone(y-1, x, prisoner);//��̐΂𒲂ׂ�
			}
			if (x < 9) {
				prisoner = DoRemoveStone(y, x+1, prisoner);//�E�̐΂𒲂ׂ�
			}
			if (y < 9) {
				prisoner = DoRemoveStone(y+1, x, prisoner);//���̐΂𒲂ׂ�
			}
		}
		return prisoner;
	}
	public void judgeGame() {
        System.out.println("����O");
		System.out.println("���̎�����΂̐�: " + blackCount);
		System.out.println("���̎�����΂̐�: " + whiteCount);
		if (blackCount > whiteCount) {
			System.out.println("���̏���");
			String msg = "Judge1" + " " + myColor;
			out.println(msg);
			out.flush();
		} else if (blackCount < whiteCount) {
			System.out.println("���̏���");
			String msg = "Judge2" + " " + myColor;
			out.println(msg);
			out.flush();
		} else {
			System.out.println("��������");
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
