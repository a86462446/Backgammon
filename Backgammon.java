import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Backgammon extends JFrame{
    private JButton[][] buttons= new JButton[19][19];
    private JButton okButton;
    private String chess= "◉";

    private JMenuBar jMenuBar;
    private JMenu jMenu;
    private JMenuItem jMenuItem;
    private JMenuItem hint;
    private JMenu changeColor;

    private JMenuItem yellow;
    private JMenuItem blue;
    private JMenuItem red;
    private JMenuItem green;
    private JMenuItem pink;
    private JMenuItem ORANGE;
    private JMenuItem white;

    private String winChess= "";

    private Container contentPane;

    private JDialog dialog;
    private JDialog intro;

    private JDialog hintDialog;
    private JTextArea textArea;
    JScrollPane scrollText;

    private String ver_map= "";
    private String hor_map= "";
    private String ld_map= "";
    private String rd_map= "";

    public Backgammon(String title){
        super(title);
        this.setLocation(425, 50);
        this.setSize(850, 850);

        contentPane = this.getContentPane();
		contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new GridLayout(19,19));

        jMenuBar= new JMenuBar();
        this.setJMenuBar(jMenuBar);

        jMenu= new JMenu("Function");
        changeColor= new JMenu("Color");
        jMenuItem= new JMenuItem("Reset");
        hint= new JMenuItem("Hint");

        yellow= new JMenuItem("Yellow");
        blue= new JMenuItem("Blue");
        red= new JMenuItem("Red");
        green= new JMenuItem("Green");
        pink= new JMenuItem("Pink");
        ORANGE= new JMenuItem("Orange");
        white= new JMenuItem("White");

        jMenu.add(hint);
        jMenu.add(jMenuItem);

        changeColor.add(yellow);
        changeColor.add(blue);
        changeColor.add(red);
        changeColor.add(green);
        changeColor.add(pink);
        changeColor.add(ORANGE);
        changeColor.add(white);

        jMenuBar.add(jMenu);
        jMenuBar.add(changeColor);

        PutChess putChess= new PutChess();
        Menu menu= new Menu();
        jMenuItem.addActionListener(menu);

        yellow.addActionListener(menu);
        blue.addActionListener(menu);
        red.addActionListener(menu);
        green.addActionListener(menu);
        pink.addActionListener(menu);
        ORANGE.addActionListener(menu);
        white.addActionListener(menu);

        textArea= new JTextArea();

        hintDialog= new JDialog(this, "Hint");
        Container ppane = hintDialog.getContentPane();
        ppane.setLayout(new BorderLayout());

        setIntroduction();

        hintDialog.setSize(350, 250);
        hintDialog.setLocation(100, 350);

        textArea.setColumns(30);
        textArea.setRows(10);
		scrollText= new JScrollPane(textArea);

        hint.addActionListener(menu);

        for(int i= 0; i< 19; i++){
            for(int j= 0; j< 19; j++){
                buttons[i][j]= new JButton(" ");
                contentPane.add(buttons[i][j]);
                buttons[i][j].addActionListener(putChess);
            }
        }
        setSpots();

        this.setVisible(true);
    }

    public void setIntroduction(){
        intro= new JDialog(this, "Introduction");
        Container pane = intro.getContentPane();
        pane.setLayout(new BorderLayout());
        intro.setModal(true);

        intro.setSize(350, 250);
        intro.setLocation(400, 300);

        intro.add(new Label("<html><body>" + "操作說明：" + "<br>" + "1. 黃色點為棋盤相對位置"+ "<br>" + "2. Menu列的Function有重置盤面功能以及提示功能"+ "<br>" + "3. 提示功能打開後可以看到活三活四死四的警告"+ "<br>"+ "4. Menu列還有顏色功能，能轉換棋盤顏色"+ "<body></html>"));
        intro.setVisible(true);
    }

    public void setSpots(){
        buttons[2][2].setBackground(Color.YELLOW);
        buttons[2][2].setOpaque(true);

        buttons[2][9].setBackground(Color.YELLOW);
        buttons[2][9].setOpaque(true);

        buttons[2][16].setBackground(Color.YELLOW);
        buttons[2][16].setOpaque(true);

        buttons[9][2].setBackground(Color.YELLOW);
        buttons[9][2].setOpaque(true);

        buttons[9][9].setBackground(Color.YELLOW);
        buttons[9][9].setOpaque(true);

        buttons[9][16].setBackground(Color.YELLOW);
        buttons[9][16].setOpaque(true);

        buttons[16][2].setBackground(Color.YELLOW);
        buttons[16][2].setOpaque(true);

        buttons[16][9].setBackground(Color.YELLOW);
        buttons[16][9].setOpaque(true);

        buttons[16][16].setBackground(Color.YELLOW);
        buttons[16][16].setOpaque(true);
    }

    public void setDialog(){
        Menu reset= new Menu();

        dialog= new JDialog(this, "Message");
        Container pane = dialog.getContentPane();
        pane.setLayout(new BorderLayout());

        dialog.setSize(250, 150);
        dialog.setLocation(350, 350);
        dialog.setModal(true);

        okButton= new JButton("Ok");

        if(winChess== "◉"){
            dialog.add(new Label("◉ player wins the game."));
        }
        else{
            dialog.add(new Label("◎ player wins the game."));
        }

        pane.add(okButton, BorderLayout.SOUTH);
        okButton.addActionListener(reset);
        
        dialog.setVisible(true);
    }

    public void resetButtons(){
        for(int i= 0; i< 19; i++){
            for(int j= 0; j< 19; j++){
                buttons[i][j].setText(" ");
                contentPane.add(buttons[i][j]);
                buttons[i][j].setEnabled(true);   //unlocked
                setSpots();
            }
        }
    }
    
    public void warning(){
        Boolean liveThree= false;
        Boolean liveFour= false;
        Boolean deadFour= false;

        //vertical warning
        for(int i= 0, j= 5, k= 6; j< ver_map.length()+ 1; i++, j++, k++){
            if(ver_map.substring(i, j).equals(" ◉◉◉ ")|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals(" ◉ ◉◉ "))|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals(" ◉◉ ◉ "))||
            ver_map.substring(i, j).equals(" ◎◎◎ ")|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals(" ◎ ◎◎ "))|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals(" ◎◎ ◎ "))){
                liveThree= true;
            }

            if(k< ver_map.length()+ 1&& (ver_map.substring(i, k).equals(" ◉◉◉◉ ")|| ver_map.substring(i, k).equals(" ◎◎◎◎ "))){
                liveFour= true;
            }

            if(ver_map.substring(i, j).equals("◉ ◉◉◉")|| ver_map.substring(i, j).equals("◉◉ ◉◉")|| ver_map.substring(i, j).equals("◉◉◉ ◉")|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals("◎◉◉◉◉ "))|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals(" ◉◉◉◉◎"))||
            ver_map.substring(i, j).equals("◎ ◎◎◎")|| ver_map.substring(i, j).equals("◎◎ ◎◎")|| ver_map.substring(i, j).equals("◎◎◎ ◎")|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals("◉◎◎◎◎ "))|| (k< ver_map.length()+ 1&& ver_map.substring(i, k).equals(" ◎◎◎◎◉"))){
                deadFour= true;
            }
        }

        //horizontal warning
        for(int i= 0, j= 5, k= 6; j< hor_map.length(); i++, j++, k++){
            if(hor_map.substring(i, j).equals(" ◉◉◉ ")|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals(" ◉ ◉◉ "))|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals(" ◉◉ ◉ "))||
            hor_map.substring(i, j).equals(" ◎◎◎ ")|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals(" ◎ ◎◎ "))|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals(" ◎◎ ◎ "))){
                liveThree= true;
            }
            else if(k< ver_map.length()+ 1&& (hor_map.substring(i, k).equals(" ◉◉◉◉ ")|| hor_map.substring(i, k).equals(" ◎◎◎◎ "))){
                liveFour= true;
            }
            else if(hor_map.substring(i, j).equals("◉ ◉◉◉")|| hor_map.substring(i, j).equals("◉◉ ◉◉")|| hor_map.substring(i, j).equals("◉◉◉ ◉")|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals("◎◉◉◉◉ "))|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals(" ◉◉◉◉◎"))||
            hor_map.substring(i, j).equals("◎ ◎◎◎")|| hor_map.substring(i, j).equals("◎◎ ◎◎")|| hor_map.substring(i, j).equals("◎◎◎ ◎")|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals("◉◎◎◎◎ "))|| (k< ver_map.length()+ 1&& hor_map.substring(i, k).equals(" ◎◎◎◎◉"))){
                deadFour= true;
            }
        }

        //leftdown warning
        for(int i= 0, j= 5, k= 6; j< ld_map.length(); i++, j++, k++){
            if(ld_map.substring(i, j).equals(" ◉◉◉ ")|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals(" ◉ ◉◉ "))|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals(" ◉◉ ◉ "))||
            ld_map.substring(i, j).equals(" ◎◎◎ ")|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals(" ◎ ◎◎ "))|| (ld_map.substring(i, k).equals(" ◎◎ ◎ "))){
                liveThree= true;
            }
            else if(k< ver_map.length()+ 1&& (ld_map.substring(i, k).equals(" ◉◉◉◉ ")|| ld_map.substring(i, k).equals(" ◎◎◎◎ "))){
                liveFour= true;
            }
            else if(ld_map.substring(i, j).equals("◉ ◉◉◉")|| ld_map.substring(i, j).equals("◉◉ ◉◉")|| ld_map.substring(i, j).equals("◉◉◉ ◉")|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals("◎◉◉◉◉ "))|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals(" ◉◉◉◉◎"))||
            ld_map.substring(i, j).equals("◎ ◎◎◎")|| ld_map.substring(i, j).equals("◎◎ ◎◎")|| ld_map.substring(i, j).equals("◎◎◎ ◎")|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals("◉◎◎◎◎ "))|| (k< ver_map.length()+ 1&& ld_map.substring(i, k).equals(" ◎◎◎◎◉"))){
                deadFour= true;
            }
        }

        //rightdown warning
        for(int i= 0, j= 5, k= 6; j< rd_map.length(); i++, j++, k++){
            if(rd_map.substring(i, j).equals(" ◉◉◉ ")|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals(" ◉ ◉◉ "))|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals(" ◉◉ ◉ "))||
            rd_map.substring(i, j).equals(" ◎◎◎ ")|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals(" ◎ ◎◎ "))|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals(" ◎◎ ◎ "))){
                liveThree= true;
            }
            else if(k< ver_map.length()+ 1&& (rd_map.substring(i, k).equals(" ◉◉◉◉ ")|| rd_map.substring(i, k).equals(" ◎◎◎◎ "))){
                liveFour= true;
            }
            else if(rd_map.substring(i, j).equals("◉ ◉◉◉")|| rd_map.substring(i, j).equals("◉◉ ◉◉")|| rd_map.substring(i, j).equals("◉◉◉ ◉")|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals("◎◉◉◉◉ "))|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals(" ◉◉◉◉◎"))||
            rd_map.substring(i, j).equals("◎ ◎◎◎")|| rd_map.substring(i, j).equals("◎◎ ◎◎")|| rd_map.substring(i, j).equals("◎◎◎ ◎")|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals("◉◎◎◎◎ "))|| (k< ver_map.length()+ 1&& rd_map.substring(i, k).equals(" ◎◎◎◎◉"))){
                deadFour= true;
            }
        }

        if(liveFour){
            textArea.append("Warning : Live Four!\n");
        }
        else if(deadFour){
            textArea.append("Warning : Dead Four!\n");
        }
        else if(liveThree){
            textArea.append("Warning : Live Three!\n");
        }
    }

    public Boolean judge(JButton target){
        int ver_cnt= 1;
        int hor_cnt= 1;
        int ld_cnt= 1;
        int rd_cnt= 1;

        String ver_prev= " ";
        String hor_prev= " ";
        String ld_prev= " ";
        String rd_prev= " ";

        hintDialog.add(new Label(""));

        for(int i= 0; i< 19; i++){
            for(int j= 0; j< 19; j++){
                if(target== buttons[i][j]){
                    for(int i_dec= i- 4, j_inc= j+ 4, j_dec= j- 4; i_dec< i+ 5; i_dec++, j_dec++, j_inc--){

                        //vertical judgement
                        if(i_dec> 0&& i_dec< 19){
                            if(ver_prev== buttons[i_dec][j].getText()&& ver_prev!= " "){
                                ver_cnt++;
                            }
                            else{
                                ver_cnt= 1;
                                ver_prev= buttons[i_dec][j].getText();
                            }
                            ver_map+= buttons[i_dec][j].getText();
                        }

                        //horizontal judgement
                        if(j_dec> 0&& j_dec< 19){
                            if(hor_prev== buttons[i][j_dec].getText()&& hor_prev!= " "){
                                hor_cnt++;
                            }
                            else{
                                hor_cnt= 1;
                                hor_prev= buttons[i][j_dec].getText();
                            }
                            hor_map+= buttons[i][j_dec].getText();
                        }

                        //leftdown judgement
                        if(i_dec> 0&& i_dec< 19&& j_dec> 0&& j_dec< 19){
                            if(ld_prev== buttons[i_dec][j_dec].getText()&& ld_prev!= " "){
                                ld_cnt++;
                            }
                            else{
                                ld_cnt= 1;
                                ld_prev= buttons[i_dec][j_dec].getText();
                            }
                            ld_map+= buttons[i_dec][j_dec].getText();
                        }

                        //rightdown judgement
                        if(i_dec> 0&& i_dec< 19&& j_inc> 0&& j_inc< 19){
                            if(rd_prev== buttons[i_dec][j_inc].getText()&& rd_prev!= " "){
                                rd_cnt++;
                            }
                            else{
                                rd_cnt= 1;
                                rd_prev= buttons[i_dec][j_inc].getText();
                            }
                            rd_map+= buttons[i_dec][j_inc].getText();
                        }

                        if(ver_cnt== 5){
                            winChess= ver_prev;
                            return true;
                        }
                        else if(hor_cnt== 5){
                            winChess= hor_prev;
                            return true;
                        }
                        else if(ld_cnt== 5){
                            winChess= ld_prev;
                            return true;
                        }
                        else if(rd_cnt== 5){
                            winChess= rd_prev;
                            return true;
                        }
                    }
                }
            }
        }

        warning();

        return false;
    }

    class Menu implements ActionListener{
        public void actionPerformed(ActionEvent event){
            if(event.getSource()== jMenuItem){
                resetButtons();
            }
            else if(event.getSource()== okButton){
                resetButtons();
                dialog.setVisible(false);
            }
            else if(event.getSource()== hint){
                hintDialog.add(scrollText);
                hintDialog.setVisible(true);
            }
            else if(event.getSource()== yellow){
                contentPane.setBackground(Color.YELLOW);
            }
            else if(event.getSource()== blue){
                contentPane.setBackground(Color.BLUE);
            }
            else if(event.getSource()== red){
                contentPane.setBackground(Color.RED);
            }
            else if(event.getSource()== green){
                contentPane.setBackground(Color.GREEN);
            }
            else if(event.getSource()== pink){
                contentPane.setBackground(Color.PINK);
            }
            else if(event.getSource()== ORANGE){
                contentPane.setBackground(Color.ORANGE);
            }
            else if(event.getSource()== white){
                contentPane.setBackground(Color.WHITE);
            }
        }
    }

    class PutChess implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JButton button= (JButton) event.getSource();
            button.setText(chess);

            ver_map= "";
            hor_map= "";
            ld_map= "";
            rd_map= "";
    
            if(chess== "◉"){
                chess= "◎";
            }
            else{
                chess= "◉";
            }
    
            button.setEnabled(false);    //locked
    
            if(judge(button)){
                setDialog();
            }
    
            return;
        }
    }


    public static void main(String[] args){
        Backgammon backgammon= new Backgammon("Backgammon");
    }
}
