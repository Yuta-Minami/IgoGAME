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
	private JButton buttonArray[][], passButton, theButton;//�{�^���p�̔z��
	private int myColor, myTurn, count = 0, myCount = 0, yourCount = 0, boardCount = 4, counter = 0, prex, prey;
	private ArrayList<Integer> checked = new ArrayList<Integer>();
	private ImageIcon myIcon, yourIcon;
	private Container c;
	private ImageIcon blackIcon, whiteIcon, boardIcon, boardIcon2, boardIcon3, boardIcon4, 
	boardIcon5, boardIcon6, boardIcon7, boardIcon8, boardIcon9, passIcon;
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
		setSize(600,500);//�E�B���h�E�̃T�C�Y��ݒ肷��
		c = getContentPane();//�t���[���̃y�C�����擾����
		c.setLayout(null);//�������C�A�E�g�̐ݒ���s��Ȃ�

		//�A�C�R���̐ݒ�
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
        
		theButton = new JButton();//�摜��\��t���郉�x��
        ImageIcon theImage = new ImageIcon("win.jpg");//�Ȃɂ��摜�t�@�C�����_�E�����[�h���Ă���
        theButton.setIcon(theImage);//���x����ݒ�
        theButton.setBounds(0,0,600,600);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
		
		c.add(theButton);
		
        
		theButton.setVisible(false);
		
		//�{�^���̐���
		buttonArray = new JButton[11][11];//�{�^���̔z���9�쐬����[0]����[8]�܂Ŏg����
		for(int i=1;i<10;i++){
			for (int j = 1;j<10;j++) {
			  
			  buttonArray[i][j] = new JButton(boardIcon);//�{�^���ɃA�C�R����ݒ肷��
			  c.add(buttonArray[i][j]);//�y�C���ɓ\��t����
			  buttonArray[i][j].setBounds(j*45,i*45,45,45);//�{�^���̑傫���ƈʒu��ݒ肷��D(x���W�Cy���W,x�̕�,y�̕��j
			  buttonArray[i][j].addMouseListener(this);//�{�^�����}�E�X�ł�������Ƃ��ɔ�������悤�ɂ���
			
			  buttonArray[i][j].addMouseMotionListener(this);//�{�^�����}�E�X�œ��������Ƃ����Ƃ��ɔ�������悤�ɂ���
			
			  buttonArray[i][j].setActionCommand(Integer.toString(j*11+i));//�{�^���ɔz��̏���t������i�l�b�g���[�N����ăI�u�W�F�N�g�����ʂ��邽�߁j
			  buttonArray[i][j].setBorderPainted(false);
			}
		}
		
		c.add(passButton);
		passButton.setBounds(350, 400, 50, 50);
		passButton.addMouseListener(this);
		
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
				} else {
					myColor = 1;
					myTurn = 0;
					myIcon = blackIcon;
					yourIcon = whiteIcon;
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
							//����̃A�C�R�����͂�ł��邩�̔���
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
				System.err.println("�G���[���������܂���: " + e);
			}
		}
	}

	public static void main(String[] args) {
		IgoGame net = new IgoGame();
		net.setVisible(true);
	}
  	
	public void mouseClicked(MouseEvent e) {//�{�^�����N���b�N�����Ƃ��̏���
	    if (myTurn == 0) {
		   System.out.println("�N���b�N");
		   JButton theButton = (JButton)e.getComponent();//�N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����
		   String theArrayIndex = theButton.getActionCommand();//�{�^���̔z��̔ԍ������o��
        
		   Icon theIcon = theButton.getIcon();
		    /*if (theIcon == passIcon) {
				System.out.println("�p�X�{�^����������܂���");
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
		         repaint();//��ʂ̃I�u�W�F�N�g��`�悵����
		       } else {
			     System.out.println("�����ɔz�u�ł��܂���");
		       }
		    }*/
			int temp = Integer.parseInt(theArrayIndex);
			int x = temp / 11;
			int y = temp % 11;
				
			if (judgeButton(y, x)) {//TRUE�Ȃ獇�@��(���̈ʒu�ɐ΂�u����)
				  SetStones(y, x, theArrayIndex);
				  
			} else {
					System.out.println("�����ɔz�u�ł��܂���");
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
	public boolean judgeSuicide(int y, int x) {
		boolean around;
		buttonArray[y][x].setIcon(myIcon);//���Ɏ����̃A�C�R�����Z�b�g����
		checked.clear();//���g���N���A
		around = judgeMyRemove(y, x);//���̓_�Ɏ����̐΂�u���΁A����̐΂Ɉ͂܂�Ă��邩�̔��f������
		if (around == true) {//����̐΂Ɉ͂܂�Ă���
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
		checked.add(y*11+x);//���ׂ����Ƃ��`�F�b�N����
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
		checked.add(y*11+x);//���ׂ����Ƃ��`�F�b�N����
		
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
